package org.litecraft.lithereal.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    public ModLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.LITHERITE_BLOCK);
        addDrop(ModBlocks.BURNING_LITHERITE_BLOCK);
        addDrop(ModBlocks.FROZEN_LITHERITE_BLOCK);

        addDrop(ModBlocks.LITHERITE_ORE, oreDrops(ModBlocks.LITHERITE_ORE, ModItems.LITHERITE_CRYSTAL));
        addDrop(ModBlocks.DEEPSLATE_LITHERITE_ORE, oreDrops(ModBlocks.DEEPSLATE_LITHERITE_ORE, ModItems.LITHERITE_CRYSTAL));
    }
}