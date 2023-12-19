package org.lithereal.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.ModBlocks;

public class BlueFireBlock extends BaseFireBlock {
    public BlueFireBlock(Properties properties, float lightLevel) {
        super(properties, lightLevel);
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        return this.canSurvive(blockState, levelAccessor, blockPos) ? this.defaultBlockState() : net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canSurviveOnBlock(levelReader.getBlockState(blockPos.below()));
    }

    public static boolean canSurviveOnBlock(BlockState blockState) {
        return blockState.is(ModBlocks.BURNING_LITHERITE_BLOCK.get());
    }

    protected boolean canBurn(BlockState blockState) {
        return true;
    }
}
