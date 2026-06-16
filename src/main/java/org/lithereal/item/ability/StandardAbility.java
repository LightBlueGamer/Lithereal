package org.lithereal.item.ability;

import net.minecraft.Optionull;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.lithereal.item.infused.InfusedItem;
import org.lithereal.tags.ModTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record StandardAbility<I extends AbilityItem>(List<ArmorMaterial> armorMaterials, List<MobEffectInstance> attackEffects, List<MobEffectInstance> passiveEffects) implements IAbility<I> {
    @Override
    public void onAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel serverLevel) attackEffects.forEach(mobEffectInstance -> InfusedAbility.applyEffectToTarget(serverLevel, mobEffectInstance, attacked, attacker, attackEffects.size(), false));
    }

    @Override
    public void postAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {

    }

    @Override
    public void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {

    }

    @Override
    public void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {
        UUID entityID = entity.getUUID();
        Map<Holder<MobEffect>, Integer> healTicker = Optionull.mapOrElse(item.getHealTicker().get(entityID), Function.identity(), HashMap::new);
        if (entity instanceof LivingEntity user && !level.isClientSide()) {
            Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
            EquipmentSlot.Type type = equippable == null ? EquipmentSlot.Type.HUMANOID_ARMOR : equippable.slot().getType();
            if (hasFullSuitOfArmorOn(user, type) && hasCorrectArmorOn(armorMaterials, user, type) && level.getGameTime() % 80 == 0) {
                boolean multiEffect = passiveEffects.size() > 1;
                float durationFactor = itemStack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 0.1F);
                passiveEffects.forEach((mobEffectInstance) -> {
                    Holder<MobEffect> effect = mobEffectInstance.getEffect();
                    boolean effectivelyBeneficial = (effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl) || (user.isInvertedHealAndHarm() && effect.is(ModTags.CAN_BE_INVERTED_HARM))) &&
                            (!user.isInvertedHealAndHarm() || !effect.is(ModTags.CAN_BE_INVERTED_HEAL));
                    if (effectivelyBeneficial || multiEffect) {
                        if (!effect.is(ModTags.DELAYED_INSTANT) || IAbility.getValueFromMapForEffect(healTicker, effect) >= 400) {
                            if (effect.value().isInstantenous())
                                effect.value().applyInstantenousEffect((ServerLevel) level, null, null, user, mobEffectInstance.getAmplifier(), 0.25);
                            else user.addEffect(InfusedItem.transformInstance(mobEffectInstance, durationFactor));
                            if (effect.is(ModTags.DELAYED_INSTANT)) healTicker.put(effect, 0);
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
