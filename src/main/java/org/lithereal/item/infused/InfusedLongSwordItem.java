package org.lithereal.item.infused;

import net.atlas.combatify.item.LongSwordItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ability.Ability;

public class InfusedLongSwordItem extends LongSwordItem implements InfusedItem {
    public InfusedLongSwordItem(ToolMaterial toolMaterial, int weaponLevel, Properties properties) {
        super(toolMaterial, weaponLevel, Ability.INFUSED.createToolComponent(properties));
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
        return "Litherite Longsword";
    }
}
