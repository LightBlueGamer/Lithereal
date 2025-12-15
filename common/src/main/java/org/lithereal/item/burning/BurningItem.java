package org.lithereal.item.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.ability.AbilityItem;

public interface BurningItem extends AbilityItem {
    void getDrops(Level level, BlockState blockState, BlockPos blockPos, ItemStack itemStack, LivingEntity livingEntity, BlockEntity blockEntity);
}