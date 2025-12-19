package org.lithereal.item.ability;

import net.minecraft.core.Holder;
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
import net.minecraft.world.level.Level;
import org.lithereal.item.infused.InfusedItem;
import org.lithereal.tags.ModTags;
import org.lithereal.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record StandardAbility<I extends AbilityItem>(List<Holder<ArmorMaterial>> armorMaterials, List<MobEffectInstance> attackEffects, List<MobEffectInstance> passiveEffects, Map<Ability.IdentityForPlayer, Integer> healTickerMap) implements IAbility<I> {
    public StandardAbility(List<Holder<ArmorMaterial>> armorMaterials, List<MobEffectInstance> attackEffects, List<MobEffectInstance> passiveEffects) {
        this(armorMaterials, attackEffects, passiveEffects, new WeakHashMap<>());
    }
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
        Ability.IdentityForPlayer entityID = new Ability.IdentityForPlayer(entity.getUUID(), item);
        if (!healTickerMap.containsKey(entityID))
            healTickerMap.put(entityID, 0);
        AtomicInteger healTicker = new AtomicInteger(healTickerMap.get(entityID));
        if (entity instanceof Player player && !level.isClientSide()) {
            if (hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(armorMaterials, player) && level.getGameTime() % 80 == 0) {
                boolean multiEffect = passiveEffects.size() > 1;
                passiveEffects.forEach((mobEffectInstance) -> {
                    Holder<MobEffect> effect = mobEffectInstance.getEffect();
                    boolean effectivelyBeneficial = effect.value().isBeneficial() || effect.is(ModTags.PSEUDO_BENEFICIAl);
                    if (effectivelyBeneficial || multiEffect) {
                        if (!effect.is(MobEffects.HEAL) || healTicker.get() >= 400) {
                            if (effect.value().isInstantenous())
                                effect.value().applyInstantenousEffect(null, null, player, mobEffectInstance.getAmplifier(), 0.25);
                            else player.addEffect(InfusedItem.transformInstance(mobEffectInstance));
                            if (effect.is(MobEffects.HEAL)) healTicker.set(0);
                        }
                    } else if (player.hasEffect(effect)) player.removeEffect(effect);
                });
            }
        }
        healTickerMap.put(entityID, healTicker.incrementAndGet());
    }
}
