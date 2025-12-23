package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.FireCrucibleBlock;
import org.lithereal.client.gui.screens.inventory.ElectricCrucibleMenu;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.data.recipes.ContainerRecipeInput;
import org.lithereal.data.recipes.FireCrucibleRecipe;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.util.ether.EtherEnergyAbsorber;
import org.lithereal.util.ether.IEnergyUser;
import org.lithereal.util.ether.IEnergyUserProvider;

import java.util.Optional;

public class ElectricCrucibleBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, IEnergyUserProvider {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;
    protected FireCrucibleBlockEntity.HeatState heatState = FireCrucibleBlockEntity.HeatState.UNLIT;
    protected boolean isOn = true;

    private final EtherEnergyAbsorber energyAbsorber = new EtherEnergyAbsorber(100);

    public ElectricCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getElectricCrucibleBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ElectricCrucibleBlockEntity.this.progress;
                    case 1 -> ElectricCrucibleBlockEntity.this.maxProgress;
                    case 2 -> ElectricCrucibleBlockEntity.this.heatState.heat;
                    case 3 -> ElectricCrucibleBlockEntity.this.energyAbsorber.oldEnergy;
                    case 4 -> ElectricCrucibleBlockEntity.this.energyAbsorber.remainingEnergy;
                    case 5 -> ElectricCrucibleBlockEntity.this.isOn ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ElectricCrucibleBlockEntity.this.progress = value;
                    case 1 -> ElectricCrucibleBlockEntity.this.maxProgress = value;
                    case 2 -> ElectricCrucibleBlockEntity.this.heatState = FireCrucibleBlockEntity.HeatState.fromHeat(value);
                    case 3 -> ElectricCrucibleBlockEntity.this.energyAbsorber.oldEnergy = value;
                    case 4 -> ElectricCrucibleBlockEntity.this.energyAbsorber.remainingEnergy = value;
                    case 5 -> ElectricCrucibleBlockEntity.this.isOn = value > 0;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Electric Crucible");
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    protected static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    protected static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ElectricCrucibleMenu(i, inventory, this);
    }

    public void toggleOn() {
        this.isOn = !this.isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        inventory.replaceAll(item -> items.get(inventory.indexOf(item)));
    }

    @Override
    public GetterAndSetter getOrSet() {
        return new GetterAndSetter(this::getItems, this::setItems);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(nbt, provider);
        saveItems(nbt, provider);
        saveEnergy(nbt, provider);
        nbt.putInt("electric_crucible.progress", progress);
        nbt.putInt("electric_crucible.max_progress", maxProgress);
        nbt.putInt("electric_crucible.heat_level", heatState.heat);
        nbt.putBoolean("electric_crucible.is_on", isOn);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        loadItems(nbt, provider);
        loadEnergy(nbt, provider);
        progress = nbt.getInt("electric_crucible.progress");
        maxProgress = nbt.getInt("electric_crucible.max_progress");
        heatState = FireCrucibleBlockEntity.HeatState.fromHeat(nbt.getInt("electric_crucible.heat_level"));
        isOn = nbt.getBoolean("electric_crucible.is_on");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ElectricCrucibleBlockEntity pEntity) {
        if (level.isClientSide()) {
            RandomSource randomSource = level.getRandom();
            if (hasRecipe(pEntity) && randomSource.nextBoolean()) {
                Vec3 center = Vec3.upFromBottomCenterOf(blockPos, 0.2);
                double xDir = (randomSource.nextDouble() - randomSource.nextDouble()) * 0.075;
                double zDir = (randomSource.nextDouble() - randomSource.nextDouble()) * 0.075;
                level.addParticle(ModParticles.LITHER_FIRE_FLAME.get(), center.x, center.y, center.z, xDir, 0.15, zDir);
            }
            return;
        }

        if (pEntity.isOn()) pEntity.energyAbsorber.tick(pEntity);
        if (pEntity.energyAbsorber.remainingEnergy == 0 || !pEntity.isOn()) {
            if (pEntity.heatState.isLit()) {
                pEntity.heatState = FireCrucibleBlockEntity.HeatState.UNLIT;
                level.setBlockAndUpdate(blockPos, blockState.setValue(FireCrucibleBlock.HEAT_STATE, pEntity.heatState));
            }
        } else if (!pEntity.heatState.isLit() && pEntity.isOn()) {
            pEntity.heatState = FireCrucibleBlockEntity.HeatState.BLUE_LIT;
            level.setBlockAndUpdate(blockPos, blockState.setValue(FireCrucibleBlock.HEAT_STATE, pEntity.heatState));
        }

        if (hasRecipe(pEntity)) {
            pEntity.progress += pEntity.heatState.heat;
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress)
                craftItem(pEntity);
        } else {
            if(pEntity.progress > 0) pEntity.progress--;
            setChanged(level, blockPos, blockState);
        }
    }

    protected static void craftItem(ElectricCrucibleBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        Optional<RecipeHolder<FireCrucibleRecipe>> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BURNING_TYPE.get(), new ContainerRecipeInput(inventory), level);

        Optional<RecipeHolder<SmeltingRecipe>> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(inventory.getItem(0)), level);

        ItemStack resultItem = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY));
        ItemStack outputItem = resultItem.copy();
        outputItem.setCount(pEntity.getItem(2).getCount() + outputItem.getCount());

        boolean shouldRemoveSecondary = crucibleRecipe.isPresent() && crucibleRecipe.get().value().secondary().isPresent();

        if(hasRecipe(pEntity) && !outputItem.isEmpty()) craftItem(pEntity, outputItem, shouldRemoveSecondary);
    }

    protected static void craftItem(ElectricCrucibleBlockEntity entity, ItemStack outputItem, boolean shouldRemoveSecondary) {
        entity.removeItem(0, 1);
        if (shouldRemoveSecondary) entity.removeItem(1, 1);
        entity.setItem(2, outputItem);

        entity.resetProgress();
    }

    protected static boolean hasRecipe(ElectricCrucibleBlockEntity entity) {
        if (!entity.isOn() || !entity.heatState.isLit()) return false;
        Level level = entity.getLevel();
        boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<RecipeHolder<FireCrucibleRecipe>> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BURNING_TYPE.get(), new ContainerRecipeInput(inventory), level);

        Optional<RecipeHolder<SmeltingRecipe>> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(inventory.getItem(0)), level);

        if (crucibleRecipe.isPresent() || furnaceRecipe.isPresent()) {
            ItemStack resultItem = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY));
            if (entity.progress == 0)
                entity.maxProgress = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().maxProgress()).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getCookingTime()).orElse(200));
            if (canInsertAmountIntoOutput(inventory) && canInsertItemIntoOutput(inventory, resultItem))
                hasRecipe = true;
        }

        return hasRecipe;
    }

    @Override
    public <B extends BlockEntity & IEnergyUserProvider> B asBlockEntity() {
        return (B) this;
    }

    @Override
    public IEnergyUser getEnergyUser() {
        return energyAbsorber;
    }

    @Override
    public TransferMode getTransferModeForDirection(Direction direction) {
        if (!isOn()) return TransferMode.NONE;
        return switch (direction) {
            case DOWN, NORTH, SOUTH, EAST, WEST -> TransferMode.INPUT_ONLY;
            default -> TransferMode.NONE;
        };
    }
}
