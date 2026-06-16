package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.LitherealPlatform;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.data.mixin.accessor.BlockSetTypeAccessor;
import org.lithereal.world.feature.ModFeatures;

import java.util.Optional;

import static org.lithereal.block.ModBlocks.*;

public class ModTreeBlocks {
    public static final TreeGrower PHANTOM_OAK = new TreeGrower("phantom_oak", 0.1F, Optional.of(ModFeatures.MEGA_PHANTOM_OAK), Optional.empty(), Optional.of(ModFeatures.PHANTOM_OAK), Optional.of(ModFeatures.FANCY_PHANTOM_OAK), Optional.empty(), Optional.empty());
    public static final BlockSetType PHANTOM_OAK_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("phantom_oak"));
    public static final WoodType PHANTOM_OAK_WOOD_TYPE = LitherealPlatform.INSTANCE.registerWoodType(new WoodType("phantom_oak", PHANTOM_OAK_SET));
    public static final RegistrySupplier<Block> PHANTOM_OAK_PLANKS = registerBlock("phantom_oak_planks", resourceKey ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_PLANKS).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SLAB = registerBlock("phantom_oak_slab", resourceKey ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SLAB).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_STAIRS = registerBlock("phantom_oak_stairs", resourceKey ->
            new StairBlock(PHANTOM_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_STAIRS).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE = registerBlock("phantom_oak_fence", resourceKey ->
            new FenceBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_FENCE).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE_GATE = registerBlock("phantom_oak_fence_gate", resourceKey ->
            new FenceGateBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_FENCE_GATE).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_PRESSURE_PLATE = registerBlock("phantom_oak_pressure_plate", resourceKey ->
            new PressurePlateBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_PRESSURE_PLATE).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_BUTTON = registerBlock("phantom_oak_button", resourceKey ->
            new ButtonBlock(PHANTOM_OAK_SET, 30, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_BUTTON).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SHELF = registerBlock("phantom_oak_shelf", resourceKey ->
                    new ShelfBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SHELF).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)),
            () -> new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SIGN = registerBlockOnly("phantom_oak_sign", resourceKey ->
            new CustomStandingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SIGN).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_SIGN = registerBlockOnly("phantom_oak_wall_sign", resourceKey ->
            new CustomWallSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_WALL_SIGN).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE).overrideLootTable(PHANTOM_OAK_SIGN.get().getLootTable()).overrideDescription(PHANTOM_OAK_SIGN.get().getDescriptionId())));

    public static final RegistrySupplier<Block> PHANTOM_OAK_HANGING_SIGN = registerBlockOnly("phantom_oak_hanging_sign", resourceKey ->
            new CustomCeilingHangingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_HANGING_SIGN).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_HANGING_SIGN = registerBlockOnly("phantom_oak_wall_hanging_sign", resourceKey ->
            new CustomWallHangingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_WALL_HANGING_SIGN).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE).overrideLootTable(PHANTOM_OAK_HANGING_SIGN.get().getLootTable()).overrideDescription(PHANTOM_OAK_HANGING_SIGN.get().getDescriptionId())));

    public static final RegistrySupplier<Block> PHANTOM_OAK_DOOR = registerBlock("phantom_oak_door", resourceKey ->
            new DoorBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_DOOR).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)), DoubleHighBlockItem::new);

    public static final RegistrySupplier<Block> PHANTOM_OAK_TRAPDOOR = registerBlock("phantom_oak_trapdoor", resourceKey ->
            new TrapDoorBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_TRAPDOOR).setId(resourceKey).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_LEAVES = registerBlock("phantom_oak_leaves", resourceKey ->
            new ModUntintedParticleLeavesBlock(0.1F, ModParticles.PHANTOM_OAK_LEAVES::get, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LEAVES).setId(resourceKey).mapColor(MapColor.COLOR_MAGENTA)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SAPLING = registerBlock("phantom_oak_sapling", resourceKey ->
            new SaplingBlock(PHANTOM_OAK, BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SAPLING).setId(resourceKey)));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_OAK_SAPLING = registerBlockOnly("potted_phantom_oak_sapling", resourceKey ->
            new FlowerPotBlock(PHANTOM_OAK_SAPLING.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POTTED_OAK_SAPLING).setId(resourceKey)));

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_LOG = registerBlock("stripped_phantom_oak_log", resourceKey ->
            strippedLog(MapColor.TERRACOTTA_PURPLE, resourceKey));

    public static final RegistrySupplier<Block> PHANTOM_OAK_LOG = registerBlock("phantom_oak_log", resourceKey ->
            log(STRIPPED_PHANTOM_OAK_LOG, MapColor.TERRACOTTA_PURPLE, MapColor.COLOR_PURPLE, resourceKey));

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_WOOD = registerBlock("stripped_phantom_oak_wood", resourceKey ->
            strippedLog(MapColor.TERRACOTTA_PURPLE, resourceKey));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WOOD = registerBlock("phantom_oak_wood", resourceKey ->
            log(STRIPPED_PHANTOM_OAK_WOOD, MapColor.COLOR_PURPLE, resourceKey));

    public static final BlockSetType FORTSHROOM_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("fortshroom"));
    public static final WoodType FORTSHROOM_WOOD_TYPE = LitherealPlatform.INSTANCE.registerWoodType(new WoodType("fortshroom", FORTSHROOM_SET));
    public static final RegistrySupplier<Block> FORTSHROOM_PLANKS = registerBlock("fortshroom_planks", resourceKey ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_PLANKS).mapColor(MapColor.COLOR_GREEN).setId(resourceKey)));

    public static final RegistrySupplier<Block> FORTSHROOM_SLAB = registerBlock("fortshroom_slab", resourceKey ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_SLAB).mapColor(MapColor.COLOR_GREEN).setId(resourceKey)));

    public static final RegistrySupplier<Block> FORTSHROOM_STAIRS = registerBlock("fortshroom_stairs", resourceKey ->
            new StairBlock(FORTSHROOM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_STAIRS).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_FENCE = registerBlock("fortshroom_fence", resourceKey ->
            new FenceBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_FENCE).mapColor(MapColor.COLOR_GREEN).setId(resourceKey)));

    public static final RegistrySupplier<Block> FORTSHROOM_FENCE_GATE = registerBlock("fortshroom_fence_gate", resourceKey ->
            new FenceGateBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_FENCE_GATE).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_PRESSURE_PLATE = registerBlock("fortshroom_pressure_plate", resourceKey ->
            new PressurePlateBlock(FORTSHROOM_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_PRESSURE_PLATE).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_BUTTON = registerBlock("fortshroom_button", resourceKey ->
            new ButtonBlock(FORTSHROOM_SET, 30, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_BUTTON).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_SHELF = registerBlock("fortshroom_shelf", resourceKey ->
                    new ShelfBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_SHELF).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)),
            () -> new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));

    public static final RegistrySupplier<Block> FORTSHROOM_SIGN = registerBlockOnly("fortshroom_sign", resourceKey ->
            new CustomStandingSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_WALL_SIGN = registerBlockOnly("fortshroom_wall_sign", resourceKey ->
            new CustomWallSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_WALL_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_GREEN).overrideLootTable(FORTSHROOM_SIGN.get().getLootTable()).overrideDescription(FORTSHROOM_SIGN.get().getDescriptionId())));

    public static final RegistrySupplier<Block> FORTSHROOM_HANGING_SIGN = registerBlockOnly("fortshroom_hanging_sign", resourceKey ->
            new CustomCeilingHangingSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_HANGING_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> FORTSHROOM_WALL_HANGING_SIGN = registerBlockOnly("fortshroom_wall_hanging_sign", resourceKey ->
            new CustomWallHangingSignBlock(FORTSHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_WALL_HANGING_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_GREEN).overrideLootTable(FORTSHROOM_HANGING_SIGN.get().getLootTable()).overrideDescription(FORTSHROOM_HANGING_SIGN.get().getDescriptionId())));

    public static final RegistrySupplier<Block> FORTSHROOM_DOOR = registerBlock("fortshroom_door", resourceKey ->
            new DoorBlock(FORTSHROOM_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_DOOR).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)), DoubleHighBlockItem::new);

    public static final RegistrySupplier<Block> FORTSHROOM_TRAPDOOR = registerBlock("fortshroom_trapdoor", resourceKey ->
            new TrapDoorBlock(FORTSHROOM_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_TRAPDOOR).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistrySupplier<Block> STRIPPED_FORTSHROOM_STEM = registerBlock("stripped_fortshroom_stem", resourceKey ->
            strippedLog(MapColor.COLOR_GREEN, resourceKey));

    public static final RegistrySupplier<Block> FORTSHROOM_STEM = registerBlock("fortshroom_stem", resourceKey ->
            log(STRIPPED_FORTSHROOM_STEM, MapColor.COLOR_GREEN, MapColor.DEEPSLATE, resourceKey));

    public static final RegistrySupplier<Block> STRIPPED_FORTSHROOM_HYPHAE = registerBlock("stripped_fortshroom_hyphae", resourceKey ->
            strippedLog(MapColor.COLOR_GREEN, resourceKey));

    public static final RegistrySupplier<Block> FORTSHROOM_HYPHAE = registerBlock("fortshroom_hyphae", resourceKey ->
            log(STRIPPED_FORTSHROOM_HYPHAE, MapColor.DEEPSLATE, resourceKey));

    public static final RegistrySupplier<Block> FORTSHROOM_BLOCK = registerBlock("fortshroom_block", resourceKey ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_WART_BLOCK).setId(resourceKey).mapColor(MapColor.COLOR_GREEN)));

    public static final BlockSetType MALISHROOM_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("malishroom"));
    public static final WoodType MALISHROOM_WOOD_TYPE = LitherealPlatform.INSTANCE.registerWoodType(new WoodType("malishroom", MALISHROOM_SET));
    public static final RegistrySupplier<Block> MALISHROOM_PLANKS = registerBlock("malishroom_planks", resourceKey ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_PLANKS).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_SLAB = registerBlock("malishroom_slab", resourceKey ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_SLAB).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_STAIRS = registerBlock("malishroom_stairs", resourceKey ->
            new StairBlock(MALISHROOM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_STAIRS).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_FENCE = registerBlock("malishroom_fence", resourceKey ->
            new FenceBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_FENCE).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_FENCE_GATE = registerBlock("malishroom_fence_gate", resourceKey ->
            new FenceGateBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_FENCE_GATE).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_PRESSURE_PLATE = registerBlock("malishroom_pressure_plate", resourceKey ->
            new PressurePlateBlock(MALISHROOM_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_PRESSURE_PLATE).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_BUTTON = registerBlock("malishroom_button", resourceKey ->
            new ButtonBlock(MALISHROOM_SET, 30, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_BUTTON).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_SHELF = registerBlock("malishroom_shelf", resourceKey ->
                    new ShelfBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_SHELF).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)),
            () -> new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));

    public static final RegistrySupplier<Block> MALISHROOM_SIGN = registerBlockOnly("malishroom_sign", resourceKey ->
            new CustomStandingSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_WALL_SIGN = registerBlockOnly("malishroom_wall_sign", resourceKey ->
            new CustomWallSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_WALL_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE).overrideLootTable(MALISHROOM_SIGN.get().getLootTable()).overrideDescription(MALISHROOM_SIGN.get().getDescriptionId())));

    public static final RegistrySupplier<Block> MALISHROOM_HANGING_SIGN = registerBlockOnly("malishroom_hanging_sign", resourceKey ->
            new CustomCeilingHangingSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_HANGING_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> MALISHROOM_WALL_HANGING_SIGN = registerBlockOnly("malishroom_wall_hanging_sign", resourceKey ->
            new CustomWallHangingSignBlock(MALISHROOM_WOOD_TYPE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_WALL_HANGING_SIGN).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE).overrideLootTable(MALISHROOM_HANGING_SIGN.get().getLootTable()).overrideDescription(MALISHROOM_HANGING_SIGN.get().getDescriptionId())));

    public static final RegistrySupplier<Block> MALISHROOM_DOOR = registerBlock("malishroom_door", resourceKey ->
            new DoorBlock(MALISHROOM_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_DOOR).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)), DoubleHighBlockItem::new);

    public static final RegistrySupplier<Block> MALISHROOM_TRAPDOOR = registerBlock("malishroom_trapdoor", resourceKey ->
            new TrapDoorBlock(MALISHROOM_SET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_TRAPDOOR).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> STRIPPED_MALISHROOM_STEM = registerBlock("stripped_malishroom_stem", resourceKey ->
            strippedLog(MapColor.COLOR_PURPLE, resourceKey));

    public static final RegistrySupplier<Block> MALISHROOM_STEM = registerBlock("malishroom_stem", resourceKey ->
            log(STRIPPED_MALISHROOM_STEM, MapColor.COLOR_PURPLE, MapColor.DEEPSLATE, resourceKey));

    public static final RegistrySupplier<Block> STRIPPED_MALISHROOM_HYPHAE = registerBlock("stripped_malishroom_hyphae", resourceKey ->
            strippedLog(MapColor.COLOR_PURPLE, resourceKey));

    public static final RegistrySupplier<Block> MALISHROOM_HYPHAE = registerBlock("malishroom_hyphae", resourceKey ->
            log(STRIPPED_MALISHROOM_HYPHAE, MapColor.DEEPSLATE, resourceKey));

    public static final RegistrySupplier<Block> MALISHROOM_BLOCK = registerBlock("malishroom_block", resourceKey ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_WART_BLOCK).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistrySupplier<Block> RED_MALISHROOM_BLOCK = registerBlock("red_malishroom_block", resourceKey ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_WART_BLOCK).setId(resourceKey).mapColor(MapColor.COLOR_RED)));

    public static void register() {

    }
}
