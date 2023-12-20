package org.lithereal.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class CommonUtils {
    public static boolean hasFullSuitOfArmorOn(Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            bl &= !armorStack.isEmpty();
        }

        return bl;
    }

    public static boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
            bl &= ((ArmorItem)armorStack.getItem()).getMaterial() == material;
        }

        return bl;
    }
    public static MobEffectInstance clone(MobEffectInstance original) {
        return new MobEffectInstance(original.getEffect(), original.getDuration(), original.getAmplifier());
    }
}
