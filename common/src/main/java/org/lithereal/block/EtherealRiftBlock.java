package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
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
    public Direction.Axis getAxis(BlockState blockState) {
        return blockState.getValue(AXIS);
    }

    @Override
    public boolean isTransportPortal(BlockPos blockPos, Level level) {
        return !(level.getBlockEntity(blockPos) instanceof EtherealRiftBlockEntity etherealRiftBlockEntity) ? super.isTransportPortal(blockPos, level) : etherealRiftBlockEntity.getDestination().filter(levelResourceKey -> !level.dimension().equals(levelResourceKey)).isPresent();
    }

    @Override
    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        List<BlockPos> positions;
        if (level.getBlockEntity(blockPos) instanceof EtherealRiftBlockEntity etherealRiftBlockEntity) {
            positions = etherealRiftBlockEntity.getAttached();
            findAttached(level, blockPos, level.getBlockState(blockPos), positions);
            final boolean[] updated = {false};
            positions.forEach(pos -> {
                if (pos != blockPos && level.getBlockEntity(pos) instanceof EtherealRiftBlockEntity attachedEntity) {
                    attachedEntity.getAttached().add(blockPos);
                    if (!updated[0]) {
                        etherealRiftBlockEntity.copyFrom(attachedEntity);
                        updated[0] = true;
                    }
                }
            });
        }
    }

    @Override
    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (level.getBlockEntity(blockPos) instanceof EtherealRiftBlockEntity etherealRiftBlockEntity) {
            etherealRiftBlockEntity.getAttached().forEach(pos -> {
                if (!pos.equals(blockPos)) level.removeBlock(pos, true);
            });
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
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
                        WorldBorder worldBorder = toMoveTo.getWorldBorder();
                        double scale = DimensionType.getTeleportationScale(serverLevel.dimensionType(), toMoveTo.dimensionType());
                        BlockPos destBlockPos = worldBorder.clampToBounds(entity.getX() * scale, entity.getY(), entity.getZ() * scale);
                        BlockState blockState = serverLevel.getBlockState(blockPos);
                        Direction.Axis axis = blockState.getValue(AXIS);
                        Vec3 destPos = createSafePos(toMoveTo, destBlockPos, axis.isVertical() ? Direction.Axis.X : axis).getBottomCenter();

                        return new DimensionTransition(toMoveTo, destPos, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND
                                .then(DimensionTransition.PLACE_PORTAL_TICKET)
                                .then(entity1 -> destroyAttachedBlocks(serverLevel, blockPos, blockState)));
                    }
                }).orElseGet(() -> {
                    WorldBorder worldBorder = serverLevel.getWorldBorder();
                    BlockPos destBlockPos = worldBorder.clampToBounds(etherealRiftBlockEntity.getPortalPosition(serverLevel, blockPos));
                    BlockState blockState = serverLevel.getBlockState(blockPos);
                    Direction.Axis axis = blockState.getValue(AXIS);
                    Vec3 destPos = createSafePos(serverLevel, destBlockPos, axis.isVertical() ? Direction.Axis.X : axis).getBottomCenter();
                    return new DimensionTransition(serverLevel, destPos, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND
                            .then(DimensionTransition.PLACE_PORTAL_TICKET)
                            .then(entity1 -> destroyAttachedBlocks(serverLevel, blockPos, blockState)));
                });
    }

    public static void destroyAttachedBlocks(Level level, BlockPos blockPos, BlockState blockState) {
        level.removeBlock(blockPos, true);
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
    protected @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
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
        Direction face = blockPlaceContext.getClickedFace();
        BlockState clickedOnState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(face.getOpposite()));
        if (!blockPlaceContext.isSecondaryUseActive() && clickedOnState.is(this)) {
            Direction.Axis axis = clickedOnState.getValue(AXIS);
            Direction.Axis sideAxis = switch (blockPlaceContext.getClickedFace().getAxis()) {
                case X, Z -> Direction.Axis.Y;
                case Y -> Direction.Axis.Z;
            };
            Direction.Axis otherSideAxis = switch (blockPlaceContext.getClickedFace().getAxis()) {
                case X, Y -> Direction.Axis.X;
                case Z -> Direction.Axis.Z;
            };
            boolean useClickedStateAxis = axis.equals(sideAxis) || axis.equals(otherSideAxis);
            if (useClickedStateAxis) return this.defaultBlockState().setValue(AXIS, axis);
        }
        return this.defaultBlockState().setValue(AXIS, face.getAxis());
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ETHEREAL_RIFT.get(), EtherealRiftBlockEntity::tick);
    }

    public static BlockPos createSafePos(ServerLevel level, BlockPos destBlockPos, Direction.Axis axis) {
        Direction posAxis = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double nearestDist = -1.0;
        BlockPos nearestPos = null;
        double fallbackDist = -1.0;
        BlockPos fallbackPos = null;
        WorldBorder worldBorder = level.getWorldBorder();
        int effectiveBuildHeight = Math.min(level.getMaxBuildHeight(), level.getMinBuildHeight() + level.getLogicalHeight()) - 1;
        BlockPos.MutableBlockPos mutableDestPos = destBlockPos.mutable();

        for (BlockPos.MutableBlockPos curPos : BlockPos.spiralAround(destBlockPos, 16, Direction.EAST, Direction.SOUTH)) {
            int height = Math.min(effectiveBuildHeight, level.getHeight(Heightmap.Types.MOTION_BLOCKING, curPos.getX(), curPos.getZ()));
            if (worldBorder.isWithinBounds(curPos) && worldBorder.isWithinBounds(curPos.move(posAxis, 1))) {
                curPos.move(posAxis.getOpposite(), 1);

                for (int curHeight = height; curHeight >= level.getMinBuildHeight(); curHeight--) {
                    curPos.setY(curHeight);
                    if (canPortalReplaceBlock(level, curPos)) {
                        int m = curHeight;

                        while (curHeight > level.getMinBuildHeight() && canPortalReplaceBlock(level, curPos.move(Direction.DOWN))) {
                            curHeight--;
                        }

                        if (curHeight + 4 <= effectiveBuildHeight) {
                            int n = m - curHeight;
                            if (n <= 0 || n >= 3) {
                                curPos.setY(curHeight);
                                if (canHostFrame(level, curPos, mutableDestPos, posAxis, 0)) {
                                    double dist = destBlockPos.distSqr(curPos);
                                    if (canHostFrame(level, curPos, mutableDestPos, posAxis, -1)
                                            && canHostFrame(level, curPos, mutableDestPos, posAxis, 1)
                                            && (nearestDist == -1.0 || nearestDist > dist)) {
                                        nearestDist = dist;
                                        nearestPos = curPos.immutable();
                                    }

                                    if (nearestDist == -1.0 && (fallbackDist == -1.0 || fallbackDist > dist)) {
                                        fallbackDist = dist;
                                        fallbackPos = curPos.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (nearestDist == -1.0 && fallbackDist != -1.0) {
            nearestPos = fallbackPos;
            nearestDist = fallbackDist;
        }

        if (nearestDist == -1.0) {
            int height = Math.max(level.getMinBuildHeight() + 1, 70);
            int maxHeight = effectiveBuildHeight - 9;
            if (maxHeight < height) {
                return destBlockPos;
            }

            nearestPos = new BlockPos(destBlockPos.getX() - posAxis.getStepX(), Mth.clamp(destBlockPos.getY(), height, maxHeight), destBlockPos.getZ() - posAxis.getStepZ())
                    .immutable();
            nearestPos = worldBorder.clampToBounds(nearestPos);
            Direction clockwiseDir = posAxis.getClockWise();

            for (int opposingHoriScale = -2; opposingHoriScale < 3; opposingHoriScale++) {
                for (int horiScale = -2; horiScale < 3; horiScale++) {
                    for (int vertOff = -1; vertOff < 4; vertOff++) {
                        BlockState blockState = vertOff < 0 ? ModBlocks.PURE_ETHEREAL_CRYSTAL_BLOCK.get().defaultBlockState() : Blocks.AIR.defaultBlockState();
                        mutableDestPos.setWithOffset(nearestPos, horiScale * posAxis.getStepX() + opposingHoriScale * clockwiseDir.getStepX(), vertOff, horiScale * posAxis.getStepZ() + opposingHoriScale * clockwiseDir.getStepZ());
                        level.setBlockAndUpdate(mutableDestPos, blockState);
                    }
                }
            }
        }

        return nearestPos.immutable();
    }

    private static boolean canPortalReplaceBlock(Level level, BlockPos.MutableBlockPos mutableBlockPos) {
        BlockState blockState = level.getBlockState(mutableBlockPos);
        return blockState.canBeReplaced() && blockState.getFluidState().isEmpty();
    }

    private static boolean canHostFrame(Level level, BlockPos pos, BlockPos.MutableBlockPos mutableBlockPos, Direction dir, int scale) {
        Direction clockwise = dir.getClockWise();

        for (int horiScale = -2; horiScale < 3; horiScale++) {
            for (int vertOff = -1; vertOff < 4; vertOff++) {
                mutableBlockPos.setWithOffset(pos, dir.getStepX() * horiScale + clockwise.getStepX() * scale, vertOff, dir.getStepZ() * horiScale + clockwise.getStepZ() * scale);
                if (vertOff < 0 && !level.getBlockState(mutableBlockPos).isSolid()) {
                    return false;
                }

                if (vertOff >= 0 && !canPortalReplaceBlock(level, mutableBlockPos)) {
                    return false;
                }
            }
        }

        return true;
    }
}
