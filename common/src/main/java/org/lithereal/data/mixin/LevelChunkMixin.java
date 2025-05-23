package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.lithereal.Lithereal;
import org.lithereal.world.feature.EtherealCoreArenaFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin {
    @Shadow public abstract BlockState getBlockState(BlockPos blockPos);

    @Shadow @Final
    Level level;

    @WrapMethod(method = "setBlockState")
    public BlockState setState(BlockPos blockPos, BlockState blockState, boolean bl, Operation<BlockState> original) {
        if (!level.dimension().location().equals(Lithereal.id("ethereal_core")) || getBlockState(blockPos).canBeReplaced() || !EtherealCoreArenaFeature.UPDATED.get() || !EtherealCoreArenaFeature.UNCHANGEABLE.isInside(blockPos)) return original.call(blockPos, blockState, bl);
        return null;
    }
}
