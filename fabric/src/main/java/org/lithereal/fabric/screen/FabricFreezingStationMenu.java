package org.lithereal.fabric.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.screen.FreezingStationMenu;

public class FabricFreezingStationMenu extends FreezingStationMenu {
    public FabricFreezingStationMenu(int syncId, Inventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(pos),
                new SimpleContainerData(3));
    }

    public FabricFreezingStationMenu(int syncId, Inventory playerInventory,
                                     BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity, containerData);
    }
}