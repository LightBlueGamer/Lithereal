package org.lithereal.item.custom.enhanceable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ability.AbilityItem;

public interface EnhanceableItem extends AbilityItem {
    @Override
    default Ability getAbility() {
        return Ability.ENHANCED;
    }

    static boolean isEnhanced(ItemStack stack) {
        return isEnhanced(stack.getTag());
    }

    static boolean isEnhanced(CompoundTag tag) {
        return tag != null && tag.getBoolean("Enhanced");
    }

    static ItemStack setEnhanced(ItemStack stack, boolean bl) {
        setEnhanced(stack.getOrCreateTag(), bl);
        return stack;
    }

    static void setEnhanced(CompoundTag tag, boolean bl) {
        tag.putBoolean("Enhanced", bl);
    }
}
