package org.lithereal.fabric.client.gui.screens.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.client.gui.screens.inventory.EtherBatteryMenu;

public class FabricEtherBatteryMenu extends EtherBatteryMenu {
    public FabricEtherBatteryMenu(int id, Inventory inv, BlockPos pos) {
        this(id, inv, inv.player.level().getBlockEntity(pos), new SimpleContainerData(2));
    }
    public FabricEtherBatteryMenu(int syncId, Inventory playerInventory,
                                  BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity, containerData);
    }
}