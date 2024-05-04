package org.lithereal.neoforge.block.entity;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.lithereal.block.entity.ImplementedInventory;

public interface NeoForgeInventory extends ImplementedInventory {
    ImplementedItemHandler getHandler();

    @Override
    default void saveItems(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        nbt.put("Items", getHandler().serializeNBT(provider));
    }
}
