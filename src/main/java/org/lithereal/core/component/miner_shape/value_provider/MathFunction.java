package org.lithereal.core.component.miner_shape.value_provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public record MathFunction(ValueProvider left, Optional<ValueProvider> right, Operation operation) implements ValueProvider {
    public static final MapCodec<MathFunction> CODEC = Operation.CODEC.fieldOf("operation").dispatchMap(MathFunction::operation, Operation::codec);
    @Override
    public double extractValue(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        return this.operation().process(this.left().extractValue(pos, centerPos, direction, radius, depth), this.right().map(valueProvider -> valueProvider.extractValue(pos, centerPos, direction, radius, depth)).orElse(0D));
    }

    @Override
    public MapCodec<? extends ValueProvider> type() {
        return CODEC;
    }

    public enum Operation implements StringRepresentable {
        ADD("add", true),
        SUBTRACT("sub", true),
        MULTIPLY("mul", true),
        DIVIDE("div", true),
        MODULO("mod", true),
        POWER("pow", true),
        SQRT("sqrt", false),
        SIN("sin", false),
        COS("cos", false),
        TAN("tan", false),
        ARCSIN("asin", false),
        ARCCOS("acos", false),
        ARCTAN("atan", false),
        MIN("min", true),
        MAX("max", true);

        private final String name;
        private final boolean hasSecondArgument;
        public static final Codec<Operation> CODEC = StringRepresentable.fromEnum(Operation::values);

        Operation(String name, boolean hasSecondArgument) {
            this.name = name;
            this.hasSecondArgument = hasSecondArgument;
        }

        public double process(double a, double b) {
            return switch (this) {
                case ADD -> a + b;
                case SUBTRACT -> a - b;
                case MULTIPLY -> a * b;
                case DIVIDE -> a / b;
                case MODULO -> a % b;
                case POWER -> Math.pow(a, b);
                case SQRT -> Math.sqrt(a);
                case SIN -> Math.sin(a);
                case COS -> Math.cos(a);
                case TAN -> Math.tan(a);
                case ARCSIN -> Math.asin(a);
                case ARCCOS -> Math.acos(a);
                case ARCTAN -> Math.atan(a);
                case MIN -> Math.min(a, b);
                case MAX -> Math.max(a, b);
            };
        }

        public MapCodec<MathFunction> codec() {
            return this.hasSecondArgument ? RecordCodecBuilder.mapCodec(instance ->
                    instance.group(ValueProvider.CODEC.fieldOf("left").forGetter(MathFunction::left),
                                    ValueProvider.CODEC.fieldOf("right").forGetter(mathFunction -> mathFunction.right().orElse(Constant.ZERO)))
                            .apply(instance, (left, right) -> new MathFunction(left, Optional.of(right), this))) :
                    ValueProvider.CODEC.fieldOf("operand").xmap(operand -> new MathFunction(operand, Optional.empty(), this), MathFunction::left);
        }

        @Override
        public @NonNull String getSerializedName() {
            return this.name;
        }
    }
}
