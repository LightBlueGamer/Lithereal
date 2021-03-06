package net.thelightbluegame.lithereal.world.feature.tree;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.thelightbluegame.lithereal.world.feature.ModConfiguredFeatures;
import org.jetbrains.annotations.Nullable;
import java.util.Random;
import net.minecraft.util.registry.RegistryEntry;

public class MangledSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ModConfiguredFeatures.MANGLED_TREE;
    }
}
