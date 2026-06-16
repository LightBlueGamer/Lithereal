//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
//? fabric {
/*import net.minecraft.tags.FluidTags;
*///?}
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
//? fabric {
/*import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
*///?}
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
//? fabric {
/*import net.minecraft.world.level.gamerules.GameRules;
*///?}
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
//? neoforge {
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.FarmlandWaterManager;
//?}
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class ModFarmlandBlock extends Block {
    public static final MapCodec<ModFarmlandBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(propertiesCodec(),
            ResourceKey.codec(Registries.BLOCK).fieldOf("dirt_block").forGetter(farmlandBlock -> farmlandBlock.baseBlock)).apply(instance, ModFarmlandBlock::new));
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    private static final VoxelShape SHAPE = Block.column(16.0F, 0.0F, 15.0F);
    private final ResourceKey<Block> baseBlock;
    public static final int MAX_MOISTURE = 7;

    public MapCodec<ModFarmlandBlock> codec() {
        return CODEC;
    }

    public ModFarmlandBlock(BlockBehaviour.Properties properties, ResourceKey<Block> baseBlock) {
        super(properties);
        this.baseBlock = baseBlock;
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (directionToNeighbour == Direction.UP && !state.canSurvive(level, pos)) {
            ticks.scheduleTick(pos, this, 1);
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState aboveState = level.getBlockState(pos.above());
        return !aboveState.isSolid() || shouldMaintainFarmland(level, pos);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? getBaseBlock(context.getLevel()).defaultBlockState() : super.getStateForPlacement(context);
    }

    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToBaseBlock(null, state, level, pos);
        }

    }

    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (moisture > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            } else if (!shouldMaintainFarmland(level, pos)) {
                turnToBaseBlock(null, state, level, pos);
            }
        } else if (moisture < MAX_MOISTURE) {
            level.setBlock(pos, state.setValue(MOISTURE, MAX_MOISTURE), 2);
        }

    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (level instanceof ServerLevel serverLevel) {
            //? fabric {
            /*if ((double)level.getRandom().nextFloat() < fallDistance - (double)0.5F && entity instanceof LivingEntity && (entity instanceof Player || serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F) {
                turnToBaseBlock(entity, state, level, pos);
            }
            *///?}
            //? neoforge {
            if (CommonHooks.onFarmlandTrample(serverLevel, pos, getBaseBlock(level).defaultBlockState(), fallDistance, entity)) {
                turnToBaseBlock(entity, state, level, pos);
            }
            //?}
        }

        super.fallOn(level, state, pos, entity, fallDistance);
    }

    public void turnToBaseBlock(@Nullable Entity sourceEntity, BlockState state, Level level, BlockPos pos) {
        Registry<Block> blocks = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        Optional<Block> baseBlock = blocks.getOptional(this.baseBlock);
        if (baseBlock.isPresent() && !level.isClientSide()) {
            turnToBaseBlock(sourceEntity, state, level, pos, baseBlock.get());
        }
    }

    public Block getBaseBlock(Level level) {
        Registry<Block> blocks = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        Optional<Block> baseBlock = blocks.getOptional(this.baseBlock);
        return baseBlock.orElse(Blocks.DIRT);
    }

    public static void turnToBaseBlock(@Nullable Entity sourceEntity, BlockState state, Level level, BlockPos pos, Block baseBlock) {
        BlockState newState = pushEntitiesUp(state, baseBlock.defaultBlockState(), level, pos);
        level.setBlockAndUpdate(pos, newState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(sourceEntity, newState));
    }

    private static boolean shouldMaintainFarmland(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(BlockTags.MAINTAINS_FARMLAND);
    }

    private static boolean isNearWater(LevelReader level, BlockPos pos) {
        //? neoforge {
        BlockState state = level.getBlockState(pos);

        for(BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (state.canBeHydrated(level, pos, level.getFluidState(blockPos), blockPos)) {
                return true;
            }
        }

        return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
        //?}
        //? fabric {
        /*for(BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
        *///?}
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
