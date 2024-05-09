package org.lithereal.fabric.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.EtherBatteryBlockEntity;
import org.lithereal.block.entity.IEnergyContainerProvider;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.fabric.screen.FabricEtherBatteryMenu;

public class FabricEtherBatteryBlockEntity extends EtherBatteryBlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, IEnergyContainerProvider {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(0, ItemStack.EMPTY);
    public FabricEtherBatteryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
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

    @Override
    public GetterAndSetter getOrSet() {
        return new GetterAndSetter(this::getItems, this::setItems);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FabricEtherBatteryMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        nbt.putInt("lither_battery.energy", getEnergyContainer().energy);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        getEnergyContainer().energy = nbt.getInt("lither_battery.energy");
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FabricEtherBatteryBlockEntity pEntity) {
        if(level.isClientSide()) return;
        //if(pEntity.getEnergyContainer().energy > 0) pEntity.getEnergyContainer().transferEnergy(pEntity);
    }

    /**
     * Writes additional server -&gt; client screen opening data to the buffer.
     *
     * @param player the player that is opening the screen
     * @return the screen opening data
     */
    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return worldPosition;
    }
}