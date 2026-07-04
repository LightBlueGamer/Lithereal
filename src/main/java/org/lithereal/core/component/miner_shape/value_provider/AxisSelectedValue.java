package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record AxisSelectedValue(ValueProvider xAxis, ValueProvider yAxis, ValueProvider zAxis) implements ValueProvider {
    public static final MapCodec<AxisSelectedValue> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(ValueProvider.CODEC.fieldOf("x_axis").forGetter(AxisSelectedValue::xAxis),
                            ValueProvider.CODEC.fieldOf("y_axis").forGetter(AxisSelectedValue::yAxis),
                            ValueProvider.CODEC.fieldOf("z_axis").forGetter(AxisSelectedValue::zAxis))
                    .apply(instance, AxisSelectedValue::new));

    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return switch (direction.getAxis()) {
            case X -> this.xAxis().extractValue(pos, centerPos, direction, radius, depth);
            case Y -> this.yAxis().extractValue(pos, centerPos, direction, radius, depth);
            case Z -> this.zAxis().extractValue(pos, centerPos, direction, radius, depth);
        };
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return CODEC;
    }
}
