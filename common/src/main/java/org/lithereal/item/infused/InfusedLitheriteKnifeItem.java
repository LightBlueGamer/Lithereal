package org.lithereal.item.infused;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityKnifeItem;

import java.util.List;
import java.util.Objects;

public class InfusedLitheriteKnifeItem extends AbilityKnifeItem implements InfusedItem {
    public InfusedLitheriteKnifeItem(Tier tier, Item.Properties properties) {
        super(Ability.INFUSED, tier, properties);
    }

    public ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        return itemStack;
    }

    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {
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
        return "Litherite Knife";
    }
}
