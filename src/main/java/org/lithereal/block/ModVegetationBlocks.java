package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.world.feature.ModFeatures;

import java.util.Optional;

import static org.lithereal.block.ModBlocks.registerBlock;
import static org.lithereal.block.ModBlocks.registerBlockOnly;

public class ModVegetationBlocks {
    public static final TreeGrower FORTSHROOM_GROWER = new TreeGrower("fortshroom", 0.0F, Optional.of(ModFeatures.MEGA_FORTSHROOM), Optional.empty(), Optional.of(ModFeatures.HUGE_FORTSHROOM), Optional.empty(), Optional.empty(), Optional.empty());
    public static final TreeGrower MALISHROOM_GROWER = new TreeGrower("malishroom", 0.0F, Optional.of(ModFeatures.MEGA_MALISHROOM), Optional.empty(), Optional.of(ModFeatures.HUGE_MALISHROOM), Optional.empty(), Optional.empty(), Optional.empty());
    public static final RegistrySupplier<Block> MALISHROOM = registerBlock("malishroom", resourceKey ->
            new SaplingLikeMushroomBlock(MALISHROOM_GROWER, BlockBehaviour.Properties.ofLegacyCopy(Blocks.RED_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> POTTED_MALISHROOM = registerBlockOnly("potted_malishroom", resourceKey ->
            new FlowerPotBlock(MALISHROOM.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_RED_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> FORTSHROOM = registerBlock("fortshroom", resourceKey ->
            new SaplingLikeMushroomBlock(FORTSHROOM_GROWER, BlockBehaviour.Properties.ofLegacyCopy(Blocks.BROWN_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> POTTED_FORTSHROOM = registerBlockOnly("potted_fortshroom", resourceKey ->
            new FlowerPotBlock(FORTSHROOM.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_BROWN_MUSHROOM).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static void register() {

    }
}
