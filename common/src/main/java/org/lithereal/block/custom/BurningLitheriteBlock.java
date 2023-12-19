package org.lithereal.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.block.entity.BurningLitheriteBlockEntity;

public class BurningLitheriteBlock extends BaseEntityBlock {
    public BurningLitheriteBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(Level arg, BlockPos arg2, BlockState arg3, Entity arg4) {
        if (!arg4.isSteppingCarefully() && arg4 instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)arg4)) {
            arg4.hurt(arg.damageSources().hotFloor(), 6.0F);
        }

        super.stepOn(arg, arg2, arg3, arg4);
    }

    private static final VoxelShape SHAPE =
            Block.box(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BurningLitheriteBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (type == ModBlockEntities.BURNING_LITHERITE_BLOCK.get()) {
            return (lvl, pos, blkState, blockEntity) -> {
                if (blockEntity instanceof BurningLitheriteBlockEntity burningEntity) {
                    burningEntity.tick(lvl, pos, blkState);
                }
            };
        }
        return null;
    }
}