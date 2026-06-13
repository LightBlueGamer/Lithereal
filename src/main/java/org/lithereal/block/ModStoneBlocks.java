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
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DEEPSLATE).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHERSTONE_STAIRS = registerBlock("etherstone_stairs",
            resourceKey -> new StairBlock(ETHERSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.COBBLED_DEEPSLATE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHERSTONE_SLAB = registerBlock("etherstone_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.COBBLED_DEEPSLATE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHERSTONE_WALL = registerBlock("etherstone_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.COBBLED_DEEPSLATE_WALL).setId(resourceKey)));

    public static final RegistrySupplier<Block> CHISELED_ETHERSTONE = registerBlock("chiseled_etherstone",
            resourceKey -> new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CHISELED_DEEPSLATE).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE = registerBlock("polished_etherstone",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DEEPSLATE).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_STAIRS = registerBlock("polished_etherstone_stairs",
            resourceKey -> new StairBlock(POLISHED_ETHERSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DEEPSLATE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_SLAB = registerBlock("polished_etherstone_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DEEPSLATE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_WALL = registerBlock("polished_etherstone_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DEEPSLATE_WALL).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICKS = registerBlock("etherstone_bricks",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DEEPSLATE_BRICKS).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_STAIRS = registerBlock("etherstone_brick_stairs",
            resourceKey -> new StairBlock(ETHERSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.DEEPSLATE_BRICK_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_SLAB = registerBlock("etherstone_brick_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DEEPSLATE_BRICK_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_WALL = registerBlock("etherstone_brick_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DEEPSLATE_BRICK_WALL).setId(resourceKey)));

    public static final RegistrySupplier<Block> PAILITE = registerBlock("pailite",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).setId(resourceKey)));

    public static final RegistrySupplier<Block> PAILITE_STAIRS = registerBlock("pailite_stairs",
            resourceKey -> new StairBlock(PAILITE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> PAILITE_SLAB = registerBlock("pailite_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> PAILITE_WALL = registerBlock("pailite_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(PAILITE.get()).forceSolidOn().setId(resourceKey)));

    public static final RegistrySupplier<Block> PAILITE_PRESSURE_PLATE = registerBlock("pailite_pressure_plate", resourceKey ->
            new PressurePlateBlock(PAILITE_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_PRESSURE_PLATE).setId(resourceKey)));

    public static final RegistrySupplier<Block> PAILITE_BUTTON = registerBlock("pailite_button", resourceKey ->
            new ButtonBlock(PAILITE_SET, 30, BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_BUTTON).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE = registerBlock("polished_pailite",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_PRESSURE_PLATE = registerBlock("polished_pailite_pressure_plate", resourceKey ->
            new PressurePlateBlock(PAILITE_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_PRESSURE_PLATE).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BUTTON = registerBlock("polished_pailite_button", resourceKey ->
            new ButtonBlock(PAILITE_SET, 30, BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_BUTTON).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_STAIRS = registerBlock("polished_pailite_stairs",
            resourceKey -> new StairBlock(POLISHED_PAILITE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_SLAB = registerBlock("polished_pailite_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_WALL = registerBlock("polished_pailite_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_PAILITE.get()).setId(resourceKey).forceSolidOn()));

    public static final RegistrySupplier<Block> CHISELED_POLISHED_PAILITE_BRICKS = registerBlock("chiseled_polished_pailite_bricks",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CHISELED_STONE_BRICKS).setId(resourceKey).lightLevel(blockState -> 1)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICKS = registerBlock("polished_pailite_bricks",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_BRICKS).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_PRESSURE_PLATE = registerBlock("polished_pailite_brick_pressure_plate", resourceKey ->
            new PressurePlateBlock(PAILITE_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_PRESSURE_PLATE).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_BUTTON = registerBlock("polished_pailite_brick_button", resourceKey ->
            new ButtonBlock(PAILITE_SET, 30, BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_BUTTON).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_STAIRS = registerBlock("polished_pailite_brick_stairs",
            resourceKey -> new StairBlock(POLISHED_PAILITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_BRICK_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_SLAB = registerBlock("polished_pailite_brick_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_BRICK_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_WALL = registerBlock("polished_pailite_brick_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_PAILITE_BRICKS.get()).setId(resourceKey).forceSolidOn()));

    public static final RegistrySupplier<Block> LUMINITE = registerBlock("luminite",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).setId(resourceKey)));

    public static final RegistrySupplier<Block> LUMINITE_STAIRS = registerBlock("luminite_stairs",
            resourceKey -> new StairBlock(LUMINITE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> LUMINITE_SLAB = registerBlock("luminite_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> LUMINITE_WALL = registerBlock("luminite_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(LUMINITE.get()).setId(resourceKey).forceSolidOn()));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE = registerBlock("polished_luminite",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DIORITE).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_STAIRS = registerBlock("polished_luminite_stairs",
            resourceKey -> new StairBlock(POLISHED_LUMINITE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DIORITE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_SLAB = registerBlock("polished_luminite_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DIORITE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_WALL = registerBlock("polished_luminite_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_LUMINITE.get()).setId(resourceKey).forceSolidOn()));

    public static final RegistrySupplier<Block> VERDONE = registerBlock("verdone",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).setId(resourceKey)));

    public static final RegistrySupplier<Block> VERDONE_STAIRS = registerBlock("verdone_stairs",
            resourceKey -> new StairBlock(VERDONE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> VERDONE_SLAB = registerBlock("verdone_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> VERDONE_WALL = registerBlock("verdone_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(VERDONE.get()).setId(resourceKey).forceSolidOn()));

    public static final RegistrySupplier<Block> POLISHED_VERDONE = registerBlock("polished_verdone",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DIORITE).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_VERDONE_STAIRS = registerBlock("polished_verdone_stairs",
            resourceKey -> new StairBlock(POLISHED_VERDONE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DIORITE_STAIRS).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_VERDONE_SLAB = registerBlock("polished_verdone_slab",
            resourceKey -> new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_DIORITE_SLAB).setId(resourceKey)));

    public static final RegistrySupplier<Block> POLISHED_VERDONE_WALL = registerBlock("polished_verdone_wall",
            resourceKey -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_VERDONE.get()).setId(resourceKey).forceSolidOn()));

    public static void register() {

    }
}
