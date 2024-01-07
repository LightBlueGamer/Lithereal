package org.lithereal.fabric.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.FreezingStationBlockEntity;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.fabric.screen.FabricFreezingStationMenu;
import org.lithereal.recipe.FreezingStationRecipe;

import java.util.Optional;

public class FabricFreezingStationBlockEntity extends FreezingStationBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

    public FabricFreezingStationBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        super.setChanged();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(worldPosition);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FabricFreezingStationMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, inventory);
        nbt.putInt("freezing_station.progress", progress);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, inventory);
        progress = nbt.getInt("freezing_station.progress");
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FabricFreezingStationBlockEntity pEntity) {
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

    private static int getCoolingPower(FabricFreezingStationBlockEntity entity) {
        Level level = entity.getLevel();
        int cooling = 0;

        Block block = level.getBlockState(entity.getBlockPos().below()).getBlock();
        cooling += getBlockCoolingPower(entity, block);

        entity.coldness = cooling;
        return cooling;
    }

    private static int getBlockCoolingPower(FabricFreezingStationBlockEntity entity, Block block) {
        int cooling = 0;
        if(block == Blocks.PACKED_ICE) cooling += 1;
        if(block == ModBlocks.FROZEN_LITHERITE_BLOCK.get()) cooling += 2;

        return cooling;
    }

    private static void craftItem(FabricFreezingStationBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        Optional<FreezingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FreezingStationRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            ItemStack resultItem = recipe.get().getResultItem(level.registryAccess());
            ItemStack outputItem = new ItemStack(resultItem.getItem(), pEntity.getItem(2).getCount() + resultItem.getCount());

            CompoundTag nbt = resultItem.getTag();
            if(nbt != null) {
                outputItem.setTag(nbt.copy());
            }

            pEntity.removeItem(0, recipe.get().recipeItems.get(0).getItems()[0].getCount());
            pEntity.removeItem(1, recipe.get().recipeItems.get(1).getItems()[0].getCount());
            pEntity.setItem(2, outputItem);

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(FabricFreezingStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<FreezingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FreezingStationRecipe.Type.INSTANCE, inventory, level);

        return getCoolingPower(entity) > 0 && recipe.isPresent() && canInsertAmountIntoOutput(inventory) &&
                canInsertItemIntoOutput(inventory, recipe.get().getResultItem(level.registryAccess()));
    }

}
