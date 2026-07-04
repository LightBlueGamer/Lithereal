package org.lithereal.core.component.miner_shape.block_pos_processor;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.lithereal.core.component.miner_shape.value_provider.Constant;
import org.lithereal.core.component.miner_shape.value_provider.ValueProvider;

public record DirectBlockPosProcessor(OriginProcessor x, ValueProvider xOffset, OriginProcessor y, ValueProvider yOffset, OriginProcessor z, ValueProvider zOffset) implements BlockPosProcessor {
    private static final MapCodec<DirectBlockPosProcessor> FULL_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(OriginProcessor.CODEC.fieldOf("x").forGetter(DirectBlockPosProcessor::x),
                            ValueProvider.CODEC.optionalFieldOf("x_offset", Constant.ZERO).forGetter(DirectBlockPosProcessor::xOffset),
                            OriginProcessor.CODEC.fieldOf("y").forGetter(DirectBlockPosProcessor::y),
                            ValueProvider.CODEC.optionalFieldOf("y_offset", Constant.ZERO).forGetter(DirectBlockPosProcessor::yOffset),
                            OriginProcessor.CODEC.fieldOf("z").forGetter(DirectBlockPosProcessor::z),
                            ValueProvider.CODEC.optionalFieldOf("z_offset", Constant.ZERO).forGetter(DirectBlockPosProcessor::zOffset))
                    .apply(instance, DirectBlockPosProcessor::new));
    private static final MapCodec<DirectBlockPosProcessor> POINT_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(OriginProcessor.CODEC.fieldOf("point").forGetter(DirectBlockPosProcessor::x),
                            ValueProvider.CODEC.optionalFieldOf("x_offset", Constant.ZERO).forGetter(DirectBlockPosProcessor::xOffset),
                            ValueProvider.CODEC.optionalFieldOf("y_offset", Constant.ZERO).forGetter(DirectBlockPosProcessor::yOffset),
                            ValueProvider.CODEC.optionalFieldOf("z_offset", Constant.ZERO).forGetter(DirectBlockPosProcessor::zOffset))
                    .apply(instance, DirectBlockPosProcessor::new));
    public static final MapCodec<DirectBlockPosProcessor> CODEC = Codec.mapEither(FULL_CODEC, POINT_CODEC)
            .xmap(Either::unwrap, Either::left);

    public DirectBlockPosProcessor(OriginProcessor point, ValueProvider xOffset, ValueProvider yOffset, ValueProvider zOffset) {
        this(point, xOffset, point, yOffset, point, zOffset);
    }

    public DirectBlockPosProcessor(OriginProcessor x, OriginProcessor y, OriginProcessor z) {
        this(x, Constant.ZERO, y, Constant.ZERO, z, Constant.ZERO);
    }

    public BlockPos process(Direction direction, BlockPos pos, BlockPos centerPos, double radius, int depth) {
        if (this.x() == this.y() && this.y() == this.z())
            return this.x().choose(pos, centerPos).offset(this.xOffset().roundedValue(pos, centerPos, direction, radius, depth),
                    this.yOffset().roundedValue(pos, centerPos, direction, radius, depth),
                    this.zOffset().roundedValue(pos, centerPos, direction, radius, depth));
        return new BlockPos(this.x().choose(pos.getX(), centerPos.getX()), this.y().choose(pos.getY(), centerPos.getY()), this.z().choose(pos.getZ(), centerPos.getZ()))
                .offset(this.xOffset().roundedValue(pos, centerPos, direction, radius, depth),
                        this.yOffset().roundedValue(pos, centerPos, direction, radius, depth),
                        this.zOffset().roundedValue(pos, centerPos, direction, radius, depth));
    }

    @Override
    public MapCodec<? extends BlockPosProcessor> type() {
        return CODEC;
    }
}
