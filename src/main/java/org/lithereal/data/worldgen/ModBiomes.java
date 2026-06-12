package org.lithereal.data.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.lithereal.Lithereal;

public class ModBiomes {
    public static final ResourceKey<Biome> LITHERITE_CAVES = ResourceKey.create(Registries.BIOME, Lithereal.id("litherite_caves"));
}
