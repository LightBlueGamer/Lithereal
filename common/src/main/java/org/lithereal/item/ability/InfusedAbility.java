package org.lithereal.item.ability;

import net.minecraft.Optionull;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record InfusedAbility<I extends InfusedItem>(
        List<Holder<ArmorMaterial>> supportedMaterials) implements IAbility<I> {
    public static void applyEffectToTarget(MobEffectInstance mobEffectInstance, LivingEntity target, LivingEntity attacker, int count, boolean swapEffects) {
        Holder<MobEffect> effect = mobEffectInstance.getEffect();
        boolean isBeneficial = effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl);
        boolean effectivelyHarm = target.isInvertedHealAndHarm() && effect.is(MobEffects.HEAL);
        if (!isBeneficial || effectivelyHarm) {
            if (!(effect.is(MobEffects.HARM) && target.isInvertedHealAndHarm())) {
                if (swapEffects && attacker.hasEffect(effect) && count == 1) attacker.removeEffect(effect);
                if (effect.value().isInstantenous())
                    effect.value().applyInstantenousEffect(attacker, attacker, target, mobEffectInstance.getAmplifier(), 0.25);
                else
                    target.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), Math.max(mobEffectInstance.getDuration() / 10, 100), mobEffectInstance.getAmplifier()));
            }
        } else {
            if (target.hasEffect(effect)) {
                target.removeEffect(effect);
                if (swapEffects)
                    attacker.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), Math.max(mobEffectInstance.getDuration() / 10, 100), mobEffectInstance.getAmplifier()));
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
        potionContents.forEachEffect((mobEffectInstance) -> applyEffectToTarget(mobEffectInstance, attacked, attacker, count.get(), true));
    }

    @Override
    public void postAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {

    }

    @Override
    public void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        UUID entityID = entity.getUUID();
        Map<Holder<MobEffect>, Integer> untilReady = Optionull.mapOrElse(item.getUntilReady().get(entityID), Function.identity(), HashMap::new);
        if (entity instanceof LivingEntity livingEntity && isSelected) {
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            AtomicInteger count = getCount(potionContents);
            potionContents.forEachEffect((mobEffectInstance) -> {
                Holder<MobEffect> effect = mobEffectInstance.getEffect();
                if (effect.value().isInstantenous()) return;
                boolean tryApply = !untilReady.containsKey(effect)
                        && (effect.value().isBeneficial() || count.get() > 1 || effect.is(ModTags.PSEUDO_BENEFICIAl));
                boolean wasApplied = false;
                if (effect.is(ModTags.APPLY_HASTE_ON_INFUSED) && !livingEntity.hasEffect(MobEffects.DIG_SPEED))
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, mobEffectInstance.getAmplifier()));
                if (livingEntity.hasEffect(effect) && livingEntity.getEffect(effect).getAmplifier() >= mobEffectInstance.getAmplifier())
                    return;
                if (tryApply || (untilReady.containsKey(effect) && untilReady.get(effect) <= 0)) {
                    livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                    wasApplied = true;
                }
                if (wasApplied && itemStack.isDamageableItem() && effect.is(ModTags.DEGRADES_LITHERITE_GEAR))
                    itemStack.hurtAndBreak(mobEffectInstance.getAmplifier() + 1, livingEntity, EquipmentSlot.MAINHAND);
            });
        }
        if (item.getLastUpdatedMap().getOrDefault(entityID, -1) != entity.tickCount) {
            untilReady.replaceAll((mobEffect, integer) -> integer - 1);
            item.getLastUpdatedMap().put(entityID, entity.tickCount);
        }
        item.getUntilReady().put(entityID, untilReady);
    }

    @Override
    public void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        UUID entityID = entity.getUUID();
        AtomicInteger degradationTicker = new AtomicInteger(item.getDegradationTicker().getOrDefault(entityID, 0));
        AtomicInteger healTicker = new AtomicInteger(item.getHealTicker().getOrDefault(entityID, 0));
        if (entity instanceof LivingEntity user) {
            if (!level.isClientSide()) {
                PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                AtomicInteger count = getCount(potionContents);
                if (user.hurtTime > 0) {
                    DamageSource source = user.getLastDamageSource();
                    if (source != null) {
                        Entity attacker = source.getEntity();
                        if (attacker instanceof LivingEntity newTarget) {
                            potionContents.forEachEffect((mobEffectInstance) -> applyEffectToTarget(mobEffectInstance, newTarget, user, count.get(), false));
                        }
                    }
                }
                if (hasFullSuitOfArmorOn(user)) {
                    if (hasCorrectArmorOn(supportedMaterials, user) && level.getGameTime() % 80 == 0) {
                        boolean multiEffect = count.get() > 1;
                        potionContents.forEachEffect((mobEffectInstance) -> {
                            Holder<MobEffect> effect = mobEffectInstance.getEffect();
                            boolean effectivelyBeneficial = effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl);
                            if (effectivelyBeneficial || multiEffect) {
                                if (!effect.is(MobEffects.HEAL) || healTicker.get() >= 400) {
                                    if (effect.value().isInstantenous())
                                        effect.value().applyInstantenousEffect(null, null, user, mobEffectInstance.getAmplifier(), 0.25);
                                    else user.addEffect(InfusedItem.transformInstance(mobEffectInstance));
                                    if (effect.is(MobEffects.HEAL)) healTicker.set(0);
                                }
                            } else {
                                if (user.hasEffect(effect)) user.removeEffect(effect);
                            }
                            if (itemStack.isDamageableItem() && degradationTicker.get() >= 200 && effect.is(ModTags.DEGRADES_LITHERITE_GEAR)) {
                                itemStack.hurtAndBreak(mobEffectInstance.getAmplifier(), user, item instanceof Equipable equipable ? equipable.getEquipmentSlot() : EquipmentSlot.MAINHAND);
                                degradationTicker.set(0);
                            }
                        });
                    }
                }
            }
        }
        if (item.getLastUpdatedMap().getOrDefault(entityID, -1) != entity.tickCount) {
            degradationTicker.set(degradationTicker.get() + 1);
            healTicker.set(healTicker.get() + 1);
            item.getLastUpdatedMap().put(entityID, entity.tickCount);
        }
        item.getDegradationTicker().put(entityID, degradationTicker.get());
        item.getHealTicker().put(entityID, healTicker.get());
    }

    private @NotNull AtomicInteger getCount(PotionContents potionContents) {
        AtomicInteger count = new AtomicInteger();
        potionContents.forEachEffect((mobEffectInstance) -> count.getAndIncrement());
        return count;
    }
}
