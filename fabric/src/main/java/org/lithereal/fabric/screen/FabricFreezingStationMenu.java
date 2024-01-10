package org.lithereal.fabric.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.screen.FreezingStationMenu;

public class FabricFreezingStationMenu extends FreezingStationMenu {
    private final Container inventory;

    public FabricFreezingStationMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()),
                new SimpleContainerData(3));
    }

    public FabricFreezingStationMenu(int syncId, Inventory playerInventory,
                                     BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity, containerData);
        this.inventory = ((Container) blockEntity);
        inventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 43, 34));
        this.addSlot(new Slot(inventory, 1, 68, 34));
        this.addSlot(new Slot(inventory, 2, 129, 34));
    }
}