package com.haru.lithereal.block.entity;

import com.haru.lithereal.Lithereal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.haru.lithereal.block.ModBlocks;

public class BurningLitheriteBlockEntity extends BlockEntity {

    public BurningLitheriteBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BURNING_LITHERITE_BLOCK.get(), pos, state);
    }
    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BurningLitheriteBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if(level.getBlockState(blockPos.above()).getBlock() == Blocks.FIRE) {
            Lithereal.LOGGER.debug(level.getBlockState(blockPos.above()).getBlock().toString());
            level.setBlockAndUpdate(blockPos.above(), ModBlocks.BLUE_FIRE.get().defaultBlockState());
        }

    }
}
