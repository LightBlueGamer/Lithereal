package org.lithereal.item.ability;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.lithereal.client.KeyMapping;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.item.infused.InfusedItem;
import org.lithereal.tags.ModTags;
import org.lithereal.util.CommonUtils;
import org.lithereal.block.ModBlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public enum Ability {
    BURNING {
        private static final Map<Holder<ArmorMaterial>, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
                (new ImmutableMap.Builder<Holder<ArmorMaterial>, MobEffectInstance>())
                        .put(ModArmorMaterials.BURNING_LITHERITE,
                                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0)).build();
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            if (attacked.isFreezing()) attacked.setTicksFrozen(0);
            attacked.setRemainingFireTicks(20000);
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

        }

        @Override
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            if (entity.isInPowderSnow) {
                for (int i = 0; i < 3; i++) {
                    BlockPos blockPos = entity.blockPosition().above(i - 1);
                    if ((level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || entity instanceof Player) && entity.mayInteract(level, blockPos) && level.getBlockState(blockPos).is(Blocks.POWDER_SNOW))
                        level.destroyBlock(blockPos, false);
                }
            }

            if (entity.isOnFire() && !(entity instanceof Player)) {
                entity.extinguishFire();
                entity.setSharedFlagOnFire(false);
            }

            if (entity instanceof Player player) {
                if (player.hurtTime > 0 && !player.level().isClientSide) {
                    DamageSource source = player.getLastDamageSource();
                    if (source == null) return;
                    Entity attacker = source.getEntity();
                    if (attacker instanceof LivingEntity) attacker.setRemainingFireTicks(100);
                }
                if(!level.isClientSide()) {
                    if(hasFullSuitOfArmorOn(player)) {
                        evaluateArmorEffects(player);
                        if (player.isOnFire()) {
                            player.extinguishFire();
                            player.setSharedFlagOnFire(false);
                        }
                        Block blockBelow = level.getBlockState(player.blockPosition().below()).getBlock();
                        if(hasCorrectArmorOn(ModArmorMaterials.BURNING_LITHERITE, player)) {
                            if(KeyMapping.SCORCH_KEY.isDown()) {
                                if (blockBelow == Blocks.NETHERRACK) level.setBlockAndUpdate(player.blockPosition().below(), ModBlocks.SCORCHED_NETHERRACK.get().defaultBlockState());
                                else if (blockBelow == Blocks.CRIMSON_NYLIUM) level.setBlockAndUpdate(player.blockPosition().below(), ModBlocks.SCORCHED_CRIMSON_NYLIUM.get().defaultBlockState());
                                else if (blockBelow == Blocks.WARPED_NYLIUM) level.setBlockAndUpdate(player.blockPosition().below(), ModBlocks.SCORCHED_WARPED_NYLIUM.get().defaultBlockState());
                            }
                        }
                    }
                }
            }
        }

        private void evaluateArmorEffects(Player player) {
            for (Map.Entry<Holder<ArmorMaterial>, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
                Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();
                MobEffectInstance mapStatusEffect = entry.getValue();

                if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
        }

        private void addStatusEffectForMaterial(Player player, Holder<ArmorMaterial> mapArmorMaterial, MobEffectInstance mapStatusEffect) {
            boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

            if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
                player.addEffect(new MobEffectInstance(mapStatusEffect));
            }
        }
    },
    FROZEN {
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            if (attacked.isOnFire()) attacked.extinguishFire();
            attacked.setTicksFrozen(1000);
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

        }

        @Override
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            if (entity instanceof Player player) {
                if (player.hurtTime > 0 && !player.level().isClientSide) {
                    DamageSource source = player.getLastDamageSource();
                    if (source == null) return;
                    Entity attacker = source.getEntity();
                    if (attacker instanceof LivingEntity) attacker.setTicksFrozen(1000);
                }
                if(!level.isClientSide()) {
                    if(hasFullSuitOfArmorOn(player)) {
                        if(hasCorrectArmorOn(ModArmorMaterials.FROZEN_LITHERITE, player)) {
                            if (player.isFreezing())
                                player.setTicksFrozen(0);
                            if(KeyMapping.FREEZE_KEY.isDown()) {
                                for (int x = -4; x <= 4; x++) {
                                    for (int z = -4; z <= 4; z++) {
                                        BlockPos checkPos = player.blockPosition().offset(x, -1, z);
                                        if (level.getBlockState(checkPos).getBlock() == Blocks.WATER) {
                                            level.setBlockAndUpdate(checkPos, Blocks.FROSTED_ICE.defaultBlockState());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    },
    WITHERING {
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

        }

        @Override
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            if (entity instanceof Player player) {
                if(!level.isClientSide()) {
                    if (player.hurtTime > 0) {
                        DamageSource source = player.getLastDamageSource();
                        if(source == null) return;
                        Entity attacker = source.getEntity();
                        if (attacker instanceof LivingEntity) {
                            ((LivingEntity) attacker).addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
                        }
                    }
                    if(hasFullSuitOfArmorOn(player)) {
                        if(hasCorrectArmorOn(ModArmorMaterials.WITHERING_LITHERITE, player)) {
                            if(player.hasEffect(MobEffects.WITHER)) {
                                player.removeEffect(MobEffects.WITHER);
                            }
                        }
                    }
                }
            }
        }
    },
    INFUSED {
        public final Map<IdentityForPlayer, Integer> degradationTickerMap = new HashMap<>();
        public final Map<IdentityForPlayer, Integer> healTickerMap = new HashMap<>();
        public final Map<IdentityForPlayer, Map<Holder<MobEffect>, Integer>> untilReadyMap = new HashMap<>();

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
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            AtomicInteger count = getCount(potionContents);
            potionContents.forEachEffect((mobEffectInstance) -> applyEffectToTarget(mobEffectInstance, attacked, attacker, count, true));
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            IdentityForPlayer entityID = new IdentityForPlayer(entity.getUUID(), item);
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
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            IdentityForPlayer entityID = new IdentityForPlayer(entity.getUUID(), item);
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
                        if(hasCorrectArmorOn(ModArmorMaterials.INFUSED_LITHERITE, player) && level.getGameTime() % 80 == 0) {
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
    },
    ENHANCED {
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            if (CommonUtils.isEnhanced(itemStack)) attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, 150));
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

        }

        @Override
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            if (entity instanceof Player player && CommonUtils.isEnhanced(itemStack) && !level.isClientSide())
                if (hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(ModArmorMaterials.ODYSIUM, player) && !player.hasEffect(MobEffects.DAMAGE_RESISTANCE))
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
        }
    };
    public abstract void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    public abstract void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected);
    public abstract void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected);
    public record IdentityForPlayer(UUID uuid, AbilityItem abilityItem) {
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IdentityForPlayer that)) return false;
            return Objects.equals(uuid(), that.uuid()) && Objects.equals(abilityItem(), that.abilityItem());
        }

        @Override
        public int hashCode() {
            return Objects.hash(uuid(), abilityItem());
        }
    }
}
