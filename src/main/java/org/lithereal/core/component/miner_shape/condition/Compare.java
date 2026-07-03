package org.lithereal.core.component.miner_shape.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;
import org.lithereal.core.component.miner_shape.value_provider.ValueProvider;

public record Compare(ValueProvider left, ValueProvider right, Comparison comparison) implements ValidityCondition {
    public static final String ID = "compare";
    public static final MapCodec<Compare> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(ValueProvider.CODEC.fieldOf("left").forGetter(Compare::left),
                            ValueProvider.CODEC.fieldOf("right").forGetter(Compare::right),
                            Comparison.CODEC.fieldOf("comparison").forGetter(Compare::comparison))
                    .apply(instance, Compare::new));
    @Override
    public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.comparison().matches(this.left().extractValue(pos, centerPos, direction, radius, depth), this.right().extractValue(pos, centerPos, direction, radius, depth));
    }

    @Override
    public MapCodec<? extends ValidityCondition> type() {
        return CODEC;
    }

    public enum Comparison implements StringRepresentable {
        LESS_THAN("less_than"),
        GREATER_THAN("greater_than"),
        EQUAL("equal"),
        NOT_EQUAL("not_equal"),
        LESS_THAN_OR_EQUAL("less_than_or_equal"),
        GREATER_THAN_OR_EQUAL("greater_than_or_equal");

        private final String name;
        public static final Codec<Comparison> CODEC = StringRepresentable.fromEnum(Comparison::values);

        Comparison(String name) {
            this.name = name;
        }

        public boolean matches(double left, double right) {
            return switch (this) {
                case LESS_THAN -> left < right;
                case GREATER_THAN -> left > right;
                case EQUAL -> left == right;
                case NOT_EQUAL -> left != right;
                case LESS_THAN_OR_EQUAL -> left <= right;
                case GREATER_THAN_OR_EQUAL -> left >= right;
            };
        }

        @Override
        public @NonNull String getSerializedName() {
            return this.name;
        }
    }
}
