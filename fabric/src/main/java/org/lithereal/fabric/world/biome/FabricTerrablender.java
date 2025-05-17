package org.lithereal.fabric.world.biome;

import net.minecraft.resources.ResourceLocation;
import org.lithereal.Lithereal;
import org.lithereal.data.worldgen.biome.ModOverworldRegion;
import org.lithereal.data.worldgen.biome.surface.ModSurfaceRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class FabricTerrablender implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "overworld"), 4));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Lithereal.MOD_ID, ModSurfaceRules.makeRules());
    }
}