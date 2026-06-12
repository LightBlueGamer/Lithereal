package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.BlueFireBlock;
import org.lithereal.block.ModBlocks;
import org.lithereal.tags.ModTags;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {
    @WrapMethod(method = "getState")
    private static BlockState modifyState(BlockGetter blockGetter, BlockPos blockPos, Operation<BlockState> original) {
        if (blockGetter.getBlockState(blockPos.below()).is(ModTags.BLUE_FIRE_BASE_BLOCKS)) return ((BlueFireBlock)ModBlocks.BLUE_FIRE.get()).getStateForPlacement(null, blockGetter, blockPos);
        return original.call(blockGetter, blockPos);
    }
}
