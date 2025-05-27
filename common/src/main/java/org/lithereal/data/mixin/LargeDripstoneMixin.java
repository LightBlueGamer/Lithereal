package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.util.CommonUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/world/level/levelgen/feature/LargeDripstoneFeature$LargeDripstone")
public class LargeDripstoneMixin {
    @WrapOperation(method = "placeBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private boolean modifyBlock(WorldGenLevel worldGenLevel, BlockPos pos, BlockState state, int i, Operation<Boolean> original) {
        return original.call(worldGenLevel, pos, CommonUtils.DRIPSTONE_REPLACEMENT.defaultBlockState(), i);
    }
}
