package org.lithereal.item.ability;

import com.mojang.serialization.MapCodec;
import net.minecraft.Optionull;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.lithereal.Lithereal;
import org.lithereal.core.component.SpecialAbility;
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

public record InfusedAbility(
        List<ResourceKey<EquipmentAsset>> supportedMaterials) implements IAbility {
    public static final Identifier ID = Lithereal.id("infused_ability");
    public static final MapCodec<InfusedAbility> CODEC = ResourceKey.codec(EquipmentAssets.ROOT_ID).listOf().fieldOf("supported_materials").xmap(InfusedAbility::new, InfusedAbility::supportedMaterials);
    public static void applyEffectToTarget(ServerLevel level, MobEffectInstance mobEffectInstance, LivingEntity target, LivingEntity attacker, int count, boolean swapEffects) {
        Holder<MobEffect> effect = mobEffectInstance.getEffect();
        boolean isBeneficial = effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl);
        boolean effectivelyHarm = target.isInvertedHealAndHarm() && effect.is(ModTags.CAN_BE_INVERTED_HEAL);
        if (!isBeneficial || effectivelyHarm) {
            if (!(effect.is(ModTags.CAN_BE_INVERTED_HARM) && target.isInvertedHealAndHarm())) {
                if (swapEffects && attacker.hasEffect(effect) && count == 1) attacker.removeEffect(effect);
                if (effect.value().isInstantenous())
                    effect.value().applyInstantenousEffect(level, attacker, attacker, target, mobEffectInstance.getAmplifier(), 0.25);
                else
                    target.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), Math.max(mobEffectInstance.getDuration(), 100), mobEffectInstance.getAmplifier()));
            }
        } else {
            if (target.hasEffect(effect)) {
                target.removeEffect(effect);
                if (swapEffects)
                    attacker.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), Math.max(mobEffectInstance.getDuration(), 100), mobEffectInstance.getAmplifier()));
            }
        }
    }

    @Override
    public void onAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        AtomicInteger count = getCount(potionContents);
        if (potionContents.potion().filter(potionHolder -> potionHolder.is(ModTags.DISPELS_FIRE)).isPresent() && attacked.isOnFire()) attacked.extinguishFire();
        float potionDurationScale = itemStack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 0.1F);
        if (attacker.level() instanceof ServerLevel serverLevel) potionContents.forEachEffect((mobEffectInstance) -> applyEffectToTarget(serverLevel, mobEffectInstance, attacked, attacker, count.get(), true), potionDurationScale);
    }

    @Override
    public void postAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {

    }

    @Override
    public void onItemTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {
        UUID entityID = entity.getUUID();
        Map<Holder<MobEffect>, Integer> untilReady = Optionull.mapOrElse(ability.untilReadyMap().get(entityID), Function.identity(), HashMap::new);
        if (entity instanceof LivingEntity user && slot == EquipmentSlot.MAINHAND) {
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            AtomicInteger count = getCount(potionContents);
            if (potionContents.potion().filter(potionHolder -> potionHolder.is(ModTags.DISPELS_FIRE)).isPresent() && entity.isOnFire()) entity.extinguishFire();
            float potionDurationScale = itemStack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1F);
            potionContents.forEachEffect((mobEffectInstance) -> {
                Holder<MobEffect> effect = mobEffectInstance.getEffect();
                if (effect.value().isInstantenous()) return;
                boolean effectivelyBeneficial = (effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl) || (user.isInvertedHealAndHarm() && effect.is(ModTags.CAN_BE_INVERTED_HARM))) &&
                        (!user.isInvertedHealAndHarm() || !effect.is(ModTags.CAN_BE_INVERTED_HEAL));
                boolean tryApply = !untilReady.containsKey(effect)
                        && (effectivelyBeneficial || count.get() > 1);
                boolean wasApplied = false;
                if (effect.is(ModTags.APPLY_HASTE_ON_INFUSED) && !user.hasEffect(MobEffects.HASTE))
                    user.addEffect(new MobEffectInstance(MobEffects.HASTE, 20, mobEffectInstance.getAmplifier()));
                if (user.hasEffect(effect) && user.getEffect(effect).getAmplifier() >= mobEffectInstance.getAmplifier())
                    return;
                if (tryApply || (untilReady.containsKey(effect) && untilReady.get(effect) <= 0)) {
                    user.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                    wasApplied = true;
                }
                if (wasApplied && itemStack.isDamageableItem() && effect.is(ModTags.DEGRADES_LITHERITE_GEAR))
                    itemStack.hurtAndBreak(mobEffectInstance.getAmplifier() + 1, user, EquipmentSlot.MAINHAND);
            }, potionDurationScale);
        }
        if (ability.lastUpdatedMap().getOrDefault(entityID, -1) != entity.tickCount) {
            untilReady.replaceAll((mobEffect, integer) -> integer - 1);
            ability.lastUpdatedMap().put(entityID, entity.tickCount);
        }
        ability.untilReadyMap().put(entityID, untilReady);
    }

    @Override
    public void onArmourTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {
        UUID entityID = entity.getUUID();
        Map<Holder<MobEffect>, Integer> degradationTicker = Optionull.mapOrElse(ability.degradationTickerMap().get(entityID), Function.identity(), HashMap::new);
        Map<Holder<MobEffect>, Integer> healTicker = Optionull.mapOrElse(ability.healTickerMap().get(entityID), Function.identity(), HashMap::new);
        if (entity instanceof LivingEntity user) {
            if (!level.isClientSide()) {
                PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                AtomicInteger count = getCount(potionContents);
                if (user.hurtTime > 0) {
                    DamageSource source = user.getLastDamageSource();
                    if (source != null) {
                        Entity attacker = source.getEntity();
                        if (attacker instanceof LivingEntity newTarget) {
                            float potionDurationScale = itemStack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1F);
                            potionContents.forEachEffect((mobEffectInstance) -> applyEffectToTarget((ServerLevel) level, mobEffectInstance, newTarget, user, count.get(), false), potionDurationScale);
                        }
                    }
                }
                Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
                EquipmentSlot.Type type = equippable == null ? EquipmentSlot.Type.HUMANOID_ARMOR : equippable.slot().getType();
                if (hasFullSuitOfArmorOn(user, type)) {
                    if (hasCorrectArmorOn(supportedMaterials, user, type) && level.getGameTime() % 80 == 0) {
                        boolean multiEffect = count.get() > 1;
                        if (potionContents.potion().filter(potionHolder -> potionHolder.is(ModTags.DISPELS_FIRE)).isPresent() && entity.isOnFire()) entity.extinguishFire();
                        float potionDurationScale = itemStack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 0.1F);
                        potionContents.forEachEffect((mobEffectInstance) -> {
                            Holder<MobEffect> effect = mobEffectInstance.getEffect();
                            boolean effectivelyBeneficial = (effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl) || (user.isInvertedHealAndHarm() && effect.is(ModTags.CAN_BE_INVERTED_HARM))) &&
                                    (!user.isInvertedHealAndHarm() || !effect.is(ModTags.CAN_BE_INVERTED_HEAL));
                            if (effectivelyBeneficial || multiEffect) {
                                if (!effect.is(ModTags.DELAYED_INSTANT) || IAbility.getValueFromMapForEffect(healTicker, effect) >= 400) {
                                    if (effect.value().isInstantenous())
                                        effect.value().applyInstantenousEffect((ServerLevel) level, null, null, user, mobEffectInstance.getAmplifier(), 0.25);
                                    else user.addEffect(InfusedItem.transformInstance(mobEffectInstance, 1.0F));
                                    if (effect.is(ModTags.DELAYED_INSTANT)) healTicker.put(effect, 0);
                                }
                            } else if (user.hasEffect(effect)) user.removeEffect(effect);
                            if (itemStack.isDamageableItem() && IAbility.getValueFromMapForEffect(degradationTicker, effect) >= 200 && effect.is(ModTags.DEGRADES_LITHERITE_GEAR)) {
                                itemStack.hurtAndBreak(mobEffectInstance.getAmplifier(), user, itemStack.has(DataComponents.EQUIPPABLE) ? itemStack.get(DataComponents.EQUIPPABLE).slot() : EquipmentSlot.MAINHAND);
                                degradationTicker.put(effect, 0);
                            }
                        }, potionDurationScale);
                    }
                }
            }
        }
        if (ability.lastUpdatedMap().getOrDefault(entityID, -1) != entity.tickCount) {
            degradationTicker.replaceAll((mobEffect, integer) -> integer + 1);
            healTicker.replaceAll((mobEffect, integer) -> integer + 1);
            ability.lastUpdatedMap().put(entityID, entity.tickCount);
        }
        ability.degradationTickerMap().put(entityID, degradationTicker);
        ability.healTickerMap().put(entityID, healTicker);
    }

    @Override
    public MapCodec<? extends IAbility> type() {
        return CODEC;
    }

    private @NotNull AtomicInteger getCount(PotionContents potionContents) {
        AtomicInteger count = new AtomicInteger();
        potionContents.forEachEffect((mobEffectInstance) -> count.getAndIncrement(), 1F);
        return count;
    }
}
