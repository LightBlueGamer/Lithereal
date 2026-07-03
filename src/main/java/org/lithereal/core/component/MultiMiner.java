package org.lithereal.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
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
import org.lithereal.core.component.miner_shape.MinerShape;
import org.lithereal.core.component.miner_shape.MinerShapes;
import org.lithereal.registry.ModRegistries;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public record MultiMiner(List<MultiMinerConfig> configs, float totalSpeedFactor, float minSpeedFactor) implements TooltipProvider {
    public static final Codec<MultiMiner> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(ExtraCodecs.nonEmptyList(ExtraCodecs.compactListCodec(MultiMinerConfig.CODEC, MultiMinerConfig.CODEC.listOf())).fieldOf("shape_configs").forGetter(MultiMiner::configs),
                            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("total_speed_factor", 0.25F).forGetter(MultiMiner::totalSpeedFactor),
                            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("min_speed_factor", 0.75F).forGetter(MultiMiner::minSpeedFactor))
                    .apply(instance, MultiMiner::new));
    public MultiMiner(MultiMinerConfig config, float totalSpeedFactor, float minSpeedFactor) {
        this(Collections.singletonList(config), totalSpeedFactor, minSpeedFactor);
    }
    public static MultiMiner hammer(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<MinerShape> minerShapes = provider.lookupOrThrow(ModRegistries.MINER_SHAPE_REGISTRY);
        return new MultiMiner(new MultiMinerConfig(minerShapes.getOrThrow(MinerShapes.RECTANGULAR_PRISM), 3, 1), 0.25F, 0.75F);
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
        Direction direction = pick.getDirection();
        BlockPos centerPos = boundingBox.getCenter();
        for (BlockPos pos : BlockPos.betweenClosed(Math.min(boundingBox.minX(), boundingBox.maxX()), Math.min(boundingBox.minY(), boundingBox.maxY()), Math.min(boundingBox.minZ(), boundingBox.maxZ()), Math.max(boundingBox.minX(), boundingBox.maxX()), Math.max(boundingBox.minY(), boundingBox.maxY()), Math.max(boundingBox.minZ(), boundingBox.maxZ()))) {
            if (!this.isValid(pos, centerPos, direction)) continue;
            BlockState targetState = blockGetter.getBlockState(pos);
            if (pos.equals(blockPos) || cannotDestroy(targetState, blockGetter, pos) || !hammerStack.isCorrectToolForDrops(targetState)) {
                continue;
            }
            if (function.apply(pos, targetState)) break;
        }
    }

    private boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction) {
        return this.configs.stream().anyMatch(config -> config.isValid(pos, centerPos, direction));
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

    public record MultiMinerConfig(Holder<MinerShape> shape, int radius, int depth) implements TooltipProvider {
        public static final Codec<MultiMinerConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(MinerShape.CODEC.fieldOf("shape").forGetter(MultiMinerConfig::shape),
                                ExtraCodecs.POSITIVE_INT.fieldOf("radius").forGetter(MultiMinerConfig::radius),
                                ExtraCodecs.POSITIVE_INT.optionalFieldOf("depth", 1).forGetter(MultiMinerConfig::depth))
                        .apply(instance, MultiMinerConfig::new));

        @NotNull
        private BoundingBox getBoundingBox(BlockHitResult pick, BlockPos blockPos) {
            var size = (this.radius() / 2);
            var offset = 0;
            var functionalDepth = this.shape().value().usesDepth() ? this.depth() - 1 : this.radius() - 1;
            Direction direction = pick.getDirection();
            return switch (direction) {
                case DOWN, UP -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? functionalDepth : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? functionalDepth : 0), blockPos.getZ() + size);
                case NORTH, SOUTH -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - size + offset, blockPos.getZ() - (direction == Direction.SOUTH ? functionalDepth : 0), blockPos.getX() + size, blockPos.getY() + size + offset, blockPos.getZ() + (direction == Direction.NORTH ? functionalDepth : 0));
                case WEST, EAST -> new BoundingBox(blockPos.getX() - (direction == Direction.EAST ? functionalDepth : 0), blockPos.getY() - size + offset, blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? functionalDepth : 0), blockPos.getY() + size + offset, blockPos.getZ() + size);
            };
        }

        private int getSize() {
            return this.radius() * (this.shape().value().usesDepth() ? this.depth() : this.radius()) * this.radius();
        }

        public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction) {
            return this.shape().value().isValid(pos, centerPos, direction, this.radius() / 2.0, this.depth());
        }

        @Override
        public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
            consumer.accept(Component.translatable("tooltip.multi_miner.mining_shape", this.shape().value().displayName()));
            consumer.accept(Component.literal(" - ").append(Component.translatable("tooltip.multi_miner.mining_radius",
                    Component.literal("" + this.radius()))));
            if (this.shape().value().usesDepth()) consumer.accept(Component.literal(" - ").append(Component.translatable("tooltip.multi_miner.mining_depth",
                    Component.literal("" + this.depth()))));
        }
    }
}
