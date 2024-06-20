package org.lithereal.item.ability;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.lithereal.core.component.ModComponents;

import java.util.List;
import java.util.function.Consumer;

import static org.lithereal.util.CommonUtils.addToTooltip;

public class AbilityShovelItem extends ShovelItem implements AbilityItem {
    final Ability ability;
    public AbilityShovelItem(Ability ability, Tier tier, Properties properties) {
        super(tier, properties.attributes(ShovelItem.createAttributes(tier, 1.5f, -3f)));
        this.ability = ability;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        getAbility().onAttack(this, itemStack, attacked, attacker);
        return super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        getAbility().onItemTick(this, itemStack, level, entity, i, bl);
        super.inventoryTick(itemStack, level, entity, i, bl);
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