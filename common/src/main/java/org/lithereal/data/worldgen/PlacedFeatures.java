package org.lithereal.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.lithereal.Lithereal;

import java.util.List;

public class PlacedFeatures {
    public static final ResourceKey<PlacedFeature> LITHERITE_PLACED_KEY = createKey("litherite_ore_placed");
    public static final ResourceKey<PlacedFeature> NERITH_PLACED_KEY = createKey("nerith_ore_placed");
    public static final ResourceKey<PlacedFeature> LUMINIUM_PLACED_KEY = createKey("luminium_ore_placed");
    public static final ResourceKey<PlacedFeature> CYRUM_PLACED_KEY = createKey("cyrum_ore_placed");
    public static final ResourceKey<PlacedFeature> COPALITE_PLACED_KEY = createKey("copalite_ore_placed");
    public static final ResourceKey<PlacedFeature> AURELITE_PLACED_KEY = createKey("aurelite_ore_placed");
    public static final ResourceKey<PlacedFeature> ALLIAN_PLACED_KEY = createKey("allian_ore_placed");

    public static final ResourceKey<PlacedFeature> ELUNITE_PLACED_KEY = createKey("elunite_ore_placed");
    public static final ResourceKey<PlacedFeature> CHRYON_PLACED_KEY = createKey("chryon_ore_placed");

    public static final ResourceKey<PlacedFeature> SATURNITE_PLACED_KEY = createKey("saturnite_ore_placed");
    public static final ResourceKey<PlacedFeature> HELLIONITE_PLACED_KEY = createKey("hellionite_ore_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CYRUM_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.CYRUM_ORE_KEY),
                OrePlacement.commonOrePlacement(20, // Most frequent, 20 veins per chunk (Cyrum)
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(80)))); // Cyrum only above Y=0

        register(context, AURELITE_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.AURELITE_ORE_KEY),
                OrePlacement.commonOrePlacement(10, // Slightly rarer than Cyrum
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-48), VerticalAnchor.aboveBottom(48))));

        register(context, COPALITE_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.COPALITE_ORE_KEY),
                OrePlacement.commonOrePlacement(10, // Same as Aurelite
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-48), VerticalAnchor.aboveBottom(48))));

        register(context, HELLIONITE_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.HELLIONITE_ORE_KEY),
                OrePlacement.commonOrePlacement(8, // Slightly rarer than Aurelite and Copalite
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))); // Nether only

        register(context, LITHERITE_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.LITHERITE_ORE_KEY),
                OrePlacement.commonOrePlacement(6, // Less frequent than Hellionite
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));

        register(context, SATURNITE_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.SATURNITE_ORE_KEY),
                OrePlacement.commonOrePlacement(5, // One vein every 5 chunks (rare)
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48)))); // Nether only

        register(context, CHRYON_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.CHRYON_ORE_KEY),
                OrePlacement.commonOrePlacement(5, // Same rarity as Saturnite
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256)))); // End only

        register(context, ELUNITE_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.ELUNITE_ORE_KEY),
                OrePlacement.commonOrePlacement(4, // Less frequent than Chryon and Saturnite
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256)))); // End only

        register(context, ALLIAN_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.ALLIAN_ORE_KEY),
                OrePlacement.rareOrePlacement(5, // Same as Elunite
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(256))));

        register(context, LUMINIUM_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.LUMINIUM_ORE_KEY),
                OrePlacement.commonOrePlacement(3, // Less frequent than Allian and Elunite
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(64))));

        register(context, NERITH_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.NERITH_ORE_KEY),
                OrePlacement.rareOrePlacement(5, // One vein every 5 chunks (rare)
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(32))));


    }


    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}