package org.litecraft.lithereal.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.item.ModItemGroup;

public class ModBlocks {
    public static final Block LITHERITE_BLOCK = registerBlock("litherite_block",
            new Block(FabricBlockSettings.of(Material.AMETHYST).strength(6.0f).requiresTool()), ModItemGroup.LITHEREAL);
    public static final Block BURNING_LITHERITE_BLOCK = registerBlockBurning("heated_litherite_block",
            new Block(FabricBlockSettings.of(Material.AMETHYST).strength(6.0f).requiresTool()), ModItemGroup.LITHEREAL);
    public static final Block FROZEN_LITHERITE_BLOCK = registerBlock("cooled_litherite_block",
            new Block(FabricBlockSettings.of(Material.AMETHYST).strength(6.0f).requiresTool()), ModItemGroup.LITHEREAL);

    public static final Block LITHERITE_ORE = registerBlock("litherite_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool(),
                    UniformIntProvider.create(2, 6)), ModItemGroup.LITHEREAL);
    public static final Block DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(6.0f).requiresTool(),
                    UniformIntProvider.create(2, 6)), ModItemGroup.LITHEREAL);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(Lithereal.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Lithereal.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    private static Block registerBlockBurning(String name, Block block, ItemGroup group) {
        registerBlockItemBurning(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(Lithereal.MOD_ID, name), block);
    }

    private static Item registerBlockItemBurning(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Lithereal.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().fireproof()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks() {
        Lithereal.LOGGER.info("Registering ModBlocks for " + Lithereal.MOD_ID);
    }
}