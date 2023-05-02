package org.litecraft.lithereal;

import net.fabricmc.api.ModInitializer;

import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItemGroup;
import org.litecraft.lithereal.item.ModItems;
import org.litecraft.lithereal.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lithereal implements ModInitializer {
    public static final String MOD_ID = "lithereal";
    public static final Logger LOGGER = LoggerFactory.getLogger("lithereal");

    @Override
    public void onInitialize() {
        ModItemGroup.registerItemGroups();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModWorldGeneration.generateModWorldGen();
    }
}