package org.lithereal.core.component.miner_shape.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.List;

public record AnyOf(List<ValidityCondition> conditions) implements ValidityCondition {
    public static final String ID = "any_of";
    public static final MapCodec<AnyOf> CODEC = ValidityCondition.CODEC.listOf().xmap(AnyOf::new, AnyOf::conditions).fieldOf("conditions");
    @Override
    public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        for (ValidityCondition condition : conditions) {
            if (condition.isValid(pos, centerPos, direction, radius, depth)) return true;
        }
        return false;
    }

    @Override
    public MapCodec<? extends ValidityCondition> type() {
        return CODEC;
    }
}
