package org.lithereal.core.component.miner_shape.block_pos_processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.Map;

public record DirectionalBlockPosProcessor(Map<Direction, DirectBlockPosProcessor> byDirection) implements BlockPosProcessor {
    public static final MapCodec<DirectionalBlockPosProcessor> CODEC = Codec.dispatchedMap(Direction.CODEC, _ -> DirectBlockPosProcessor.CODEC.codec()).xmap(DirectionalBlockPosProcessor::new, DirectionalBlockPosProcessor::byDirection).fieldOf("directions");

    @Override
    public BlockPos process(Direction direction, BlockPos pos, BlockPos centerPos) {
        return this.byDirection().get(direction).process(direction, pos, centerPos);
    }

    @Override
    public MapCodec<? extends BlockPosProcessor> type() {
        return CODEC;
    }
}
