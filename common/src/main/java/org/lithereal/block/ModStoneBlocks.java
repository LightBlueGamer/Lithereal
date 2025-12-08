package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static org.lithereal.block.ModBlocks.registerBlock;

public class ModStoneBlocks {
    public static final RegistrySupplier<Block> ETHERSTONE = registerBlock("etherstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_STAIRS = registerBlock("etherstone_stairs",
            () -> new StairBlock(ETHERSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));

    public static final RegistrySupplier<Block> ETHERSTONE_SLAB = registerBlock("etherstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));

    public static final RegistrySupplier<Block> ETHERSTONE_WALL = registerBlock("etherstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> CHISELED_ETHERSTONE = registerBlock("chiseled_etherstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE = registerBlock("polished_etherstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_STAIRS = registerBlock("polished_etherstone_stairs",
            () -> new StairBlock(POLISHED_ETHERSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_SLAB = registerBlock("polished_etherstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_WALL = registerBlock("polished_etherstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICKS = registerBlock("etherstone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_STAIRS = registerBlock("etherstone_brick_stairs",
            () -> new StairBlock(ETHERSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_SLAB = registerBlock("etherstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_WALL = registerBlock("etherstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL)));

    public static final RegistrySupplier<Block> PAILITE = registerBlock("pailite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final RegistrySupplier<Block> PAILITE_STAIRS = registerBlock("pailite_stairs",
            () -> new StairBlock(PAILITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)));

    public static final RegistrySupplier<Block> PAILITE_SLAB = registerBlock("pailite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)));

    public static final RegistrySupplier<Block> PAILITE_WALL = registerBlock("pailite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(PAILITE.get()).forceSolidOn()));

    public static final RegistrySupplier<Block> POLISHED_PAILITE = registerBlock("polished_pailite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_STAIRS = registerBlock("polished_pailite_stairs",
            () -> new StairBlock(POLISHED_PAILITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_SLAB = registerBlock("polished_pailite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_WALL = registerBlock("polished_pailite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_PAILITE.get()).forceSolidOn()));

    public static void register() {

    }
}
