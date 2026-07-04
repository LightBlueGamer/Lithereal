package org.lithereal.core.component.miner_shape.block_pos_processor;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record AxisSelectedBlockPosProcessor(DirectBlockPosProcessor xAxis, DirectBlockPosProcessor yAxis, DirectBlockPosProcessor zAxis) implements BlockPosProcessor {
    public static final MapCodec<AxisSelectedBlockPosProcessor> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(DirectBlockPosProcessor.CODEC.fieldOf("x_axis").forGetter(AxisSelectedBlockPosProcessor::xAxis),
                            DirectBlockPosProcessor.CODEC.fieldOf("y_axis").forGetter(AxisSelectedBlockPosProcessor::yAxis),
                            DirectBlockPosProcessor.CODEC.fieldOf("z_axis").forGetter(AxisSelectedBlockPosProcessor::zAxis))
                    .apply(instance, AxisSelectedBlockPosProcessor::new));

    @Override
    public BlockPos process(Direction direction, BlockPos pos, BlockPos centerPos, double radius, int depth) {
        return switch (direction.getAxis()) {
            case X -> this.xAxis().process(direction, pos, centerPos, radius, depth);
            case Y -> this.yAxis().process(direction, pos, centerPos, radius, depth);
            case Z -> this.zAxis().process(direction, pos, centerPos, radius, depth);
        };
    }

    @Override
    public MapCodec<? extends BlockPosProcessor> type() {
        return CODEC;
    }
}
