package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.world.feature.ModFeatures;

import static org.lithereal.block.ModBlocks.registerBlock;
import static org.lithereal.block.ModBlocks.registerBlockOnly;

public class ModVegetationBlocks {
    public static final RegistrySupplier<Block> MALISHROOM = registerBlock("malishroom", () ->
            new NoLightLimitMushroomBlock(ModFeatures.HUGE_MALISHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> POTTED_MALISHROOM = registerBlockOnly("potted_malishroom", () ->
            new FlowerPotBlock(MALISHROOM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_RED_MUSHROOM).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> FORTSHROOM = registerBlock("fortshroom", () ->
            new NoLightLimitMushroomBlock(ModFeatures.HUGE_FORTSHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> POTTED_FORTSHROOM = registerBlockOnly("potted_fortshroom", () ->
            new FlowerPotBlock(FORTSHROOM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_BROWN_MUSHROOM).mapColor(MapColor.COLOR_GREEN)));

    public static void register() {

    }
}
