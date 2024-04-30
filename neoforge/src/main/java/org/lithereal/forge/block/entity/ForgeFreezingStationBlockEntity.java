package org.lithereal.forge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.FreezingStationBlockEntity;
import org.lithereal.forge.screen.ForgeFreezingStationMenu;
import org.lithereal.recipe.FreezingStationRecipe;

import java.util.Optional;

public class ForgeFreezingStationBlockEntity extends FreezingStationBlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public ForgeFreezingStationBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ForgeFreezingStationMenu(id, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ForgeFreezingStationBlockEntity pEntity) {
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

    private static int getCoolingPower(ForgeFreezingStationBlockEntity entity) {
        Level level = entity.getLevel();
        int cooling = 0;

        Block block = level.getBlockState(entity.getBlockPos().below()).getBlock();
        cooling += getBlockCoolingPower(entity, block);

        return cooling;
    }

    private static int getBlockCoolingPower(ForgeFreezingStationBlockEntity entity, Block block) {
        int cooling = 0;
        if(block == Blocks.PACKED_ICE) cooling += 1;
        if(block == ModBlocks.FROZEN_LITHERITE_BLOCK.get()) cooling += 2;

        entity.coldness = cooling;
        return cooling;
    }

    private static void craftItem(ForgeFreezingStationBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<FreezingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FreezingStationRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            ItemStack resultItem = recipe.get().getResultItem(level.registryAccess());
            ItemStack outputItem = new ItemStack(resultItem.getItem(), pEntity.itemHandler.getStackInSlot(2).getCount() + resultItem.getCount());

            CompoundTag nbt = resultItem.getTag();
            if(nbt != null) {
                outputItem.setTag(nbt.copy());
            }

            pEntity.itemHandler.extractItem(0, recipe.get().recipeItems.get(0).getItems()[0].getCount(), false);
            pEntity.itemHandler.extractItem(1, recipe.get().recipeItems.get(1).getItems()[0].getCount(), false);
            pEntity.itemHandler.setStackInSlot(2, outputItem);

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(ForgeFreezingStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FreezingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FreezingStationRecipe.Type.INSTANCE, inventory, level);

        return getCoolingPower(entity) > 0 && recipe.isPresent() && canInsertAmountIntoOutput(inventory) &&
                canInsertItemIntoOutput(inventory, recipe.get().getResultItem(level.registryAccess()));
    }
}
