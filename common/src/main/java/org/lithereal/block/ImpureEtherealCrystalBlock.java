package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ImpureEtherealCrystalBlock extends Block implements SimpleWaterloggedBlock {
    public static final VoxelShape SHAPE_UP = box(2, 0, 2, 14, 11, 14);
    public static final VoxelShape SHAPE_DOWN = box(2, 5, 2, 14, 16, 14);
    public static final VoxelShape SHAPE_NORTH = box(2, 2, 5, 14, 14, 16);
    public static final VoxelShape SHAPE_SOUTH = box(2, 2, 0, 14, 14, 11);
    public static final VoxelShape SHAPE_EAST = box(0, 2, 2, 11, 14, 14);
    public static final VoxelShape SHAPE_WEST = box(5, 2, 2, 16, 14, 14);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final MapCodec<ImpureEtherealCrystalBlock> CODEC = simpleCodec(ImpureEtherealCrystalBlock::new);

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }

    public ImpureEtherealCrystalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        return switch (direction) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            case DOWN -> SHAPE_DOWN;
            default -> SHAPE_UP;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(FACING, blockPlaceContext.getClickedFace());
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        Block fallback = Blocks.AIR;
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
            fallback = Blocks.WATER;
        }

        return !blockState.canSurvive(levelAccessor, blockPos) ? fallback.defaultBlockState() : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        Direction oppositeDir = blockState.getValue(FACING).getOpposite();
        BlockPos attachedPos = blockPos.relative(oppositeDir);
        return levelReader.getBlockState(attachedPos).isFaceSturdy(levelReader, attachedPos, oppositeDir);
    }
}
