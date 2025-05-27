package org.lithereal.fabric.data.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import org.lithereal.Lithereal;
import org.lithereal.data.worldgen.ModBiomes;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class LitherealOverworldRegion extends Region {
    public static final ResourceLocation LOCATION = Lithereal.id("overworld");

    public LitherealOverworldRegion(int weight) {
        super(LOCATION, RegionType.OVERWORLD, weight);
    }

    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, modifiedVanillaOverworldBuilder -> modifiedVanillaOverworldBuilder.replaceBiome(Biomes.LUSH_CAVES, ModBiomes.LITHERITE_CAVES));
    }
}
