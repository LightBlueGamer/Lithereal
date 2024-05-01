package org.lithereal.util;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.lithereal.item.custom.component.ModComponents;

public class CommonUtils {
    public static boolean hasFullSuitOfArmorOn(Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            bl &= !armorStack.isEmpty();
        }

        return bl;
    }

    public static boolean hasCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
            bl &= ((ArmorItem)armorStack.getItem()).getMaterial() == material;
        }

        return bl;
    }

    public static <T> boolean isAnyOf(T t, T... objects) {
        boolean ret = false;
        for (T object : objects) {
            ret |= t == object;
        }
        return ret;
    }
    public static MobEffectInstance clone(MobEffectInstance original) {
        return new MobEffectInstance(original);
    }

    public static boolean isEnhanced(ItemStack stack) {
        return stack.getOrDefault(ModComponents.ENHANCED.get(), false);
    }

    public static boolean isEnhanced(CompoundTag tag) {
        return tag != null && tag.getBoolean("Enhanced");
    }

    public static ItemStack setEnhanced(ItemStack stack, boolean bl) {
        stack.set(ModComponents.ENHANCED.get(), bl);
        return stack;
    }

    public static void setEnhanced(CompoundTag tag, boolean bl) {
        tag.putBoolean("Enhanced", bl);
    }
}
