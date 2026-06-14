package org.lithereal.item.infused;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityHoeItem;

public class InfusedLitheriteHoeItem extends AbilityHoeItem implements InfusedItem {
    public InfusedLitheriteHoeItem(ToolMaterial toolMaterial, Properties properties) {
        super(Ability.INFUSED, toolMaterial, properties);
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        return itemStack;
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        return getModifiedName(itemStack);
    }

    @Override
    public String getBaseName(ItemStack stack) {
        return "Litherite Hoe";
    }
}