package org.lithereal.forge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.custom.FireCrucibleBlock;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.forge.block.custom.ForgeFireCrucibleBlock;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.item.ModItems;
import org.lithereal.recipe.FireCrucibleRecipe;

import java.util.Optional;

public class ForgeFireCrucibleBlockEntity extends FireCrucibleBlockEntity implements ImplementedInventory {
    private final ForgeFireCrucibleItemHandler itemHandler = new ForgeFireCrucibleItemHandler(4);

    private class ForgeFireCrucibleItemHandler extends ItemStackHandler {
        public ForgeFireCrucibleItemHandler(int size) {
            super(size);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        public NonNullList<ItemStack> getStacks() {
            return stacks;
        }
    }
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public ForgeFireCrucibleBlockEntity(BlockPos pos, BlockState state) {
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

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ForgeFireCrucibleBlockEntity pEntity) {
        if(level.isClientSide()) return;

        boolean hasSolidFuel = hasSolidFuel(pEntity);
        boolean isBlueFireBelow = level.getBlockState(blockPos.below()).getBlock() == ModBlocks.BLUE_FIRE.get();

        if (getFuelLevel(pEntity) > 0 && !isBlueFireBelow) {
            pEntity.fuelLevel--;
        } else {
            if (isBlueFireBelow) {
                pEntity.fuelLevel = 100;
                pEntity.maxFuel = 100;
                pEntity.heatLevel = 2;
                level.setBlockAndUpdate(blockPos, blockState.setValue(ForgeFireCrucibleBlock.BLUE_LIT, true));
            } else if (hasSolidFuel) {
                int fuel = ForgeHooks.getBurnTime(((Container) pEntity).getItem(1), null);
                pEntity.maxFuel = fuel;
                pEntity.fuelLevel = fuel;
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.heatLevel = 1;
                level.setBlockAndUpdate(blockPos, blockState.setValue(ForgeFireCrucibleBlock.LIT, true));
            } else {
                pEntity.maxFuel = 0;
                pEntity.fuelLevel = 0;
                pEntity.heatLevel = 0;
                level.setBlockAndUpdate(blockPos, blockState.setValue(ForgeFireCrucibleBlock.BLUE_LIT, false).setValue(FireCrucibleBlock.LIT, false));
            }
        }

        pEntity.setBucket(pEntity);

        if(hasRecipe(pEntity)) {
            pEntity.progress += pEntity.heatLevel;
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            if(pEntity.progress > 0) pEntity.progress--;
            setChanged(level, blockPos, blockState);
        }
    }

    protected static void craftItem(ForgeFireCrucibleBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<FireCrucibleRecipe> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(FireCrucibleRecipe.Type.INSTANCE, inventory, level);

        Optional<SmeltingRecipe> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, inventory, level);

        ItemStack resultItem = crucibleRecipe.isPresent() ? crucibleRecipe.get().getResultItem(level.registryAccess()) : furnaceRecipe.get().getResultItem(level.registryAccess());
        ItemStack outputItem = new ItemStack(resultItem.getItem(), pEntity.itemHandler.getStackInSlot(2).getCount() + resultItem.getCount());

        if(pEntity.hasBucket(pEntity)  && pEntity.itemHandler.getStackInSlot(0).is(ForgeItems.LITHERITE_CRYSTAL.get())) {
            outputItem = new ItemStack(ModItems.MOLTEN_LITHERITE_BUCKET.get(), pEntity.itemHandler.getStackInSlot(2).getCount() + 1);
            pEntity.itemHandler.extractItem(3, 1, false);
        }

        if(hasRecipe(pEntity)) craftItem(pEntity, resultItem, outputItem);
    }

    protected static void craftItem(ForgeFireCrucibleBlockEntity entity, ItemStack resultItem, ItemStack outputItem) {
        CompoundTag nbt = resultItem.getTag();
        if(nbt != null) {
            outputItem.setTag(nbt.copy());
        }

        entity.itemHandler.extractItem(0, 1, false);
        entity.itemHandler.setStackInSlot(2, outputItem);

        entity.resetProgress();
    }

    protected static boolean hasRecipe(ForgeFireCrucibleBlockEntity entity) {
        Level level = entity.getLevel();
        Boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FireCrucibleRecipe> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(FireCrucibleRecipe.Type.INSTANCE, inventory, level);

        Optional<SmeltingRecipe> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, inventory, level);

        if (crucibleRecipe.isPresent() || furnaceRecipe.isPresent()) {
            ItemStack resultItem = crucibleRecipe.isPresent() ? crucibleRecipe.get().getResultItem(level.registryAccess()) : furnaceRecipe.get().getResultItem(level.registryAccess());
            if(entity.hasBucket(entity) && entity.itemHandler.getStackInSlot(0).is(ForgeItems.LITHERITE_CRYSTAL.get())) {
                resultItem = new ItemStack(ModItems.MOLTEN_LITHERITE_BUCKET.get(), entity.itemHandler.getStackInSlot(2).getCount() + 1);
            }
            if (canInsertAmountIntoOutput(inventory) && canInsertItemIntoOutput(inventory, resultItem)) {
                hasRecipe = true;
            }
        }

        return hasRecipe;
    }

    protected static boolean hasSolidFuel(ForgeFireCrucibleBlockEntity entity) {
        ItemStack item = entity.itemHandler.getStackInSlot(1);
        return ForgeHooks.getBurnTime(item, RecipeType.SMELTING) > 0;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return itemHandler.getStacks();
    }
}
