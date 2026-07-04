package org.lithereal.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
//? fabric {
/*import net.minecraft.stats.Stats;
*///?}
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
//? fabric {
/*import net.minecraft.world.level.block.state.pattern.BlockInWorld;
*///?}
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
//? neoforge {
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
//?}
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.lithereal.core.component.miner_shape.MinerShape;
import org.lithereal.core.component.miner_shape.MinerShapes;
import org.lithereal.core.component.miner_shape.PosOffset;
import org.lithereal.core.component.miner_shape.block_pos_processor.OriginProcessor;
import org.lithereal.core.component.miner_shape.value_provider.Constant;
import org.lithereal.core.component.miner_shape.value_provider.DirectionalValue;
import org.lithereal.core.component.miner_shape.value_provider.MathFunction;
import org.lithereal.core.component.miner_shape.value_provider.Variable;
//? neoforge {
import org.lithereal.data.mixin.accessor.ItemStackAccessor;
//?}
import org.lithereal.registry.ModRegistries;
import org.lithereal.util.CommonUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public record MultiMiner(List<MultiMinerConfig> configs, float totalSpeedFactor, float minSpeedFactor, List<TriggerConditions> triggerConditions) implements TooltipProvider {
    public static final Codec<MultiMiner> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(ExtraCodecs.nonEmptyList(ExtraCodecs.compactListCodec(MultiMinerConfig.CODEC, MultiMinerConfig.CODEC.listOf())).fieldOf("shape_configs").forGetter(MultiMiner::configs),
                            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("total_speed_factor", 0.25F).forGetter(MultiMiner::totalSpeedFactor),
                            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("min_speed_factor", 0.75F).forGetter(MultiMiner::minSpeedFactor),
                            TriggerConditions.CODEC.listOf().optionalFieldOf("triggers_if", Collections.emptyList()).forGetter(MultiMiner::triggerConditions))
                    .apply(instance, MultiMiner::new));
    public MultiMiner(MultiMinerConfig config, float totalSpeedFactor, float minSpeedFactor) {
        this(Collections.singletonList(config), totalSpeedFactor, minSpeedFactor, Collections.singletonList(TriggerConditions.IS_MINING));
    }
    public static MultiMiner hammer(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<MinerShape> minerShapes = provider.lookupOrThrow(ModRegistries.MINER_SHAPE_REGISTRY);
        return new MultiMiner(new MultiMinerConfig(minerShapes.getOrThrow(MinerShapes.RECTANGULAR_PRISM), 3, 1, PosOffset.ZERO_CENTER_OFFSET), 0.25F, 0.75F);
    }

    public static MultiMiner specialShovel(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<MinerShape> minerShapes = provider.lookupOrThrow(ModRegistries.MINER_SHAPE_REGISTRY);
        MathFunction halfDepth = new MathFunction(new MathFunction(Variable.DEPTH, Optional.of(new Constant(2.0)), MathFunction.Operation.DIVIDE), Optional.empty(), MathFunction.Operation.FLOOR);
        PosOffset offsetRadius = new PosOffset(OriginProcessor.CENTER, new DirectionalValue(halfDepth, Constant.ZERO, Constant.ZERO),
                new DirectionalValue(Constant.ZERO, halfDepth, Constant.ZERO),
                new DirectionalValue(Constant.ZERO, Constant.ZERO, halfDepth));
        return new MultiMiner(List.of(new MultiMinerConfig(minerShapes.getOrThrow(MinerShapes.RECTANGULAR_PRISM), 3, 2, offsetRadius), new MultiMinerConfig(minerShapes.getOrThrow(MinerShapes.TRICROSS), 7, 3, offsetRadius)), 0.15F, 0.75F, Collections.singletonList(TriggerConditions.SECONDARY_ACTION));
    }

    public static MultiMiner specialHoe(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<MinerShape> minerShapes = provider.lookupOrThrow(ModRegistries.MINER_SHAPE_REGISTRY);
        return new MultiMiner(Collections.singletonList(new MultiMinerConfig(minerShapes.getOrThrow(MinerShapes.RECTANGULAR_PRISM), 3, 1, PosOffset.ZERO_CENTER_OFFSET)), 0.25F, 0.75F, List.of(TriggerConditions.IS_USING, TriggerConditions.PRIMARY_ACTION));
    }

    public boolean allows(UseOnContext context, boolean isUseInteraction) {
        return this.triggerConditions().stream().allMatch(trigger -> trigger.allows(context, isUseInteraction));
    }

    public void triggerOnMine(ItemStack usedStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (level.isClientSide() || blockState.getDestroySpeed(level, blockPos) == 0.0F) return;

        HitResult pick = livingEntity.pick(livingEntity.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) + 2, 1F, false);

        // Not possible?
        if (!(pick instanceof BlockHitResult blockHitResult) || !(livingEntity instanceof Player player)) return;
        UseOnContext useOnContext = new UseOnContext(level, player, InteractionHand.MAIN_HAND, usedStack, blockHitResult);

        if (this.allows(useOnContext, false)) this.findAndBreakNearBlocks(useOnContext, blockPos, usedStack, level, livingEntity);
    }

    public InteractionResult triggerOnUse(ItemStack usedStack, UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        if (player == null) return InteractionResult.PASS;

        if (this.allows(useOnContext, true)) return this.findAndUseNearBlocks(useOnContext, usedStack, useOnContext.getLevel(), player);
        return InteractionResult.PASS;
    }

    public InteractionResult findAndUseNearBlocks(UseOnContext useOnContext, ItemStack usedStack, Level level, Player user) {
        InteractionResult[] ret = {InteractionResult.PASS};

        forEachMineableBlock(true, level, useOnContext, useOnContext.getClickedPos(), usedStack, (pos, _) -> {
            if (usedStack.isEmpty()) return true;
            BlockHitResult blockHitResult = new BlockHitResult(pos.getCenter(), useOnContext.getClickedFace(), pos, useOnContext.isInside());
            InteractionResult thisAttempt = useItemOn(new UseOnContext(level, user, useOnContext.getHand(), usedStack, blockHitResult), blockHitResult);
            if (thisAttempt.consumesAction() && !ret[0].consumesAction()) ret[0] = thisAttempt;
            return false;
        });
        return ret[0];
    }

    public void findAndBreakNearBlocks(UseOnContext useOnContext, BlockPos blockPos, ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        final int[] damage = {0};

        int damagePerBlock;
        Tool tool = stack.get(DataComponents.TOOL);
        if (tool != null) damagePerBlock = tool.damagePerBlock();
        else damagePerBlock = 1;

        forEachMineableBlock(false, level, useOnContext, blockPos, stack, (pos, targetState) -> {
            if (damage[0] >= (stack.getMaxDamage() - stack.getDamageValue() - 1)) {
                return true;
            }
            level.destroyBlock(pos, false, livingEntity);

            if (!player.isCreative()) {
                boolean correctToolForDrops = stack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops)
                    Block.dropResources(targetState, level, pos, level.getBlockEntity(pos), livingEntity, stack);
            }
            damage[0] += damagePerBlock;
            return false;
        });

        if (damage[0] != 0 && !player.isCreative() && tool != null) stack.hurtAndBreak(damage[0], livingEntity, EquipmentSlot.MAINHAND);
    }

    public InteractionResult useItemOn(UseOnContext context, BlockHitResult hitResult) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        if (player.gameMode() == GameType.SPECTATOR) return InteractionResult.PASS;
        ItemStack itemStack = context.getItemInHand();
        InteractionHand hand = context.getHand();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!state.getBlock().isEnabled(level.enabledFeatures())) return InteractionResult.PASS;
        boolean haveSomethingInOurHands = !player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty();
        boolean suppressUsingBlock = player.isSecondaryUseActive() && haveSomethingInOurHands;
        ItemStack usedItemStack = itemStack.copy();
        if (!suppressUsingBlock) {
            InteractionResult itemUse = state.useItemOn(player.getItemInHand(hand), level, player, hand, hitResult);
            if (itemUse.consumesAction() && player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, usedItemStack);
                return InteractionResult.PASS;
            }
        }

        if (!itemStack.isEmpty() && !player.getCooldowns().isOnCooldown(itemStack)) {
            InteractionResult success;
            if (player.hasInfiniteMaterials()) {
                int count = itemStack.getCount();
                success = useOn(itemStack, context);
                itemStack.setCount(count);
            } else success = useOn(itemStack, context);

            if (success.consumesAction() && player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, usedItemStack);
            }
            return success;
        }
        return InteractionResult.PASS;
    }

    public static InteractionResult useOn(ItemStack usedStack, UseOnContext context) {
        //? neoforge {
        UseItemOnBlockEvent e = NeoForge.EVENT_BUS.post(new UseItemOnBlockEvent(context, UseItemOnBlockEvent.UsePhase.ITEM_AFTER_BLOCK));
        if (e.isCanceled()) {
            return e.getCancellationResult();
        } else {
            return !context.getLevel().isClientSide() ? CommonHooks.onPlaceItemIntoWorld(context) : ItemStackAccessor.class.cast(usedStack).callOnItemUse(context, (_) -> usedStack.getItem().useOn(context));
        }
        //?}
        //? fabric {
        /*Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        if (player != null && !player.getAbilities().mayBuild && !usedStack.canPlaceOnBlockInAdventureMode(new BlockInWorld(context.getLevel(), pos, false))) {
            return InteractionResult.PASS;
        } else {
            Item usedItem = usedStack.getItem();
            InteractionResult result = usedItem.useOn(context);
            if (player != null && result instanceof InteractionResult.Success success && success.wasItemInteraction()) {
                player.awardStat(Stats.ITEM_USED.get(usedItem));
            }

            return result;
        }
        *///?}
    }

    public void forEachMineableBlock(boolean isUseInteraction, BlockGetter blockGetter, UseOnContext useOnContext, BlockPos blockPos, ItemStack usedStack, BiFunction<BlockPos, BlockState, Boolean> function) {
        var boundingBox = getOuterBoundingBox(useOnContext, blockPos);
        Direction direction = useOnContext.getClickedFace();
        BlockPos centerPos = boundingBox.getCenter();
        for (BlockPos pos : BlockPos.betweenClosed(Math.min(boundingBox.minX(), boundingBox.maxX()), Math.min(boundingBox.minY(), boundingBox.maxY()), Math.min(boundingBox.minZ(), boundingBox.maxZ()), Math.max(boundingBox.minX(), boundingBox.maxX()), Math.max(boundingBox.minY(), boundingBox.maxY()), Math.max(boundingBox.minZ(), boundingBox.maxZ()))) {
            if (!this.isValid(pos, centerPos, direction)) continue;
            BlockState targetState = blockGetter.getBlockState(pos);
            if (pos.equals(blockPos) || (!isUseInteraction ?
                    improperForMining(blockGetter, targetState, pos, usedStack) :
                    improperForUsing())) {
                continue;
            }
            if (function.apply(pos, targetState)) break;
        }
    }

    public boolean improperForMining(BlockGetter blockGetter, BlockState targetState, BlockPos pos, ItemStack usedStack) {
        return cannotDestroy(targetState, blockGetter, pos) || !usedStack.isCorrectToolForDrops(targetState);
    }

    public boolean improperForUsing() {
        return false;
    }

    private boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction) {
        return this.configs.stream().anyMatch(config -> config.isValid(pos, centerPos, direction));
    }

    @NotNull
    private BoundingBox getOuterBoundingBox(UseOnContext useOnContext, BlockPos blockPos) {
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
        return largestConfig.getBoundingBox(useOnContext, blockPos);
    }

    private boolean cannotDestroy(BlockState targetState, BlockGetter blockGetter, BlockPos pos) {
        if (targetState.getDestroySpeed(blockGetter, pos) <= 0) {
            return true;
        }

        return blockGetter.getBlockEntity(pos) != null;
    }

    public float getDestroySpeed(ItemStack usedStack, BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos, TriFunction<BlockState, BlockGetter, BlockPos, Float> destroySpeedFunc) {
        HitResult pick = player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) + 2, 1F, false);
        UseOnContext useOnContext = new UseOnContext(player.level(), player, InteractionHand.MAIN_HAND, usedStack, (BlockHitResult) pick);
        float baseSpeed = destroySpeedFunc.apply(blockState, blockGetter, blockPos);
        if (!this.allows(useOnContext, false)) return baseSpeed;
        float[] totalDestroySpeed = new float[] {baseSpeed};
        forEachMineableBlock(false, blockGetter, useOnContext, blockPos, usedStack, (pos, targetState) -> {
            totalDestroySpeed[0] += destroySpeedFunc.apply(targetState, blockGetter, pos);
            return false;
        });
        return Math.max(totalDestroySpeed[0] * this.totalSpeedFactor(), baseSpeed * this.minSpeedFactor());
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        for (MultiMinerConfig multiMinerConfig : this.configs)
            multiMinerConfig.addToTooltip(tooltipContext, consumer, tooltipFlag, dataComponentGetter);
        if (!this.triggerConditions().isEmpty()) {
            consumer.accept(Component.translatable("tooltip.multi_miner.triggers_when", combineTriggerConditionsTogether()));
        }
    }

    public Component combineTriggerConditionsTogether() {
        return CommonUtils.combineListTogether(triggerConditions(), TriggerConditions::key, TriggerConditions::getSerializedName);
    }

    public record MultiMinerConfig(Holder<MinerShape> shape, int radius, int depth, PosOffset centerOffset) implements TooltipProvider {
        public static final Codec<MultiMinerConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(MinerShape.CODEC.fieldOf("shape").forGetter(MultiMinerConfig::shape),
                                ExtraCodecs.POSITIVE_INT.fieldOf("radius").forGetter(MultiMinerConfig::radius),
                                ExtraCodecs.POSITIVE_INT.optionalFieldOf("depth", 1).forGetter(MultiMinerConfig::depth),
                                PosOffset.codec(OriginProcessor.CENTER).optionalFieldOf("center_offset", PosOffset.ZERO_CENTER_OFFSET).forGetter(MultiMinerConfig::centerOffset))
                        .apply(instance, MultiMinerConfig::new));

        @NotNull
        private BoundingBox getBoundingBox(UseOnContext useOnContext, BlockPos blockPos) {
            var size = (this.radius() / 2);
            var offset = 0;
            var functionalDepth = this.shape().value().usesDepth() ? this.depth() - 1 : this.radius() - 1;
            Direction direction = useOnContext.getClickedFace();
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
            double functionalRadius = this.radius() / 2.0;
            return this.shape().value().isValid(pos, this.centerOffset().process(direction, pos, centerPos, functionalRadius, this.depth()), direction, functionalRadius, this.depth());
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

    public enum TriggerConditions implements StringRepresentable {
        PRIMARY_ACTION("primary_action", useOnContext -> !useOnContext.isSecondaryUseActive()),
        SECONDARY_ACTION("secondary_action", UseOnContext::isSecondaryUseActive),
        IS_FACE_HORIZONTAL("is_face_horizontal", useOnContext -> useOnContext.getClickedFace().getAxis().isHorizontal()),
        IS_FACE_VERTICAL("is_face_vertical", useOnContext -> useOnContext.getClickedFace().getAxis().isVertical()),
        IS_MINING("is_mining", (_, isUseInteraction) -> !isUseInteraction),
        IS_USING("is_using", (_, isUseInteraction) -> isUseInteraction);

        private final String name;
        private final BiPredicate<UseOnContext, Boolean> matches;
        public static final Codec<TriggerConditions> CODEC = StringRepresentable.fromEnum(TriggerConditions::values);

        TriggerConditions(String name, Predicate<UseOnContext> matches) {
            this.name = name;
            this.matches = (useOnContext, _) -> matches.test(useOnContext);
        }

        TriggerConditions(String name, BiPredicate<UseOnContext, Boolean> matches) {
            this.name = name;
            this.matches = matches;
        }

        public boolean allows(UseOnContext useOnContext, boolean isUseInteraction) {
            return this.matches.test(useOnContext, isUseInteraction);
        }

        @Override
        public @NonNull String getSerializedName() {
            return this.name;
        }

        public String key() {
            return "trigger_condition.lithereal." + this.name;
        }
    }
}
