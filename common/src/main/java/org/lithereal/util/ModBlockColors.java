package org.lithereal.util;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.world.block.entity.InfusedLitheriteBlockEntity;

public class ModBlockColors {
    public static final BlockColor INFUSED_LITHERITE_BLOCK_COLOR = (BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) -> {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof InfusedLitheriteBlockEntity infusedLitheriteBlockEntity)
            return tintIndex == 0 ? infusedLitheriteBlockEntity.getStoredPotion().getColor() : -1;

        return -1;
    };
}
