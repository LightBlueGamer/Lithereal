package org.lithereal.core.component.miner_shape.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

public interface ValidityCondition {
    ExtraCodecs.LateBoundIdMapper<@NotNull String, @NotNull MapCodec<? extends ValidityCondition>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    Codec<ValidityCondition> DISPATCH_CODEC = ID_MAPPER.codec(Codec.STRING)
            .dispatch(ValidityCondition::type, mapCodec -> mapCodec);
    Codec<ValidityCondition> CODEC = Codec.either(Constant.CODEC, DISPATCH_CODEC)
            .xmap((either) -> either.map((left) -> left, (right) -> right), (valueProvider) -> valueProvider instanceof Constant constant ? Either.left(constant) : Either.right(valueProvider));
    boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth);
    MapCodec<? extends ValidityCondition> type();
    static void bootstrap() {
        ID_MAPPER.put(AllOf.ID, AllOf.CODEC);
        ID_MAPPER.put(AnyOf.ID, AnyOf.CODEC);
        ID_MAPPER.put(AxisSelectedCondition.ID, AxisSelectedCondition.CODEC);
        ID_MAPPER.put(Compare.ID, Compare.CODEC);
        ID_MAPPER.put(Constant.ID, Constant.MAP_CODEC);
        ID_MAPPER.put(DirectionalCondition.ID, DirectionalCondition.CODEC);
    }
}
