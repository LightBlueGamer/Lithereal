package org.lithereal.fabric.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.screen.FireCrucibleMenu;

public class FabricFireCrucibleMenu extends FireCrucibleMenu {
    private final Container inventory;
    public FabricFireCrucibleMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }
    public FabricFireCrucibleMenu(int syncId, Inventory playerInventory,
                                  BlockEntity blockEntity, ContainerData containerData) {
        super(syncId, playerInventory, blockEntity, containerData);

        this.inventory = ((Container) blockEntity);
        inventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 94, 57));
        this.addSlot(new Slot(inventory, 1, 140, 13));
        this.addSlot(new Slot(inventory, 2, 80, 13));
        this.addSlot(new Slot(inventory, 3, 66, 57));
    }
}