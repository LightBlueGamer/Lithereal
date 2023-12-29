package org.lithereal.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FrozenLitheriteBlock extends Block {
    public FrozenLitheriteBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (entity instanceof LivingEntity) {
            int currentTicksFrozen = entity.getTicksFrozen();

            int incrementPerStep = 200 / 20;

            int newTicksFrozen = Math.min(currentTicksFrozen + incrementPerStep, 200);
            entity.setTicksFrozen(newTicksFrozen);
        }
        super.stepOn(level, blockPos, blockState, entity);
    }
}
