package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.lithereal.core.component.miner_shape.block_pos_processor.BlockPosProcessor;

public record BlockPosValue(BlockPosProcessor source, Direction.Axis axis) implements ValueProvider {
    public static final MapCodec<BlockPosValue> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(BlockPosProcessor.CODEC.fieldOf("source").forGetter(BlockPosValue::source),
                            Direction.Axis.CODEC.fieldOf("axis").forGetter(BlockPosValue::axis))
                    .apply(instance, BlockPosValue::new));
    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.source().process(direction, pos, centerPos, radius, depth).get(this.axis());
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return CODEC;
    }
}
