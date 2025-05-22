package org.lithereal.item.infused;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityArmorItem;

import java.util.List;

public class InfusedLitheriteArmorItem extends AbilityArmorItem implements InfusedItem {

    public InfusedLitheriteArmorItem(Holder<ArmorMaterial> armorMaterial, Type type, int durability, Properties properties) {
        super(Ability.INFUSED, armorMaterial, type, durability, properties);
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        return itemStack;
    }

    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, components, tooltipFlag);
        PotionContents.addPotionTooltip(transformEffects(itemStack), components::add, 1.0F, tooltipContext.tickRate());
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
        String armorType = "";
        if (stack.getItem() instanceof ArmorItem armorItem) {
            EquipmentSlot slot = armorItem.getEquipmentSlot();
            armorType = switch (slot) {
                case HEAD -> "Helmet";
                case CHEST -> "Chestplate";
                case LEGS -> "Leggings";
                case FEET -> "Boots";
                default -> armorType;
            };
        }
        return "Litherite " + armorType;
    }
}