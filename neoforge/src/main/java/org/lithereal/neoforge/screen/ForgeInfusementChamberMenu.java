package org.lithereal.neoforge.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.lithereal.screen.InfusementChamberMenu;

public class ForgeInfusementChamberMenu extends InfusementChamberMenu {
    public ForgeInfusementChamberMenu(int syncId, Inventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(pos));
    }

    public ForgeInfusementChamberMenu(int syncId, Inventory playerInventory, BlockEntity blockEntity) {
        super(syncId, playerInventory, blockEntity);
    }

    public static @NotNull ForgeInfusementChamberMenu create(int syncId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        return new ForgeInfusementChamberMenu(syncId, inventory, buf.readBlockPos());
    }
}