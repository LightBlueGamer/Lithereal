package org.lithereal.item.custom.obscured;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ObscuredItem {
    Item self();
    default ItemStack getObscuredInstance() {
        ItemStack base = new ItemStack(self());
        return setRevealed(base, false);
    }
    static boolean isRevealed(ItemStack stack) {
        return isRevealed(stack.getTag());
    }

    static boolean isRevealed(CompoundTag tag) {
        return tag != null && tag.getBoolean("Revealed");
    }

    static ItemStack setRevealed(ItemStack stack, boolean bl) {
        setRevealed(stack.getOrCreateTag(), bl);
        return stack;
    }

    static void setRevealed(CompoundTag tag, boolean bl) {
        tag.putBoolean("Revealed", bl);
    }
}
