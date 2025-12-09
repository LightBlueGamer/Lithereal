package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.data.mixin.BlockSetTypeAccessor;
import org.lithereal.world.feature.ModFeatures;

import java.util.Optional;

import static org.lithereal.block.ModBlocks.*;

public class ModTreeBlocks {
    public static final TreeGrower PHANTOM_OAK = new TreeGrower("phantom_oak", 0.1F, Optional.of(ModFeatures.MEGA_PHANTOM_OAK), Optional.empty(), Optional.of(ModFeatures.PHANTOM_OAK), Optional.of(ModFeatures.FANCY_PHANTOM_OAK), Optional.empty(), Optional.empty());
    public static final BlockSetType PHANTOM_OAK_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("phantom_oak"));
    public static final WoodType PHANTOM_OAK_WOOD_TYPE = LitherealExpectPlatform.registerWoodType(new WoodType("phantom_oak", PHANTOM_OAK_SET));
    public static final RegistrySupplier<Block> PHANTOM_OAK_PLANKS = registerBlock("phantom_oak_planks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SLAB = registerBlock("phantom_oak_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_STAIRS = registerBlock("phantom_oak_stairs", () ->
            new StairBlock(PHANTOM_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE = registerBlock("phantom_oak_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE_GATE = registerBlock("phantom_oak_fence_gate", () ->
            new FenceGateBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_PRESSURE_PLATE = registerBlock("phantom_oak_pressure_plate", () ->
            new PressurePlateBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_BUTTON = registerBlock("phantom_oak_button", () ->
            new ButtonBlock(PHANTOM_OAK_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SIGN = registerBlockOnly("phantom_oak_sign", () ->
            new CustomStandingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_SIGN = registerBlockOnly("phantom_oak_wall_sign", () ->
            new CustomWallSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).dropsLike(PHANTOM_OAK_SIGN.get())));

    public static final RegistrySupplier<Block> PHANTOM_OAK_HANGING_SIGN = registerBlockOnly("phantom_oak_hanging_sign", () ->
            new CustomCeilingHangingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_HANGING_SIGN = registerBlockOnly("phantom_oak_wall_hanging_sign", () ->
            new CustomWallHangingSignBlock(PHANTOM_OAK_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).dropsLike(PHANTOM_OAK_HANGING_SIGN.get())));

    public static final RegistrySupplier<Block> PHANTOM_OAK_DOOR = registerBlock("phantom_oak_door", () ->
            new DoorBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)), block -> new DoubleHighBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_TRAPDOOR = registerBlock("phantom_oak_trapdoor", () ->
            new TrapDoorBlock(PHANTOM_OAK_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_LEAVES = registerBlock("phantom_oak_leaves", () ->
            new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_SAPLING = registerBlock("phantom_oak_sapling", () ->
            new SaplingBlock(PHANTOM_OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> POTTED_PHANTOM_OAK_SAPLING = registerBlockOnly("potted_phantom_oak_sapling", () ->
            new FlowerPotBlock(PHANTOM_OAK_SAPLING.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_OAK_SAPLING)));

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_LOG = registerBlock("stripped_phantom_oak_log", () ->
            strippedLog(MapColor.TERRACOTTA_PURPLE), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_LOG = registerBlock("phantom_oak_log", () ->
            log(STRIPPED_PHANTOM_OAK_LOG, MapColor.TERRACOTTA_PURPLE, MapColor.COLOR_PURPLE), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_WOOD = registerBlock("stripped_phantom_oak_wood", () ->
            strippedLog(MapColor.TERRACOTTA_PURPLE), block -> new BlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_OAK_WOOD = registerBlock("phantom_oak_wood", () ->
            log(STRIPPED_PHANTOM_OAK_WOOD, MapColor.COLOR_PURPLE), block -> new BlockItem(block, new Item.Properties()));

    public static void register() {

    }
}
