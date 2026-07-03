package org.lithereal.core.component.miner_shape.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.Map;

public record DirectionalCondition(Map<Direction, ValidityCondition> byDirection) implements ValidityCondition {
    public static final String ID = "directional";
    public static final MapCodec<DirectionalCondition> CODEC = Codec.dispatchedMap(Direction.CODEC, _ -> ValidityCondition.CODEC).xmap(DirectionalCondition::new, DirectionalCondition::byDirection).fieldOf("directions");

    @Override
    public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.byDirection().get(direction).isValid(pos, centerPos, direction, radius, depth);
    }

    @Override
    public MapCodec<? extends ValidityCondition> type() {
        return CODEC;
    }
}
