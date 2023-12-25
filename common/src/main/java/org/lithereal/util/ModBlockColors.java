package org.lithereal.util;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public class ModBlockColors {
    public static final BiFunction<ItemStack, Integer, Integer> INFUSED_LITHERITE_COLOR_HANDLER = (itemStack, layer) ->
            layer == 0 ? PotionUtils.getColor(itemStack) : -1;

    public static final BlockColor INFUSED_LITHERITE_BLOCK_COLOR = (BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) -> {
        ItemStack stack = getItemStackFromState(state);

        return INFUSED_LITHERITE_COLOR_HANDLER.apply(stack, tintIndex);
    };

    private static ItemStack getItemStackFromState(BlockState state) {
        return state.getBlock().asItem().getDefaultInstance();
    }
}
