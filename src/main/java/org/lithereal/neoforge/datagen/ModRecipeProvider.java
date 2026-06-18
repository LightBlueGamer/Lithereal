package org.lithereal.neoforge.datagen;

//? neoforge {
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.lithereal.Lithereal;
import org.lithereal.block.*;
import org.lithereal.data.datagen.CustomRecipeProvider;
import org.lithereal.data.recipes.FireCrucibleRecipeBuilder;
import org.lithereal.data.recipes.FreezingStationRecipeBuilder;
import org.lithereal.data.recipes.InfusementChamberRecipeBuilder;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.item.datagen.ItemDataProvider;
import org.lithereal.tags.ModTags;
import org.lithereal.util.ModBlockFamilies;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements CustomRecipeProvider<ModRecipeProvider> {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public ModRecipeProvider self() {
        return this;
    }

    @Override
    public void nineBlockStorageRecipesFromBaseModNamespace(RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed) {
        nineBlockStorageRecipesFromBaseModNamespace(unpackCategory, unpacked, packCategory, packed, getSimpleRecipeName(packed), null, getSimpleRecipeName(unpacked), null);
    }

    @Override
    public void nineBlockStorageRecipesWithCustomPackingFromBaseModNamespace(
            RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String forPacking, String packingGroup
    ) {
        nineBlockStorageRecipesFromBaseModNamespace(unpackCategory, unpacked, packCategory, packed, forPacking, packingGroup, getSimpleRecipeName(unpacked), null);
    }

    @Override
    public void nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
            RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String forUnpacking, String unpackingGroup
    ) {
        nineBlockStorageRecipesFromBaseModNamespace(unpackCategory, unpacked, packCategory, packed, getSimpleRecipeName(packed), null, forUnpacking, unpackingGroup);
    }

    @Override
    public void nineBlockStorageRecipesFromBaseModNamespace(
            RecipeCategory unpackCategory,
            ItemLike unpacked,
            RecipeCategory packCategory,
            ItemLike packed,
            String forPacking,
            @Nullable String packingGroup,
            String forUnpacking,
            @Nullable String unpackingGroup
    ) {
        ShapelessRecipeBuilder.shapeless(this.items, unpackCategory, unpacked, 9)
                .requires(packed)
                .group(unpackingGroup)
                .unlockedBy(getHasName(packed), has(packed))
                .save(this.output, Lithereal.key(Registries.RECIPE, forUnpacking));
        ShapedRecipeBuilder.shaped(this.items, packCategory, packed)
                .define('#', unpacked)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(packingGroup)
                .unlockedBy(getHasName(unpacked), has(unpacked))
                .save(this.output, Lithereal.key(Registries.RECIPE, forPacking));
    }

    @Override
    public <T extends AbstractCookingRecipe> void oreCookingFromBaseModNamespace(
            AbstractCookingRecipe.Factory<T> factory,
            List<ItemLike> itemsToSmelt,
            RecipeCategory recipeCategory,
            CookingBookCategory cookingBookCategory,
            ItemLike result,
            float experience,
            int cookingTime,
            String group,
            String method
    ) {
        for (ItemLike itemlike : itemsToSmelt) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), recipeCategory, cookingBookCategory, result, experience, cookingTime, factory)
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(this.output, "lithereal:" + getItemName(result) + method + "_" + getItemName(itemlike));
        }
    }

    @Override
    public void stonecutterResultFromBaseModNamespace(RecipeCategory category, ItemLike to, ItemLike from, int count) {
        SingleItemRecipeBuilder recipeBuilder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), category, to, count).unlockedBy(getHasName(from), has(from));
        String convertName = getConversionRecipeName(to, from);
        recipeBuilder.save(this.output, "lithereal:" + convertName + "_stonecutting");
    }

    @Override
    public void improvedThermalItem(RecipeCategory recipeCategory, Item base, Item improved) {
        ShapedRecipeBuilder.shaped(this.items, recipeCategory, improved, 1)
                .define('A', ModRawMaterialItems.AURELITE_INGOT.get())
                .define('B', base)
                .define('C', ModRawMaterialItems.CHRYON_CRYSTAL.get())
                .pattern(" A ")
                .pattern("CBC")
                .pattern(" A ")
                .unlockedBy("has_aurelite_ingot", has(ModRawMaterialItems.AURELITE_INGOT.get()))
                .save(this.output);
    }

    @Override
    public void swordItem(Item rod, Item baseMaterial, Item sword) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, sword, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("B")
                .pattern("B")
                .pattern("R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void spearItem(Item rod, Item baseMaterial, Item spear) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, spear, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("  B")
                .pattern(" R ")
                .pattern("R  ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void pickaxeItem(Item rod, Item baseMaterial, Item pickaxe) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, pickaxe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BBB")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void axeItem(Item rod, Item baseMaterial, Item axe) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, axe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BB")
                .pattern("BR")
                .pattern(" R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void shovelItem(Item rod, Item baseMaterial, Item shovel) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, shovel, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("B")
                .pattern("R")
                .pattern("R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void hoeItem(Item rod, Item baseMaterial, Item hoe) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, hoe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BB")
                .pattern(" R")
                .pattern(" R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void hammerItem(Item rod, Item baseMaterial, Item hammer) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, hammer, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BBB")
                .pattern("BBB")
                .pattern(" R ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void warHammerItem(Item rod, Item baseMaterial, Item warHammer) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, warHammer)
                .define('#', baseMaterial)
                .define('O', rod)
                .pattern("#")
                .pattern("O")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void helmetItem(Item baseMaterial, Item helmet) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, helmet, 1)
                .define('B', baseMaterial)
                .pattern("BBB")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void chestplateItem(Item baseMaterial, Item chestplate) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, chestplate, 1)
                .define('B', baseMaterial)
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void leggingsItem(Item baseMaterial, Item leggings) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, leggings, 1)
                .define('B', baseMaterial)
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void bootsItem(Item baseMaterial, Item boots) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, boots, 1)
                .define('B', baseMaterial)
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void bowItem(Item baseMaterial, Item string, Item bow) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, bow, 1)
                .define('#', baseMaterial)
                .define('S', string)
                .pattern(" #S")
                .pattern("# S")
                .pattern(" #S")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void bowItem(Item baseMaterial, Item stringAttached, Item string, Item bow) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, bow, 1)
                .define('#', baseMaterial)
                .define('I', stringAttached)
                .define('S', string)
                .pattern(" #I")
                .pattern("# S")
                .pattern(" #I")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void brushItem(Item baseMaterial, Item brush) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, brush)
                .define('#', baseMaterial)
                .define('I', Tags.Items.RODS_WOODEN)
                .define('X', Items.FEATHER)
                .pattern("X")
                .pattern("#")
                .pattern("I")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    @Override
    public void wrenchItem(Item baseMaterialA, Item baseMaterialB, Item wrench) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, wrench)
                .define('#', baseMaterialA)
                .define('I', baseMaterialB)
                .pattern(" II")
                .pattern(" #I")
                .pattern("I  ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterialA), has(baseMaterialA))
                .save(this.output);
    }

    @Override
    public void upgradeRecipe(boolean tool, Item template, Item base, Item addition, Item result) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(base), Ingredient.of(addition), tool ? RecipeCategory.TOOLS : RecipeCategory.COMBAT, result)
                .unlocks("has_" + getSimpleRecipeName(addition), has(addition))
                .save(this.output, "lithereal:" + getItemName(result) + "_smithing");
    }

    @Override
    public void buildRecipes() {
        ModBlockFamilies.MOD_BLOCK_FAMILIES.forEach(blockFamily ->
                generateRecipes(blockFamily, FeatureFlags.VANILLA_SET));
        ItemDataProvider.ALL_ITEM_DATA_PROVIDERS.forEach(itemDataProvider -> itemDataProvider.recipeCreator().ifPresent(creator -> creator.accept(this)));
        FireCrucibleRecipeBuilder.noSecondary(RecipeCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get())
                .unlockedBy("has_litherite_crystal", has(ModRawMaterialItems.LITHERITE_CRYSTAL.get()))
                .save(this.output);
        FireCrucibleRecipeBuilder.noSecondary(RecipeCategory.MISC, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModRawMaterialItems.LITHERITE_CRYSTAL.get())
                .cookingTime(400)
                .unlockedBy("has_frozen_litherite_crystal", has(ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get()))
                .save(this.output, "litherite_crystal_from_reversing_freezing");
        FireCrucibleRecipeBuilder.fullRecipe(RecipeCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), Items.BUCKET, ModItems.MOLTEN_LITHERITE_BUCKET.get())
                .unlockedBy("has_litherite_crystal", has(ModRawMaterialItems.LITHERITE_CRYSTAL.get()))
                .save(this.output);
        FireCrucibleRecipeBuilder.fullRecipe(RecipeCategory.MISC, ModRawMaterialItems.ELUNITE_CRYSTAL.get(), ModRawMaterialItems.CYRUM_CRYSTAL.get(), ModRawMaterialItems.ELCRUM_INGOT.get())
                .cookingTime(250)
                .unlockedBy("has_elunite_crystal", has(ModRawMaterialItems.ELUNITE_CRYSTAL.get()))
                .save(this.output);
        FireCrucibleRecipeBuilder.fullRecipe(RecipeCategory.MISC, ModRawMaterialItems.HELLIONITE_CRYSTAL.get(), ModRawMaterialItems.LUMINIUM_CRYSTAL.get(), ModRawMaterialItems.SOLIUMITE_INGOT.get())
                .cookingTime(250)
                .unlockedBy("has_hellionite_crystal", has(ModRawMaterialItems.HELLIONITE_CRYSTAL.get()))
                .save(this.output);
        FreezingStationRecipeBuilder.freezing(RecipeCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get())
                .unlockedBy("has_litherite_crystal", has(ModRawMaterialItems.LITHERITE_CRYSTAL.get()))
                .save(this.output);
        FreezingStationRecipeBuilder.freezing(RecipeCategory.MISC, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModRawMaterialItems.LITHERITE_CRYSTAL.get())
                .recipeTime(400)
                .unlockedBy("has_burning_litherite_crystal", has(ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get()))
                .save(this.output, "litherite_crystal_from_reversing_burning");
        InfusementChamberRecipeBuilder.infusing(RecipeCategory.MISC, Items.POTION, ModItems.MOLTEN_LITHERITE_BUCKET.get(), ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get())
                .unlockedBy("has_molten_litherite_bucket", has(ModItems.MOLTEN_LITHERITE_BUCKET.get()))
                .save(this.output);
        InfusementChamberRecipeBuilder.infusing(RecipeCategory.FOOD, Items.POTION, Items.HONEY_BOTTLE, Items.HONEY_BOTTLE)
                .unlockedBy("has_potion", has(Items.POTION))
                .save(this.output, "honey_bottle_concoction");
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModBlocks.FIRE_CRUCIBLE.get())
                .define('T', Items.TERRACOTTA)
                .define('C', Items.CAULDRON)
                .pattern("T T")
                .pattern("TCT")
                .pattern("TTT")
                .unlockedBy("has_cauldron", has(Items.CAULDRON))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModBlocks.FREEZING_STATION.get())
                .define('T', Items.IRON_TRAPDOOR)
                .define('I', Items.IRON_INGOT)
                .define('B', Items.BUCKET)
                .pattern("ITI")
                .pattern("IBI")
                .pattern("III")
                .unlockedBy("has_bucket", has(Items.BUCKET))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModBlocks.INFUSEMENT_CHAMBER.get())
                .define('L', ModRawMaterialItems.LITHERITE_CRYSTAL.get())
                .define('I', Items.IRON_INGOT)
                .define('O', Items.OBSERVER)
                .pattern("ILI")
                .pattern("LOL")
                .pattern("ILI")
                .unlockedBy("has_litherite_crystal", has(ModRawMaterialItems.LITHERITE_CRYSTAL.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, ModItems.LITHER_TORCH.get(), 4)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('#', ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get())
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("G")
                .pattern("#")
                .pattern("X")
                .unlockedBy("has_charged_litherite_crystal", has(ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LITHER_LANTERN.get(), 1)
                .define('X', ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get())
                .define('#', ModItems.LITHER_TORCH.get())
                .pattern(" X ")
                .pattern("X#X")
                .pattern(" X ")
                .unlockedBy("has_impure_ethereal_crystal_shard", has(ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModBlocks.PASSIVE_ETHER_ABSORBER.get(), 1)
                .define('C', ModRawMaterialItems.CHRYON_CRYSTAL.get())
                .define('A', ModRawMaterialItems.ALLIAN_INGOT.get())
                .define('N', ModRawMaterialItems.NERITH_INGOT.get())
                .pattern("ACA")
                .pattern("N N")
                .pattern("ACA")
                .unlockedBy("has_allian_ingot", has(ModRawMaterialItems.ALLIAN_INGOT.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModBlocks.ELECTRIC_CRUCIBLE.get(), 1)
                .define('S', ModRawMaterialItems.AURELITE_DUST.get())
                .define('B', ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get())
                .define('C', ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('#', Tags.Items.STORAGE_BLOCKS_IRON)
                .pattern("ISI")
                .pattern("IBI")
                .pattern("#C#")
                .unlockedBy("has_charged_litherite_crystal", has(ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModRawMaterialItems.UNIFIER.get(), 1)
                .define('A', ModRawMaterialItems.ALLIAN_INGOT.get())
                .define('N', Items.NETHER_STAR)
                .define('B', ModItems.BOSS_ESSENCE.get())
                .pattern("ABA")
                .pattern("ANA")
                .pattern(" A ")
                .unlockedBy("has_boss_essence", has(ModItems.BOSS_ESSENCE.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModItems.MYSTERIOUS_ROD.get(), 8)
                .define('O', ModRawMaterialItems.ODYSIUM_INGOT.get())
                .pattern("O")
                .pattern("O")
                .unlockedBy("has_odysium", has(ModRawMaterialItems.ODYSIUM_INGOT.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), 2)
                .define('#', ModRawMaterialItems.LITHERITE_CRYSTAL.get())
                .define('C', ModStoneBlocks.ETHERSTONE.get())
                .define('S', ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get())
                .pattern("#S#")
                .pattern("#C#")
                .pattern("###")
                .unlockedBy("has_odysium_upgrade_smithing_template", has(ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get()))
                .save(this.output);

        oreDual(List.of(ModOreBlocks.LITHERITE_ORE.get(), ModOreBlocks.DEEPSLATE_LITHERITE_ORE.get(), ModOreBlocks.ETHERSTONE_LITHERITE_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), 1, 200, "litherite_crystal");
        oreDual(List.of(ModOreBlocks.NERITH_ORE.get(), ModOreBlocks.DEEPSLATE_NERITH_ORE.get(), ModOreBlocks.ETHERSTONE_NERITH_ORE.get(), ModRawMaterialItems.RAW_NERITH.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.NERITH_INGOT.get(), 2, 300, "nerith_ingot");
        oreDual(List.of(ModOreBlocks.LUMINIUM_ORE.get(), ModOreBlocks.DEEPSLATE_LUMINIUM_ORE.get(), ModOreBlocks.ETHERSTONE_LUMINIUM_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.LUMINIUM_CRYSTAL.get(), 0.6F, 200, "luminium_crystal");
        oreDual(List.of(ModOreBlocks.CYRUM_ORE.get(), ModOreBlocks.DEEPSLATE_CYRUM_ORE.get(), ModOreBlocks.ETHERSTONE_CYRUM_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.CYRUM_CRYSTAL.get(), 0.1F, 200, "cyrum_crystal");
        oreDual(List.of(ModOreBlocks.COPALITE_ORE.get(), ModOreBlocks.DEEPSLATE_COPALITE_ORE.get(), ModOreBlocks.ETHERSTONE_COPALITE_ORE.get(), ModRawMaterialItems.COPALITE_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.COPALITE_INGOT.get(), 0.5F, 200, "copalite_ingot");
        oreDual(List.of(ModOreBlocks.AURELITE_ORE.get(), ModOreBlocks.DEEPSLATE_AURELITE_ORE.get(), ModOreBlocks.ETHERSTONE_AURELITE_ORE.get(), ModRawMaterialItems.AURELITE_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.AURELITE_INGOT.get(), 0.5F, 200, "aurelite_ingot");
        oreDual(List.of(ModOreBlocks.SATURNITE_ORE.get(), ModOreBlocks.PAILITE_SATURNITE_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.SATURNITE_CRYSTAL.get(), 0.7F, 200, "saturnite_crystal");
        oreDual(List.of(ModOreBlocks.HELLIONITE_ORE.get(), ModOreBlocks.PAILITE_HELLIONITE_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.HELLIONITE_CRYSTAL.get(), 0.8F, 200, "hellionite_crystal");
        oreDual(List.of(ModOreBlocks.ELUNITE_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.ELUNITE_CRYSTAL.get(), 1, 200, "elunite_crystal");
        oreDual(List.of(ModOreBlocks.CHRYON_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.CHRYON_CRYSTAL.get(), 1.4F, 200, "chryon_crystal");
        oreDual(List.of(ModOreBlocks.ALLIAN_ORE.get(), ModRawMaterialItems.RAW_ALLIUM.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.ALLIAN_INGOT.get(), 2, 300, "allian_ingot");
        oreDual(List.of(ModOreBlocks.PAILITE_NETHERITE_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.NETHERITE_FRAGMENT.get(), 1.2F, 400, "netherite_fragment");
        oreDual(List.of(ModPhantomBlocks.PHANTOM_DIAMOND_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ModRawMaterialItems.PHANTOM_DIAMOND.get(), 1, 200, "phantom_diamond");
        oreDual(List.of(ModPhantomBlocks.PHANTOM_QUARTZ_ORE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, Items.QUARTZ, 0.2F, 200, "quartz");

        planksFromLog(ModTreeBlocks.PHANTOM_OAK_PLANKS.get(), ModTags.PHANTOM_OAK_LOGS, 4);
        woodFromLogs(ModTreeBlocks.PHANTOM_OAK_WOOD.get(), ModTreeBlocks.PHANTOM_OAK_LOG.get());
        woodFromLogs(ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        woodenBoat(ModItems.PHANTOM_OAK_BOAT.get(), ModTreeBlocks.PHANTOM_OAK_PLANKS.get());
        chestBoat(ModItems.PHANTOM_OAK_CHEST_BOAT.get(), ModItems.PHANTOM_OAK_BOAT.get());
        hangingSign(ModItems.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        shelf(ModTreeBlocks.PHANTOM_OAK_SHELF.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());

        planksFromLog(ModTreeBlocks.FORTSHROOM_PLANKS.get(), ModTags.FORTSHROOM_STEMS, 4);
        woodFromLogs(ModTreeBlocks.FORTSHROOM_HYPHAE.get(), ModTreeBlocks.FORTSHROOM_STEM.get());
        woodFromLogs(ModTreeBlocks.STRIPPED_FORTSHROOM_HYPHAE.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        woodenBoat(ModItems.FORTSHROOM_BOAT.get(), ModTreeBlocks.FORTSHROOM_PLANKS.get());
        chestBoat(ModItems.FORTSHROOM_CHEST_BOAT.get(), ModItems.FORTSHROOM_BOAT.get());
        hangingSign(ModItems.FORTSHROOM_HANGING_SIGN.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        shelf(ModTreeBlocks.FORTSHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModVegetationBlocks.FORTSHROOM.get(), RecipeCategory.MISC, ModTreeBlocks.FORTSHROOM_BLOCK.get(), "fortshroom_from_block", "fortshroom"
        );

        planksFromLog(ModTreeBlocks.MALISHROOM_PLANKS.get(), ModTags.MALISHROOM_STEMS, 4);
        woodFromLogs(ModTreeBlocks.MALISHROOM_HYPHAE.get(), ModTreeBlocks.MALISHROOM_STEM.get());
        woodFromLogs(ModTreeBlocks.STRIPPED_MALISHROOM_HYPHAE.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
        woodenBoat(ModItems.MALISHROOM_BOAT.get(), ModTreeBlocks.MALISHROOM_PLANKS.get());
        chestBoat(ModItems.MALISHROOM_CHEST_BOAT.get(), ModItems.MALISHROOM_BOAT.get());
        hangingSign(ModItems.MALISHROOM_HANGING_SIGN.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
        shelf(ModTreeBlocks.MALISHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModVegetationBlocks.MALISHROOM.get(), RecipeCategory.MISC, ModTreeBlocks.MALISHROOM_BLOCK.get(), "malishroom_from_block", "malishroom"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), RecipeCategory.MISC, ModStorageBlocks.LITHERITE_BLOCK.get(), "litherite_crystal_from_block", "litherite_crystal"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), RecipeCategory.MISC, ModStorageBlocks.BURNING_LITHERITE_BLOCK.get(), "burning_litherite_crystal_from_block", "burning_litherite_crystal"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), RecipeCategory.MISC, ModStorageBlocks.FROZEN_LITHERITE_BLOCK.get(), "frozen_litherite_crystal_from_block", "frozen_litherite_crystal"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), RecipeCategory.MISC, ModStorageBlocks.INFUSED_LITHERITE_BLOCK.get(), "infused_litherite_ingot_from_block", "infused_litherite_ingot"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), RecipeCategory.MISC, ModStorageBlocks.WITHERING_LITHERITE_BLOCK.get(), "withering_litherite_crystal_from_block", "withering_litherite_crystal"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get(), RecipeCategory.MISC, ModStorageBlocks.CHARGED_LITHERITE_BLOCK.get(), "charged_litherite_crystal_from_block", "charged_litherite_crystal"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.ODYSIUM_INGOT.get(), RecipeCategory.MISC, ModStorageBlocks.ODYSIUM_BLOCK.get(), "odysium_ingot_from_block", "odysium_ingot"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.PHANTOM_DIAMOND.get(), RecipeCategory.MISC, ModPhantomBlocks.PHANTOM_DIAMOND_BLOCK.get(), "phantom_diamond_from_block", "phantom_diamond"
        );
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get(), RecipeCategory.MISC, ModBlocks.ETHEREAL_CRYSTAL_BLOCK.get(), "impure_ethereal_crystal_shard_from_block", "impure_ethereal_crystal_shard"
        );
        nineBlockStorageRecipesWithCustomPackingFromBaseModNamespace(
                RecipeCategory.MISC, ModRawMaterialItems.NETHERITE_NUGGET.get(), RecipeCategory.MISC, Items.NETHERITE_INGOT, "netherite_ingot_from_nuggets", "netherite_ingot"
        );
        twoByTwoPacker(
                RecipeCategory.MISC, ModBlocks.LITHERITE_CRYSTAL_BLOCK.get(), ModRawMaterialItems.LITHERITE_CRYSTAL.get()
        );
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), 1)
                .requires(ModItems.MOLTEN_LITHERITE_BUCKET.get())
                .requires(Items.WATER_BUCKET)
                .group("litherite_crystal")
                .unlockedBy("has_molten_litherite_bucket", has(ModItems.MOLTEN_LITHERITE_BUCKET.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "litherite_crystal_from_cooling_molten_litherite_bucket"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, Items.BLUE_DYE, 1)
                .requires(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get())
                .group("blue_dye")
                .unlockedBy("has_phantom_rose_ethereal_core", has(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "blue_dye_from_phantom_rose_ethereal_core"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, Items.LIGHT_BLUE_DYE, 1)
                .requires(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get())
                .group("light_blue_dye")
                .unlockedBy("has_phantom_ice_flower", has(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "light_blue_dye_from_phantom_ice_flower"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, Items.RED_DYE, 1)
                .requires(ModPhantomBlocks.PHANTOM_ROSE.get())
                .group("red_dye")
                .unlockedBy("has_phantom_rose", has(ModPhantomBlocks.PHANTOM_ROSE.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "red_dye_from_phantom_rose"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModRawMaterialItems.ODYSIUM_INGOT.get(), 2)
                .requires(ModRawMaterialItems.ALLIAN_INGOT.get())
                .requires(ModRawMaterialItems.NERITH_INGOT.get())
                .requires(ModRawMaterialItems.COPALITE_INGOT.get())
                .requires(ModRawMaterialItems.AURELITE_INGOT.get())
                .requires(ModRawMaterialItems.UNIFIER.get())
                .requires(ModRawMaterialItems.ELCRUM_INGOT.get())
                .requires(ModRawMaterialItems.SOLIUMITE_INGOT.get())
                .requires(ModRawMaterialItems.CHRYON_CRYSTAL.get())
                .requires(ModRawMaterialItems.SATURNITE_CRYSTAL.get())
                .group("odysium_ingot")
                .unlockedBy("has_unifier", has(ModRawMaterialItems.UNIFIER.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "odysium_ingot"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.BUILDING_BLOCKS, ModTreeBlocks.RED_MALISHROOM_BLOCK.get(), 3)
                .requires(ModTreeBlocks.MALISHROOM_BLOCK.get(), 3)
                .requires(Items.RED_DYE)
                .group("red_malishroom_block")
                .unlockedBy("has_malishroom_block", has(ModTreeBlocks.RED_MALISHROOM_BLOCK.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "red_malishroom_block"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.BUILDING_BLOCKS, ModTreeBlocks.MALISHROOM_BLOCK.get())
                .requires(ModTreeBlocks.RED_MALISHROOM_BLOCK.get())
                .group("malishroom_block")
                .unlockedBy("has_red_malishroom_block", has(ModTreeBlocks.RED_MALISHROOM_BLOCK.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "malishroom_block_from_red_malishroom_block"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), 4)
                .requires(ModBlocks.LITHERITE_CRYSTAL_BLOCK.get())
                .group("litherite_crystal")
                .unlockedBy("has_litherite_crystal_block", has(ModBlocks.LITHERITE_CRYSTAL_BLOCK.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "litherite_crystal_from_natural_crystal_block"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModRawMaterialItems.NETHERITE_NUGGET.get(), 1)
                .requires(ModRawMaterialItems.NETHERITE_FRAGMENT.get(), 4)
                .requires(Items.GOLD_NUGGET, 4)
                .group("netherite_nugget")
                .unlockedBy("has_netherite_fragment", has(ModRawMaterialItems.NETHERITE_FRAGMENT.get()))
                .save(this.output, Lithereal.key(Registries.RECIPE, "netherite_nugget_from_fragments"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_ETHEREAL_DIRT.get(), 4)
                .define('D', ModBlocks.ETHEREAL_DIRT.get())
                .define('G', ModBlocks.PHANTOM_GRAVEL.get())
                .pattern("DG")
                .pattern("GD")
                .unlockedBy("has_phantom_gravel", has(ModBlocks.PHANTOM_GRAVEL.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE.get(), 4)
                .define('C', ModRawMaterialItems.CYRUM_CRYSTAL.get())
                .define('E', ModStoneBlocks.ETHERSTONE.get())
                .pattern("EC")
                .pattern("CE")
                .unlockedBy("has_etherstone", has(ModStoneBlocks.ETHERSTONE.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.LUMINITE.get(), 4)
                .define('Q', Items.QUARTZ)
                .define('P', ModStoneBlocks.PAILITE.get())
                .pattern("PQ")
                .pattern("QP")
                .unlockedBy("has_pailite", has(ModStoneBlocks.PAILITE.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.VERDONE.get(), 4)
                .define('C', ModRawMaterialItems.COPALITE_INGOT.get())
                .define('P', ModStoneBlocks.PAILITE.get())
                .pattern("PC")
                .pattern("CP")
                .unlockedBy("has_pailite", has(ModStoneBlocks.PAILITE.get()))
                .save(this.output);
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        public RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
            return new ModRecipeProvider(lookupProvider, output);
        }

        @Override
        public String getName() {
            return "Lithereal recipes";
        }
    }
}
//?}