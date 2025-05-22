package org.lithereal.mob_effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;

public class CustomMobEffect extends MobEffect {
    public CustomMobEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    public CustomMobEffect(MobEffectCategory mobEffectCategory, int i, ParticleOptions particleOptions) {
        super(mobEffectCategory, i, particleOptions);
    }

    public CustomMobEffect(MobEffectCategory mobEffectCategory, int i, Function<MobEffectInstance, ParticleOptions> particleFactory) {
        super(mobEffectCategory, i);
        lithereal$setParticleFactory(particleFactory);
    }
}
