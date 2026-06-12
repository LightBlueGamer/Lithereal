package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.lithereal.Lithereal;
import org.lithereal.world.feature.EtherealCoreArenaFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExplosionDamageCalculator.class)
public class ExplosionDamageCalculatorMixin {
    //? fabric {
    @WrapOperation(method = "getBlockExplosionResistance", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getExplosionResistance()F"))
    public float getResistance(Block instance, Operation<Float> original, @Local(ordinal = 0, argsOnly = true) BlockGetter blockGetter, @Local(ordinal = 0, argsOnly = true) BlockPos blockPos) {
    //?}
    //? neoforge {
    /*@WrapOperation(method = "getBlockExplosionResistance", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getExplosionResistance(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Explosion;)F"))
    public float getResistance(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Explosion explosion, Operation<Float> original) {
    *///?}
        if (!(blockGetter instanceof Level level) || !level.dimension().identifier().equals(Lithereal.id("ethereal_core")) || !EtherealCoreArenaFeature.UNCHANGEABLE.isInside(blockPos))
            //? fabric {
            return original.call(instance);
            //?}
            //? neoforge {
            /*return original.call(instance, blockGetter, blockPos, explosion);
            *///?}
        return 3600000.0F;
    }
}
