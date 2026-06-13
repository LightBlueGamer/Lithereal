package org.lithereal.block.entity;

//? fabric {
/*import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
*///?}
//? neoforge {
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.lithereal.neoforge.util.ImplementedItemHandler;
import org.lithereal.neoforge.util.NeoForgeInventory;
 //?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.lithereal.block.ModStorageBlocks;
import org.lithereal.client.gui.screens.inventory.FreezingStationMenu;
import org.lithereal.data.recipes.ContainerRecipeInput;
import org.lithereal.data.recipes.FreezingStationRecipe;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.util.CommonUtils;

import java.util.Optional;
//? neoforge {
import java.util.concurrent.atomic.AtomicInteger;

import static net.neoforged.neoforge.transfer.transaction.Transaction.openRoot;
//?}

//? fabric {
/*public class FreezingStationBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, ExtendedMenuProvider<BlockPos> {
*///?}
//? neoforge {
public class FreezingStationBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, NeoForgeInventory {
//?}
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;
    protected int coldness = 0;

    public FreezingStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FREEZING_STATION_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FreezingStationBlockEntity.this.progress;
                    case 1 -> FreezingStationBlockEntity.this.maxProgress;
                    case 2 -> FreezingStationBlockEntity.this.coldness;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FreezingStationBlockEntity.this.progress = value;
                    case 1 -> FreezingStationBlockEntity.this.maxProgress = value;
                    case 2 -> FreezingStationBlockEntity.this.coldness = value;
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
        return Component.literal("Freezing Station");
    }

    //? fabric {
    /*@Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
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

    protected static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    protected static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FreezingStationMenu(i, inventory, this);
    }

    @Override
    public GetterAndSetter getOrSet() {
        return new GetterAndSetter(this::getItems, this::setItems);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        saveItems(valueOutput);
        valueOutput.putInt("freezing_station.progress", progress);
        valueOutput.putInt("freezing_station.max_progress", maxProgress);
    }

    @Override
    public void loadAdditional(@NotNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        loadItems(valueInput);
        progress = valueInput.getIntOr("freezing_station.progress", 0);
        maxProgress = valueInput.getIntOr("freezing_station.max_progress", 200);
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

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FreezingStationBlockEntity pEntity) {
        if(level.isClientSide()) return;

        if(hasRecipe(pEntity)) {
            pEntity.progress += getCoolingPower(pEntity);
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            if(pEntity.progress > 0) pEntity.heat(1);
            setChanged(level, blockPos, blockState);
        }
    }

    private void heat(int multiplier) {
        if(this.progress > 0) this.progress -= multiplier;
    }

    private static int getCoolingPower(FreezingStationBlockEntity entity) {
        Level level = entity.getLevel();
        int cooling = 0;

        Block block = level.getBlockState(entity.getBlockPos().below()).getBlock();
        cooling += getBlockCoolingPower(entity, block);

        entity.coldness = cooling;
        return cooling;
    }

    private static int getBlockCoolingPower(FreezingStationBlockEntity entity, Block block) {
        int cooling = 0;
        if (CommonUtils.isAnyOf(block, Blocks.PACKED_ICE, Blocks.BLUE_ICE)) cooling += 1;
        if (block == ModStorageBlocks.FROZEN_LITHERITE_BLOCK.get()) cooling += 2;

        return cooling;
    }

    private static void craftItem(FreezingStationBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        ContainerRecipeInput freezingInput = new ContainerRecipeInput(inventory);
        Optional<RecipeHolder<FreezingStationRecipe>> recipe = ((ServerLevel)level).recipeAccess()
                .getRecipeFor(ModRecipes.FREEZING_TYPE.get(), freezingInput, level);

        if(hasRecipe(pEntity)) {
            ItemStack resultItem = recipe.get().value().assemble(freezingInput);
            ItemStack outputItem = resultItem.copy();
            outputItem.setCount(pEntity.getItem(2).getCount() + resultItem.getCount());

            pEntity.removeItem(0, 1);
            pEntity.removeItem(1, 1);
            pEntity.setItem(2, outputItem);

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(FreezingStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++)
            inventory.setItem(i, entity.getItem(i));

        ContainerRecipeInput freezingInput = new ContainerRecipeInput(inventory);
        Optional<RecipeHolder<FreezingStationRecipe>> recipe = ((ServerLevel)level).recipeAccess()
                .getRecipeFor(ModRecipes.FREEZING_TYPE.get(), freezingInput, level);
        if (entity.progress == 0)
            entity.maxProgress = recipe.map(freezingStationRecipeRecipeHolder -> freezingStationRecipeRecipeHolder.value().maxProgress()).orElse(200);

        return getCoolingPower(entity) > 0 && recipe.isPresent() && canInsertAmountIntoOutput(inventory) &&
                canInsertItemIntoOutput(inventory, recipe.get().value().assemble(freezingInput));
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
        ItemResource resource = itemHandler.getResource(slot);
        int oldCount = itemHandler.getAmountAsInt(slot);
        try (var tx = openRoot()) {
            itemHandler.extract(slot, resource, count, tx);
        }
        return resource.toStack(oldCount);
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
}
