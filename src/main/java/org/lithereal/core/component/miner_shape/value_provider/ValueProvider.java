package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

public interface ValueProvider {
    ExtraCodecs.LateBoundIdMapper<@NotNull String, @NotNull MapCodec<? extends ValueProvider>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    Codec<ValueProvider> DISPATCH_CODEC = ID_MAPPER.codec(Codec.STRING)
            .dispatch(ValueProvider::type, mapCodec -> mapCodec);
    Codec<ValueProvider> CODEC = Codec.either(Constant.CODEC, DISPATCH_CODEC)
            .xmap((either) -> either.map((left) -> left, (right) -> right), (valueProvider) -> valueProvider instanceof Constant constant ? Either.left(constant) : Either.right(valueProvider));
    double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth);
    MapCodec<? extends ValueProvider> type();
    static void bootstrap() {
        ID_MAPPER.put("block_pos_value", BlockPosValue.CODEC);
        ID_MAPPER.put("constant", Constant.MAP_CODEC);
        ID_MAPPER.put("distance", Distance.CODEC);
        ID_MAPPER.put("function", MathFunction.CODEC);
        ID_MAPPER.put("variable", Variable.CODEC);
    }
}
