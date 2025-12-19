package org.lithereal.item.ability;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.Lithereal;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.mob_effect.ModMobEffects;

import java.util.*;

public enum Ability {
    BURNING(new ThermalAbility<>(0, 1, ThermalAbility.ArmorType.BURNING,
            List.of(ModArmorMaterials.BURNING_LITHERITE),
            List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0)))),
    FROZEN(new ThermalAbility<>(0, 1, ThermalAbility.ArmorType.FREEZING,
            List.of(ModArmorMaterials.FROZEN_LITHERITE),
            Collections.emptyList())),
    SMOLDERING(new ThermalAbility<>(2, 1.5F, ThermalAbility.ArmorType.BURNING,
            List.of(ModArmorMaterials.SMOLDERING_LITHERITE, ModArmorMaterials.FROSTBITTEN_LITHERITE, ModArmorMaterials.INFUSED_LITHERITE),
            List.of(new MobEffectInstance(Lithereal.asHolder(ModMobEffects.PROTECTED), 200, 0),
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0)))),
    FROSTBITTEN(new ThermalAbility<>(1, 1.25F, ThermalAbility.ArmorType.FROSTBURN,
            List.of(ModArmorMaterials.SMOLDERING_LITHERITE, ModArmorMaterials.FROSTBITTEN_LITHERITE, ModArmorMaterials.INFUSED_LITHERITE),
            List.of(new MobEffectInstance(Lithereal.asHolder(ModMobEffects.PROTECTED), 200, 0)))),
    WITHERING(new WitheringAbility<>(Collections.singletonList(ModArmorMaterials.WITHERING_LITHERITE))),
    INFUSED(new InfusedAbility<>(List.of(ModArmorMaterials.SMOLDERING_LITHERITE, ModArmorMaterials.FROSTBITTEN_LITHERITE, ModArmorMaterials.INFUSED_LITHERITE))),
    ODYSIUM(new StandardAbility<>(List.of(ModArmorMaterials.ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM), Collections.emptyList(), List.of(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0), new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0)))),
    ENHANCED_ODYSIUM(new StandardAbility<>(Collections.singletonList(ModArmorMaterials.ENHANCED_ODYSIUM), Collections.singletonList(new MobEffectInstance(Lithereal.asHolder(ModMobEffects.RETRIBUTION), 150)), List.of(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1), new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0), new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 200, 2))));
    public final IAbility<?> ability;
    Ability(IAbility<?> ability) {
        this.ability = ability;
    }
    public void onAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        ability.onAttackRaw(item, itemStack, attacked, attacker);
    }
    public void postAttack(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        ability.postAttackRaw(item, itemStack, attacked, attacker);
    }
    public void onItemTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        ability.onItemTickRaw(item, itemStack, level, entity, slot, isSelected);
    }
    public void onArmourTick(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        ability.onArmourTickRaw(item, itemStack, level, entity, slot, isSelected);
    }
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
