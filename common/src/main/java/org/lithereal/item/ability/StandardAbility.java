package org.lithereal.item.ability;

import net.minecraft.Optionull;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.item.infused.InfusedItem;
import org.lithereal.tags.ModTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record StandardAbility<I extends AbilityItem>(List<Holder<ArmorMaterial>> armorMaterials, List<MobEffectInstance> attackEffects, List<MobEffectInstance> passiveEffects) implements IAbility<I> {
    @Override
    public void onAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        attackEffects.forEach(mobEffectInstance -> InfusedAbility.applyEffectToTarget(mobEffectInstance, attacked, attacker, attackEffects.size(), false));
    }

    @Override
    public void postAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {

    }

    @Override
    public void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

    }

    @Override
    public void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        UUID entityID = entity.getUUID();
        Map<Holder<MobEffect>, Integer> healTicker = Optionull.mapOrElse(item.getHealTicker().get(entityID), Function.identity(), HashMap::new);
        if (entity instanceof LivingEntity user && !level.isClientSide()) {
            if (hasFullSuitOfArmorOn(user) && hasCorrectArmorOn(armorMaterials, user) && level.getGameTime() % 80 == 0) {
                boolean multiEffect = passiveEffects.size() > 1;
                passiveEffects.forEach((mobEffectInstance) -> {
                    Holder<MobEffect> effect = mobEffectInstance.getEffect();
                    boolean effectivelyBeneficial = effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl);
                    if (effectivelyBeneficial || multiEffect) {
                        if (!effect.is(MobEffects.HEAL) || IAbility.getValueFromMapForEffect(healTicker, effect) == 400) {
                            if (effect.value().isInstantenous())
                                effect.value().applyInstantenousEffect(null, null, user, mobEffectInstance.getAmplifier(), 0.25);
                            else user.addEffect(InfusedItem.transformInstance(mobEffectInstance));
                            if (effect.is(MobEffects.HEAL)) healTicker.put(effect, 0);
                        }
                    } else if (user.hasEffect(effect)) user.removeEffect(effect);
                });
            }
        }
        if (item.getLastUpdatedMap().getOrDefault(entityID, -1) != entity.tickCount) {
            healTicker.replaceAll((mobEffect, integer) -> integer + 1);
            item.getLastUpdatedMap().put(entityID, entity.tickCount);
        }
        item.getHealTicker().put(entityID, healTicker);
    }
}
