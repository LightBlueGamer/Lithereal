package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static org.lithereal.block.ModBlocks.registerBlock;
import static org.lithereal.block.ModBlocks.registerBlockOnly;

public class ModVegetationBlocks {
    public static final RegistrySupplier<Block> MALISHROOM = registerBlock("malishroom", () ->
            new MushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM)));

    public static final RegistrySupplier<Block> POTTED_MALISHROOM = registerBlockOnly("potted_malishroom", () ->
            new FlowerPotBlock(MALISHROOM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_RED_MUSHROOM)));

    public static final RegistrySupplier<Block> FORTSHROOM = registerBlock("fortshroom", () ->
            new MushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));

    public static final RegistrySupplier<Block> POTTED_FORTSHROOM = registerBlockOnly("potted_fortshroom", () ->
            new FlowerPotBlock(FORTSHROOM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_BROWN_MUSHROOM)));

    public static void register() {

    }
}
