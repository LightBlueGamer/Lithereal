package org.lithereal.core.component.miner_shape.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record AxisSelectedCondition(ValidityCondition xAxis, ValidityCondition yAxis, ValidityCondition zAxis) implements ValidityCondition {
    public static final String ID = "axis_selected";
    public static final MapCodec<AxisSelectedCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(ValidityCondition.CODEC.fieldOf("x_axis").forGetter(AxisSelectedCondition::xAxis),
                            ValidityCondition.CODEC.fieldOf("y_axis").forGetter(AxisSelectedCondition::yAxis),
                            ValidityCondition.CODEC.fieldOf("z_axis").forGetter(AxisSelectedCondition::zAxis))
                    .apply(instance, AxisSelectedCondition::new));

    @Override
    public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return switch (direction.getAxis()) {
            case X -> this.xAxis().isValid(pos, centerPos, direction, radius, depth);
            case Y -> this.yAxis().isValid(pos, centerPos, direction, radius, depth);
            case Z -> this.zAxis().isValid(pos, centerPos, direction, radius, depth);
        };
    }

    @Override
    public MapCodec<? extends ValidityCondition> type() {
        return CODEC;
    }
}
