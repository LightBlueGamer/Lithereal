package org.lithereal.fabric.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.screen.InfusementChamberMenu;

public class FabricInfusementChamberMenu extends InfusementChamberMenu {
    private final Container inventory;

    public FabricInfusementChamberMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()),
                new SimpleContainerData(3));
    }

    public FabricInfusementChamberMenu(int syncId, Inventory playerInventory,
                                       BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity, containerData);
        this.inventory = ((Container) blockEntity);
        inventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 80, 57));
        this.addSlot(new Slot(inventory, 1, 80, 13));
    }
}