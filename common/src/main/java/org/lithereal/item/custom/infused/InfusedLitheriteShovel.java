package org.lithereal.item.custom.infused;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lithereal.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfusedLitheriteShovel extends ShovelItem implements InfusedItem {
    public int regenTicker = 0;
    public Map<MobEffect, Integer> untilReady = new HashMap<>();
    public InfusedLitheriteShovel(Tier tier, float damage, float attackSpeed, Properties properties) {
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
                    if(attacker.hasEffect(effect) && PotionUtils.getPotion(itemStack).getEffects().size() == 1) attacker.removeEffect(effect);
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
                boolean bl = !untilReady.containsKey(effect) && (effect.isBeneficial()
                        || PotionUtils.getPotion(itemStack).getEffects().size() > 1)
                        && !effect.isInstantenous();
                if(bl) {
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

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        PotionUtils.addPotionTooltip(itemStack, components, 1F);

        ItemStack potion = PotionUtils.setPotion(new ItemStack(Items.POTION), PotionUtils.getPotion(itemStack));
        Component name = potion.getHoverName();
        String hoverStr = name.getString().replaceAll("^(?i)(potion of the |potion of |potion )", "");
        Component newName = Component.literal(hoverStr+ " Litherite Shovel").withStyle(Style.EMPTY.withItalic(false));
        itemStack.setHoverName(newName);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }
}
