package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.MultiMiningItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @WrapOperation(method = "getDestroyProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"))
    public float wrapDestroySpeed(BlockState instance, BlockGetter blockGetter, BlockPos pos, Operation<Float> original, @Local(ordinal = 0, argsOnly = true) Player player) {
        ItemStack miningItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (miningItem.getItem() instanceof MultiMiningItem multiMiningItem)
            return multiMiningItem.getDestroySpeed(miningItem, instance, player, blockGetter, pos, original::call);
        return original.call(instance, blockGetter, pos);
    }
}
