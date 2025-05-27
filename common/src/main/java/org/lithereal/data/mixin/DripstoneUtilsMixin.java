package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import org.lithereal.util.CommonUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DripstoneUtils.class)
public class DripstoneUtilsMixin {
    @ModifyReceiver(method = "placeDripstoneBlockIfPossible", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    private static Block modifyBlock(Block instance, @Local(ordinal = 0, argsOnly = true) LevelAccessor levelAccessor, @Local(ordinal = 0, argsOnly = true) BlockPos pos) {
        if (CommonUtils.DRIPSTONE_REPLACEMENT != null) return CommonUtils.DRIPSTONE_REPLACEMENT;
        return instance;
    }
}
