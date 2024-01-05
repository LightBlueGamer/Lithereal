package org.lithereal.forge.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.lithereal.forge.block.entity.ForgeInfusementChamberBlockEntity;
import org.lithereal.screen.InfusementChamberMenu;

public class ForgeInfusementChamberMenu extends InfusementChamberMenu {

    public ForgeInfusementChamberMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(5));
    }

    public ForgeInfusementChamberMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, entity, data);
        checkContainerSize(inv, 3);
        blockEntity = (ForgeInfusementChamberBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 43, 34));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 68, 34));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 129, 34));
        });
    }

}
