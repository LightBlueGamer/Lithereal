package org.lithereal.item.custom.infused;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ability.AbilityArmor;

import java.util.List;

public class InfusedLitheriteArmor extends AbilityArmor implements InfusedItem {

    public InfusedLitheriteArmor(Holder<ArmorMaterial> armorMaterial, Type type, int durability, Properties properties) {
        super(Ability.INFUSED, armorMaterial, type, durability, properties);
    }

    public ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        return itemStack;
    }

    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {
        PotionContents.addPotionTooltip(transformEffects(itemStack, 100), components::add, 1.0F, tooltipContext.tickRate());
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
