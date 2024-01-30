package org.lithereal.fabric.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.block.entity.LitherCollectorBlockEntity;

public class FabricLitherCollectorBlockEntity extends LitherCollectorBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public FabricLitherCollectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {

    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return null;
    }
}
