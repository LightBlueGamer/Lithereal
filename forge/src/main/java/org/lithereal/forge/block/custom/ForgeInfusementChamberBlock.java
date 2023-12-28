package org.lithereal.forge.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.custom.FreezingStationBlock;
import org.lithereal.block.custom.InfusementChamberBlock;
import org.lithereal.block.entity.FreezingStationBlockEntity;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.forge.block.entities.ForgeFreezingStationBlockEntity;
import org.lithereal.forge.block.entities.ForgeInfusementChamberBlockEntity;

public class ForgeInfusementChamberBlock extends InfusementChamberBlock {
    public ForgeInfusementChamberBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeInfusementChamberBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ForgeInfusementChamberBlockEntity) {
                ((ForgeInfusementChamberBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof InfusementChamberBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (ForgeInfusementChamberBlockEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
}
