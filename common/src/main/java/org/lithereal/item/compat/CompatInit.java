package org.lithereal.item.compat;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;

public class CompatInit {
    public static void initCombatify() {
        CombatifyItems.init();
    }

    public static void setColoursForCombatify(ItemColor itemColor) {
        ColorHandlerRegistry.registerItemColors(itemColor, CombatifyItems.INFUSED_LITHERITE_KNIFE, CombatifyItems.INFUSED_LITHERITE_LONGSWORD);
    }

    public static List<ItemStack> populateInfusedForCombatify(List<ItemStack> litherite, Holder<Potion> potion) {
        List<ItemLike> itemLikes = Arrays.asList(CombatifyItems.INFUSED_LITHERITE_LONGSWORD.get(),
                CombatifyItems.INFUSED_LITHERITE_KNIFE.get());
        for (ItemLike itemLike : itemLikes){
            ItemStack current = new ItemStack(itemLike);
            current.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
            litherite.add(current);
        }
        return litherite;
    }

//    public static void addInfusedNbtSubtypesForCombatify(ISubtypeRegistration registration) {
//        registration.useNbtForSubtypes(CombatifyItems.INFUSED_LITHERITE_LONGSWORD.get(),
//                CombatifyItems.INFUSED_LITHERITE_KNIFE.get());
//    }
}
