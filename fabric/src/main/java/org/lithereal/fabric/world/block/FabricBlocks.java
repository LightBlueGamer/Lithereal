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

public class FabricBlocks {
    public static final Block ELECTRIC_CRUCIBLE_BLOCK = registerBlock("electric_crucible",
            new FabricElectricCrucibleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

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

    public static void registerModBlocks() {

    }
}
