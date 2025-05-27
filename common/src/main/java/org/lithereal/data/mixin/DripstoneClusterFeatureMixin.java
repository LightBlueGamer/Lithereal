package org.lithereal.data.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.DripstoneClusterFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import org.lithereal.block.ModBlocks;
import org.lithereal.data.worldgen.ModBiomes;
import org.lithereal.util.CommonUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DripstoneClusterFeature.class)
public class DripstoneClusterFeatureMixin {
    @Inject(method = "place", at = @At(value = "HEAD"))
    public void updateBlock(FeaturePlaceContext<DripstoneClusterConfiguration> featurePlaceContext, CallbackInfoReturnable<Boolean> cir) {
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        BlockPos pos = featurePlaceContext.origin();
        Holder<Biome> biome = worldGenLevel.getBiome(pos);
        if (biome.is(ModBiomes.LITHERITE_CAVES)) CommonUtils.DRIPSTONE_REPLACEMENT = ModBlocks.LITHERITE_CRYSTAL_BLOCK.get();
        else CommonUtils.DRIPSTONE_REPLACEMENT = Blocks.DRIPSTONE_BLOCK;
    }
    @Inject(method = "place", at = @At(value = "TAIL"))
    public void resetBlock(FeaturePlaceContext<DripstoneClusterConfiguration> featurePlaceContext, CallbackInfoReturnable<Boolean> cir) {
        CommonUtils.DRIPSTONE_REPLACEMENT = null;
    }
}
