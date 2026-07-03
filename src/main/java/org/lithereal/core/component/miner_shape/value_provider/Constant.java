package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record Constant(double value) implements ValueProvider {
    public static final Constant ZERO = new Constant(0);
    public static final Codec<Constant> CODEC = Codec.DOUBLE.xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = CODEC.fieldOf("value");
    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.value();
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return MAP_CODEC;
    }
}
