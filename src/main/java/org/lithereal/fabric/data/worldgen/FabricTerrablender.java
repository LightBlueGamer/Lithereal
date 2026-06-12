package org.lithereal.fabric.data.worldgen;

//? fabric {
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class FabricTerrablender implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new LitherealOverworldRegion(2));
    }
}
//?}