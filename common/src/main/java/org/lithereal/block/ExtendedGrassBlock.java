package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.function.Supplier;

public class ExtendedGrassBlock extends GrassBlock {
    public final Supplier<Block> dirtBlock;
    public static final MapCodec<GrassBlock> CODEC = simpleCodec(GrassBlock::new);

    public MapCodec<GrassBlock> codec() {
        return CODEC;
    }

    public ExtendedGrassBlock(Properties properties, Supplier<Block> dirtBlock) {
        super(properties);
        this.dirtBlock = dirtBlock;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        super.performBonemeal(serverLevel, randomSource, blockPos, blockState); // Change impl once more features exist
    }

    private static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState2.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(levelReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getLightBlock(levelReader, blockPos2));
            return i < levelReader.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPos2).is(FluidTags.WATER);
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!canBeGrass(blockState, serverLevel, blockPos)) {
            serverLevel.setBlockAndUpdate(blockPos, dirtBlock.get().defaultBlockState());
        } else {
            if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
                BlockState newState = this.defaultBlockState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                    if (serverLevel.getBlockState(blockPos2).is(dirtBlock.get()) && canPropagate(newState, serverLevel, blockPos2)) {
                        serverLevel.setBlockAndUpdate(blockPos2, newState.setValue(SNOWY, serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW)));
                    }
                }
            }

        }
    }
}
