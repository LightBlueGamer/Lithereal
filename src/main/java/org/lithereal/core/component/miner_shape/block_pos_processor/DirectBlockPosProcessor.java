package org.lithereal.core.component.miner_shape.block_pos_processor;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record DirectBlockPosProcessor(OriginProcessor x, int xOffset, OriginProcessor y, int yOffset, OriginProcessor z, int zOffset) implements BlockPosProcessor {
    private static final MapCodec<DirectBlockPosProcessor> FULL_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(OriginProcessor.CODEC.fieldOf("x").forGetter(DirectBlockPosProcessor::x),
                            Codec.INT.optionalFieldOf("x_offset", 0).forGetter(DirectBlockPosProcessor::xOffset),
                            OriginProcessor.CODEC.fieldOf("y").forGetter(DirectBlockPosProcessor::y),
                            Codec.INT.optionalFieldOf("y_offset", 0).forGetter(DirectBlockPosProcessor::yOffset),
                            OriginProcessor.CODEC.fieldOf("z").forGetter(DirectBlockPosProcessor::z),
                            Codec.INT.optionalFieldOf("z_offset", 0).forGetter(DirectBlockPosProcessor::zOffset))
                    .apply(instance, DirectBlockPosProcessor::new));
    private static final MapCodec<DirectBlockPosProcessor> POINT_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(OriginProcessor.CODEC.fieldOf("point").forGetter(DirectBlockPosProcessor::x),
                            Codec.INT.optionalFieldOf("x_offset", 0).forGetter(DirectBlockPosProcessor::xOffset),
                            Codec.INT.optionalFieldOf("y_offset", 0).forGetter(DirectBlockPosProcessor::yOffset),
                            Codec.INT.optionalFieldOf("z_offset", 0).forGetter(DirectBlockPosProcessor::zOffset))
                    .apply(instance, DirectBlockPosProcessor::new));
    public static final MapCodec<DirectBlockPosProcessor> CODEC = Codec.mapEither(FULL_CODEC, POINT_CODEC)
            .xmap(Either::unwrap, Either::left);

    public DirectBlockPosProcessor(OriginProcessor point, int xOffset, int yOffset, int zOffset) {
        this(point, xOffset, point, yOffset, point, zOffset);
    }

    public DirectBlockPosProcessor(OriginProcessor x, OriginProcessor y, OriginProcessor z) {
        this(x, 0, y, 0, z, 0);
    }

    public BlockPos process(Direction direction, BlockPos pos, BlockPos centerPos) {
        if (this.x() == this.y() && this.y() == this.z())
            return this.x().choose(pos, centerPos).offset(this.xOffset(), this.yOffset(), this.zOffset());
        return new BlockPos(this.x().choose(pos.getX(), centerPos.getX()), this.y().choose(pos.getY(), centerPos.getY()), this.z().choose(pos.getZ(), centerPos.getZ()))
                .offset(this.xOffset(), this.yOffset(), this.zOffset());
    }

    @Override
    public MapCodec<? extends BlockPosProcessor> type() {
        return CODEC;
    }
}
