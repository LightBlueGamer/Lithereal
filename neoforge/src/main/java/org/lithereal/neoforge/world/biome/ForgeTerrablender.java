package org.lithereal.neoforge.world.biome;

import net.minecraft.resources.ResourceLocation;
import org.lithereal.Lithereal;
import org.lithereal.world.biome.ModOverworldRegion;
import terrablender.api.Regions;

public class ForgeTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "overworld"), 5));
    }
}
