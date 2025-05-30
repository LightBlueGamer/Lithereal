package org.lithereal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.entity.FireCrucibleBlockEntity;

public abstract class FireCrucibleBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape OUTER_SHAPE = Shapes.block();
    public static final VoxelShape SHAPE;
    public static final EnumProperty<FireCrucibleBlockEntity.HeatState> HEAT_STATE = EnumProperty.create("heat_state", FireCrucibleBlockEntity.HeatState.class);

    public FireCrucibleBlock(Properties properties) {
        super(properties.sound(SoundType.STONE));
    }

    static {
        SHAPE = Shapes.join(Shapes.join(OUTER_SHAPE, Block.box(1.0, 15.0, 1.0, 15.0, 16.0, 15.0), BooleanOp.ONLY_FIRST), Block.box(2.0, 5.0, 2.0, 14.0, 15.0, 14.0), BooleanOp.ONLY_FIRST);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return OUTER_SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(HEAT_STATE, FireCrucibleBlockEntity.HeatState.UNLIT);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HEAT_STATE);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);
        if (blockState.getValue(HEAT_STATE).isLit()) {
            if (randomSource.nextInt(24) == 0) {
                level.playLocalSound(
                        (double)blockPos.getX() + 0.5,
                        (double)blockPos.getY() + 0.5,
                        (double)blockPos.getZ() + 0.5,
                        SoundEvents.FIRE_AMBIENT,
                        SoundSource.BLOCKS,
                        1.0F + randomSource.nextFloat(),
                        randomSource.nextFloat() * 0.7F + 0.3F,
                        false
                );
            }

            for (int i = 0; i < 3; i++) {
                double d = (double)blockPos.getX() + randomSource.nextDouble();
                double e = (double)blockPos.getY() + randomSource.nextDouble() * 0.5 + 0.5;
                double f = (double)blockPos.getZ() + randomSource.nextDouble();
                level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
            }
        }
    }

    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FireCrucibleBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type, LitherealExpectPlatform.getFireCrucibleBlockEntity(),
                FireCrucibleBlockEntity::tick);
    }
}