package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.data.mixin.accessor.BlockSetTypeAccessor;
import org.lithereal.world.feature.ModFeatures;

import java.util.Optional;

import static org.lithereal.block.ModBlocks.*;

public class ModTreeBlocks {
    public static final TreeGrower PHANTOM_OAK = new TreeGrower("phantom_oak", 0.1F, Optional.of(ModFeatures.MEGA_PHANTOM_OAK), Optional.empty(), Optional.of(ModFeatures.PHANTOM_OAK), Optional.of(ModFeatures.FANCY_PHANTOM_OAK), Optional.empty(), Optional.empty());
    public static final BlockSetType PHANTOM_OAK_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("phantom_oak"));
    public static final WoodType PHANTOM_OAK_WOOD_TYPE = LitherealExpectPlatform.registerWoodType(new WoodType("phantom_oak", PHANTOM_OAK_SET));
    public static final RegistrySupplier<Block> PHANTOM_OAK_PLANKS = registerBlock("phantom_oak_planks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SLAB = registerBlock("phantom_oak_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_STAIRS = registerBlock("phantom_oak_stairs", () ->
            new StairBlock(PHANTOM_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE = registerBlock("phantom_oak_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE_GATE = registerBlock("phantom_oak_fence_gate", () ->
            new FenceGateBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_PRESSURE_PLATE = registerBlock("phantom_oak_pressure_plate", () ->
            new PressurePlateBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_BUTTON = registerBlock("phantom_oak_button", () ->
            new ButtonBlock(PHANTOM_OAK_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SIGN = registerBlockOnly("phantom_oak_sign", () ->
            new CustomStandingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_SIGN = registerBlockOnly("phantom_oak_wall_sign", () ->
            new CustomWallSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_PURPLE).dropsLike(PHANTOM_OAK_SIGN.get())));

    public static final RegistrySupplier<Block> PHANTOM_OAK_HANGING_SIGN = registerBlockOnly("phantom_oak_hanging_sign", () ->
            new CustomCeilingHangingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_HANGING_SIGN = registerBlockOnly("phantom_oak_wall_hanging_sign", () ->
            new CustomWallHangingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PURPLE).dropsLike(PHANTOM_OAK_HANGING_SIGN.get())));

    public static final RegistrySupplier<Block> PHANTOM_OAK_DOOR = registerBlock("phantom_oak_door", () ->
            new DoorBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_PURPLE)), block -> new DoubleHighBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_TRAPDOOR = registerBlock("phantom_oak_trapdoor", () ->
            new TrapDoorBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_LEAVES = registerBlock("phantom_oak_leaves", () ->
            new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_MAGENTA)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SAPLING = registerBlock("phantom_oak_sapling", () ->
            new SaplingBlock(PHANTOM_OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_OAK_SAPLING = registerBlockOnly("potted_phantom_oak_sapling", () ->
            new FlowerPotBlock(PHANTOM_OAK_SAPLING.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_OAK_SAPLING)));

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_LOG = registerBlock("stripped_phantom_oak_log", () ->
            strippedLog(MapColor.TERRACOTTA_PURPLE));

    public static final RegistrySupplier<Block> PHANTOM_OAK_LOG = registerBlock("phantom_oak_log", () ->
            log(STRIPPED_PHANTOM_OAK_LOG, MapColor.TERRACOTTA_PURPLE, MapColor.COLOR_PURPLE));

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_WOOD = registerBlock("stripped_phantom_oak_wood", () ->
            strippedLog(MapColor.TERRACOTTA_PURPLE));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WOOD = registerBlock("phantom_oak_wood", () ->
            log(STRIPPED_PHANTOM_OAK_WOOD, MapColor.COLOR_PURPLE));

    public static final BlockSetType FORTSHROOM_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("fortshroom"));
    public static final WoodType FORTSHROOM_WOOD_TYPE = LitherealExpectPlatform.registerWoodType(new WoodType("fortshroom", FORTSHROOM_SET));
    public static final RegistrySupplier<Block> FORTSHROOM_PLANKS = registerBlock("fortshroom_planks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_SLAB = registerBlock("fortshroom_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_SLAB).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_STAIRS = registerBlock("fortshroom_stairs", () ->
            new StairBlock(FORTSHROOM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_STAIRS).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_FENCE = registerBlock("fortshroom_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FENCE).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_FENCE_GATE = registerBlock("fortshroom_fence_gate", () ->
            new FenceGateBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FENCE_GATE).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_PRESSURE_PLATE = registerBlock("fortshroom_pressure_plate", () ->
            new PressurePlateBlock(FORTSHROOM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PRESSURE_PLATE).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_BUTTON = registerBlock("fortshroom_button", () ->
            new ButtonBlock(FORTSHROOM_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_BUTTON).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_SIGN = registerBlockOnly("fortshroom_sign", () ->
            new CustomStandingSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_SIGN).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_WALL_SIGN = registerBlockOnly("fortshroom_wall_sign", () ->
            new CustomWallSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_WALL_SIGN).mapColor(MapColor.COLOR_GREEN).dropsLike(FORTSHROOM_SIGN.get())));

    public static final RegistrySupplier<Block> FORTSHROOM_HANGING_SIGN = registerBlockOnly("fortshroom_hanging_sign", () ->
            new CustomCeilingHangingSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_HANGING_SIGN).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_WALL_HANGING_SIGN = registerBlockOnly("fortshroom_wall_hanging_sign", () ->
            new CustomWallHangingSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_GREEN).dropsLike(FORTSHROOM_HANGING_SIGN.get())));

    public static final RegistrySupplier<Block> FORTSHROOM_DOOR = registerBlock("fortshroom_door", () ->
            new DoorBlock(FORTSHROOM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_DOOR).mapColor(MapColor.COLOR_GREEN)), block -> new DoubleHighBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> FORTSHROOM_TRAPDOOR = registerBlock("fortshroom_trapdoor", () ->
            new TrapDoorBlock(FORTSHROOM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_TRAPDOOR).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> STRIPPED_FORTSHROOM_STEM = registerBlock("stripped_fortshroom_stem", () ->
            strippedLog(MapColor.COLOR_GREEN));

    public static final RegistrySupplier<Block> FORTSHROOM_STEM = registerBlock("fortshroom_stem", () ->
            log(STRIPPED_FORTSHROOM_STEM, MapColor.COLOR_GREEN, MapColor.DEEPSLATE));

    public static final RegistrySupplier<Block> STRIPPED_FORTSHROOM_HYPHAE = registerBlock("stripped_fortshroom_hyphae", () ->
            strippedLog(MapColor.COLOR_GREEN));

    public static final RegistrySupplier<Block> FORTSHROOM_HYPHAE = registerBlock("fortshroom_hyphae", () ->
            log(STRIPPED_FORTSHROOM_HYPHAE, MapColor.DEEPSLATE));

    public static final RegistrySupplier<Block> FORTSHROOM_BLOCK = registerBlock("fortshroom_block", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.COLOR_GREEN)));

    public static final BlockSetType MALISHROOM_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("malishroom"));
    public static final WoodType MALISHROOM_WOOD_TYPE = LitherealExpectPlatform.registerWoodType(new WoodType("malishroom", MALISHROOM_SET));
    public static final RegistrySupplier<Block> MALISHROOM_PLANKS = registerBlock("malishroom_planks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_SLAB = registerBlock("malishroom_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_SLAB).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_STAIRS = registerBlock("malishroom_stairs", () ->
            new StairBlock(MALISHROOM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_STAIRS).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_FENCE = registerBlock("malishroom_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FENCE).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_FENCE_GATE = registerBlock("malishroom_fence_gate", () ->
            new FenceGateBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FENCE_GATE).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_PRESSURE_PLATE = registerBlock("malishroom_pressure_plate", () ->
            new PressurePlateBlock(MALISHROOM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PRESSURE_PLATE).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_BUTTON = registerBlock("malishroom_button", () ->
            new ButtonBlock(MALISHROOM_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_BUTTON).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_SIGN = registerBlockOnly("malishroom_sign", () ->
            new CustomStandingSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_SIGN).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_WALL_SIGN = registerBlockOnly("malishroom_wall_sign", () ->
            new CustomWallSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_WALL_SIGN).mapColor(MapColor.COLOR_PURPLE).dropsLike(MALISHROOM_SIGN.get())));

    public static final RegistrySupplier<Block> MALISHROOM_HANGING_SIGN = registerBlockOnly("malishroom_hanging_sign", () ->
            new CustomCeilingHangingSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_HANGING_SIGN).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_WALL_HANGING_SIGN = registerBlockOnly("malishroom_wall_hanging_sign", () ->
            new CustomWallHangingSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_PURPLE).dropsLike(MALISHROOM_HANGING_SIGN.get())));

    public static final RegistrySupplier<Block> MALISHROOM_DOOR = registerBlock("malishroom_door", () ->
            new DoorBlock(MALISHROOM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_DOOR).mapColor(MapColor.COLOR_PURPLE)), block -> new DoubleHighBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> MALISHROOM_TRAPDOOR = registerBlock("malishroom_trapdoor", () ->
            new TrapDoorBlock(MALISHROOM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_TRAPDOOR).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> STRIPPED_MALISHROOM_STEM = registerBlock("stripped_malishroom_stem", () ->
            strippedLog(MapColor.COLOR_PURPLE));

    public static final RegistrySupplier<Block> MALISHROOM_STEM = registerBlock("malishroom_stem", () ->
            log(STRIPPED_MALISHROOM_STEM, MapColor.COLOR_PURPLE, MapColor.DEEPSLATE));

    public static final RegistrySupplier<Block> STRIPPED_MALISHROOM_HYPHAE = registerBlock("stripped_malishroom_hyphae", () ->
            strippedLog(MapColor.COLOR_PURPLE));

    public static final RegistrySupplier<Block> MALISHROOM_HYPHAE = registerBlock("malishroom_hyphae", () ->
            log(STRIPPED_MALISHROOM_HYPHAE, MapColor.DEEPSLATE));

    public static final RegistrySupplier<Block> MALISHROOM_BLOCK = registerBlock("malishroom_block", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> RED_MALISHROOM_BLOCK = registerBlock("red_malishroom_block", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK).mapColor(MapColor.COLOR_RED)));

    public static void register() {

    }
}
