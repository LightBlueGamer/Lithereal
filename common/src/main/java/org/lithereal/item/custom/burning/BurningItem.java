package org.lithereal.item.custom.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ability.AbilityItem;

public interface BurningItem extends AbilityItem {
    void getDrops(Level level, BlockState blockState, BlockPos blockPos, ItemStack itemStack, LivingEntity livingEntity, BlockEntity blockEntity);

    @Override
    default Ability getAbility() {
        return Ability.BURNING;
    }
}
