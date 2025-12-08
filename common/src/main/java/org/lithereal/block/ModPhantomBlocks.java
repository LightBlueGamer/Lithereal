package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.item.obscured.MysteriousBlockItem;

import static org.lithereal.block.ModBlocks.*;

public class ModPhantomBlocks {
    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_ORE = registerBlock("phantom_diamond_ore", () ->
            new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_BLOCK = registerBlock("phantom_diamond_block", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_ROSE = registerBlock("phantom_rose", () ->
            new FlowerBlock(MobEffects.DAMAGE_BOOST, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_ICE_FLOWER = registerBlock("phantom_ice_flower", () ->
            new FlowerBlock(MobEffects.WEAVING, 5, BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_ROSE_ETHEREAL_CORE = registerBlock("phantom_rose_ethereal_core", () ->
            new FlowerBlock(MobEffects.DIG_SPEED, 3.5F, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static void register() {

    }
}
