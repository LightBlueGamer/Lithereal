package org.lithereal.block.entity;

import dev.architectury.hooks.item.ItemStackHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModStorageBlocks;
import org.lithereal.client.gui.screens.inventory.InfusementChamberMenu;
import org.lithereal.data.recipes.ContainerRecipeInput;
import org.lithereal.data.recipes.InfusementChamberRecipe;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.util.ether.EtherEnergyAbsorber;
import org.lithereal.util.ether.IEnergyUser;
import org.lithereal.util.ether.IEnergyUserProvider;

import java.util.Optional;
import java.util.Random;

import static org.lithereal.block.entity.FireCrucibleBlockEntity.canInsertItemInto;

public abstract class InfusementChamberBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, IEnergyUserProvider {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 6000;
    protected PowerState powerState = PowerState.UNPOWERED;
    protected int attachedFrozenBlocks = 0;
    protected int attachedBurningBlocks = 0;
    protected float basePower = 1.0f;
    protected float baseSuccessRate = 0.4f;
    protected float power = 1.0f;
    protected float successRate = 0.4f;
    protected ItemStack held = ItemStack.EMPTY;
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

    public PotionContents getStoredPotion() {
        return getItem(1).getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    public ItemStack getStoredItem() {
        return getItem(0);
    }

    private final EtherEnergyAbsorber energyAbsorber = new EtherEnergyAbsorber(100);

    @Override
    public IEnergyUser getEnergyUser() {
        return energyAbsorber;
    }

    @Override
    public TransferMode getTransferModeForDirection(Direction direction) {
        return TransferMode.BOTH;
    }

    @Override
    public <B extends BlockEntity & IEnergyUserProvider> B asBlockEntity() {
        return (B) this;
    }

    public InfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getInfusementChamberBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress;
                    case 2 -> InfusementChamberBlockEntity.this.powerState.id;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress = value;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress = value;
                    case 2 -> InfusementChamberBlockEntity.this.powerState = PowerState.fromId(value);
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Infusement Chamber");
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new InfusementChamberMenu(i, inventory, this);
    }

    public void setEmpowerments() {
        this.basePower = 1.0f;
        this.baseSuccessRate = 0.4f;
        int frozenBlocks = 0;
        int burningBlocks = 0;
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = this.getBlockPos().relative(direction);
            BlockState blockState = this.level.getBlockState(adjacentPos);
            if (blockState.is(ModStorageBlocks.FROZEN_LITHERITE_BLOCK.get())) {
                this.basePower -= 0.1f;
                this.baseSuccessRate += 0.15f;
                frozenBlocks++;
            } else if (blockState.is(ModStorageBlocks.BURNING_LITHERITE_BLOCK.get())) {
                this.basePower += 0.15f;
                this.baseSuccessRate -= 0.1f;
                burningBlocks++;
            }
        }
        this.attachedFrozenBlocks = frozenBlocks;
        this.attachedBurningBlocks = burningBlocks;
        int attached = getEnergyUser().getConnectionsThatCouldProvideEnergy(this);
        if (attached == 0) {
            this.power = this.basePower;
            this.successRate = this.baseSuccessRate;
            this.powerState = PowerState.fromSurrounding(new SurroundingBlocks(this.attachedFrozenBlocks, this.attachedBurningBlocks, attached));
            setChanged();
        }
    }

    @Override
    public void onSuccess(int remainingEnergy, IEnergyUser.TransferDirection direction) {
        this.power = this.basePower;
        this.successRate = this.baseSuccessRate;
        int attached = getEnergyUser().getConnectionsThatCouldProvideEnergy(this);
        this.power += 0.2f * attached;
        this.successRate += 0.2f * attached;
        this.powerState = PowerState.fromSurrounding(new SurroundingBlocks(this.attachedFrozenBlocks, this.attachedBurningBlocks, attached));
        setChanged();
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
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        saveItems(nbt, provider);
        saveEnergy(nbt, provider);
        nbt.putInt("infusement_chamber.progress", progress);
        nbt.putInt("infusement_chamber.max_progress", maxProgress);
        nbt.putInt("infusement_chamber.power_state", powerState.id);
        nbt.putFloat("infusement_chamber.power", power);
        nbt.putFloat("infusement_chamber.success_rate", successRate);
        basePower = -10;
        baseSuccessRate = -10;
        Tag tag = ItemStack.OPTIONAL_CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), held).getOrThrow();
        nbt.put("infusement_chamber.held_stack", tag);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        loadItems(nbt, provider);
        loadEnergy(nbt, provider);
        progress = nbt.getInt("infusement_chamber.progress");
        maxProgress = nbt.getInt("infusement_chamber.max_progress");
        powerState = PowerState.fromId(nbt.getInt("infusement_chamber.power_state"));
        power = nbt.getFloat("infusement_chamber.power");
        successRate = nbt.getFloat("infusement_chamber.success_rate");
        held = ItemStack.parseOptional(provider, nbt.getCompound("infusement_chamber.held_stack"));
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

    private static void craftItem(InfusementChamberBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            ItemStack item = pEntity.getItem(i);
            inventory.setItem(i, item);
        }

        PotionContents potion = pEntity.getItem(1).getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        Optional<RecipeHolder<InfusementChamberRecipe>> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.INFUSING_TYPE.get(), new ContainerRecipeInput(inventory), level);

        ItemStack outputItem = ItemStack.EMPTY;
        if (infusingRecipe.isPresent()) {
            ItemStack resultItem = infusingRecipe.get().value().getResultItem(level.registryAccess());
            resultItem.set(DataComponents.POTION_CONTENTS, potion);
            outputItem = resultItem.copy();
        }

        if(hasRecipe(pEntity) && !outputItem.isEmpty()) {
            craftItem(pEntity, outputItem);
            setChanged(level, pEntity.getBlockPos(), pEntity.getBlockState());
        }
    }

    private static void craftItem(InfusementChamberBlockEntity entity, ItemStack outputItem) {
        Random random = new Random();

        ItemStack originalBucket = entity.removeItem(0, 1);
        ItemStack bucketRemainder = ItemStackHooks.getCraftingRemainingItem(originalBucket);
        if (!bucketRemainder.isEmpty()) entity.setItem(0, bucketRemainder);
        ItemStack stack = entity.removeItem(1, 1);
        ItemStack remainder = ItemStackHooks.getCraftingRemainingItem(stack);
        if (remainder.isEmpty() && stack.is(Items.POTION)) remainder = Items.GLASS_BOTTLE.getDefaultInstance();
        if (entity.held.isEmpty() && !remainder.isEmpty())
            entity.held = remainder.copy();
        else if (!remainder.isEmpty()) entity.held.grow(1);
        if (entity.getItem(1).getCount() - 1 <= 0) {
            if (!entity.held.isEmpty()) entity.setItem(1, entity.held);
            entity.held = ItemStack.EMPTY;
        }
        if (random.nextFloat() < entity.baseSuccessRate) {
            ItemStack originalResultStack = entity.getItem(2);
            if (originalResultStack.isEmpty()) entity.setItem(2, outputItem);
            else originalResultStack.grow(outputItem.getCount());
        } else {
            boolean mobGriefingEnabled = entity.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            BlockPos blockPos = entity.getBlockPos();
            entity.level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 7.0f, mobGriefingEnabled, Level.ExplosionInteraction.TNT);
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(InfusementChamberBlockEntity entity) {
        Level level = entity.level;
        boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<RecipeHolder<InfusementChamberRecipe>> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.INFUSING_TYPE.get(), new ContainerRecipeInput(inventory), level);

        if (infusingRecipe.isPresent()) {
            ItemStack resultItem = infusingRecipe.map(infusementChamberRecipeHolder -> infusementChamberRecipeHolder.value().getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY);
            if (entity.progress == 0)
                entity.maxProgress = infusingRecipe.map(infusementChamberRecipeHolder -> infusementChamberRecipeHolder.value().maxProgress()).orElse(200);
            if (canInsertItemInto(2, inventory, resultItem))
                hasRecipe = true;
        }

        return hasRecipe;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, InfusementChamberBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if (pEntity.baseSuccessRate == -10 || pEntity.basePower == -10) pEntity.setEmpowerments();

        pEntity.getEnergyUser().tick(pEntity);

        if (pEntity.getItem(1).isEmpty() && !pEntity.held.isEmpty()) {
            pEntity.setItem(1, pEntity.held);
            pEntity.held = ItemStack.EMPTY;
        }

        if(hasRecipe(pEntity)) {
            pEntity.progress += (int) (10 * pEntity.power);
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress)
                craftItem(pEntity);
        } else {
            if(pEntity.progress > 0) pEntity.progress -= (int) (10 * pEntity.power);
            setChanged(level, blockPos, blockState);
        }
    }

    public enum PowerState {
        UNPOWERED(0),
        FROZEN(1),
        BURNING(2),
        CHARGED(3);
        public static final PowerState[] VALUES = new PowerState[]{UNPOWERED, FROZEN, BURNING, CHARGED};
        public final int id;

        PowerState(int id) {
            this.id = id;
        }

        public static PowerState fromId(int id) {
            return VALUES[id];
        }

        public static PowerState fromSurrounding(SurroundingBlocks surrounding) {
            return switch (surrounding) {
                case SurroundingBlocks ignored when surrounding.isGreatestCharged() -> CHARGED;
                case SurroundingBlocks ignored when surrounding.isGreatestFrozen() -> FROZEN;
                case SurroundingBlocks ignored when surrounding.isGreatestBurning() -> BURNING;
                default -> UNPOWERED;
            };
        }
    }

    public record SurroundingBlocks(int frozen, int burning, int charged) {
        public boolean isGreatestCharged() {
            return charged >= frozen && charged >= burning && charged > 0;
        }
        public boolean isGreatestFrozen() {
            return frozen >= burning && frozen >= charged && frozen > 0;
        }
        public boolean isGreatestBurning() {
            return burning >= frozen && burning >= charged && burning > 0;
        }

    }

}
