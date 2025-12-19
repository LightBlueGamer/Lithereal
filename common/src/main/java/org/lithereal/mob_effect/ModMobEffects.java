package org.lithereal.mob_effect;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.lithereal.Lithereal;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Lithereal.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> BOSS_POWER = EFFECTS.register("boss_power", () ->
            new CustomMobEffect(MobEffectCategory.BENEFICIAL, 12262171, (mobEffectInstance) -> {
                int color = 12262171;
                if (mobEffectInstance.getAmplifier() == 1) color = 5909975;
                else if (mobEffectInstance.getAmplifier() > 1) color = 13985849;
                int alpha = mobEffectInstance.isAmbient() ? 38 : 255;
                return ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, FastColor.ARGB32.color(alpha, color));
            })
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, Lithereal.id("boss_power_attack"), 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, Lithereal.id("boss_power_speed"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.MAX_HEALTH, Lithereal.id("boss_power_health"), 10, AttributeModifier.Operation.ADD_VALUE));
    public static final RegistrySupplier<MobEffect> PROTECTED = EFFECTS.register("protected", () ->
            new CustomMobEffect(MobEffectCategory.BENEFICIAL, 3450863)
                    .addAttributeModifier(Attributes.ARMOR, Lithereal.id("effect.protected"), 2, AttributeModifier.Operation.ADD_VALUE));
    public static final RegistrySupplier<MobEffect> WEAK_ARMOUR = EFFECTS.register("weak_armour", () ->
            new CustomMobEffect(MobEffectCategory.HARMFUL, 11350042)
                    .addAttributeModifier(Attributes.ARMOR, Lithereal.id("effect.weak_armour"), -2, AttributeModifier.Operation.ADD_VALUE));
    public static final RegistrySupplier<MobEffect> RETRIBUTION = EFFECTS.register("retribution", () -> new RetributionMobEffect(MobEffectCategory.HARMFUL, 0x8FCDFC));

    public static void register() {
        EFFECTS.register();
    }
}
