package org.lithereal.fabric.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.custom.FreezingStationBlock;
import org.lithereal.block.entity.FreezingStationBlockEntity;
import org.lithereal.fabric.block.entity.FabricFreezingStationBlockEntity;

public class FabricFreezingStationBlock extends FreezingStationBlock implements EntityBlock {

    public FabricFreezingStationBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FabricFreezingStationBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FabricFreezingStationBlockEntity) {
                Containers.dropContents(world, pos, (FabricFreezingStationBlockEntity)blockEntity);
                world.updateNeighbourForOutputSignal(pos,this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof FreezingStationBlockEntity) {
            MenuProvider screenHandlerFactory = ((FreezingStationBlockEntity) pLevel.getBlockEntity(pPos));

            if (screenHandlerFactory != null) {
                pPlayer.openMenu(screenHandlerFactory);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
