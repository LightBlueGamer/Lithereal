package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MushroomBlock extends BushBlock {
    public static final MapCodec<MushroomBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(propertiesCodec()).apply(instance, MushroomBlock::new));
    protected static final VoxelShape SHAPE = Block.box(5.0F, 0.0F, 5.0F, 11.0F, 6.0F, 11.0F);

    public @NotNull MapCodec<MushroomBlock> codec() {
        return CODEC;
    }

    public MushroomBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(25) == 0) {
            int i = 5;

            for(BlockPos blockPos2 : BlockPos.betweenClosed(blockPos.offset(-4, -1, -4), blockPos.offset(4, 1, 4))) {
                if (serverLevel.getBlockState(blockPos2).is(this)) {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockPos3 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(2) - randomSource.nextInt(2), randomSource.nextInt(3) - 1);

            for(int k = 0; k < 4; ++k) {
                if (serverLevel.isEmptyBlock(blockPos3) && blockState.canSurvive(serverLevel, blockPos3)) {
                    blockPos = blockPos3;
                }

                blockPos3 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(2) - randomSource.nextInt(2), randomSource.nextInt(3) - 1);
            }

            if (serverLevel.isEmptyBlock(blockPos3) && blockState.canSurvive(serverLevel, blockPos3)) {
                serverLevel.setBlock(blockPos3, blockState, 2);
            }
        }

    }

    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.isSolidRender(blockGetter, blockPos);
    }

    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        } else {
            return levelReader.getRawBrightness(blockPos, 0) < 13 && this.mayPlaceOn(blockState2, levelReader, blockPos2);
        }
    }
}
