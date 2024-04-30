package org.lithereal.fabric.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.screen.LitherCollectorMenu;

public class FabricLitherCollectorMenu extends LitherCollectorMenu {
    public FabricLitherCollectorMenu(int id, Inventory inv, BlockPos pos) {
        this(id, inv, inv.player.level().getBlockEntity(pos), new SimpleContainerData(2));
    }
    public FabricLitherCollectorMenu(int syncId, Inventory playerInventory,
                                     BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity, containerData);
    }
}