package org.lithereal.neoforge.data.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.Lithereal;
import org.lithereal.world.feature.EtherealCoreArenaFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExplosionDamageCalculator.class)
public class ExplosionDamageCalculatorMixin {
    @WrapOperation(method = "getBlockExplosionResistance", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getExplosionResistance(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Explosion;)F"))
    public float getResistance(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Explosion explosion, Operation<Float> original) {
        if (!(blockGetter instanceof Level level) || !level.dimension().location().equals(Lithereal.id("ethereal_core")) || !EtherealCoreArenaFeature.UNCHANGEABLE.isInside(blockPos)) return original.call(instance, blockGetter, blockPos, explosion);
        return 3600000.0F;
    }
}
