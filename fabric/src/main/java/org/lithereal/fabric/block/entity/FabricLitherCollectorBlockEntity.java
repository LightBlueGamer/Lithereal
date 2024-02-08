package org.lithereal.fabric.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.block.entity.LitherCollectorBlockEntity;
import org.lithereal.fabric.screen.FabricInfusementChamberMenu;
import org.lithereal.fabric.screen.FabricLitherCollectorMenu;

public class FabricLitherCollectorBlockEntity extends LitherCollectorBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
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
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
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

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FabricLitherCollectorBlockEntity pEntity) {
        if(level.isClientSide()) return;
    }
}
