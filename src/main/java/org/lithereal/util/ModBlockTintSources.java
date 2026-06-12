package org.lithereal.util;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class ModBlockTintSources {
    public static final BlockTintSource BLANK_LAYER = BlockTintSources.constant(-1);
    public static BlockTintSource infusedLitherite(Function<BlockEntity, Integer> getPotionColor) {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return -1;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                BlockEntity entity = level.getBlockEntity(pos);
                return getPotionColor.apply(entity);
            }
        };
    }

    public static BlockTintSource etherealGrassBlock() {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return 8573157;
            }

            @Override
            public int colorInWorld(final BlockState state, final BlockAndTintGetter level, final BlockPos pos) {
                return BiomeColors.getAverageGrassColor(level, pos);
            }

            @Override
            public int colorAsTerrainParticle(final BlockState state, final BlockAndTintGetter level, final BlockPos pos) {
                return -1;
            }
        };
    }
}
