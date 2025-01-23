package org.lithereal.fabric.world.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.Lithereal;
import org.lithereal.block.InfusedLitheriteBlock;
import org.lithereal.item.infused.InfusedLitheriteBlockItem;

public class FabricBlocks {

    public static final Block INFUSED_LITHERITE_BLOCK = registerColoredBlock("infused_litherite_block",
            new InfusedLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final Block FIRE_CRUCIBLE_BLOCK = registerBlock("fire_crucible",
            new FabricFireCrucibleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final Block FREEZING_STATION_BLOCK = registerBlock("freezing_station",
            new FabricFreezingStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final Block INFUSEMENT_CHAMBER_BLOCK = registerBlock("infusement_chamber",
            new FabricInfusementChamberBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
    }

    private static Block registerColoredBlock(String name, Block block) {
        registerColoredBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name), block);
    }

    private static void registerColoredBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name),
                new InfusedLitheriteBlockItem(block, new Item.Properties()));
    }

    public static void registerModBlocks() {

    }
}
