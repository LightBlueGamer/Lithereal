package org.lithereal.core.component.miner_shape.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record Constant(boolean value) implements ValidityCondition {
    public static final String ID = "constant";
    public static final Codec<Constant> CODEC = Codec.BOOL.xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = CODEC.fieldOf("value");
    @Override
    public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.value();
    }

    @Override
    public MapCodec<? extends ValidityCondition> type() {
        return MAP_CODEC;
    }
}
