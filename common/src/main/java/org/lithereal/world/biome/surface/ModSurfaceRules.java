package org.lithereal.world.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.lithereal.block.ModBlocks;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(ModBlocks.LITHER_DIRT.get());
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(ModBlocks.LITHER_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource ETHERSTONE = makeStateRule(ModBlocks.ETHERSTONE.get());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource underSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT));
        SurfaceRules.RuleSource deepUnderSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, ETHERSTONE));
        SurfaceRules.RuleSource veryDeepUnderSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, ETHERSTONE));

        return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface), underSurface, deepUnderSurface, veryDeepUnderSurface);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}