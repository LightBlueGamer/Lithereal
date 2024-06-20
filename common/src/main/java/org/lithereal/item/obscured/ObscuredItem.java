package org.lithereal.item.obscured;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.lithereal.core.component.ModComponents;

public interface ObscuredItem {
    Item self();
    default ItemStack getObscuredInstance() {
        ItemStack base = new ItemStack(self());
        return setRevealed(base, false);
    }
    static boolean isRevealed(ItemStack stack) {
        return stack.getOrDefault(ModComponents.REVEALED.get(), false);
    }

    static boolean isRevealed(CompoundTag tag) {
        return tag != null && tag.getBoolean("Revealed");
    }

    static ItemStack setRevealed(ItemStack stack, boolean bl) {
        stack.set(ModComponents.REVEALED.get(), bl);
        return stack;
    }

    static void setRevealed(CompoundTag tag, boolean bl) {
        tag.putBoolean("Revealed", bl);
    }
}
