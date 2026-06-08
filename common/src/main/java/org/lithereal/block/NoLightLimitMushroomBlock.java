package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class NoLightLimitMushroomBlock extends MushroomBlock {
    public static final MapCodec<NoLightLimitMushroomBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(mushroomBlock -> mushroomBlock.feature), propertiesCodec()
                    )
                    .apply(instance, NoLightLimitMushroomBlock::new)
    );
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public NoLightLimitMushroomBlock(ResourceKey<ConfiguredFeature<?, ?>> resourceKey, Properties properties) {
        super(resourceKey, properties);
        this.feature = resourceKey;
    }

    @Override
    public MapCodec codec() {
        return CODEC;
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        return blockState2.is(BlockTags.MUSHROOM_GROW_BLOCK) || this.mayPlaceOn(blockState2, levelReader, blockPos2);
    }
}
