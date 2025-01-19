package org.lithereal.data.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class OrePlacement {
    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_, BiomeFilter biomeFilter) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int veinsPerChunk, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(veinsPerChunk), placementModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int chunksPerVein, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(chunksPerVein), placementModifier);
    }
}