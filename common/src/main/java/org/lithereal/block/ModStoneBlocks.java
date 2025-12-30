package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.lithereal.data.mixin.accessor.BlockSetTypeAccessor;

import static org.lithereal.block.ModBlocks.registerBlock;

public class ModStoneBlocks {
    public static final BlockSetType PAILITE_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("pailite", true, true, false, BlockSetType.PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));

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

    public static final RegistrySupplier<Block> PAILITE_PRESSURE_PLATE = registerBlock("pailite_pressure_plate", () ->
            new PressurePlateBlock(PAILITE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)));

    public static final RegistrySupplier<Block> PAILITE_BUTTON = registerBlock("pailite_button", () ->
            new ButtonBlock(PAILITE_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE = registerBlock("polished_pailite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_PRESSURE_PLATE = registerBlock("polished_pailite_pressure_plate", () ->
            new PressurePlateBlock(PAILITE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BUTTON = registerBlock("polished_pailite_button", () ->
            new ButtonBlock(PAILITE_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_STAIRS = registerBlock("polished_pailite_stairs",
            () -> new StairBlock(POLISHED_PAILITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_SLAB = registerBlock("polished_pailite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_WALL = registerBlock("polished_pailite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_PAILITE.get()).forceSolidOn()));

    public static final RegistrySupplier<Block> CHISELED_POLISHED_PAILITE_BRICKS = registerBlock("chiseled_polished_pailite_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_STONE_BRICKS).lightLevel(blockState -> 1)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICKS = registerBlock("polished_pailite_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_PRESSURE_PLATE = registerBlock("polished_pailite_brick_pressure_plate", () ->
            new PressurePlateBlock(PAILITE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_BUTTON = registerBlock("polished_pailite_brick_button", () ->
            new ButtonBlock(PAILITE_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_STAIRS = registerBlock("polished_pailite_brick_stairs",
            () -> new StairBlock(POLISHED_PAILITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_SLAB = registerBlock("polished_pailite_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_WALL = registerBlock("polished_pailite_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_PAILITE_BRICKS.get()).forceSolidOn()));

    public static final RegistrySupplier<Block> LUMINITE = registerBlock("luminite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final RegistrySupplier<Block> LUMINITE_STAIRS = registerBlock("luminite_stairs",
            () -> new StairBlock(LUMINITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)));

    public static final RegistrySupplier<Block> LUMINITE_SLAB = registerBlock("luminite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)));

    public static final RegistrySupplier<Block> LUMINITE_WALL = registerBlock("luminite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(LUMINITE.get()).forceSolidOn()));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE = registerBlock("polished_luminite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE)));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_STAIRS = registerBlock("polished_luminite_stairs",
            () -> new StairBlock(POLISHED_LUMINITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE_STAIRS)));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_SLAB = registerBlock("polished_luminite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE_SLAB)));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_WALL = registerBlock("polished_luminite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_LUMINITE.get()).forceSolidOn()));

    public static final RegistrySupplier<Block> VERDONE = registerBlock("verdone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final RegistrySupplier<Block> VERDONE_STAIRS = registerBlock("verdone_stairs",
            () -> new StairBlock(VERDONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)));

    public static final RegistrySupplier<Block> VERDONE_SLAB = registerBlock("verdone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)));

    public static final RegistrySupplier<Block> VERDONE_WALL = registerBlock("verdone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(VERDONE.get()).forceSolidOn()));

    public static final RegistrySupplier<Block> POLISHED_VERDONE = registerBlock("polished_verdone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE)));

    public static final RegistrySupplier<Block> POLISHED_VERDONE_STAIRS = registerBlock("polished_verdone_stairs",
            () -> new StairBlock(POLISHED_VERDONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE_STAIRS)));

    public static final RegistrySupplier<Block> POLISHED_VERDONE_SLAB = registerBlock("polished_verdone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE_SLAB)));

    public static final RegistrySupplier<Block> POLISHED_VERDONE_WALL = registerBlock("polished_verdone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_VERDONE.get()).forceSolidOn()));

    public static void register() {

    }
}
