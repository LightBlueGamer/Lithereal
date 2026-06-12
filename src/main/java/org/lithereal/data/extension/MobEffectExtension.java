package org.lithereal.data.extension;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;

public interface MobEffectExtension {
    default void lithereal$setParticleFactory(Function<MobEffectInstance, ParticleOptions> factory) {
        throw new IllegalStateException("Implemented via mixin");
    }
}
