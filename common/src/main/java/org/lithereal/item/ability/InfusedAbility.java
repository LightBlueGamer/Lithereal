package org.lithereal.item.ability;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.infused.InfusedItem;
import org.lithereal.tags.ModTags;
import org.lithereal.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public class InfusedAbility<I extends InfusedItem> implements IAbility<I> {
    public final Map<Ability.IdentityForPlayer, Integer> degradationTickerMap = new HashMap<>();
    public final Map<Ability.IdentityForPlayer, Integer> healTickerMap = new HashMap<>();
    public final Map<Ability.IdentityForPlayer, Map<Holder<MobEffect>, Integer>> untilReadyMap = new HashMap<>();
    public final List<Holder<ArmorMaterial>> supportedMaterials;

    public InfusedAbility(List<Holder<ArmorMaterial>> supportedMaterials) {
        this.supportedMaterials = supportedMaterials;
    }

    private void applyEffectToTarget(MobEffectInstance mobEffectInstance, LivingEntity target, LivingEntity attacker, AtomicInteger count, boolean swapEffects) {
        Holder<MobEffect> effect = mobEffectInstance.getEffect();
        boolean isBeneficial = effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl);
        boolean effectivelyHarm = target.isInvertedHealAndHarm() && effect.is(MobEffects.HEAL);
        if(!isBeneficial || effectivelyHarm) {
            if (!(effect.is(MobEffects.HARM) && target.isInvertedHealAndHarm())) {
                if (swapEffects && attacker.hasEffect(effect) && count.get() == 1) attacker.removeEffect(effect);
                if (effect.value().isInstantenous()) effect.value().applyInstantenousEffect(attacker, attacker, target, mobEffectInstance.getAmplifier(), 0.25);
                else target.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), Math.max(mobEffectInstance.getDuration() / 10, 100), mobEffectInstance.getAmplifier()));
            }
        } else {
            if (target.hasEffect(effect)) {
                target.removeEffect(effect);
                if (swapEffects) attacker.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), Math.max(mobEffectInstance.getDuration() / 10, 100), mobEffectInstance.getAmplifier()));
            }
        }
    }

    @Override
    public boolean canCast(AbilityItem abilityItem) {
        return abilityItem instanceof InfusedItem;
    }

    @Override
    public void onAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        AtomicInteger count = getCount(potionContents);
        potionContents.forEachEffect((mobEffectInstance) -> applyEffectToTarget(mobEffectInstance, attacked, attacker, count, true));
    }

    @Override
    public void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        Ability.IdentityForPlayer entityID = new Ability.IdentityForPlayer(entity.getUUID(), item);
        if (!untilReadyMap.containsKey(entityID))
            untilReadyMap.put(entityID, new HashMap<>());
        Map<Holder<MobEffect>, Integer> untilReady = untilReadyMap.get(entityID);
        if(entity instanceof LivingEntity livingEntity && isSelected) {
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            AtomicInteger count = getCount(potionContents);
            potionContents.forEachEffect((mobEffectInstance) -> {
                Holder<MobEffect> effect = mobEffectInstance.getEffect();
                if (effect.value().isInstantenous()) return;
                boolean tryApply = !untilReady.containsKey(effect)
                        && (effect.value().isBeneficial() || count.get() > 1 || effect.is(ModTags.PSEUDO_BENEFICIAl));
                boolean wasApplied = false;
                if (tryApply) {
                    if (!livingEntity.hasEffect(effect)) livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                    wasApplied = true;
                }
                if (effect.is(ModTags.APPLY_HASTE_ON_INFUSED) && !livingEntity.hasEffect(MobEffects.DIG_SPEED))
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, mobEffectInstance.getAmplifier()));
                if (untilReady.containsKey(effect) && untilReady.get(effect) <= 0) {
                    if (!livingEntity.hasEffect(effect)) livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                    wasApplied = true;
                }
                if (wasApplied && itemStack.isDamageableItem() && effect.is(ModTags.DEGRADES_LITHERITE_GEAR)) itemStack.hurtAndBreak(mobEffectInstance.getAmplifier() + 1, livingEntity, EquipmentSlot.MAINHAND);
            });
        }
        untilReady.replaceAll((mobEffect, integer) -> integer - 1);
        untilReadyMap.put(entityID, untilReady);
    }

    @Override
    public void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        Ability.IdentityForPlayer entityID = new Ability.IdentityForPlayer(entity.getUUID(), item);
        if (!degradationTickerMap.containsKey(entityID))
            degradationTickerMap.put(entityID, 0);
        if (!healTickerMap.containsKey(entityID))
            healTickerMap.put(entityID, 0);
        AtomicInteger degradationTicker = new AtomicInteger(degradationTickerMap.get(entityID));
        AtomicInteger healTicker = new AtomicInteger(healTickerMap.get(entityID));
        if (entity instanceof Player player && player.getInventory().armor.contains(itemStack)) {
            if(!level.isClientSide()) {
                PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                AtomicInteger count = getCount(potionContents);
                if (player.hurtTime > 0) {
                    DamageSource source = player.getLastDamageSource();
                    if (source != null) {
                        Entity attacker = source.getEntity();
                        if (attacker instanceof LivingEntity newTarget) {
                            potionContents.forEachEffect((mobEffectInstance) -> applyEffectToTarget(mobEffectInstance, newTarget, player, count, false));
                        }
                    }
                }
                if(hasFullSuitOfArmorOn(player)) {
                    if(hasCorrectArmorOn(supportedMaterials, player) && level.getGameTime() % 80 == 0) {
                        boolean multiEffect = count.get() > 1;
                        potionContents.forEachEffect((mobEffectInstance) -> {
                            Holder<MobEffect> effect = mobEffectInstance.getEffect();
                            boolean effectivelyBeneficial = effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl);
                            if (effectivelyBeneficial || multiEffect) {
                                if(!effect.is(MobEffects.HEAL) || healTicker.get() >= 400) {
                                    if (effect.value().isInstantenous()) effect.value().applyInstantenousEffect(null, null, player, mobEffectInstance.getAmplifier(), 0.25);
                                    else player.addEffect(InfusedItem.transformInstance(mobEffectInstance));
                                    if (effect.is(MobEffects.HEAL)) healTicker.set(0);
                                }
                            } else {
                                if (player.hasEffect(effect)) player.removeEffect(effect);
                            }
                            if (itemStack.isDamageableItem() && degradationTicker.get() >= 200 && effect.is(ModTags.DEGRADES_LITHERITE_GEAR)) {
                                itemStack.hurtAndBreak(mobEffectInstance.getAmplifier(), player, item instanceof Equipable equipable ? equipable.getEquipmentSlot() : EquipmentSlot.MAINHAND);
                                degradationTicker.set(0);
                            }
                        });
                    }
                }
            }
        }
        degradationTickerMap.put(entityID, degradationTicker.incrementAndGet());
        healTickerMap.put(entityID, healTicker.incrementAndGet());
    }

    private @NotNull AtomicInteger getCount(PotionContents potionContents) {
        AtomicInteger count = new AtomicInteger();
        potionContents.forEachEffect((mobEffectInstance) -> count.getAndIncrement());
        return count;
    }
}
