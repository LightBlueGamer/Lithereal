package org.lithereal.client.particle;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Lithereal.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> BLUE_FIRE_FLAME = PARTICLE_TYPES.register("blue_fire_flame", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> SOUL = PARTICLE_TYPES.register("ethereal_core_soul", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> CRYSTAL_SPARKLE = PARTICLE_TYPES.register("crystal_sparkle", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static void register() {
        PARTICLE_TYPES.register();
    }
}
