package org.lithereal.data.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.lithereal.Lithereal;

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

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name));
    }
}