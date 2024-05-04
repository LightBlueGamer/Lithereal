package org.lithereal.item.custom;

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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.lithereal.block.ModBlocks;
import org.lithereal.item.custom.ability.AbilityItem;
import org.lithereal.item.custom.infused.InfusedItem;
import org.lithereal.item.enchantment.ModEnchantments;
import org.lithereal.util.CommonUtils;
import org.lithereal.util.KeyBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public enum Ability {
    BURNING {
        private static final Map<Holder<ArmorMaterial>, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
                (new ImmutableMap.Builder<Holder<ArmorMaterial>, MobEffectInstance>())
                        .put(ModArmorMaterials.BURNING_LITHERITE,
                                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1)).build();
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
                            if(KeyBinding.SCORCH_KEY.isDown()) {
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
                            if(KeyBinding.FREEZE_KEY.isDown()) {
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
        public final Map<UUID, Integer> regenTickerAMap = new HashMap<>();
        public final Map<UUID, Integer> healTickerMap = new HashMap<>();
        public final Map<UUID, Integer> regenTickerIMap = new HashMap<>();
        public final Map<UUID, Map<Holder<MobEffect>, Integer>> untilReadyMap = new HashMap<>();
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            AtomicInteger count = new AtomicInteger();
            potionContents.forEachEffect((mobEffectInstance) -> count.getAndIncrement());
            potionContents.forEachEffect((mobEffectInstance) -> {
                Holder<MobEffect> effect = mobEffectInstance.getEffect();
                boolean bl = effect.value().isBeneficial();
                boolean bl2 = attacked.isInvertedHealAndHarm() && effect == MobEffects.HEAL;
                if(!bl || bl2) {
                    if(!(attacked.isInvertedHealAndHarm() && effect == MobEffects.HARM)) {
                        if(attacker.hasEffect(effect) && count.get() == 1) attacker.removeEffect(effect);
                        if (effect.value().isInstantenous()) effect.value().applyInstantenousEffect(attacker, attacker, attacked, mobEffectInstance.getAmplifier(), 1.0);
                        else attacked.addEffect(CommonUtils.clone(mobEffectInstance));
                    }
                } else {
                    if (attacked.hasEffect(effect)) {
                        attacked.removeEffect(effect);
                        attacker.addEffect(CommonUtils.clone(mobEffectInstance));
                    }
                }
            });
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            UUID entityID = entity.getUUID();
            if (!regenTickerIMap.containsKey(entityID))
                regenTickerIMap.put(entityID, 0);
            if (!untilReadyMap.containsKey(entityID))
                untilReadyMap.put(entityID, new HashMap<>());
            AtomicInteger regenTickerI = new AtomicInteger(regenTickerIMap.get(entityID));
            Map<Holder<MobEffect>, Integer> untilReady = untilReadyMap.get(entityID);
            if(entity instanceof LivingEntity livingEntity && isSelected) {
                PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                AtomicInteger count = new AtomicInteger();
                potionContents.forEachEffect((mobEffectInstance) -> count.getAndIncrement());
                potionContents.forEachEffect((mobEffectInstance) -> {
                    Holder<MobEffect> effect = mobEffectInstance.getEffect();
                    boolean bl = !untilReady.containsKey(effect) && (effect.value().isBeneficial()
                            || count.get() > 1 || effect == MobEffects.WEAVING)
                            && !effect.value().isInstantenous();
                    if (bl) {
                        if (!livingEntity.hasEffect(effect)) livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                        untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                    }
                    if (effect == MobEffects.MOVEMENT_SPEED && !livingEntity.hasEffect(MobEffects.DIG_SPEED))
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, mobEffectInstance.getAmplifier()));
                    if (untilReady.containsKey(effect) && untilReady.get(effect) <= 0) {
                        if (!livingEntity.hasEffect(effect)) livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                        untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                    }
                    if (itemStack.isDamaged() && regenTickerI.get() >= 10 && effect == MobEffects.REGENERATION) {
                        itemStack.setDamageValue(itemStack.getDamageValue() - mobEffectInstance.getAmplifier());
                        regenTickerI.set(0);
                    }
                });
            }
            untilReady.replaceAll((mobEffect, integer) -> integer - 1);
            regenTickerIMap.put(entityID, regenTickerI.getAndIncrement());
            untilReadyMap.put(entityID, untilReady);
        }

        @Override
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            UUID entityID = entity.getUUID();
            if (!regenTickerAMap.containsKey(entityID))
                regenTickerAMap.put(entityID, 0);
            if (!healTickerMap.containsKey(entityID))
                healTickerMap.put(entityID, 0);
            AtomicInteger regenTickerA = new AtomicInteger(regenTickerAMap.get(entityID));
            AtomicInteger healTicker = new AtomicInteger(healTickerMap.get(entityID));
            if (entity instanceof Player player && player.getInventory().armor.contains(itemStack)) {
                if (player.hurtTime > 0 && !player.level().isClientSide) {
                    DamageSource source = player.getLastDamageSource();
                    if(source == null) return;
                    Entity attacker = source.getEntity();
                    if(attacker instanceof LivingEntity livingEntity) {
                        PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                        AtomicInteger count = new AtomicInteger();
                        potionContents.forEachEffect((mobEffectInstance) -> count.getAndIncrement());
                        potionContents.forEachEffect((mobEffectInstance) -> {
                            Holder<MobEffect> effect = mobEffectInstance.getEffect();
                            boolean bl = effect.value().isBeneficial();
                            boolean bl2 = livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HEAL;
                            if(!bl || bl2) {
                                if (!(livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HARM)) {
                                    if (effect.value().isInstantenous()) effect.value().applyInstantenousEffect(player, player, livingEntity, mobEffectInstance.getAmplifier(), 1.0);
                                    else livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                                }
                            } else {
                                if (livingEntity.hasEffect(effect))
                                    livingEntity.removeEffect(effect);
                            }
                        });
                    }
                }
                if(!level.isClientSide()) {
                    if(hasFullSuitOfArmorOn(player)) {
                        if(hasCorrectArmorOn(ModArmorMaterials.INFUSED_LITHERITE, player)) {
                            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                            AtomicInteger count = new AtomicInteger();
                            potionContents.forEachEffect((mobEffectInstance) -> count.getAndIncrement());
                            boolean bl2 = count.get() > 1;
                            potionContents.forEachEffect((mobEffectInstance) -> {
                                Holder<MobEffect> effect = mobEffectInstance.getEffect();
                                boolean bl = effect.value().isBeneficial() || effect == MobEffects.WEAVING;
                                if (bl || bl2) {
                                    if(effect != MobEffects.HEAL || healTicker.get() >= 200) {
                                        MobEffectInstance mobEff = InfusedItem.transformInstance(mobEffectInstance, 100);
                                        if (effect.value().isInstantenous()) effect.value().applyInstantenousEffect(null, null, player, mobEffectInstance.getAmplifier(), 1.0);
                                        else player.addEffect(mobEff);
                                        if (effect == MobEffects.HEAL) healTicker.set(0);
                                    }
                                } else {
                                    if (player.hasEffect(effect)) player.removeEffect(effect);
                                }
                                if (itemStack.isDamaged() && regenTickerA.get() >= 10 && effect == MobEffects.REGENERATION) {
                                    itemStack.hurtAndBreak(2 * mobEffectInstance.getAmplifier(), player, item instanceof ArmorItem armorItem ? armorItem.getEquipmentSlot() : EquipmentSlot.MAINHAND);
                                    regenTickerA.set(0);
                                }
                            });
                        }
                    }
                }
            }
            regenTickerAMap.put(entityID, regenTickerA.getAndIncrement());
            healTickerMap.put(entityID, healTicker.getAndIncrement());
        }
    },
    ENHANCED {
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            if (CommonUtils.isEnhanced(itemStack))
                ModEnchantments.HEROS_EDGE.get().doPostAttack(attacker, attacked, 1);
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
}
