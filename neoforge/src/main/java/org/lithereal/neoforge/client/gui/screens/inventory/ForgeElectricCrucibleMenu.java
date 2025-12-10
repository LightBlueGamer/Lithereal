package org.lithereal.neoforge.client.gui.screens.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.lithereal.client.gui.screens.inventory.ElectricCrucibleMenu;

public class ForgeElectricCrucibleMenu extends ElectricCrucibleMenu {
    public ForgeElectricCrucibleMenu(int syncId, Inventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(pos));
    }

    public ForgeElectricCrucibleMenu(int syncId, Inventory playerInventory, BlockEntity blockEntity) {
        super(syncId, playerInventory, blockEntity);
    }

    public static @NotNull ForgeElectricCrucibleMenu create(int syncId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        return new ForgeElectricCrucibleMenu(syncId, inventory, buf.readBlockPos());
    }
}