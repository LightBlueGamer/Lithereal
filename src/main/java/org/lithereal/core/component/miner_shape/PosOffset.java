package org.lithereal.core.component.miner_shape;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.lithereal.core.component.miner_shape.block_pos_processor.OriginProcessor;
import org.lithereal.core.component.miner_shape.value_provider.Constant;
import org.lithereal.core.component.miner_shape.value_provider.ValueProvider;

public record PosOffset(OriginProcessor origin, ValueProvider xOffset, ValueProvider yOffset, ValueProvider zOffset) {
    public static Codec<PosOffset> codec(OriginProcessor origin) {
        return RecordCodecBuilder.create(instance ->
                instance.group(ValueProvider.CODEC.optionalFieldOf("x", Constant.ZERO).forGetter(PosOffset::xOffset),
                                ValueProvider.CODEC.optionalFieldOf("y", Constant.ZERO).forGetter(PosOffset::yOffset),
                                ValueProvider.CODEC.optionalFieldOf("z", Constant.ZERO).forGetter(PosOffset::zOffset))
                        .apply(instance, (x, y, z) -> new PosOffset(origin, x, y, z)));
    }
    public static final PosOffset ZERO_CENTER_OFFSET = new PosOffset(OriginProcessor.CENTER, Constant.ZERO, Constant.ZERO, Constant.ZERO);

    public BlockPos process(Direction direction, BlockPos pos, BlockPos centerPos, double radius, int depth) {
        return this.origin().choose(pos, centerPos).offset(this.xOffset().roundedValue(pos, centerPos, direction, radius, depth),
            this.yOffset().roundedValue(pos, centerPos, direction, radius, depth),
            this.zOffset().roundedValue(pos, centerPos, direction, radius, depth));
    }
}
