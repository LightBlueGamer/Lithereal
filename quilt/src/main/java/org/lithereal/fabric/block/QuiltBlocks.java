package org.lithereal.fabric.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.lithereal.Lithereal;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.item.custom.infused.InfusedLitheriteBlockItem;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class QuiltBlocks {

    public static final Block INFUSED_LITHERITE_BLOCK = registerBlock("infused_litherite_block",
            new InfusedLitheriteBlock(QuiltBlockSettings.copyOf(Blocks.STONE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Lithereal.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Lithereal.MOD_ID, name),
                new InfusedLitheriteBlockItem(block, new QuiltItemSettings()));
    }

    public static void registerModBlocks() {

    }
}
