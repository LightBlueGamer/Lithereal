package org.lithereal.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.lithereal.block.ModStoneBlocks;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public static final BlockFamily PHANTOM_OAK_PLANKS = new BlockFamily.Builder(ModTreeBlocks.PHANTOM_OAK_PLANKS.get())
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
    public static final BlockFamily PAILITE = new BlockFamily.Builder(ModStoneBlocks.PAILITE.get())
            .wall(ModStoneBlocks.PAILITE_WALL.get())
            .stairs(ModStoneBlocks.PAILITE_STAIRS.get())
            .slab(ModStoneBlocks.PAILITE_SLAB.get())
            .polished(ModStoneBlocks.POLISHED_PAILITE.get())
            .getFamily();
    public static final BlockFamily POLISHED_PAILITE = new BlockFamily.Builder(ModStoneBlocks.POLISHED_PAILITE.get())
            .wall(ModStoneBlocks.POLISHED_PAILITE_WALL.get())
            .stairs(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get())
            .slab(ModStoneBlocks.POLISHED_PAILITE_SLAB.get())
            .getFamily();
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ForgeBlocks.ELECTRIC_CRUCIBLE.get(), 1)
                .define('S', ModRawMaterialItems.AURELITE_DUST.get())
                .define('B', ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get())
                .define('C', ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('#', Tags.Items.STORAGE_BLOCKS_IRON)
                .pattern("ISI")
                .pattern("IBI")
                .pattern("#C#")
                .unlockedBy("has_charged_litherite_crystal", has(ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get()))
                .save(recipeOutput);

        generateRecipes(recipeOutput, PAILITE, FeatureFlagSet.of(FeatureFlags.VANILLA));
        generateRecipes(recipeOutput, POLISHED_PAILITE, FeatureFlagSet.of(FeatureFlags.VANILLA));
        generateRecipes(recipeOutput, PHANTOM_OAK_PLANKS, FeatureFlagSet.of(FeatureFlags.VANILLA));
        planksFromLog(recipeOutput, ModTreeBlocks.PHANTOM_OAK_PLANKS.get(), ModTags.PHANTOM_OAK_LOGS, 4);
        woodFromLogs(recipeOutput, ModTreeBlocks.PHANTOM_OAK_WOOD.get(), ModTreeBlocks.PHANTOM_OAK_LOG.get());
        woodFromLogs(recipeOutput, ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        woodenBoat(recipeOutput, ModItems.PHANTOM_OAK_BOAT.get(), ModTreeBlocks.PHANTOM_OAK_PLANKS.get());
        chestBoat(recipeOutput, ModItems.PHANTOM_OAK_CHEST_BOAT.get(), ModItems.PHANTOM_OAK_BOAT.get());
        hangingSign(recipeOutput, ModItems.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE.get(), 2)
                .define('C', ModRawMaterialItems.CYRUM_CRYSTAL.get())
                .define('E', ModStoneBlocks.ETHERSTONE.get())
                .pattern("EC")
                .pattern("CE")
                .unlockedBy("has_etherstone", has(ModStoneBlocks.ETHERSTONE.get()))
                .save(recipeOutput);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE_SLAB.get(), ModStoneBlocks.PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE_STAIRS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.DECORATIONS, ModStoneBlocks.PAILITE_WALL.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), ModStoneBlocks.PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_WALL.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), ModStoneBlocks.POLISHED_PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_WALL.get(), ModStoneBlocks.POLISHED_PAILITE.get());
    }

    protected static void stonecutterResultFromBaseModNamespace(RecipeOutput recipeOutput, RecipeCategory category, ItemLike to, ItemLike from) {
        stonecutterResultFromBaseModNamespace(recipeOutput, category, to, from, 1);
    }

    protected static void stonecutterResultFromBaseModNamespace(RecipeOutput recipeOutput, RecipeCategory category, ItemLike to, ItemLike from, int count) {
        SingleItemRecipeBuilder recipeBuilder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), category, to, count).unlockedBy(getHasName(from), has(from));
        String convertName = getConversionRecipeName(to, from);
        recipeBuilder.save(recipeOutput, "lithereal:" + convertName + "_stonecutting");
    }
}