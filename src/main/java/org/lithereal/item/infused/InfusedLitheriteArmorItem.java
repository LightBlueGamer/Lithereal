package org.lithereal.item.infused;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityArmorItem;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class InfusedLitheriteArmorItem extends AbilityArmorItem implements InfusedItem {
    public InfusedLitheriteArmorItem(ArmorMaterial armorMaterial, ArmorType type, Properties properties) {
        super(Ability.INFUSED, armorMaterial, type, properties.component(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.POTION_CONTENTS, true)));
    }

    public InfusedLitheriteArmorItem(ArmorMaterial armorMaterial, BiFunction<Properties, ArmorMaterial, Properties> toMakeArmor, Properties properties) {
        super(Ability.INFUSED, armorMaterial, toMakeArmor, properties.component(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.POTION_CONTENTS, true)));
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        return itemStack;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        PotionContents.addPotionTooltip(transformEffects(itemStack), builder, 1.0F, context.tickRate());
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        return getModifiedName(itemStack);
    }

    @Override
    public String getBaseName(ItemStack stack) {
        String armorType = "";
        if (stack.has(DataComponents.EQUIPPABLE)) {
            EquipmentSlot slot = stack.get(DataComponents.EQUIPPABLE).slot();
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