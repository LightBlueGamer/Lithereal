package org.lithereal.world.item.ability;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.lithereal.core.component.ModComponents;
import org.lithereal.world.item.ability.Ability;
import org.lithereal.world.item.ModArmorMaterials;

import java.util.List;
import java.util.function.Consumer;

import static org.lithereal.util.CommonUtils.addToTooltip;

public class AbilityArmorItem extends ArmorItem implements AbilityItem {
    final Ability ability;
    public AbilityArmorItem(Ability ability, Holder<ArmorMaterial> armorMaterial, Type type, int durability, Properties properties) {
        super(armorMaterial, type, properties.durability(ModArmorMaterials.HEALTH_FUNCTION_FOR_TYPE.apply(type, durability)));
        this.ability = ability;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        getAbility().onArmourTick(this, itemStack, level, entity, slot, isSelected);
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        Consumer<Component> consumer = list::add;
        addToTooltip(itemStack, ModComponents.ENHANCED.get(), tooltipContext, consumer, tooltipFlag);
    }

    @Override
    public Ability getAbility() {
        return ability;
    }
}