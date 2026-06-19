package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.lithereal.block.datagen.BlockDataProvider;
import org.lithereal.block.datagen.BlockDataTemplates;
import org.lithereal.data.mixin.accessor.BlockSetTypeAccessor;
import org.lithereal.tags.ModTags;

import static org.lithereal.block.ModBlocks.*;

public class ModStoneBlocks {
    public static final BlockSetType ETHERSTONE_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("etherstone", true, true, false, BlockSetType.PressurePlateSensitivity.MOBS, SoundType.DEEPSLATE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final BlockSetType PAILITE_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("pailite", true, true, false, BlockSetType.PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final BlockSetType LUMINITE_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("luminite", true, true, false, BlockSetType.PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final BlockSetType VERDONE_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("verdone", true, true, false, BlockSetType.PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));

    public static final RegistrySupplier<Block> ETHERSTONE = createStone("etherstone", SoundType.DEEPSLATE, MapColor.COLOR_BLUE, 3.0F, 6.0F, ModTags.ETHERSTONE, ModTags.BASE_STONE_ETHEREAL_CORE);

    public static final RegistrySupplier<Block> ETHERSTONE_PRESSURE_PLATE = createPressurePlate("etherstone_pressure_plate", ModTags.ETHERSTONE, ETHERSTONE_SET, MapColor.COLOR_BLUE);

    public static final RegistrySupplier<Block> ETHERSTONE_BUTTON = createButton("etherstone_button", ModTags.ETHERSTONE, ETHERSTONE_SET, MapColor.COLOR_BLUE);

    public static final RegistrySupplier<Block> ETHERSTONE_STAIRS = createStairs("etherstone_stairs", ModTags.ETHERSTONE, ETHERSTONE);

    public static final RegistrySupplier<Block> ETHERSTONE_SLAB = createSlab("etherstone_slab", ModTags.ETHERSTONE, ETHERSTONE);

    public static final RegistrySupplier<Block> ETHERSTONE_WALL = createWall("etherstone_wall", ModTags.ETHERSTONE, ETHERSTONE);

    public static final RegistrySupplier<Block> CHISELED_ETHERSTONE = createSpecialChiseledBlock("chiseled_etherstone", ModTags.ETHERSTONE, SoundType.DEEPSLATE, MapColor.COLOR_BLUE, 3.0F, 6.0F, 0);

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE = createStoneVariant("polished_etherstone", ModTags.ETHERSTONE, SoundType.DEEPSLATE, MapColor.COLOR_BLUE, 3.0F, 6.0F);

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_PRESSURE_PLATE = createPressurePlate("polished_etherstone_pressure_plate", ModTags.ETHERSTONE, ETHERSTONE_SET, MapColor.COLOR_BLUE);

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_BUTTON = createButton("polished_etherstone_button", ModTags.ETHERSTONE, ETHERSTONE_SET, MapColor.COLOR_BLUE);

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_STAIRS = createStairs("polished_etherstone_stairs", ModTags.ETHERSTONE, POLISHED_ETHERSTONE);

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_SLAB = createSlab("polished_etherstone_slab", ModTags.ETHERSTONE, POLISHED_ETHERSTONE);

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_WALL = createWall("polished_etherstone_wall", ModTags.ETHERSTONE, POLISHED_ETHERSTONE);

    public static final RegistrySupplier<Block> ETHERSTONE_BRICKS = createStoneVariant("etherstone_bricks", ModTags.ETHERSTONE, SoundType.DEEPSLATE, MapColor.COLOR_BLUE, 3.0F, 6.0F);

    public static final RegistrySupplier<Block> CRACKED_ETHERSTONE_BRICKS = createStoneVariant("cracked_etherstone_bricks", ModTags.ETHERSTONE, SoundType.DEEPSLATE, MapColor.COLOR_BLUE, 3.0F, 6.0F);

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_PRESSURE_PLATE = createPressurePlate("etherstone_brick_pressure_plate", ModTags.ETHERSTONE, ETHERSTONE_SET, MapColor.COLOR_BLUE);

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_BUTTON = createButton("etherstone_brick_button", ModTags.ETHERSTONE, ETHERSTONE_SET, MapColor.COLOR_BLUE);

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_STAIRS = createStairs("etherstone_brick_stairs", ModTags.ETHERSTONE, ETHERSTONE_BRICKS);

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_SLAB = createSlab("etherstone_brick_slab", ModTags.ETHERSTONE, ETHERSTONE_BRICKS);

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_WALL = createWall("etherstone_brick_wall", ModTags.ETHERSTONE, ETHERSTONE_BRICKS);

    public static final RegistrySupplier<Block> PAILITE = createStone("pailite", SoundType.STONE, MapColor.DEEPSLATE, 1.5F, 6.0F, ModTags.PAILITE, ModTags.BASE_STONE_ETHEREAL_CORE);

    public static final RegistrySupplier<Block> PAILITE_PRESSURE_PLATE = createPressurePlate("pailite_pressure_plate", ModTags.PAILITE, PAILITE_SET, MapColor.DEEPSLATE);

    public static final RegistrySupplier<Block> PAILITE_BUTTON = createButton("pailite_button", ModTags.PAILITE, PAILITE_SET, MapColor.DEEPSLATE);

    public static final RegistrySupplier<Block> PAILITE_STAIRS = createStairs("pailite_stairs", ModTags.PAILITE, PAILITE);

    public static final RegistrySupplier<Block> PAILITE_SLAB = createSlab("pailite_slab", ModTags.PAILITE, PAILITE);

    public static final RegistrySupplier<Block> PAILITE_WALL = createWall("pailite_wall", ModTags.PAILITE, PAILITE);

    public static final RegistrySupplier<Block> POLISHED_PAILITE = createStoneVariant("polished_pailite", ModTags.PAILITE, SoundType.STONE, MapColor.DEEPSLATE, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_PRESSURE_PLATE = createPressurePlate("polished_pailite_pressure_plate", ModTags.PAILITE, PAILITE_SET, MapColor.DEEPSLATE);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BUTTON = createButton("polished_pailite_button", ModTags.PAILITE, PAILITE_SET, MapColor.DEEPSLATE);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_STAIRS = createStairs("polished_pailite_stairs", ModTags.PAILITE, POLISHED_PAILITE);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_SLAB = createSlab("polished_pailite_slab", ModTags.PAILITE, POLISHED_PAILITE);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_WALL = createWall("polished_pailite_wall", ModTags.PAILITE, POLISHED_PAILITE);

    public static final RegistrySupplier<Block> CHISELED_POLISHED_PAILITE_BRICKS = createSpecialChiseledBlock("chiseled_polished_pailite_bricks", ModTags.PAILITE, SoundType.STONE, MapColor.DEEPSLATE, 1.5F, 6.0F, 1);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICKS = createStoneVariant("polished_pailite_bricks", ModTags.PAILITE, SoundType.STONE, MapColor.DEEPSLATE, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> CRACKED_POLISHED_PAILITE_BRICKS = createStoneVariant("cracked_polished_pailite_bricks", ModTags.PAILITE, SoundType.STONE, MapColor.DEEPSLATE, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_PRESSURE_PLATE = createPressurePlate("polished_pailite_brick_pressure_plate", ModTags.PAILITE, PAILITE_SET, MapColor.DEEPSLATE);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_BUTTON = createButton("polished_pailite_brick_button", ModTags.PAILITE, PAILITE_SET, MapColor.DEEPSLATE);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_STAIRS = createStairs("polished_pailite_brick_stairs", ModTags.PAILITE, POLISHED_PAILITE_BRICKS);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_SLAB = createSlab("polished_pailite_brick_slab", ModTags.PAILITE, POLISHED_PAILITE_BRICKS);

    public static final RegistrySupplier<Block> POLISHED_PAILITE_BRICK_WALL = createWall("polished_pailite_brick_wall", ModTags.PAILITE, POLISHED_PAILITE_BRICKS);

    public static final RegistrySupplier<Block> LUMINITE = createStone("luminite", SoundType.STONE, MapColor.QUARTZ, 1.5F, 6.0F, ModTags.LUMINITE, ModTags.BASE_STONE_ETHEREAL_CORE);

    public static final RegistrySupplier<Block> LUMINITE_PRESSURE_PLATE = createPressurePlate("luminite_pressure_plate", ModTags.LUMINITE, LUMINITE_SET, MapColor.QUARTZ);

    public static final RegistrySupplier<Block> LUMINITE_BUTTON = createButton("luminite_button", ModTags.LUMINITE, LUMINITE_SET, MapColor.QUARTZ);

    public static final RegistrySupplier<Block> LUMINITE_STAIRS = createStairs("luminite_stairs", ModTags.LUMINITE, LUMINITE);

    public static final RegistrySupplier<Block> LUMINITE_SLAB = createSlab("luminite_slab", ModTags.LUMINITE, LUMINITE);

    public static final RegistrySupplier<Block> LUMINITE_WALL = createWall("luminite_wall", ModTags.LUMINITE, LUMINITE);

    public static final RegistrySupplier<Block> POLISHED_LUMINITE = createStoneVariant("polished_luminite", ModTags.LUMINITE, SoundType.STONE, MapColor.QUARTZ, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_PRESSURE_PLATE = createPressurePlate("polished_luminite_pressure_plate", ModTags.LUMINITE, LUMINITE_SET, MapColor.QUARTZ);

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_BUTTON = createButton("polished_luminite_button", ModTags.LUMINITE, LUMINITE_SET, MapColor.QUARTZ);

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_STAIRS = createStairs("polished_luminite_stairs", ModTags.LUMINITE, POLISHED_LUMINITE);

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_SLAB = createSlab("polished_luminite_slab", ModTags.LUMINITE, POLISHED_LUMINITE);

    public static final RegistrySupplier<Block> POLISHED_LUMINITE_WALL = createWall("polished_luminite_wall", ModTags.LUMINITE, POLISHED_LUMINITE);

    public static final RegistrySupplier<Block> LUMINITE_BRICKS = createStoneVariant("luminite_bricks", ModTags.LUMINITE, SoundType.STONE, MapColor.QUARTZ, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> CRACKED_LUMINITE_BRICKS = createStoneVariant("cracked_luminite_bricks", ModTags.LUMINITE, SoundType.STONE, MapColor.QUARTZ, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> CHISELED_LUMINITE_BRICKS = createStoneVariant("chiseled_luminite_bricks", ModTags.LUMINITE, SoundType.STONE, MapColor.QUARTZ, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> LUMINITE_BRICK_PRESSURE_PLATE = createPressurePlate("luminite_brick_pressure_plate", ModTags.LUMINITE, LUMINITE_SET, MapColor.QUARTZ);

    public static final RegistrySupplier<Block> LUMINITE_BRICK_BUTTON = createButton("luminite_brick_button", ModTags.LUMINITE, LUMINITE_SET, MapColor.QUARTZ);

    public static final RegistrySupplier<Block> LUMINITE_BRICK_STAIRS = createStairs("luminite_brick_stairs", ModTags.LUMINITE, LUMINITE_BRICKS);

    public static final RegistrySupplier<Block> LUMINITE_BRICK_SLAB = createSlab("luminite_brick_slab", ModTags.LUMINITE, LUMINITE_BRICKS);

    public static final RegistrySupplier<Block> LUMINITE_BRICK_WALL = createWall("luminite_brick_wall", ModTags.LUMINITE, LUMINITE_BRICKS);

    public static final RegistrySupplier<Block> VERDONE = createStone("verdone", SoundType.STONE, MapColor.COLOR_GREEN, 1.5F, 6.0F, ModTags.VERDONE, ModTags.BASE_STONE_ETHEREAL_CORE);

    public static final RegistrySupplier<Block> VERDONE_PRESSURE_PLATE = createPressurePlate("verdone_pressure_plate", ModTags.VERDONE, VERDONE_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> VERDONE_BUTTON = createButton("verdone_button", ModTags.VERDONE, VERDONE_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> VERDONE_STAIRS = createStairs("verdone_stairs", ModTags.VERDONE, VERDONE);

    public static final RegistrySupplier<Block> VERDONE_SLAB = createSlab("verdone_slab", ModTags.VERDONE, VERDONE);

    public static final RegistrySupplier<Block> VERDONE_WALL = createWall("verdone_wall", ModTags.VERDONE, VERDONE);

    public static final RegistrySupplier<Block> POLISHED_VERDONE = createStoneVariant("polished_verdone", ModTags.VERDONE, SoundType.STONE, MapColor.COLOR_GREEN, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> POLISHED_VERDONE_PRESSURE_PLATE = createPressurePlate("polished_verdone_pressure_plate", ModTags.VERDONE, VERDONE_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> POLISHED_VERDONE_BUTTON = createButton("polished_verdone_button", ModTags.VERDONE, VERDONE_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> POLISHED_VERDONE_STAIRS = createStairs("polished_verdone_stairs", ModTags.VERDONE, POLISHED_VERDONE);

    public static final RegistrySupplier<Block> POLISHED_VERDONE_SLAB = createSlab("polished_verdone_slab", ModTags.VERDONE, POLISHED_VERDONE);

    public static final RegistrySupplier<Block> POLISHED_VERDONE_WALL = createWall("polished_verdone_wall", ModTags.VERDONE, POLISHED_VERDONE);

    public static final RegistrySupplier<Block> VERDONE_BRICKS = createStoneVariant("verdone_bricks", ModTags.VERDONE, SoundType.STONE, MapColor.COLOR_GREEN, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> CRACKED_VERDONE_BRICKS = createStoneVariant("cracked_verdone_bricks", ModTags.VERDONE, SoundType.STONE, MapColor.COLOR_GREEN, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> CHISELED_VERDONE_BRICKS = createStoneVariant("chiseled_verdone_bricks", ModTags.VERDONE, SoundType.STONE, MapColor.COLOR_GREEN, 1.5F, 6.0F);

    public static final RegistrySupplier<Block> VERDONE_BRICK_PRESSURE_PLATE = createPressurePlate("verdone_brick_pressure_plate", ModTags.VERDONE, VERDONE_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> VERDONE_BRICK_BUTTON = createButton("verdone_brick_button", ModTags.VERDONE, VERDONE_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> VERDONE_BRICK_STAIRS = createStairs("verdone_brick_stairs", ModTags.VERDONE, VERDONE_BRICKS);

    public static final RegistrySupplier<Block> VERDONE_BRICK_SLAB = createSlab("verdone_brick_slab", ModTags.VERDONE, VERDONE_BRICKS);

    public static final RegistrySupplier<Block> VERDONE_BRICK_WALL = createWall("verdone_brick_wall", ModTags.VERDONE, VERDONE_BRICKS);

    public static RegistrySupplier<Block> createSpecialChiseledBlock(String name, TagKey<Block> setTag, SoundType soundType, MapColor color, float strength, float explosionResistance, int lightLevel) {
        return BlockDataTemplates.EMPTY.addTag(setTag).consume(registerBlock(name, resourceKey ->
                new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(strength, explosionResistance).sound(soundType).lightLevel(_ -> lightLevel).setId(resourceKey))));
    }

    @SafeVarargs
    public static RegistrySupplier<Block> createStone(String name, SoundType soundType, MapColor color, float strength, float explosionResistance, TagKey<Block>... setTag) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.STONES)
                .addTags(setTag).consume(registerBlock(name, resourceKey ->
                        new Block(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(strength, explosionResistance).sound(soundType).setId(resourceKey))));
    }


    public static RegistrySupplier<Block> createStoneVariant(String name, TagKey<Block> setTag, SoundType soundType, MapColor color, float strength, float explosionResistance) {
        return BlockDataTemplates.EMPTY.addTag(setTag).consume(registerBlock(name, resourceKey ->
                new Block(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(strength, explosionResistance).sound(soundType).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createSlab(String name, TagKey<Block> setTag, RegistrySupplier<Block> parent) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.SLABS)
                .addTag(setTag).consume(registerBlock(name, resourceKey ->
                        new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(parent.get()).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createStairs(String name, TagKey<Block> setTag, RegistrySupplier<Block> parent) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.STAIRS)
                .addTag(setTag).consume(registerBlock(name, resourceKey ->
                        new StairBlock(parent.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(parent.get()).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createWall(String name, TagKey<Block> setTag, RegistrySupplier<Block> parent) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WALLS)
                .addTag(setTag).consume(registerBlock(name, resourceKey ->
                        new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(parent.get()).forceSolidOn().setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createPressurePlate(String name, TagKey<Block> setTag, BlockSetType type, MapColor color) {
        return BlockDataTemplates.EMPTY.addTags(BlockTags.STONE_PRESSURE_PLATES, setTag).consume(registerBlock(name, resourceKey ->
                new PressurePlateBlock(type, BlockBehaviour.Properties.of()
                        .forceSolidOn()
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .noCollision()
                        .strength(0.5F)
                        .pushReaction(PushReaction.DESTROY).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createButton(String name, TagKey<Block> setTag, BlockSetType type, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.STONE_BUTTONS).addTag(setTag).consume(registerBlock(name, resourceKey ->
                new ButtonBlock(type, 10, BlockBehaviour.Properties.of()
                        .forceSolidOn()
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .noCollision()
                        .strength(0.5F)
                        .pushReaction(PushReaction.DESTROY).setId(resourceKey).mapColor(color))));
    }

    public static void register() {

    }
}
