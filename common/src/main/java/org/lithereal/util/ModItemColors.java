package org.lithereal.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.function.BiFunction;

public class ModItemColors {
    public static final BiFunction<ItemStack, Integer, Integer> INFUSED_LITHERITE_COLOR_HANDLER = (itemStack, layer) ->
            layer == 0 ? PotionContents.getColor(itemStack) : -1;
}
