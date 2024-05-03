package org.lithereal.fabric.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.screen.FireCrucibleMenu;

public class FabricFireCrucibleMenu extends FireCrucibleMenu {
    public FabricFireCrucibleMenu(int id, Inventory inv, BlockPos pos) {
        this(id, inv, inv.player.level().getBlockEntity(pos), new SimpleContainerData(6));
    }
    public FabricFireCrucibleMenu(int syncId, Inventory playerInventory,
                                  BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity);
    }
}