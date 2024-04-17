package org.lithereal.item.enchantment.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentImpalerEnchantment;
import org.lithereal.item.custom.infused.InfusedItem;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class DamageEffectEnchantment extends Enchantment {
    final Predicate<LivingEntity> canEffect;
    final MobEffectInstance[] effectInstances;
    final BiFunction<LivingEntity, Integer, Integer> timeFunction;
    final BiFunction<MobType, Integer, Float> damageFunction;
    final int maxLevel;
    public DamageEffectEnchantment(Rarity rarity, Predicate<LivingEntity> canEffect, MobEffectInstance[] effect, BiFunction<LivingEntity, Integer, Integer> timeFunc, BiFunction<MobType, Integer, Float> damageFunc, int max, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.WEAPON, equipmentSlots);
        this.canEffect = canEffect;
        effectInstances = effect;
        timeFunction = timeFunc;
        damageFunction = damageFunc;
        maxLevel = max;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinCost(int i) {
        return i * 25;
    }

    public int getMaxCost(int i) {
        return this.getMinCost(i) + 50;
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof TieredItem;
    }

    @Override
    public float getDamageBonus(int i, MobType mobType) {
        return damageFunction.apply(mobType, i);
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof DamageEnchantment || enchantment instanceof TridentImpalerEnchantment || enchantment instanceof DamageEffectEnchantment);
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public void doPostAttack(LivingEntity livingEntity, Entity entity, int level) {
        if (level > 0 && entity instanceof LivingEntity target && canEffect.test(target)) {
            for (MobEffectInstance mobEffectInstance : effectInstances) {
                target.addEffect(InfusedItem.transformInstance(mobEffectInstance, timeFunction.apply(target, level)));
            }
        }
    }
}
