package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ScorchedBlock extends MagmaBlock {
    public static final MapCodec<MagmaBlock> CODEC = simpleCodec(MagmaBlock::new);

    public @NotNull MapCodec<MagmaBlock> codec() {
        return CODEC;
    }

    public ScorchedBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        entity.hurt(level.damageSources().hotFloor(), 2.0F);

        super.stepOn(level, blockPos, blockState, entity);
    }

    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        level.scheduleTick(blockPos, this, 20);
    }
}