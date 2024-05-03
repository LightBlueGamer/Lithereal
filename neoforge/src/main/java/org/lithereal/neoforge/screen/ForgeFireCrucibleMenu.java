package org.lithereal.neoforge.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.lithereal.screen.FireCrucibleMenu;

public class ForgeFireCrucibleMenu extends FireCrucibleMenu {
    public ForgeFireCrucibleMenu(int syncId, Inventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(pos));
    }

    public ForgeFireCrucibleMenu(int syncId, Inventory playerInventory, BlockEntity blockEntity) {
        super(syncId, playerInventory, blockEntity);
    }

    public static @NotNull ForgeFireCrucibleMenu create(int syncId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        return new ForgeFireCrucibleMenu(syncId, inventory, buf.readBlockPos());
    }
}