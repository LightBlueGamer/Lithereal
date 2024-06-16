package org.lithereal.neoforge.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.world.block.FreezingStationBlock;
import org.lithereal.world.block.entity.FreezingStationBlockEntity;
import org.lithereal.neoforge.world.block.entity.ForgeFreezingStationBlockEntity;

public class ForgeFreezingStationBlock extends FreezingStationBlock {
    public static final MapCodec<ForgeFreezingStationBlock> CODEC = simpleCodec(ForgeFreezingStationBlock::new);
    public ForgeFreezingStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeFreezingStationBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ForgeFreezingStationBlockEntity) {
                Containers.dropContents(pLevel, pPos, (ForgeFreezingStationBlockEntity)blockEntity);
                pLevel.updateNeighbourForOutputSignal(pPos,this);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof FreezingStationBlockEntity) {
            MenuProvider screenHandlerFactory = ((ForgeFreezingStationBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof FreezingStationBlockEntity) {
            MenuProvider screenHandlerFactory = ((ForgeFreezingStationBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
