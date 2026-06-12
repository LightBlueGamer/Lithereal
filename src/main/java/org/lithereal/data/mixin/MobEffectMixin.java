package org.lithereal.data.mixin;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.lithereal.data.extension.MobEffectExtension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Function;

@Mixin(MobEffect.class)
public class MobEffectMixin implements MobEffectExtension {
    @Shadow @Final @Mutable
    private Function<MobEffectInstance, ParticleOptions> particleFactory;

    @Override
    public void lithereal$setParticleFactory(Function<MobEffectInstance, ParticleOptions> factory) {
        particleFactory = factory;
    }
}
