package org.lithereal.fabric.client.gui.screens.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.client.gui.screens.inventory.InfusementChamberMenu;

public class FabricInfusementChamberMenu extends InfusementChamberMenu {
    public FabricInfusementChamberMenu(int syncId, Inventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(pos));
    }

    public FabricInfusementChamberMenu(int syncId, Inventory playerInventory, BlockEntity blockEntity) {
        super(syncId, playerInventory, blockEntity);
    }
}