package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.Lithereal;
import org.lithereal.world.feature.EtherealCoreArenaFeature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {
    @WrapMethod(method = "getDestroySpeed")
    public float newDestroySpeed(BlockGetter blockGetter, BlockPos blockPos, Operation<Float> original) {
        if (!(blockGetter instanceof Level level) || !level.dimension().location().equals(Lithereal.id("ethereal_core")) || !EtherealCoreArenaFeature.UNCHANGEABLE.isInside(blockPos)) return original.call(blockGetter, blockPos);
        return -1;
    }
}