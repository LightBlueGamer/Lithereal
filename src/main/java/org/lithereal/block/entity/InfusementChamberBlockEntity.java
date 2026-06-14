package org.lithereal.block.entity;

import dev.architectury.hooks.item.ItemStackHooks;
//? fabric {
/*import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
*///?}
import net.minecraft.world.item.ItemStackTemplate;
//? neoforge {
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.lithereal.neoforge.util.ImplementedItemHandler;
import org.lithereal.neoforge.util.NeoForgeInventory;
 //?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.lithereal.block.InfusementChamberBlock;
import org.lithereal.block.ModStorageBlocks;
import org.lithereal.client.gui.screens.inventory.InfusementChamberMenu;
import org.lithereal.data.recipes.ContainerRecipeInput;
import org.lithereal.data.recipes.InfusementChamberRecipe;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.util.PotionStorage;
import org.lithereal.util.ether.EtherEnergyAbsorber;
import org.lithereal.util.ether.IEnergyUser;
import org.lithereal.util.ether.IEnergyUserProvider;

import java.util.Optional;
//? neoforge {
import java.util.concurrent.atomic.AtomicInteger;
//?}

import static org.lithereal.block.entity.FireCrucibleBlockEntity.canInsertItemInto;

//? fabric {
/*public class InfusementChamberBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, IEnergyUserProvider, PotionStorage, ExtendedMenuProvider<BlockPos> {
*///?}
//? neoforge {
public class InfusementChamberBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, IEnergyUserProvider, PotionStorage, NeoForgeInventory {
//?}
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

    @Override
    public void setPotion(PotionContents potion) {
        ItemStack potionStack = getItem(1);
        if (!potionStack.isEmpty())
            potionStack.set(DataComponents.POTION_CONTENTS, potion);
    }

    @Override
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
        super(ModBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY.get(), pos, state);
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

    //? fabric {
    /*@Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        super.setChanged();
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        inventory.replaceAll(item -> items.get(inventory.indexOf(item)));
    }
    *///?}

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
    public GetterAndSetter getOrSet() {
        return new GetterAndSetter(this::getItems, this::setItems);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        saveItems(valueOutput);
        saveEnergy(valueOutput);
        valueOutput.putInt("infusement_chamber.progress", progress);
        valueOutput.putInt("infusement_chamber.max_progress", maxProgress);
        valueOutput.putInt("infusement_chamber.power_state", powerState.id);
        valueOutput.putFloat("infusement_chamber.power", power);
        valueOutput.putFloat("infusement_chamber.success_rate", successRate);
        basePower = -10;
        baseSuccessRate = -10;
        valueOutput.store("infusement_chamber.held_stack", ItemStack.OPTIONAL_CODEC, held);
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        loadItems(valueInput);
        loadEnergy(valueInput);
        progress = valueInput.getIntOr("infusement_chamber.progress", 0);
        maxProgress = valueInput.getIntOr("infusement_chamber.max_progress", 200);
        powerState = PowerState.fromId(valueInput.getIntOr("infusement_chamber.power_state", 0));
        power = valueInput.getFloatOr("infusement_chamber.power", 1);
        successRate = valueInput.getFloatOr("infusement_chamber.success_rate", 0.4F);
        held = valueInput.read("infusement_chamber.held_stack", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
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

        ContainerRecipeInput infusingInput = new ContainerRecipeInput(inventory);
        Optional<RecipeHolder<InfusementChamberRecipe>> infusingRecipe = ((ServerLevel)level).recipeAccess()
                .getRecipeFor(ModRecipes.INFUSING_TYPE.get(), infusingInput, level);

        ItemStack outputItem = ItemStack.EMPTY;
        if (infusingRecipe.isPresent()) {
            ItemStack resultItem = infusingRecipe.get().value().assemble(infusingInput);
            resultItem.set(DataComponents.POTION_CONTENTS, potion);
            outputItem = resultItem.copy();
        }

        if(hasRecipe(pEntity) && !outputItem.isEmpty()) {
            craftItem(pEntity, outputItem, level);
            setChanged(level, pEntity.getBlockPos(), pEntity.getBlockState());
        }
    }

    private static void craftItem(InfusementChamberBlockEntity entity, ItemStack outputItem, Level level) {
        entity.removeItemWithRemainder(0, 1);
        ItemStack stack = entity.removeItem(1, 1);
        ItemStack remainder = Optional.ofNullable(stack.getCraftingRemainder()).map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
        if (remainder.isEmpty() && stack.is(Items.POTION)) remainder = Items.GLASS_BOTTLE.getDefaultInstance();
        if (entity.held.isEmpty() && !remainder.isEmpty())
            entity.held = remainder;
        else if (!remainder.isEmpty()) entity.held.grow(1);
        if (entity.getItem(1).getCount() <= 0) {
            if (!entity.held.isEmpty()) entity.setItem(1, entity.held);
            entity.held = ItemStack.EMPTY;
        }
        if (level.getRandom().nextFloat() < entity.successRate) {
            ItemStack originalResultStack = entity.getItem(2);
            if (originalResultStack.isEmpty()) entity.setItem(2, outputItem);
            else originalResultStack.grow(outputItem.getCount());
        } else {
            BlockPos blockPos = entity.getBlockPos();
            level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 7.0f, true, Level.ExplosionInteraction.MOB);
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

        ContainerRecipeInput infusingInput = new ContainerRecipeInput(inventory);
        Optional<RecipeHolder<InfusementChamberRecipe>> infusingRecipe = ((ServerLevel) level).recipeAccess().getRecipeFor(ModRecipes.INFUSING_TYPE.get(), infusingInput, level);

        if (infusingRecipe.isPresent()) {
            ItemStack resultItem = infusingRecipe.map(infusementChamberRecipeHolder -> infusementChamberRecipeHolder.value().assemble(infusingInput)).orElse(ItemStack.EMPTY);
            if (entity.progress == 0)
                entity.maxProgress = infusingRecipe.map(infusementChamberRecipeHolder -> infusementChamberRecipeHolder.value().maxProgress()).orElse(200);
            if (canInsertItemInto(2, inventory, resultItem))
                hasRecipe = true;
        }

        return hasRecipe;
    }

    /**
     * Writes additional server -&gt; client screen opening data to the buffer.
     *
     * @param player the player that is opening the screen
     * @return the screen opening data
     */
    //? fabric {
    /*@Override
    public BlockPos getScreenOpeningData(@NonNull ServerPlayer player) {
        return worldPosition;
    }
    *///?}

    //? neoforge {
    private final ImplementedItemHandler itemHandler = new ImplementedItemHandler(3, this);

    @Override
    public NonNullList<ItemStack> getItems() {
        return itemHandler.getStacks();
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        AtomicInteger index = new AtomicInteger();
        items.forEach(itemStack -> {
            if (index.get() > getHandler().size())
                return;
            itemHandler.set(index.getAndIncrement(), ItemResource.of(itemStack), itemStack.getCount());
        });
    }

    @Override
    public ImplementedItemHandler getHandler() {
        return itemHandler;
    }

    @Override
    public void saveItems(@NotNull ValueOutput valueOutput) {
        getHandler().serialize(valueOutput.child("Items"));
    }

    @Override
    public void loadItems(@NotNull ValueInput valueInput) {
        getHandler().deserialize(valueInput.childOrEmpty("Items"));
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return itemHandler.setStackInSlotNoUpdate(slot, ItemStack.EMPTY);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int count) {
        ItemStack originalStack = itemHandler.getStacks().get(slot);
        itemHandler.set(slot, itemHandler.getResourceFrom(originalStack), originalStack.getCount() - count);
        return originalStack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        itemHandler.set(slot, ItemResource.of(stack), stack.getCount());
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return itemHandler.getResource(slot).toStack(itemHandler.getAmountAsInt(slot));
    }

    @Override
    public void clearContent() {
        for (int index = 0; index < itemHandler.size(); index++) {
            removeItemNoUpdate(index);
        }
    }

    @Override
    public int getContainerSize() {
        return itemHandler.size();
    }
    //?}

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, InfusementChamberBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if (pEntity.baseSuccessRate == -10 || pEntity.basePower == -10) pEntity.setEmpowerments();

        pEntity.getEnergyUser().tick(pEntity);

        if (pEntity.getItem(1).isEmpty() && !pEntity.held.isEmpty()) {
            pEntity.setItem(1, pEntity.held);
            pEntity.held = ItemStack.EMPTY;
        }
        boolean nowHoldsInside = !pEntity.getStoredItem().isEmpty();
        if (blockState.hasProperty(InfusementChamberBlock.SECONDARY_FILLED) && blockState.getValue(InfusementChamberBlock.SECONDARY_FILLED) != nowHoldsInside) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(InfusementChamberBlock.SECONDARY_FILLED, nowHoldsInside));
        }
        boolean nowHoldsPotionInside = pEntity.getStoredPotion() != PotionContents.EMPTY;
        if (blockState.hasProperty(InfusementChamberBlock.PRIMARY_FILLED) && blockState.getValue(InfusementChamberBlock.PRIMARY_FILLED) != nowHoldsPotionInside) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(InfusementChamberBlock.PRIMARY_FILLED, nowHoldsPotionInside));
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
