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
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.IEnergyContainerProvider;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.block.entity.LitherCollectorBlockEntity;
import org.lithereal.fabric.item.FabricItems;
import org.lithereal.fabric.screen.FabricInfusementChamberMenu;
import org.lithereal.fabric.screen.FabricLitherCollectorMenu;
import org.lithereal.item.ModItems;
import org.lithereal.util.LitherEnergyContainer;

public class FabricLitherCollectorBlockEntity extends LitherCollectorBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, IEnergyContainerProvider {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    public FabricLitherCollectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(worldPosition);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 4);
        super.setChanged();
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FabricLitherCollectorMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, inventory);
        nbt.putInt("lither_collector.progress", progress);
        nbt.putInt("lither_collector.max_progress", maxProgress);
        nbt.putInt("lither_collector.energy", getEnergyContainer().energy);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, inventory);
        progress = nbt.getInt("lither_collector.progress");
        maxProgress = nbt.getInt("lither_collector.max_progress");
        getEnergyContainer().energy = nbt.getInt("lither_collector.energy");
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FabricLitherCollectorBlockEntity pEntity) {
        if(level.isClientSide()) return;
        if(pEntity.hasCrystal(pEntity) || pEntity.progress > 0) {
            LitherEnergyContainer energyContainer = pEntity.getEnergyContainer();

            if(energyContainer.energy < energyContainer.maxEnergy) {
                setMaxProgress(pEntity);
                if(pEntity.progress <= 0) pEntity.removeItem(0, 1);
                pEntity.progress += energyContainer.transferRate / 10;
                energyContainer.energy += energyContainer.transferRate / 10;
            }
            if(pEntity.progress >= pEntity.maxProgress) {
                pEntity.progress = 0;
            }
        }

        if(pEntity.getEnergyContainer().energy > 0) pEntity.getEnergyContainer().transferEnergy(pEntity);
    }

    public static void setMaxProgress(FabricLitherCollectorBlockEntity pEntity) {
        if(pEntity.getItem(0).getItem() == FabricItems.LITHERITE_CRYSTAL) pEntity.maxProgress = 1000;
        else if(pEntity.getItem(0).getItem() == ModBlocks.LITHERITE_BLOCK.get().asItem()) pEntity.maxProgress = 9000;
    }

    public boolean hasCrystal(FabricLitherCollectorBlockEntity pEntity) {
        return pEntity.getItem(0).getItem() == FabricItems.LITHERITE_CRYSTAL || pEntity.getItem(0).getItem() == ModBlocks.LITHERITE_BLOCK.get().asItem();
    }
}