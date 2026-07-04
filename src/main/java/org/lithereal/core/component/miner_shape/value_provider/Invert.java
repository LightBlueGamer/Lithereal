package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record Invert(ValueProvider baseProvider) implements ValueProvider {
    public static final MapCodec<Invert> CODEC = ValueProvider.CODEC.fieldOf("invert").xmap(Invert::new, Invert::baseProvider);
    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return -this.baseProvider.extractValue(pos, centerPos, direction, radius, depth);
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return CODEC;
    }
}
