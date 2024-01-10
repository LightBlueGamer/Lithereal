package org.lithereal.item.custom.infused;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lithereal.item.custom.Hammer;
import org.lithereal.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfusedLitheriteHammer extends Hammer implements InfusedItem {
    public int regenTicker = 0;
    public Map<MobEffect, Integer> untilReady = new HashMap<>();
    public InfusedLitheriteHammer(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {

        PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
            MobEffect effect = mobEffectInstance.getEffect();
            boolean bl = effect.isBeneficial();
            boolean bl2 = attacked.isInvertedHealAndHarm() && effect == MobEffects.HEAL;
            if(!bl || bl2) {
                if(!(attacked.isInvertedHealAndHarm() && effect == MobEffects.HARM)) {
                    if(attacker.hasEffect(effect))
                        attacker.removeEffect(effect);
                    attacked.addEffect(CommonUtils.clone(mobEffectInstance));
                }
            } else {
                if (attacked.hasEffect(effect)) {
                    attacked.removeEffect(effect);
                    attacker.addEffect(CommonUtils.clone(mobEffectInstance));
                }
            }
        });

        return super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if(entity instanceof LivingEntity livingEntity && isSelected) {
            PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                MobEffect effect = mobEffectInstance.getEffect();
                if(!untilReady.containsKey(effect) && effect.isBeneficial() && !effect.isInstantenous()) {
                    livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                }
                if(effect == MobEffects.MOVEMENT_SPEED) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, mobEffectInstance.getAmplifier()));
                }
                if(untilReady.containsKey(effect) && untilReady.get(effect) <= 0) {
                    livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                }
            });
        }
        if (itemStack.isDamaged() && regenTicker >= 10) {
            PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                MobEffect effect = mobEffectInstance.getEffect();
                if(effect == MobEffects.REGENERATION) {
                    itemStack.setDamageValue(itemStack.getDamageValue() - mobEffectInstance.getAmplifier());
                    regenTicker = 0;
                }
            });
        }
        regenTicker++;
        untilReady.forEach(((mobEffect, integer) -> untilReady.put(mobEffect, integer - 1)));
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }

    public void appendHoverText(ItemStack p_43359_, @Nullable Level p_43360_, List<Component> p_43361_, TooltipFlag p_43362_) {
        PotionUtils.addPotionTooltip(p_43359_, p_43361_, 1F);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }
    public @Nullable String getCreatorModId(ItemStack itemStack) {
        Potion potion = PotionUtils.getPotion(itemStack);
        ResourceLocation resourceLocation = BuiltInRegistries.POTION.getKey(potion);

        return resourceLocation.getNamespace();
    }
}
