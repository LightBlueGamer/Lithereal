package org.lithereal.neoforge.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.lithereal.screen.FreezingStationMenu;

public class ForgeFreezingStationMenu extends FreezingStationMenu {
    public ForgeFreezingStationMenu(int syncId, Inventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(pos));
    }

    public ForgeFreezingStationMenu(int syncId, Inventory playerInventory, BlockEntity blockEntity) {
        super(syncId, playerInventory, blockEntity);
    }

    public static @NotNull ForgeFreezingStationMenu create(int syncId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        return new ForgeFreezingStationMenu(syncId, inventory, buf.readBlockPos());
    }
}