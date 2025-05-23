package org.lithereal.world.feature;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.lithereal.Lithereal;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Lithereal.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<EtherealCoreArenaFeature> ETHEREAL_CORE_ARENA = registerStructureProcessorType("ethereal_core_arena", EtherealCoreArenaFeature::new);
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistrySupplier<F> registerStructureProcessorType(String name, Supplier<F> feature) {
        return FEATURES.register(name, feature);
    }

    public static void register() {
        FEATURES.register();
    }
}
