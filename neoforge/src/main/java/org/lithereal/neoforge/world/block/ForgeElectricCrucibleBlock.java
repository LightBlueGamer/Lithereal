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
import org.lithereal.block.ElectricCrucibleBlock;
import org.lithereal.block.entity.ElectricCrucibleBlockEntity;
import org.lithereal.item.ModToolItems;
import org.lithereal.neoforge.world.block.entity.ForgeElectricCrucibleBlockEntity;

public class ForgeElectricCrucibleBlock extends ElectricCrucibleBlock {
    public static final MapCodec<ForgeElectricCrucibleBlock> CODEC = simpleCodec(ForgeElectricCrucibleBlock::new);
    public ForgeElectricCrucibleBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeElectricCrucibleBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ForgeElectricCrucibleBlockEntity) {
                Containers.dropContents(pLevel, pPos, (ForgeElectricCrucibleBlockEntity)blockEntity);
                pLevel.updateNeighbourForOutputSignal(pPos,this);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull BlockHitResult blockHitResult) {
        if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof ElectricCrucibleBlockEntity) {
            MenuProvider screenHandlerFactory = ((ForgeElectricCrucibleBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, @NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (itemStack.is(ModToolItems.LITHERITE_WRENCH.get())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof ElectricCrucibleBlockEntity electricCrucibleBlockEntity) {
                electricCrucibleBlockEntity.toggleOn();
                return ItemInteractionResult.SUCCESS;
            }
        }
        if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof ElectricCrucibleBlockEntity) {
            MenuProvider screenHandlerFactory = ((ForgeElectricCrucibleBlockEntity) level.getBlockEntity(blockPos));

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory, (buf) -> buf.writeBlockPos(blockPos));
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
