package org.lithereal.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.lithereal.Lithereal;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.fabric.world.block.entity.FabricBlockEntities;
import org.lithereal.fabric.compat.ModWeaponType;
import org.lithereal.fabric.world.item.FabricItems;
import org.lithereal.fabric.screen.FabricScreenHandlers;
import org.lithereal.fabric.data.worldgen.FabricWorldGeneration;

public class LitherealFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("combatify"))
            ModWeaponType.init();
        FabricItems.registerModItems();
        FabricBlocks.registerModBlocks();
        FabricBlockEntities.registerBlockEntities();
        FabricScreenHandlers.registerScreenHandlers();
        FabricWorldGeneration.generateModWorldGen();
        Lithereal.init();
    }


}
