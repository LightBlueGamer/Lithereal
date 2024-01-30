package org.lithereal.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.LitherCollectorBlockEntity;

public class LitherCollectorBlock extends BaseEntityBlock {
    protected LitherCollectorBlock(Properties properties) {
        super(properties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LitherCollectorBlockEntity(blockPos, blockState);
    }
}
