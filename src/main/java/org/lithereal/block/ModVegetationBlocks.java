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
    public static final RegistrySupplier<Block> MALISHROOM = registerBlock("malishroom", resourceKey ->
            new NoLightLimitMushroomBlock(ModFeatures.HUGE_MALISHROOM, BlockBehaviour.Properties.ofLegacyCopy(Blocks.RED_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> POTTED_MALISHROOM = registerBlockOnly("potted_malishroom", resourceKey ->
            new FlowerPotBlock(MALISHROOM.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_RED_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> FORTSHROOM = registerBlock("fortshroom", resourceKey ->
            new NoLightLimitMushroomBlock(ModFeatures.HUGE_FORTSHROOM, BlockBehaviour.Properties.ofLegacyCopy(Blocks.BROWN_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> POTTED_FORTSHROOM = registerBlockOnly("potted_fortshroom", resourceKey ->
            new FlowerPotBlock(FORTSHROOM.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_BROWN_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static void register() {

    }
}
