package org.lithereal.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WitheringLitheriteBlock extends Block {
    public static final MapCodec<WitheringLitheriteBlock> CODEC = simpleCodec(WitheringLitheriteBlock::new);
    public WitheringLitheriteBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 1, 1));
        }
        super.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }
}
