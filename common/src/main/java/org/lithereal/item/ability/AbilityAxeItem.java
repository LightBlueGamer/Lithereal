package org.lithereal.item.ability;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class AbilityAxeItem extends AxeItem implements AbilityItem {
    final Ability ability;
    public AbilityAxeItem(Ability ability, Tier tier, Properties properties) {
        super(tier, properties.attributes(AxeItem.createAttributes(tier, 5, -3)));
        this.ability = ability;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        getAbility().onAttack(this, itemStack, attacked, attacker);
        return super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        getAbility().postAttack(this, itemStack, attacked, attacker);
        super.postHurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        getAbility().onItemTick(this, itemStack, level, entity, i, bl);
        super.inventoryTick(itemStack, level, entity, i, bl);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

    @Override
    public Ability getAbility() {
        return ability;
    }

    public final Map<UUID, Integer> degradationTickerMap = new WeakHashMap<>();
    public final Map<UUID, Integer> healTickerMap = new WeakHashMap<>();
    public final Map<UUID, Map<Holder<MobEffect>, Integer>> untilReadyMap = new WeakHashMap<>();

    @Override
    public Map<UUID, Integer> getDegradationTicker() {
        return degradationTickerMap;
    }

    @Override
    public Map<UUID, Integer> getHealTicker() {
        return healTickerMap;
    }

    @Override
    public Map<UUID, Map<Holder<MobEffect>, Integer>> getUntilReady() {
        return untilReadyMap;
    }
}