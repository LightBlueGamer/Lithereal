package org.lithereal.fabric.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.custom.InfusementChamberBlock;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.fabric.block.entity.FabricInfusementChamberBlockEntity;

public class FabricInfusementChamberBlock extends InfusementChamberBlock implements EntityBlock {
    public static final MapCodec<FabricInfusementChamberBlock> CODEC = simpleCodec(FabricInfusementChamberBlock::new);

    public FabricInfusementChamberBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FabricInfusementChamberBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FabricInfusementChamberBlockEntity) {
                Containers.dropContents(world, pos, (FabricInfusementChamberBlockEntity)blockEntity);
                world.updateNeighbourForOutputSignal(pos,this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof InfusementChamberBlockEntity) {
            MenuProvider screenHandlerFactory = ((FabricInfusementChamberBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof InfusementChamberBlockEntity) {
            MenuProvider screenHandlerFactory = ((FabricInfusementChamberBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
