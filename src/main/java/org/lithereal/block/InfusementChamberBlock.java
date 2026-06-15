package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;

public class InfusementChamberBlock extends BaseEntityBlock {
    public static final MapCodec<InfusementChamberBlock> CODEC = simpleCodec(InfusementChamberBlock::new);
    public static final BooleanProperty PRIMARY_FILLED = BooleanProperty.create("primary_filled");
    public static final BooleanProperty SECONDARY_FILLED = BooleanProperty.create("secondary_filled");
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public InfusementChamberBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PRIMARY_FILLED, false).setValue(SECONDARY_FILLED, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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

    @Override
    public @org.jspecify.annotations.Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new InfusementChamberBlockEntity(worldPosition, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY.get(),
                InfusementChamberBlockEntity::tick);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @org.jspecify.annotations.Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);

        InfusementChamberBlockEntity entity = (InfusementChamberBlockEntity) level.getBlockEntity(pos);
        if(entity != null)
            entity.setEmpowerments();
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        InfusementChamberBlockEntity entity = (InfusementChamberBlockEntity) level.getBlockEntity(pos);
        if(entity != null)
            entity.setEmpowerments();
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void affectNeighborsAfterRemoval(final BlockState state, final ServerLevel level, final BlockPos pos, final boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide() && level.getBlockEntity(blockPos) instanceof InfusementChamberBlockEntity) {
            MenuProvider screenHandlerFactory = ((InfusementChamberBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                //? fabric {
                /*player.openMenu(screenHandlerFactory);
                *///?}
                //? neoforge {
                player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
                 //?}
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide() && level.getBlockEntity(blockPos) instanceof InfusementChamberBlockEntity) {
            MenuProvider screenHandlerFactory = ((InfusementChamberBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                //? fabric {
                /*player.openMenu(screenHandlerFactory);
                *///?}
                //? neoforge {
                player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
                 //?}
            }
        }

        return InteractionResult.SUCCESS;
    }
}