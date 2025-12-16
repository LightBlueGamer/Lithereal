package org.lithereal.world.feature;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.lithereal.Lithereal;
import org.lithereal.world.feature.generic.ClusterFeature;
import org.lithereal.world.feature.generic.LargeSpikeFeature;
import org.lithereal.world.feature.generic.configurations.ClusterConfiguration;
import org.lithereal.world.feature.generic.configurations.LargeSpikeConfiguration;

import java.util.function.Supplier;

public class ModFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PHANTOM_OAK = createConfiguredFeatureKey("phantom_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_PHANTOM_OAK = createConfiguredFeatureKey("fancy_phantom_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_PHANTOM_OAK = createConfiguredFeatureKey("mega_phantom_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_PHANTOM_OAK = createConfiguredFeatureKey("super_phantom_oak");
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Lithereal.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<EtherealCoreArenaFeature> ETHEREAL_CORE_ARENA = registerFeature("ethereal_core_arena", EtherealCoreArenaFeature::new);
    public static final RegistrySupplier<ClusterFeature> CLUSTER = registerFeature("cluster", () -> new ClusterFeature(ClusterConfiguration.CODEC));
    public static final RegistrySupplier<LargeSpikeFeature> LARGE_SPIKE = registerFeature("large_spike", () -> new LargeSpikeFeature(LargeSpikeConfiguration.CODEC));
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistrySupplier<F> registerFeature(String name, Supplier<F> feature) {
        return FEATURES.register(name, feature);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Lithereal.id(name));
    }

    public static void register() {
        FEATURES.register();
    }
}
