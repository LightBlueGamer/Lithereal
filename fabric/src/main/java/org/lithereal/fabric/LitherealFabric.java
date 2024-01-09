package org.lithereal.fabric;

import net.fabricmc.api.ModInitializer;
import org.lithereal.Lithereal;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.entity.FabricBlockEntities;
import org.lithereal.fabric.item.FabricItems;
import org.lithereal.fabric.screen.FabricScreenHandlers;
import org.lithereal.fabric.worldgen.gen.FabricWorldGeneration;

public class LitherealFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricItems.registerModItems();
        FabricBlocks.registerModBlocks();
        FabricBlockEntities.registerBlockEntities();
        FabricScreenHandlers.registerScreenHandlers();
        FabricWorldGeneration.generateModWorldGen();
        Lithereal.init();
    }


}
