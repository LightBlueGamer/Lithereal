package org.lithereal.item.custom.obscured;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ObscuredItem {

    static boolean isRevealed(ItemStack stack) {
        return isRevealed(stack.getTag());
    }

    static boolean isRevealed(CompoundTag tag) {
        return tag != null && tag.getBoolean("Revealed");
    }

    static ItemStack setRevealed(ItemStack stack) {
        setRevealed(stack.getOrCreateTag());
        return stack;
    }

    static void setRevealed(CompoundTag tag) {
        tag.putBoolean("Revealed", false);
    }
}
