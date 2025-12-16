package org.lithereal.world.feature.generic.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;

import java.util.Optional;

public record DripstoneReplacementConfiguration(float corePercentage,
                                                RuleBasedBlockStateProvider core,
                                                RuleBasedBlockStateProvider outer,
                                                Optional<BlockStateProvider> dripstoneSpike,
                                                Optional<BlockStateProvider> attached,
                                                HolderSet<Block> replaceable,
                                                HolderSet<Block> placedIgnoreBlocks,
                                                HolderSet<Block> baseBlocks) {
    public static final MapCodec<DripstoneReplacementConfiguration> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(Codec.floatRange(0, 1)
                                    .fieldOf("core_percentage")
                                    .forGetter(DripstoneReplacementConfiguration::corePercentage),
                            RuleBasedBlockStateProvider.CODEC
                                    .fieldOf("core_block")
                                    .forGetter(DripstoneReplacementConfiguration::core),
                            RuleBasedBlockStateProvider.CODEC
                                    .fieldOf("outer_block")
                                    .forGetter(DripstoneReplacementConfiguration::outer),
                            BlockStateProvider.CODEC
                                    .optionalFieldOf("dripstone_spike")
                                    .forGetter(DripstoneReplacementConfiguration::dripstoneSpike),
                            BlockStateProvider.CODEC
                                    .optionalFieldOf("attached")
                                    .forGetter(DripstoneReplacementConfiguration::attached),
                            RegistryCodecs.homogeneousList(Registries.BLOCK)
                                    .fieldOf("replaceable")
                                    .forGetter(DripstoneReplacementConfiguration::replaceable),
                            RegistryCodecs.homogeneousList(Registries.BLOCK)
                                    .fieldOf("placed_ignore_blocks")
                                    .forGetter(DripstoneReplacementConfiguration::placedIgnoreBlocks),
                            RegistryCodecs.homogeneousList(Registries.BLOCK)
                                    .fieldOf("base_blocks")
                                    .forGetter(DripstoneReplacementConfiguration::placedIgnoreBlocks))
                    .apply(instance, DripstoneReplacementConfiguration::new));
}
