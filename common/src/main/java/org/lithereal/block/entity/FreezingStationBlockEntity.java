package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModBlocks;
import org.lithereal.recipe.FreezingStationRecipe;
import org.lithereal.recipe.ModRecipes;
import org.lithereal.screen.FreezingStationMenu;
import org.lithereal.util.CommonUtils;

import java.util.Optional;

public class FreezingStationBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;
    protected int coldness = 0;

    public FreezingStationBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getFreezingStationBlockEntity(), pos, state);
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
        nbt.putInt("freezing_station.progress", progress);
        nbt.putInt("freezing_station.max_progress", maxProgress);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        loadItems(nbt, provider);
        progress = nbt.getInt("freezing_station.progress");
        maxProgress = nbt.getInt("freezing_station.max_progress");
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
        if (block == ModBlocks.FROZEN_LITHERITE_BLOCK.get()) cooling += 2;

        return cooling;
    }

    private static void craftItem(FreezingStationBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        Optional<RecipeHolder<FreezingStationRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FREEZING_TYPE.get(), inventory, level);

        if(hasRecipe(pEntity)) {
            ItemStack resultItem = recipe.get().value().getResultItem(level.registryAccess());
            ItemStack outputItem = resultItem.copy();
            outputItem.setCount(pEntity.getItem(2).getCount() + resultItem.getCount());

            pEntity.removeItem(0, recipe.get().value().cooler().getItems()[0].getCount());
            pEntity.removeItem(1, recipe.get().value().crystal().getItems()[0].getCount());
            pEntity.setItem(2, outputItem);

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(FreezingStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++)
            inventory.setItem(i, entity.getItem(i));

        Optional<RecipeHolder<FreezingStationRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FREEZING_TYPE.get(), inventory, level);
        if (entity.progress == 0)
            entity.maxProgress = recipe.map(freezingStationRecipeRecipeHolder -> freezingStationRecipeRecipeHolder.value().maxProgress()).orElse(200);

        return getCoolingPower(entity) > 0 && recipe.isPresent() && canInsertAmountIntoOutput(inventory) &&
                canInsertItemIntoOutput(inventory, recipe.get().value().getResultItem(level.registryAccess()));
    }
}
