package org.litecraft.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;
import org.litecraft.lithereal.recipe.FireCrucibleRecipe;
import org.litecraft.lithereal.recipe.FreezingStationRecipe;
import org.litecraft.lithereal.screen.FireCrucibleMenu;

import java.util.Optional;

public class FireCrucibleBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 30000;
    private int hasHeat = 0;

    public FireCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_CRUCIBLE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FireCrucibleBlockEntity.this.progress;
                    case 1 -> FireCrucibleBlockEntity.this.maxProgress;
                    case 2 -> FireCrucibleBlockEntity.this.hasHeat;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FireCrucibleBlockEntity.this.progress = value;
                    case 1 -> FireCrucibleBlockEntity.this.maxProgress = value;
                    case 2 -> FireCrucibleBlockEntity.this.hasHeat = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fire Crucible");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FireCrucibleMenu(id, inventory, this, this.data);
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

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FireCrucibleBlockEntity pEntity) {
        if(level.isClientSide()) return;

        pEntity.hasHeat = getHeatingPower(pEntity);

        if(hasRecipe(pEntity)) {
            pEntity.progress += getHeatingPower(pEntity);
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.cooldown(750);
            setChanged(level, blockPos, blockState);
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void cooldown(int multiplier) {
        if(this.progress > 0) this.progress -= multiplier;
    }

    private static void craftItem(FireCrucibleBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<FireCrucibleRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FireCrucibleRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(0, recipe.get().recipeItems.get(0).getItems()[0].getCount(), false);
            pEntity.itemHandler.setStackInSlot(1, new ItemStack(recipe.get().getResultItem(level.registryAccess()).getItem(),
                    pEntity.itemHandler.getStackInSlot(1).getCount() + recipe.get().getResultItem(level.registryAccess()).getCount()));

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(FireCrucibleBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FireCrucibleRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FireCrucibleRecipe.Type.INSTANCE, inventory, level);

        return getHeatingPower(entity) > 0 && recipe.isPresent() && canInsertAmountIntoOutput(inventory) &&
                canInsertItemIntoOutput(inventory, recipe.get().getResultItem(level.registryAccess()));
    }

    private static int getHeatingPower(FireCrucibleBlockEntity entity) {
        Level level = entity.level;
        int heating = 0;

        Block above = level.getBlockState(entity.worldPosition.above()).getBlock();
        Block below = level.getBlockState(entity.worldPosition.below()).getBlock();
        Block north = level.getBlockState(entity.worldPosition.north()).getBlock();
        Block east = level.getBlockState(entity.worldPosition.east()).getBlock();
        Block south = level.getBlockState(entity.worldPosition.south()).getBlock();
        Block west = level.getBlockState(entity.worldPosition.west()).getBlock();

        if(level.isDay() && !level.isRaining() && !level.isThundering()) heating += 25;

        heating += getBlockHeatingPower(entity, above);
        heating += getBlockHeatingPower(entity, below);
        heating += getBlockHeatingPower(entity, north);
        heating += getBlockHeatingPower(entity, east);
        heating += getBlockHeatingPower(entity, south);
        heating += getBlockHeatingPower(entity, west);

        return heating;
    }

    private static int getBlockHeatingPower(FireCrucibleBlockEntity entity, Block block) {
        Level level = entity.level;
        int heating = 0;
        if(block == Blocks.FIRE) heating += 1;
        if(block == Blocks.MAGMA_BLOCK) heating += 9;
        if(block == Blocks.LAVA) heating += 81;
        if(block == ModBlocks.HEATED_LITHERITE_BLOCK.get()) heating += 240;

        return heating;
    }

    private static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(1).getItem() == itemStack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
    }

}
