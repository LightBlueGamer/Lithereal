package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static org.lithereal.block.ModBlocks.*;

public class ModPhantomBlocks {
    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_ORE = registerBlock("phantom_diamond_ore", resourceKey ->
            new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.ofLegacyCopy(Blocks.DIAMOND_ORE).setId(resourceKey)));

    public static final RegistrySupplier<Block> PHANTOM_QUARTZ_ORE = registerBlock("phantom_quartz_ore", resourceKey ->
            new DropExperienceBlock(UniformInt.of(4, 12), BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_QUARTZ_ORE).setId(resourceKey)));

    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_BLOCK = registerBlock("phantom_diamond_block", resourceKey ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DIAMOND_BLOCK).setId(resourceKey)));

    public static final RegistrySupplier<Block> PHANTOM_ROSE = registerBlock("phantom_rose", resourceKey ->
            new FlowerBlock(MobEffects.STRENGTH, 3, BlockBehaviour.Properties.ofLegacyCopy(Blocks.POPPY).setId(resourceKey)));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ROSE = registerBlockOnly("potted_phantom_rose", resourceKey ->
            new FlowerPotBlock(PHANTOM_ROSE.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_POPPY).setId(resourceKey)));

    public static final RegistrySupplier<Block> PHANTOM_ICE_FLOWER = registerBlock("phantom_ice_flower", resourceKey ->
            new FlowerBlock(MobEffects.WEAVING, 5, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CORNFLOWER).setId(resourceKey)));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ICE_FLOWER = registerBlockOnly("potted_phantom_ice_flower", resourceKey ->
            new FlowerPotBlock(PHANTOM_ICE_FLOWER.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_CORNFLOWER).setId(resourceKey)));

    public static final RegistrySupplier<Block> PHANTOM_ROSE_ETHEREAL_CORE = registerBlock("phantom_rose_ethereal_core", resourceKey ->
            new FlowerBlock(MobEffects.HASTE, 3.5F, BlockBehaviour.Properties.ofLegacyCopy(Blocks.POPPY).setId(resourceKey)));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ROSE_ETHEREAL_CORE = registerBlockOnly("potted_phantom_rose_ethereal_core", resourceKey ->
            new FlowerPotBlock(PHANTOM_ROSE_ETHEREAL_CORE.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_POPPY).setId(resourceKey)));

    public static void register() {

    }
}
