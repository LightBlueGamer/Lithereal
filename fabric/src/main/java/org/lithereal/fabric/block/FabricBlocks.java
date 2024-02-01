package org.lithereal.fabric.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.fabric.block.custom.FabricFireCrucibleBlock;
import org.lithereal.fabric.block.custom.FabricFreezingStationBlock;
import org.lithereal.fabric.block.custom.FabricInfusementChamberBlock;
import org.lithereal.fabric.block.custom.FabricLitherCollectorBlock;
import org.lithereal.item.custom.infused.InfusedLitheriteBlockItem;

public class FabricBlocks {

    public static final Block INFUSED_LITHERITE_BLOCK = registerColoredBlock("infused_litherite_block",
            new InfusedLitheriteBlock(FabricBlockSettings.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final Block FIRE_CRUCIBLE_BLOCK = registerBlock("fire_crucible",
            new FabricFireCrucibleBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block FREEZING_STATION_BLOCK = registerBlock("freezing_station",
            new FabricFreezingStationBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    public static final Block INFUSEMENT_CHAMBER_BLOCK = registerBlock("infusement_chamber",
            new FabricInfusementChamberBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    public static final Block LITHER_COLLECTOR_BLOCK = registerBlock("lither_collector",
            new FabricLitherCollectorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Lithereal.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Lithereal.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    private static Block registerColoredBlock(String name, Block block) {
        registerColoredBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Lithereal.MOD_ID, name), block);
    }

    private static void registerColoredBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Lithereal.MOD_ID, name),
                new InfusedLitheriteBlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {

    }
}
