package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.ElectricCrucibleBlockEntity;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.item.ModToolItems;

public class ElectricCrucibleBlock extends BaseEntityBlock {
    public static final MapCodec<ElectricCrucibleBlock> CODEC = simpleCodec(ElectricCrucibleBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<FireCrucibleBlockEntity.HeatState> HEAT_STATE = FireCrucibleBlock.HEAT_STATE;

    public ElectricCrucibleBlock(Properties properties) {
        super(properties.sound(SoundType.STONE));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(HEAT_STATE, FireCrucibleBlockEntity.HeatState.UNLIT);
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
        builder.add(FACING, HEAT_STATE);
    }

    /* BLOCK ENTITY */

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ElectricCrucibleBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.ELECTRIC_CRUCIBLE_BLOCK_ENTITY.get(),
                ElectricCrucibleBlockEntity::tick);
    }

    @Override
    protected void affectNeighborsAfterRemoval(final BlockState state, final ServerLevel level, final BlockPos pos, final boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide() && level.getBlockEntity(blockPos) instanceof ElectricCrucibleBlockEntity) {
            MenuProvider screenHandlerFactory = ((ElectricCrucibleBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                //? fabric {
                player.openMenu(screenHandlerFactory);
                //?}
                //? neoforge {
                /*player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
                 *///?}
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (itemStack.is(ModToolItems.LITHERITE_WRENCH.get())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof ElectricCrucibleBlockEntity electricCrucibleBlockEntity) {
                electricCrucibleBlockEntity.toggleOn();
                return InteractionResult.SUCCESS;
            }
        }
        if (!level.isClientSide() && level.getBlockEntity(blockPos) instanceof ElectricCrucibleBlockEntity) {
            MenuProvider screenHandlerFactory = ((ElectricCrucibleBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                //? fabric {
                player.openMenu(screenHandlerFactory);
                //?}
                //? neoforge {
                /*player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
                *///?}
            }
        }

        return InteractionResult.SUCCESS;
    }
}