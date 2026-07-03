package org.lithereal.core.component.miner_shape.block_pos_processor;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

public interface BlockPosProcessor {
    ExtraCodecs.LateBoundIdMapper<@NotNull String, @NotNull MapCodec<? extends BlockPosProcessor>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    Codec<BlockPosProcessor> DISPATCH_CODEC = ID_MAPPER.codec(Codec.STRING)
            .dispatch(BlockPosProcessor::type, mapCodec -> mapCodec);
    Codec<BlockPosProcessor> CODEC = Codec.either(OriginProcessor.CODEC, DISPATCH_CODEC)
            .xmap((either) -> either.map((left) -> left, (right) -> right), (blockPosProcessor) -> blockPosProcessor instanceof OriginProcessor originProcessor ? Either.left(originProcessor) : Either.right(blockPosProcessor));
    BlockPos process(Direction direction, BlockPos pos, BlockPos centerPos);
    MapCodec<? extends BlockPosProcessor> type();
    static void bootstrap() {
        ID_MAPPER.put("origin", OriginProcessor.CODEC.fieldOf("source"));
        ID_MAPPER.put("direct", DirectBlockPosProcessor.CODEC);
        ID_MAPPER.put("axis_selected", AxisSelectedBlockPosProcessor.CODEC);
        ID_MAPPER.put("directional", DirectionalBlockPosProcessor.CODEC);
    }
}
