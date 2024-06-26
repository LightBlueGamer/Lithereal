package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.ModBlocks;

public class BurningLitheriteBlockEntity extends BlockEntity {

    public BurningLitheriteBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BURNING_LITHERITE_BLOCK.get(), pos, state);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide()) return;

        if(level.getBlockState(blockPos.above()).getBlock() == Blocks.FIRE) {
            level.setBlockAndUpdate(blockPos.above(), ModBlocks.BLUE_FIRE.get().defaultBlockState());
        }
    }
}
