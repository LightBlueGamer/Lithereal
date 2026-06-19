package org.lithereal.util;

import com.google.common.collect.Lists;
import net.minecraft.data.BlockFamily;
import org.lithereal.block.ModStoneBlocks;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.data.mixin.accessor.BlockFamiliesAccessor;

import java.util.List;

public class ModBlockFamilies {
    public static final BlockFamily PHANTOM_OAK_PLANKS = BlockFamiliesAccessor.callFamilyBuilder(ModTreeBlocks.PHANTOM_OAK_PLANKS.get())
            .button(ModTreeBlocks.PHANTOM_OAK_BUTTON.get())
            .fence(ModTreeBlocks.PHANTOM_OAK_FENCE.get())
            .fenceGate(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get())
            .pressurePlate(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get())
            .sign(ModTreeBlocks.PHANTOM_OAK_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_SIGN.get())
            .slab(ModTreeBlocks.PHANTOM_OAK_SLAB.get())
            .stairs(ModTreeBlocks.PHANTOM_OAK_STAIRS.get())
            .door(ModTreeBlocks.PHANTOM_OAK_DOOR.get())
            .trapdoor(ModTreeBlocks.PHANTOM_OAK_TRAPDOOR.get())
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();
    public static final BlockFamily FORTSHROOM_PLANKS = BlockFamiliesAccessor.callFamilyBuilder(ModTreeBlocks.FORTSHROOM_PLANKS.get())
            .button(ModTreeBlocks.FORTSHROOM_BUTTON.get())
            .fence(ModTreeBlocks.FORTSHROOM_FENCE.get())
            .fenceGate(ModTreeBlocks.FORTSHROOM_FENCE_GATE.get())
            .pressurePlate(ModTreeBlocks.FORTSHROOM_PRESSURE_PLATE.get())
            .sign(ModTreeBlocks.FORTSHROOM_SIGN.get(), ModTreeBlocks.FORTSHROOM_WALL_SIGN.get())
            .slab(ModTreeBlocks.FORTSHROOM_SLAB.get())
            .stairs(ModTreeBlocks.FORTSHROOM_STAIRS.get())
            .door(ModTreeBlocks.FORTSHROOM_DOOR.get())
            .trapdoor(ModTreeBlocks.FORTSHROOM_TRAPDOOR.get())
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();
    public static final BlockFamily MALISHROOM_PLANKS = BlockFamiliesAccessor.callFamilyBuilder(ModTreeBlocks.MALISHROOM_PLANKS.get())
            .button(ModTreeBlocks.MALISHROOM_BUTTON.get())
            .fence(ModTreeBlocks.MALISHROOM_FENCE.get())
            .fenceGate(ModTreeBlocks.MALISHROOM_FENCE_GATE.get())
            .pressurePlate(ModTreeBlocks.MALISHROOM_PRESSURE_PLATE.get())
            .sign(ModTreeBlocks.MALISHROOM_SIGN.get(), ModTreeBlocks.MALISHROOM_WALL_SIGN.get())
            .slab(ModTreeBlocks.MALISHROOM_SLAB.get())
            .stairs(ModTreeBlocks.MALISHROOM_STAIRS.get())
            .door(ModTreeBlocks.MALISHROOM_DOOR.get())
            .trapdoor(ModTreeBlocks.MALISHROOM_TRAPDOOR.get())
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();
    public static final BlockFamily ETHERSTONE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.ETHERSTONE.get())
            .wall(ModStoneBlocks.ETHERSTONE_WALL.get())
            .stairs(ModStoneBlocks.ETHERSTONE_STAIRS.get())
            .slab(ModStoneBlocks.ETHERSTONE_SLAB.get())
            .button(ModStoneBlocks.ETHERSTONE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.ETHERSTONE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.POLISHED_ETHERSTONE.get())
            .chiseled(ModStoneBlocks.CHISELED_ETHERSTONE.get())
            .generateStonecutterRecipe()
            .dontGenerateModel()
            .getFamily();
    public static final BlockFamily POLISHED_ETHERSTONE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.POLISHED_ETHERSTONE.get())
            .wall(ModStoneBlocks.POLISHED_ETHERSTONE_WALL.get())
            .stairs(ModStoneBlocks.POLISHED_ETHERSTONE_STAIRS.get())
            .slab(ModStoneBlocks.POLISHED_ETHERSTONE_SLAB.get())
            .button(ModStoneBlocks.POLISHED_ETHERSTONE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.POLISHED_ETHERSTONE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.ETHERSTONE_BRICKS.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily ETHERSTONE_BRICKS = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.ETHERSTONE_BRICKS.get())
            .wall(ModStoneBlocks.ETHERSTONE_BRICK_WALL.get())
            .stairs(ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get())
            .slab(ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get())
            .button(ModStoneBlocks.ETHERSTONE_BRICK_BUTTON.get())
            .pressurePlate(ModStoneBlocks.ETHERSTONE_BRICK_PRESSURE_PLATE.get())
            .cracked(ModStoneBlocks.CRACKED_ETHERSTONE_BRICKS.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily PAILITE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.PAILITE.get())
            .wall(ModStoneBlocks.PAILITE_WALL.get())
            .stairs(ModStoneBlocks.PAILITE_STAIRS.get())
            .slab(ModStoneBlocks.PAILITE_SLAB.get())
            .button(ModStoneBlocks.PAILITE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.PAILITE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.POLISHED_PAILITE.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily POLISHED_PAILITE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.POLISHED_PAILITE.get())
            .wall(ModStoneBlocks.POLISHED_PAILITE_WALL.get())
            .stairs(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get())
            .slab(ModStoneBlocks.POLISHED_PAILITE_SLAB.get())
            .button(ModStoneBlocks.POLISHED_PAILITE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.POLISHED_PAILITE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily POLISHED_PAILITE_BRICKS = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get())
            .wall(ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get())
            .stairs(ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get())
            .slab(ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get())
            .button(ModStoneBlocks.POLISHED_PAILITE_BRICK_BUTTON.get())
            .pressurePlate(ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get())
            .cracked(ModStoneBlocks.CRACKED_POLISHED_PAILITE_BRICKS.get())
            .chiseled(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get())
            .generateStonecutterRecipe()
            .dontGenerateModel()
            .getFamily();
    public static final BlockFamily LUMINITE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.LUMINITE.get())
            .wall(ModStoneBlocks.LUMINITE_WALL.get())
            .stairs(ModStoneBlocks.LUMINITE_STAIRS.get())
            .slab(ModStoneBlocks.LUMINITE_SLAB.get())
            .button(ModStoneBlocks.LUMINITE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.LUMINITE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.POLISHED_LUMINITE.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily POLISHED_LUMINITE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.POLISHED_LUMINITE.get())
            .wall(ModStoneBlocks.POLISHED_LUMINITE_WALL.get())
            .stairs(ModStoneBlocks.POLISHED_LUMINITE_STAIRS.get())
            .slab(ModStoneBlocks.POLISHED_LUMINITE_SLAB.get())
            .button(ModStoneBlocks.POLISHED_LUMINITE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.POLISHED_LUMINITE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.LUMINITE_BRICKS.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily LUMINITE_BRICKS = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.LUMINITE_BRICKS.get())
            .wall(ModStoneBlocks.LUMINITE_BRICK_WALL.get())
            .stairs(ModStoneBlocks.LUMINITE_BRICK_STAIRS.get())
            .slab(ModStoneBlocks.LUMINITE_BRICK_SLAB.get())
            .button(ModStoneBlocks.LUMINITE_BRICK_BUTTON.get())
            .pressurePlate(ModStoneBlocks.LUMINITE_BRICK_PRESSURE_PLATE.get())
            .cracked(ModStoneBlocks.CRACKED_LUMINITE_BRICKS.get())
            .chiseled(ModStoneBlocks.CHISELED_LUMINITE_BRICKS.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily VERDONE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.VERDONE.get())
            .wall(ModStoneBlocks.VERDONE_WALL.get())
            .stairs(ModStoneBlocks.VERDONE_STAIRS.get())
            .slab(ModStoneBlocks.VERDONE_SLAB.get())
            .button(ModStoneBlocks.VERDONE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.VERDONE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.POLISHED_VERDONE.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily POLISHED_VERDONE = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.POLISHED_VERDONE.get())
            .wall(ModStoneBlocks.POLISHED_VERDONE_WALL.get())
            .stairs(ModStoneBlocks.POLISHED_VERDONE_STAIRS.get())
            .slab(ModStoneBlocks.POLISHED_VERDONE_SLAB.get())
            .button(ModStoneBlocks.POLISHED_VERDONE_BUTTON.get())
            .pressurePlate(ModStoneBlocks.POLISHED_VERDONE_PRESSURE_PLATE.get())
            .polished(ModStoneBlocks.VERDONE_BRICKS.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily VERDONE_BRICKS = BlockFamiliesAccessor.callFamilyBuilder(ModStoneBlocks.VERDONE_BRICKS.get())
            .wall(ModStoneBlocks.VERDONE_BRICK_WALL.get())
            .stairs(ModStoneBlocks.VERDONE_BRICK_STAIRS.get())
            .slab(ModStoneBlocks.VERDONE_BRICK_SLAB.get())
            .button(ModStoneBlocks.VERDONE_BRICK_BUTTON.get())
            .pressurePlate(ModStoneBlocks.VERDONE_BRICK_PRESSURE_PLATE.get())
            .cracked(ModStoneBlocks.CRACKED_VERDONE_BRICKS.get())
            .chiseled(ModStoneBlocks.CHISELED_VERDONE_BRICKS.get())
            .generateStonecutterRecipe()
            .getFamily();
    public static final List<BlockFamily> MOD_BLOCK_FAMILIES = Lists.newArrayList(PHANTOM_OAK_PLANKS,
            FORTSHROOM_PLANKS,
            MALISHROOM_PLANKS,
            ETHERSTONE,
            POLISHED_ETHERSTONE,
            ETHERSTONE_BRICKS,
            PAILITE,
            POLISHED_PAILITE,
            POLISHED_PAILITE_BRICKS,
            LUMINITE,
            POLISHED_LUMINITE,
            LUMINITE_BRICKS,
            VERDONE,
            POLISHED_VERDONE,
            VERDONE_BRICKS);
}
