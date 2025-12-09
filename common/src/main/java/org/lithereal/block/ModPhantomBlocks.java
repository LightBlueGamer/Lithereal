package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
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

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ROSE = registerBlockOnly("potted_phantom_rose", () ->
            new FlowerPotBlock(PHANTOM_ROSE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

    public static final RegistrySupplier<Block> PHANTOM_ICE_FLOWER = registerBlock("phantom_ice_flower", () ->
            new FlowerBlock(MobEffects.WEAVING, 5, BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ICE_FLOWER = registerBlockOnly("potted_phantom_ice_flower", () ->
            new FlowerPotBlock(PHANTOM_ICE_FLOWER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_CORNFLOWER)));

    public static final RegistrySupplier<Block> PHANTOM_ROSE_ETHEREAL_CORE = registerBlock("phantom_rose_ethereal_core", () ->
            new FlowerBlock(MobEffects.DIG_SPEED, 3.5F, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ROSE_ETHEREAL_CORE = registerBlockOnly("potted_phantom_rose_ethereal_core", () ->
            new FlowerPotBlock(PHANTOM_ROSE_ETHEREAL_CORE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

    public static void register() {

    }
}
