package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.lithereal.Lithereal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$Placer")
public class JigsawPlacementMixin {
    @ModifyExpressionValue(method = "tryPlacingChildren", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/Shapes;joinIsNotEmpty(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/BooleanOp;)Z"))
    public boolean disableCheck(boolean original, @Local(ordinal = 0) ResourceKey<StructureTemplatePool> pool) {
        return !pool.identifier().equals(Lithereal.id("nouveau/exit_vaults")) && original;
    }
}
