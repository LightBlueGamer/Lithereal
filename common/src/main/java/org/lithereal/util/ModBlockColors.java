package org.lithereal.util;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;

import java.util.function.BiFunction;

public class ModBlockColors {
    public static final BiFunction<ItemStack, Integer, Integer> INFUSED_LITHERITE_COLOR_HANDLER = (itemStack, layer) ->
            layer == 0 ? itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor() : -1;

    public static final BlockColor INFUSED_LITHERITE_BLOCK_COLOR = (BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) -> {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof InfusedLitheriteBlockEntity infusedLitheriteBlockEntity)
            return tintIndex == 0 ? infusedLitheriteBlockEntity.getStoredPotion().getColor() : -1;

        return -1;
    };
}
