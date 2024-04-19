package org.lithereal.item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.lithereal.block.ModBlocks;
import org.lithereal.item.custom.ability.AbilityItem;
import org.lithereal.item.custom.enhanceable.EnhanceableItem;
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
        private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
                (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                        .put(ModArmorMaterials.BURNING_LITHERITE,
                                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1)).build();
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            if (attacked.isFreezing()) attacked.setTicksFrozen(0);
            attacked.setSecondsOnFire(1000);
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

        }

        @Override
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            if (entity instanceof Player player) {
                if (player.hurtTime > 0 && !player.level().isClientSide) {
                    DamageSource source = player.getLastDamageSource();
                    if(source == null) return;
                    Entity attacker = source.getEntity();
                    if (attacker instanceof LivingEntity) {
                        attacker.setSecondsOnFire(5);
                    }
                }
                if(!level.isClientSide()) {
                    if(hasFullSuitOfArmorOn(player)) {
                        evaluateArmorEffects(player);
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
            for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
                ArmorMaterial mapArmorMaterial = entry.getKey();
                MobEffectInstance mapStatusEffect = entry.getValue();

                if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
        }

        private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffectInstance mapStatusEffect) {
            boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

            if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
                player.addEffect(new MobEffectInstance(mapStatusEffect));
            }
        }
    },
    FROZEN {
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            if(attacked.isOnFire()) attacked.extinguishFire();
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
                    if(source == null) return;
                    Entity attacker = source.getEntity();
                    if (attacker instanceof LivingEntity) {
                        attacker.setTicksFrozen(1000);
                    }
                }
                if(!level.isClientSide()) {
                    if(hasFullSuitOfArmorOn(player)) {
                        if(hasCorrectArmorOn(ModArmorMaterials.FROZEN_LITHERITE, player)) {
                            if(player.isFreezing()) {
                                player.setTicksFrozen(0);
                            }
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
        public final Map<UUID, Map<MobEffect, Integer>> untilReadyMap = new HashMap<>();
        @Override
        public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
            PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                MobEffect effect = mobEffectInstance.getEffect();
                boolean bl = effect.isBeneficial();
                boolean bl2 = attacked.isInvertedHealAndHarm() && effect == MobEffects.HEAL;
                if(!bl || bl2) {
                    if(!(attacked.isInvertedHealAndHarm() && effect == MobEffects.HARM)) {
                        if(attacker.hasEffect(effect) && PotionUtils.getPotion(itemStack).getEffects().size() == 1) attacker.removeEffect(effect);
                        if (mobEffectInstance.getEffect().isInstantenous()) mobEffectInstance.getEffect().applyInstantenousEffect(attacker, attacker, attacked, mobEffectInstance.getAmplifier(), 1.0);
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
            Map<MobEffect, Integer> untilReady = untilReadyMap.get(entityID);
            if(entity instanceof LivingEntity livingEntity && isSelected) {
                PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                    MobEffect effect = mobEffectInstance.getEffect();
                    boolean bl = !untilReady.containsKey(effect) && (effect.isBeneficial()
                            || PotionUtils.getPotion(itemStack).getEffects().size() > 1)
                            && !effect.isInstantenous();
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
                    if (itemStack.isDamaged() && regenTickerI.get() >= 10 && mobEffectInstance.getEffect() == MobEffects.REGENERATION) {
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
                        PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                            MobEffect effect = mobEffectInstance.getEffect();
                            boolean bl = effect.isBeneficial();
                            boolean bl2 = livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HEAL;
                            if(!bl || bl2) {
                                if (!(livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HARM)) {
                                    if (mobEffectInstance.getEffect().isInstantenous()) mobEffectInstance.getEffect().applyInstantenousEffect(player, player, livingEntity, mobEffectInstance.getAmplifier(), 1.0);
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
                            boolean bl2 = PotionUtils.getPotion(itemStack).getEffects().size() > 1;
                            PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                                boolean bl = mobEffectInstance.getEffect().isBeneficial();
                                if (bl || bl2) {
                                    if(mobEffectInstance.getEffect() != MobEffects.HEAL || healTicker.get() >= 200) {
                                        MobEffectInstance mobEff = InfusedItem.transformInstance(mobEffectInstance, 100);
                                        if (mobEffectInstance.getEffect().isInstantenous()) mobEffectInstance.getEffect().applyInstantenousEffect(null, null, player, mobEffectInstance.getAmplifier(), 1.0);
                                        else player.addEffect(mobEff);
                                        if (mobEffectInstance.getEffect() == MobEffects.HEAL) healTicker.set(0);
                                    }
                                } else {
                                    if (player.hasEffect(mobEffectInstance.getEffect())) player.removeEffect(mobEffectInstance.getEffect());
                                }
                                if (itemStack.isDamaged() && regenTickerA.get() >= 10 && mobEffectInstance.getEffect() == MobEffects.REGENERATION) {
                                    itemStack.hurtAndBreak(2 * mobEffectInstance.getAmplifier(), player, player1 -> player1.broadcastBreakEvent(item instanceof ArmorItem armorItem ? armorItem.getEquipmentSlot() : EquipmentSlot.MAINHAND));
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
            if (EnhanceableItem.isEnhanced(itemStack))
                ModEnchantments.HEROS_EDGE.get().doPostAttack(attacker, attacked, 1);
        }

        @Override
        public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

        }

        @Override
        public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
            if (entity instanceof Player player && EnhanceableItem.isEnhanced(itemStack) && !level.isClientSide())
                if (hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(ModArmorMaterials.ODYSIUM, player) && !player.hasEffect(MobEffects.DAMAGE_RESISTANCE))
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
        }
    };
    public abstract void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    public abstract void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected);
    public abstract void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected);
}
