package org.lithereal.fabric.world.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.FreezingStationBlockEntity;
import org.lithereal.fabric.client.gui.screens.inventory.FabricFreezingStationMenu;

public class FabricFreezingStationBlockEntity extends FreezingStationBlockEntity implements ExtendedScreenHandlerFactory<BlockPos> {

    public FabricFreezingStationBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        super.setChanged();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FabricFreezingStationMenu(id, inventory, this);
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
