package org.lithereal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.entity.InfusementChamberBlockEntity;

public abstract class InfusementChamberBlock extends BaseEntityBlock {
    public static final BooleanProperty PRIMARY_FILLED = BooleanProperty.create("primary_filled");
    public static final BooleanProperty SECONDARY_FILLED = BooleanProperty.create("secondary_filled");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public InfusementChamberBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PRIMARY_FILLED, false).setValue(SECONDARY_FILLED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PRIMARY_FILLED, SECONDARY_FILLED);
    }

    /* BLOCK ENTITY */

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        if(!blockState2.is(blockState.getBlock())) {
            InfusementChamberBlockEntity entity = (InfusementChamberBlockEntity) level.getBlockEntity(blockPos);
            if(entity != null)
                entity.setEmpowerments();
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type, LitherealExpectPlatform.getInfusementChamberBlockEntity(),
                InfusementChamberBlockEntity::tick);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);

        InfusementChamberBlockEntity entity = (InfusementChamberBlockEntity) level.getBlockEntity(blockPos);
        if(entity != null)
            entity.setEmpowerments();
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        InfusementChamberBlockEntity entity = (InfusementChamberBlockEntity) levelAccessor.getBlockEntity(blockPos);
        if(entity != null)
            entity.setEmpowerments();
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }
}