package org.lithereal.neoforge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.neoforge.screen.ForgeInfusementChamberMenu;

public class ForgeInfusementChamberBlockEntity extends InfusementChamberBlockEntity implements ImplementedInventory {

    public ForgeInfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ForgeInfusementChamberMenu(i, inventory, this);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        super.setChanged();
    }
}
