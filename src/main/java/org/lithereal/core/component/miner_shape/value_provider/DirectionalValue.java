package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;

public record DirectionalValue(Map<Direction, ValueProvider> byDirection) implements ValueProvider {
    public static final MapCodec<DirectionalValue> CODEC = Codec.dispatchedMap(Direction.CODEC, _ -> ValueProvider.CODEC).xmap(DirectionalValue::new, DirectionalValue::byDirection).fieldOf("directions");

    public DirectionalValue(ValueProvider x, ValueProvider y, ValueProvider z) {
        this(Util.make(new HashMap<>(), map -> {
            map.put(Direction.Axis.X.getPositive(), x);
            map.put(Direction.Axis.X.getNegative(), new Invert(x));
            map.put(Direction.Axis.Y.getPositive(), y);
            map.put(Direction.Axis.Y.getNegative(), new Invert(y));
            map.put(Direction.Axis.Z.getPositive(), z);
            map.put(Direction.Axis.Z.getNegative(), new Invert(z));
        }));
    }

    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.byDirection().get(direction).extractValue(pos, centerPos, direction, radius, depth);
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return CODEC;
    }
}
