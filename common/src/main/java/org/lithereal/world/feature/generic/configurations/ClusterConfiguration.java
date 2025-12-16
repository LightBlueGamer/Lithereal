package org.lithereal.world.feature.generic.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record ClusterConfiguration(int floorToCeilingSearchRange, IntProvider height, IntProvider radius,
                                   int maxStalagmiteStalactiteHeightDiff, int heightDeviation,
                                   IntProvider blockLayerThickness, FloatProvider density,
                                   FloatProvider wetness, float chanceOfColumnAtMaxDistanceFromCenter,
                                   int maxDistanceFromEdgeAffectingChanceOfColumn,
                                   int maxDistanceFromCenterAffectingHeightBias,
                                   DripstoneReplacementConfiguration dripstoneReplacementConfiguration) implements FeatureConfiguration {
    public static final Codec<ClusterConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.intRange(1, 512)
                                    .fieldOf("floor_to_ceiling_search_range")
                                    .forGetter(clusterConfiguration -> clusterConfiguration.floorToCeilingSearchRange),
                            IntProvider.codec(1, 128).fieldOf("height").forGetter(clusterConfiguration -> clusterConfiguration.height),
                            IntProvider.codec(1, 128).fieldOf("radius").forGetter(clusterConfiguration -> clusterConfiguration.radius),
                            Codec.intRange(0, 64)
                                    .fieldOf("max_stalagmite_stalactite_height_diff")
                                    .forGetter(clusterConfiguration -> clusterConfiguration.maxStalagmiteStalactiteHeightDiff),
                            Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(clusterConfiguration -> clusterConfiguration.heightDeviation),
                            IntProvider.codec(0, 128)
                                    .fieldOf("block_layer_thickness")
                                    .forGetter(clusterConfiguration -> clusterConfiguration.blockLayerThickness),
                            FloatProvider.codec(0.0F, 2.0F).fieldOf("density").forGetter(clusterConfiguration -> clusterConfiguration.density),
                            FloatProvider.codec(0.0F, 2.0F).fieldOf("wetness").forGetter(clusterConfiguration -> clusterConfiguration.wetness),
                            Codec.floatRange(0.0F, 1.0F)
                                    .fieldOf("chance_of_column_at_max_distance_from_center")
                                    .forGetter(clusterConfiguration -> clusterConfiguration.chanceOfColumnAtMaxDistanceFromCenter),
                            Codec.intRange(1, 64)
                                    .fieldOf("max_distance_from_edge_affecting_chance_of_column")
                                    .forGetter(clusterConfiguration -> clusterConfiguration.maxDistanceFromEdgeAffectingChanceOfColumn),
                            Codec.intRange(1, 64)
                                    .fieldOf("max_distance_from_center_affecting_height_bias")
                                    .forGetter(clusterConfiguration -> clusterConfiguration.maxDistanceFromCenterAffectingHeightBias),
                            DripstoneReplacementConfiguration.CODEC
                                    .forGetter(clusterConfiguration -> clusterConfiguration.dripstoneReplacementConfiguration))
                    .apply(instance, ClusterConfiguration::new)
    );
}