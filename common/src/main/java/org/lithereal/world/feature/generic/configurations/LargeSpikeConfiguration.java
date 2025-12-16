package org.lithereal.world.feature.generic.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record LargeSpikeConfiguration(int floorToCeilingSearchRange, IntProvider columnRadius,
                                      FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio,
                                      FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness,
                                      FloatProvider windSpeed, int minRadiusForWind,
                                      float minBluntnessForWind, DripstoneReplacementConfiguration dripstoneReplacementConfiguration) implements FeatureConfiguration {
    public static final Codec<LargeSpikeConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.intRange(1, 512)
                                    .fieldOf("floor_to_ceiling_search_range")
                                    .orElse(30)
                                    .forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.floorToCeilingSearchRange),
                            IntProvider.codec(1, 60).fieldOf("column_radius").forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.columnRadius),
                            FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.heightScale),
                            Codec.floatRange(0.1F, 1.0F)
                                    .fieldOf("max_column_radius_to_cave_height_ratio")
                                    .forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.maxColumnRadiusToCaveHeightRatio),
                            FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.stalactiteBluntness),
                            FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.stalagmiteBluntness),
                            FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.windSpeed),
                            Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.minRadiusForWind),
                            Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.minBluntnessForWind),
                            DripstoneReplacementConfiguration.CODEC.forGetter(largeSpikeConfiguration -> largeSpikeConfiguration.dripstoneReplacementConfiguration)
                    )
                    .apply(instance, LargeSpikeConfiguration::new)
    );
}