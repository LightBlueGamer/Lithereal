package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;
import org.lithereal.core.component.miner_shape.block_pos_processor.BlockPosProcessor;

public record Distance(BlockPosProcessor from, BlockPosProcessor to, DistanceType distanceType) implements ValueProvider {
    public static final MapCodec<Distance> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(BlockPosProcessor.CODEC.fieldOf("from").forGetter(Distance::from),
                            BlockPosProcessor.CODEC.fieldOf("to").forGetter(Distance::to),
                            DistanceType.CODEC.optionalFieldOf("distance_type", DistanceType.DIST_SQR).forGetter(Distance::distanceType))
                    .apply(instance, Distance::new));
    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.distanceType.distanceTo(this.from.process(direction, pos, centerPos), this.to.process(direction, pos, centerPos));
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return CODEC;
    }

    public enum DistanceType implements StringRepresentable {
        DIST("dist"),
        DIST_SQR("dist_sqr"),
        DIST_MANHATTAN("dist_manhattan"),
        DIST_CHESSBOARD("dist_chessboard");

        private final String name;
        public static final Codec<DistanceType> CODEC = StringRepresentable.fromEnum(DistanceType::values);

        DistanceType(String name) {
            this.name = name;
        }

        public double distanceTo(BlockPos from, BlockPos to) {
            return switch (this) {
                case DIST -> Math.sqrt(from.distSqr(to));
                case DIST_SQR -> from.distSqr(to);
                case DIST_MANHATTAN -> from.distManhattan(to);
                case DIST_CHESSBOARD -> from.distChessboard(to);
            };
        }

        @Override
        public @NonNull String getSerializedName() {
            return this.name;
        }
    }
}
