package org.lithereal.core.component.miner_shape.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.List;

public record AllOf(List<ValidityCondition> conditions) implements ValidityCondition {
    public static final String ID = "all_of";
    public static final MapCodec<AllOf> CODEC = ValidityCondition.CODEC.listOf().xmap(AllOf::new, AllOf::conditions).fieldOf("conditions");
    @Override
    public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        for (ValidityCondition condition : conditions) {
            if (!condition.isValid(pos, centerPos, direction, radius, depth)) return false;
        }
        return true;
    }

    @Override
    public MapCodec<AllOf> type() {
        return CODEC;
    }
}
