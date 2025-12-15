package org.lithereal.item.infused;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityShovelItem;

import java.util.List;
import java.util.Objects;

public class InfusedLitheriteShovelItem extends AbilityShovelItem implements InfusedItem {
    public InfusedLitheriteShovelItem(Tier tier, Properties properties) {
        super(Ability.INFUSED, tier, properties);
    }

    public @NotNull ItemStack getDefaultInstance() {
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

    public @NotNull String getDescriptionId(ItemStack itemStack) {
        return Potion.getName(itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion(), this.getDescriptionId() + ".effect.");
    }


    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        return getModifiedName(itemStack);
    }

    @Override
    public String getBaseName(ItemStack stack) {
        return "Litherite Shovel";
    }
}