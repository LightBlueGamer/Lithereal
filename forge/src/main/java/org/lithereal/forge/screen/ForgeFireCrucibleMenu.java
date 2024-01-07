package org.lithereal.forge.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.lithereal.forge.block.entity.ForgeFireCrucibleBlockEntity;
import org.lithereal.screen.FireCrucibleMenu;

public class ForgeFireCrucibleMenu extends FireCrucibleMenu {

    public ForgeFireCrucibleMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public ForgeFireCrucibleMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, entity, data);
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 94, 57));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 140, 13));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 80, 13));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 66, 57));
        });
    }

}
