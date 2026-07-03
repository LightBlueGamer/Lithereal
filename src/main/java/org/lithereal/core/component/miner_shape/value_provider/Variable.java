package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum Variable implements StringRepresentable, ValueProvider {
    RADIUS("radius"),
    DEPTH("depth");
    public final String name;
    public static final MapCodec<Variable> CODEC = StringRepresentable.fromEnum(Variable::values).fieldOf("var");

    Variable(String name) {
        this.name = name;
    }

    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return switch (this) {
            case RADIUS -> radius;
            case DEPTH -> depth;
        };
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return CODEC;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
