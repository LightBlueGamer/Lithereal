package org.lithereal.fabric.client.gui.screens.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.client.gui.screens.inventory.ElectricCrucibleMenu;

public class FabricElectricCrucibleMenu extends ElectricCrucibleMenu {
    public FabricElectricCrucibleMenu(int id, Inventory inv, BlockPos pos) {
        this(id, inv, inv.player.level().getBlockEntity(pos), new SimpleContainerData(7));
    }
    public FabricElectricCrucibleMenu(int syncId, Inventory playerInventory,
                                      BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity);
    }
}