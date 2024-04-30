package org.lithereal.item.enchantment.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import org.lithereal.item.custom.infused.InfusedItem;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class DamageEffectEnchantment extends Enchantment {
    final Predicate<LivingEntity> canEffect;
    final MobEffectInstance[] effectInstances;
    final BiFunction<LivingEntity, Integer, Integer> timeFunction;
    final BiFunction<EntityType<?>, Integer, Float> damageFunction;
    final boolean isTradeable;
    final boolean isDiscoverable;
    final boolean isTreasureOnly;
    public DamageEffectEnchantment(EnchantmentDefinition enchantmentDefinition, Predicate<LivingEntity> canEffect, MobEffectInstance[] effect, BiFunction<LivingEntity, Integer, Integer> timeFunc, BiFunction<EntityType<?>, Integer, Float> damageFunc, boolean tradeable, boolean discoverable, boolean treasureOnly) {
        super(enchantmentDefinition);
        this.canEffect = canEffect;
        effectInstances = effect;
        timeFunction = timeFunc;
        damageFunction = damageFunc;
        isTradeable = tradeable;
        isDiscoverable = discoverable;
        isTreasureOnly = treasureOnly;
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof TieredItem;
    }

    @Override
    public float getDamageBonus(int i, EntityType<?> mobType) {
        return damageFunction.apply(mobType, i);
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof DamageEnchantment || enchantment instanceof DamageEffectEnchantment);
    }

    @Override
    public boolean isTradeable() {
        return isTradeable;
    }

    @Override
    public boolean isDiscoverable() {
        return isDiscoverable;
    }

    @Override
    public boolean isTreasureOnly() {
        return isTreasureOnly;
    }

    @Override
    public void doPostAttack(LivingEntity livingEntity, Entity entity, int level) {
        if (level > 0 && entity instanceof LivingEntity target && canEffect.test(target)) {
            for (MobEffectInstance mobEffectInstance : effectInstances) {
                Holder<MobEffect> effect = mobEffectInstance.getEffect();
                if (effect.value().isInstantenous()) effect.value().applyInstantenousEffect(null, null, livingEntity, mobEffectInstance.getAmplifier(), 1.0);
                else target.addEffect(InfusedItem.transformInstance(mobEffectInstance, timeFunction.apply(target, level)));
            }
        }
    }
}
