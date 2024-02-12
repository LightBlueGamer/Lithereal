package org.lithereal.fabric.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.screen.LitherBatteryMenu;

public class FabricLitherBatteryMenu extends LitherBatteryMenu {
    private final Container inventory;
    public FabricLitherBatteryMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }
    public FabricLitherBatteryMenu(int syncId, Inventory playerInventory,
                                   BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity, containerData);

        this.inventory = ((Container) blockEntity);
        inventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 94, 57));
    }
}