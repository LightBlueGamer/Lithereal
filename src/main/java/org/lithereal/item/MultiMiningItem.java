package org.lithereal.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.TriFunction;

public interface MultiMiningItem {
    float getDestroySpeed(ItemStack itemStack, BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos, TriFunction<BlockState, BlockGetter, BlockPos, Float> destroySpeedFunc);
}
