package org.lithereal.item.compat;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.List;

public class CompatInit {
    public static void initCombatify() {
        CombatifyItems.init();
    }

    public static void setColoursForCombatify(ItemColor itemColor) {
        ColorHandlerRegistry.registerItemColors(itemColor, CombatifyItems.INFUSED_LITHERITE_KNIFE, CombatifyItems.INFUSED_LITHERITE_LONGSWORD);
    }

    public static List<ItemStack> populateInfusedForCombatify(List<ItemStack> litherite, Potion potion) {
        litherite.add(PotionUtils.setPotion(new ItemStack(CombatifyItems.INFUSED_LITHERITE_LONGSWORD.get()), potion));
        litherite.add(PotionUtils.setPotion(new ItemStack(CombatifyItems.INFUSED_LITHERITE_KNIFE.get()), potion));
        return litherite;
    }

    public static void addInfusedNbtSubtypesForCombatify(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(CombatifyItems.INFUSED_LITHERITE_LONGSWORD.get(),
                CombatifyItems.INFUSED_LITHERITE_KNIFE.get());
    }
}
