package org.lithereal.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static org.lithereal.item.ability.IAbility.snakeCaseToName;

public record MultiMiner(List<MultiMinerConfig> configs, float totalSpeedFactor, float minSpeedFactor) implements TooltipProvider {
    public static final MultiMiner HAMMER = new MultiMiner(new MultiMinerConfig(Shape.RECTANGULAR_PRISM, 3, 1), 0.25F, 0.75F);
    public static final Codec<MultiMiner> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(ExtraCodecs.nonEmptyList(ExtraCodecs.compactListCodec(MultiMinerConfig.CODEC, MultiMinerConfig.CODEC.listOf())).fieldOf("shape_configs").forGetter(MultiMiner::configs),
                            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("total_speed_factor", 0.25F).forGetter(MultiMiner::totalSpeedFactor),
                            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("min_speed_factor", 0.75F).forGetter(MultiMiner::minSpeedFactor))
                    .apply(instance, MultiMiner::new));
    public MultiMiner(MultiMinerConfig config, float totalSpeedFactor, float minSpeedFactor) {
        this(Collections.singletonList(config), totalSpeedFactor, minSpeedFactor);
    }
    public void triggerOnMine(ItemStack hammerStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (level.isClientSide() || blockState.getDestroySpeed(level, blockPos) == 0.0F) return;

        HitResult pick = livingEntity.pick(livingEntity.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) + 2, 1F, false);

        // Not possible?
        if (!(pick instanceof BlockHitResult)) return;


        this.findAndBreakNearBlocks(pick, blockPos, hammerStack, level, livingEntity);
    }

    public void findAndBreakNearBlocks(HitResult pick, BlockPos blockPos, ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        final int[] damage = {0};

        forEachMineableBlock(level, (BlockHitResult) pick, blockPos, stack, (pos, targetState) -> {
            if (damage[0] >= (stack.getMaxDamage() - stack.getDamageValue() - 1)) {
                return true;
            }
            level.destroyBlock(pos, false, livingEntity);

            if (!player.isCreative()) {
                boolean correctToolForDrops = stack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops)
                    Block.dropResources(targetState, level, pos, level.getBlockEntity(pos), livingEntity, stack);
            }
            damage[0]++;
            return false;
        });

        if (damage[0] != 0 && !player.isCreative()) {
            Tool tool = stack.get(DataComponents.TOOL);
            if (tool != null) stack.hurtAndBreak(damage[0] * tool.damagePerBlock(), livingEntity, EquipmentSlot.MAINHAND);
        }
    }

    public void forEachMineableBlock(BlockGetter blockGetter, BlockHitResult pick, BlockPos blockPos, ItemStack hammerStack, BiFunction<BlockPos, BlockState, Boolean> function) {
        var boundingBox = getOuterBoundingBox(pick, blockPos);
        Direction.Axis axis = pick.getDirection().getAxis();
        BlockPos centerPos = boundingBox.getCenter();
        for (BlockPos pos : BlockPos.betweenClosed(Math.min(boundingBox.minX(), boundingBox.maxX()), Math.min(boundingBox.minY(), boundingBox.maxY()), Math.min(boundingBox.minZ(), boundingBox.maxZ()), Math.max(boundingBox.minX(), boundingBox.maxX()), Math.max(boundingBox.minY(), boundingBox.maxY()), Math.max(boundingBox.minZ(), boundingBox.maxZ()))) {
            if (!this.isValid(pos, centerPos, axis)) continue;
            BlockState targetState = blockGetter.getBlockState(pos);
            if (pos.equals(blockPos) || cannotDestroy(targetState, blockGetter, pos) || !hammerStack.isCorrectToolForDrops(targetState)) {
                continue;
            }
            if (function.apply(pos, targetState)) break;
        }
    }

    private boolean isValid(BlockPos pos, BlockPos centerPos, Direction.Axis axis) {
        return this.configs.stream().anyMatch(config -> config.isValid(pos, centerPos, axis));
    }

    @NotNull
    private BoundingBox getOuterBoundingBox(BlockHitResult pick, BlockPos blockPos) {
        MultiMinerConfig largestConfig = this.configs.getFirst();
        int blocksIncluded = largestConfig.getSize();
        if (this.configs.size() > 1) {
            for (MultiMinerConfig multiMinerConfig : this.configs) {
                if (largestConfig.depth() <= multiMinerConfig.depth() && largestConfig.radius() <= multiMinerConfig.radius()) {
                    largestConfig = multiMinerConfig;
                    blocksIncluded = multiMinerConfig.getSize();
                }
                if (blocksIncluded < multiMinerConfig.getSize()) {
                    largestConfig = multiMinerConfig;
                    blocksIncluded = multiMinerConfig.getSize();
                }
            }
        }
        return largestConfig.getBoundingBox(pick, blockPos);
    }

    private boolean cannotDestroy(BlockState targetState, BlockGetter blockGetter, BlockPos pos) {
        if (targetState.getDestroySpeed(blockGetter, pos) <= 0) {
            return true;
        }

        return blockGetter.getBlockEntity(pos) != null;
    }

    public float getDestroySpeed(ItemStack itemStack, BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos, TriFunction<BlockState, BlockGetter, BlockPos, Float> destroySpeedFunc) {
        HitResult pick = player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) + 2, 1F, false);
        float baseSpeed = destroySpeedFunc.apply(blockState, blockGetter, blockPos);
        float[] totalDestroySpeed = new float[] {baseSpeed};
        forEachMineableBlock(blockGetter, (BlockHitResult) pick, blockPos, itemStack, (pos, targetState) -> {
            totalDestroySpeed[0] += destroySpeedFunc.apply(targetState, blockGetter, pos);
            return false;
        });
        return Math.max(totalDestroySpeed[0] * this.totalSpeedFactor(), baseSpeed * this.minSpeedFactor());
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        for (MultiMinerConfig multiMinerConfig : this.configs)
            multiMinerConfig.addToTooltip(tooltipContext, consumer, tooltipFlag, dataComponentGetter);
    }

    public record MultiMinerConfig(Shape shape, int radius, int depth) implements TooltipProvider {
        public static final Codec<MultiMinerConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(Shape.CODEC.optionalFieldOf("shape", Shape.RECTANGULAR_PRISM).forGetter(MultiMinerConfig::shape),
                                ExtraCodecs.POSITIVE_INT.fieldOf("radius").forGetter(MultiMinerConfig::radius),
                                ExtraCodecs.POSITIVE_INT.optionalFieldOf("depth", 1).forGetter(MultiMinerConfig::depth))
                        .apply(instance, MultiMinerConfig::new));

        @NotNull
        private BoundingBox getBoundingBox(BlockHitResult pick, BlockPos blockPos) {
            var size = (this.radius() / 2);
            var offset = 0;
            var functionalDepth = this.shape().usesDepth() ? this.depth() - 1 : this.radius() - 1;
            Direction direction = pick.getDirection();
            return switch (direction) {
                case DOWN, UP -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? functionalDepth : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? functionalDepth : 0), blockPos.getZ() + size);
                case NORTH, SOUTH -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - size + offset, blockPos.getZ() - (direction == Direction.SOUTH ? functionalDepth : 0), blockPos.getX() + size, blockPos.getY() + size + offset, blockPos.getZ() + (direction == Direction.NORTH ? functionalDepth : 0));
                case WEST, EAST -> new BoundingBox(blockPos.getX() - (direction == Direction.EAST ? functionalDepth : 0), blockPos.getY() - size + offset, blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? functionalDepth : 0), blockPos.getY() + size + offset, blockPos.getZ() + size);
            };
        }

        private int getSize() {
            return this.radius() * (this.shape().usesDepth() ? this.depth() : this.radius()) * this.radius();
        }

        public boolean isValid(BlockPos pos, BlockPos centerPos, Direction.Axis axis) {
            return this.shape().isValid(pos, centerPos, axis, this.radius() / 2.0, this.depth());
        }

        @Override
        public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
            consumer.accept(Component.translatable("tooltip.multi_miner.mining_shape",
                    Component.translatableWithFallback("miner_shape." + this.shape().getSerializedName(),
                            snakeCaseToName(this.shape().getSerializedName()))));
            consumer.accept(Component.literal(" - ").append(Component.translatable("tooltip.multi_miner.mining_radius",
                    Component.literal("" + this.radius()))));
            if (this.shape().usesDepth()) consumer.accept(Component.literal(" - ").append(Component.translatable("tooltip.multi_miner.mining_depth",
                    Component.literal("" + this.depth()))));
        }
    }

    public enum Shape implements StringRepresentable {
        RECTANGULAR_PRISM("rectangular_prism"),
        CYLINDER("cylinder"),
        SPHERE("sphere"),
        OCTAHEDRON("octahedron"),
        DIAMOND_PRISM("diamond_prism"),
        CROSS("cross"),
        TRICROSS("tricross"),
        STRAIGHT("straight");

        public static final Codec<Shape> CODEC = StringRepresentable.fromEnum(Shape::values);
        public final String name;

        Shape(String name) {
            this.name = name;
        }

        @Override
        public @NonNull String getSerializedName() {
            return name;
        }

        public boolean usesDepth() {
            return this == Shape.RECTANGULAR_PRISM || this == Shape.CYLINDER || this == DIAMOND_PRISM || this == CROSS || this == STRAIGHT;
        }

        public boolean isValid(BlockPos pos, BlockPos centerPos, Direction.Axis axis, double radius, int depth) {
            if (usesDepth()) {
                switch (axis) {
                    case X -> {
                        if (Math.abs(pos.getX() - centerPos.getX()) > (depth - 1) || Math.abs(pos.getY() - centerPos.getY()) > radius || Math.abs(pos.getZ() - centerPos.getZ()) > radius)
                            return false;
                    }
                    case Y -> {
                        if (Math.abs(pos.getX() - centerPos.getX()) > radius || Math.abs(pos.getY() - centerPos.getY()) > (depth - 1) || Math.abs(pos.getZ() - centerPos.getZ()) > radius)
                            return false;
                    }
                    case Z -> {
                        if (Math.abs(pos.getX() - centerPos.getX()) > radius || Math.abs(pos.getY() - centerPos.getY()) > radius || Math.abs(pos.getZ() - centerPos.getZ()) > (depth - 1))
                            return false;
                    }
                }
            } else if (Math.abs(pos.getX() - centerPos.getX()) > radius || Math.abs(pos.getY() - centerPos.getY()) > radius || Math.abs(pos.getZ() - centerPos.getZ()) > radius)
                return false;
            return switch (this) {
                case RECTANGULAR_PRISM -> true;
                case CYLINDER -> pos.distSqr(switch (axis) {
                    case X -> new BlockPos(pos.getX(), centerPos.getY(), centerPos.getZ());
                    case Y -> centerPos.atY(pos.getY());
                    case Z -> new BlockPos(centerPos.getZ(), centerPos.getY(), pos.getZ());
                }) <= radius * radius;
                case SPHERE -> pos.distSqr(centerPos) <= radius * radius;
                case OCTAHEDRON -> pos.distManhattan(centerPos) <= radius;
                case DIAMOND_PRISM -> pos.distManhattan(switch (axis) {
                    case X -> new BlockPos(pos.getX(), centerPos.getY(), centerPos.getZ());
                    case Y -> centerPos.atY(pos.getY());
                    case Z -> new BlockPos(centerPos.getZ(), centerPos.getY(), pos.getZ());
                }) <= radius;
                case CROSS -> switch (axis) {
                    case X -> pos.getY() == centerPos.getY() || pos.getZ() == centerPos.getZ();
                    case Y -> pos.getX() == centerPos.getX() || pos.getZ() == centerPos.getZ();
                    case Z -> pos.getX() == centerPos.getX() || pos.getY() == centerPos.getY();
                };
                case TRICROSS -> pos.getX() == centerPos.getX() || pos.getY() == centerPos.getY() || pos.getZ() == centerPos.getZ();
                case STRAIGHT -> switch (axis) {
                    case X -> pos.getY() == centerPos.getY() && pos.getZ() == centerPos.getZ();
                    case Y -> pos.getX() == centerPos.getX() && pos.getZ() == centerPos.getZ();
                    case Z -> pos.getX() == centerPos.getX() && pos.getY() == centerPos.getY();
                };
            };
        }
    }
}
