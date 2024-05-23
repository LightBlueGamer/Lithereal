package org.lithereal.item.custom.infused;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ability.AbilitySwordItem;

import java.util.List;
import java.util.Objects;

public class InfusedLitheriteSwordItem extends AbilitySwordItem implements InfusedItem {
    public InfusedLitheriteSwordItem(Tier tier, Properties properties) {
        super(Ability.INFUSED, tier, properties);
    }

    public ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        return itemStack;
    }

    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, components, tooltipFlag);
        PotionContents potionContents = itemStack.get(DataComponents.POTION_CONTENTS);
        if (potionContents != null) {
            Objects.requireNonNull(components);
            potionContents.addPotionTooltip(components::add, 1.0F, tooltipContext.tickRate());
        }
    }

    public String getDescriptionId(ItemStack itemStack) {
        return Potion.getName(itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion(), this.getDescriptionId() + ".effect.");
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return getModifiedName(itemStack);
    }

    @Override
    public String getBaseName(ItemStack stack) {
        return "Litherite Sword";
    }
}
