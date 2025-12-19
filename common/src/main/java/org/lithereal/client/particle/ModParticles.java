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

    public static final RegistrySupplier<SimpleParticleType> LITHER_FIRE_FLAME = PARTICLE_TYPES.register("lither_fire_flame", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> BLUE_FIRE_FLAME = PARTICLE_TYPES.register("blue_fire_flame", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> SOUL = PARTICLE_TYPES.register("ethereal_core_soul", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> RETRIBUTION_HOLY_BEAM = PARTICLE_TYPES.register("retribution_holy_beam", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> RETRIBUTION_LIGHT_BURST = PARTICLE_TYPES.register("retribution_light_burst", () ->
            LitherealExpectPlatform.createSimpleParticleType(true));

    public static final RegistrySupplier<SimpleParticleType> CRYSTAL_SPARKLE = PARTICLE_TYPES.register("crystal_sparkle", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> PORTAL_SPARKLE = PARTICLE_TYPES.register("portal_sparkle", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static final RegistrySupplier<SimpleParticleType> PORTAL_EMISSION = PARTICLE_TYPES.register("portal_emission", () ->
            LitherealExpectPlatform.createSimpleParticleType(false));

    public static void register() {
        PARTICLE_TYPES.register();
    }
}
