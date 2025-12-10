package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.EtherealRiftBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;

import java.util.ArrayList;
import java.util.List;

public class EtherealRiftBlock extends EtherealCorePortalBlock {
    public static final MapCodec<EtherealRiftBlock> CODEC = simpleCodec(EtherealRiftBlock::new);
    protected static final VoxelShape SHAPE_X_AXIS = Block.box(0.0F, 0.0F, 7.0F, 16.0F, 16.0F, 9.0F);
    protected static final VoxelShape SHAPE_Z_AXIS = Block.box(7.0F, 0.0F, 0.0F, 9.0F, 16.0F, 16.0F);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    @Override
    public @NotNull MapCodec<? extends EtherealRiftBlock> codec() {
        return CODEC;
    }

    public EtherealRiftBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ModBlockEntities.ETHEREAL_RIFT.get().create(blockPos, blockState);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction.Axis axis = blockState.getValue(AXIS);
        return axis.isVertical() ? SHAPE : axis == Direction.Axis.X ? SHAPE_X_AXIS : SHAPE_Z_AXIS;
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity.canUsePortal(false) && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ())), blockState.getShape(level, blockPos), BooleanOp.AND))
            entity.setAsInsidePortal(this, blockPos);
    }

    @Override
    public DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (!(blockEntity instanceof EtherealRiftBlockEntity etherealRiftBlockEntity)) return null;
        return etherealRiftBlockEntity.getDestination()
                .filter(resourceKey -> !serverLevel.dimension().equals(resourceKey))
                .map(resourceKey -> {
                    ServerLevel toMoveTo = serverLevel.getServer().getLevel(resourceKey);
                    if (toMoveTo == null) {
                        return null;
                    } else {
                        float newYRot = entity.getYRot();
                        Vec3 destPos = Vec3.atLowerCornerOf(EtherealRiftBlockEntity.findExitPosition(toMoveTo, blockPos));

                        return new DimensionTransition(toMoveTo, destPos, entity.getDeltaMovement(), newYRot, entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND
                                .then(DimensionTransition.PLACE_PORTAL_TICKET)
                                .then(entity1 -> destroyAttachedBlocks(serverLevel, blockPos, serverLevel.getBlockState(blockPos))));
                    }
                }).orElseGet(() -> {
                    Vec3 destPos = etherealRiftBlockEntity.getPortalPosition(serverLevel, blockPos);
                    return new DimensionTransition(serverLevel, destPos, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND
                            .then(DimensionTransition.PLACE_PORTAL_TICKET)
                            .then(entity1 -> destroyAttachedBlocks(serverLevel, blockPos, serverLevel.getBlockState(blockPos))));
                });
    }

    public static void destroyAttachedBlocks(Level level, BlockPos blockPos, BlockState blockState) {
        List<BlockPos> attached = new ArrayList<>();
        findAttached(level, blockPos, blockState, attached);
        attached.forEach(pos -> level.removeBlock(pos, true));
    }

    public static void findAttached(Level level, BlockPos blockPos, BlockState blockState, List<BlockPos> toEmitTo) {
        Direction.Axis axis = blockState.getValue(AXIS);
        if (axis.isHorizontal()) {
            BlockPos attachedUp = blockPos.above();
            BlockPos attachedDown = blockPos.below();
            BlockPos attachedSideA = blockPos.relative(axis, -1);
            BlockPos attachedSideB = blockPos.relative(axis, 1);
            BlockState upState = level.getBlockState(attachedUp);
            BlockState downState = level.getBlockState(attachedDown);
            BlockState sideStateA = level.getBlockState(attachedSideA);
            BlockState sideStateB = level.getBlockState(attachedSideB);
            toEmitTo.add(blockPos);
            if (!toEmitTo.contains(attachedUp) && upState.is(blockState.getBlock()) && upState.getValue(AXIS) == axis)
                findAttached(level, attachedUp, upState, toEmitTo);
            if (!toEmitTo.contains(attachedDown) && downState.is(blockState.getBlock()) && downState.getValue(AXIS) == axis)
                findAttached(level, attachedDown, downState, toEmitTo);
            if (!toEmitTo.contains(attachedSideA) && sideStateA.is(blockState.getBlock()) && sideStateA.getValue(AXIS) == axis)
                findAttached(level, attachedSideA, sideStateA, toEmitTo);
            if (!toEmitTo.contains(attachedSideB) && sideStateB.is(blockState.getBlock()) && sideStateB.getValue(AXIS) == axis)
                findAttached(level, attachedSideB, sideStateB, toEmitTo);
        } else {
            BlockPos attachedSideXA = blockPos.relative(Direction.Axis.X, -1);
            BlockPos attachedSideXB = blockPos.relative(Direction.Axis.X, 1);
            BlockPos attachedSideZA = blockPos.relative(Direction.Axis.Z, -1);
            BlockPos attachedSideZB = blockPos.relative(Direction.Axis.Z, 1);
            BlockState sideStateXA = level.getBlockState(attachedSideXA);
            BlockState sideStateXB = level.getBlockState(attachedSideXB);
            BlockState sideStateZA = level.getBlockState(attachedSideZA);
            BlockState sideStateZB = level.getBlockState(attachedSideZB);
            toEmitTo.add(blockPos);
            if (!toEmitTo.contains(attachedSideXA) && sideStateXA.is(blockState.getBlock()) && sideStateXA.getValue(AXIS) == axis)
                findAttached(level, attachedSideXA, sideStateXA, toEmitTo);
            if (!toEmitTo.contains(attachedSideXB) && sideStateXB.is(blockState.getBlock()) && sideStateXB.getValue(AXIS) == axis)
                findAttached(level, attachedSideXB, sideStateXB, toEmitTo);
            if (!toEmitTo.contains(attachedSideZA) && sideStateZA.is(blockState.getBlock()) && sideStateZA.getValue(AXIS) == axis)
                findAttached(level, attachedSideZA, sideStateZA, toEmitTo);
            if (!toEmitTo.contains(attachedSideZB) && sideStateZB.is(blockState.getBlock()) && sideStateZB.getValue(AXIS) == axis)
                findAttached(level, attachedSideZB, sideStateZB, toEmitTo);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return rotatePillar(blockState, rotation);
    }

    public static BlockState rotatePillar(BlockState blockState, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (blockState.getValue(AXIS)) {
                case X -> blockState.setValue(AXIS, Direction.Axis.Z);
                case Z -> blockState.setValue(AXIS, Direction.Axis.X);
                default -> blockState;
            };
            default -> blockState;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(AXIS, blockPlaceContext.getClickedFace().getAxis());
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ETHEREAL_RIFT.get(), EtherealRiftBlockEntity::tick);
    }
}
