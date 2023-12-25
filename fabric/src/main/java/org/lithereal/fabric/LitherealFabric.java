package org.lithereal.fabric;

import net.fabricmc.api.ModInitializer;
import org.lithereal.Lithereal;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.entities.FabricBlockEntities;

public class LitherealFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricBlocks.registerModBlocks();
        FabricBlockEntities.registerBlockEntities();
        Lithereal.init();
    }


}
