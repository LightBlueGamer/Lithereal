package org.lithereal.core.component.miner_shape.block_pos_processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum OriginProcessor implements StringRepresentable, BlockPosProcessor {
    ORIGIN("origin"),
    TARGET("target"),
    CENTER("center");

    private final String name;
    public static final Codec<OriginProcessor> CODEC = StringRepresentable.fromEnum(OriginProcessor::values);

    OriginProcessor(String name) {
        this.name = name;
    }

    public BlockPos choose(BlockPos pos, BlockPos centerPos) {
        return switch (this) {
            case ORIGIN -> BlockPos.ZERO;
            case TARGET -> pos;
            case CENTER -> centerPos;
        };
    }

    public int choose(int coord, int centerCoord) {
        return switch (this) {
            case ORIGIN -> 0;
            case TARGET -> coord;
            case CENTER -> centerCoord;
        };
    }

    @Override
    public @NonNull String getSerializedName() {
        return this.name;
    }

    @Override
    public BlockPos process(Direction direction, BlockPos pos, BlockPos centerPos) {
        return choose(pos, centerPos);
    }

    @Override
    public MapCodec<? extends BlockPosProcessor> type() {
        return CODEC.fieldOf("source");
    }
}