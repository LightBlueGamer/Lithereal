package org.lithereal.neoforge.datagen;

//? neoforge {
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.lithereal.Lithereal;
import org.lithereal.block.*;
import org.lithereal.item.ModArmorItems;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.item.ModToolItems;
import org.lithereal.tags.ModTags;
import org.lithereal.util.ModBlockFamilies;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }


    protected void nineBlockStorageRecipesFromBaseModNamespace(RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed) {
        nineBlockStorageRecipesFromBaseModNamespace(unpackCategory, unpacked, packCategory, packed, getSimpleRecipeName(packed), null, getSimpleRecipeName(unpacked), null);
    }

    protected void nineBlockStorageRecipesWithCustomPackingFromBaseModNamespace(
            RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String forPacking, String packingGroup
    ) {
        nineBlockStorageRecipesFromBaseModNamespace(unpackCategory, unpacked, packCategory, packed, forPacking, packingGroup, getSimpleRecipeName(unpacked), null);
    }

    protected void nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
            RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String forUnpacking, String unpackingGroup
    ) {
        nineBlockStorageRecipesFromBaseModNamespace(unpackCategory, unpacked, packCategory, packed, getSimpleRecipeName(packed), null, forUnpacking, unpackingGroup);
    }

    protected void nineBlockStorageRecipesFromBaseModNamespace(
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
                .save(this.output, Lithereal.id(forUnpacking).toString());
        ShapedRecipeBuilder.shaped(this.items, packCategory, packed)
                .define('#', unpacked)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(packingGroup)
                .unlockedBy(getHasName(unpacked), has(unpacked))
                .save(this.output, Lithereal.id(forPacking).toString());
    }

    protected void oreSmeltingFromBaseModNamespace(List<ItemLike> itemsToSmelt, RecipeCategory recipeCategory, CookingBookCategory cookingBookCategory, ItemLike result, float experience, int cookingTime, String group) {
        oreCookingFromBaseModNamespace(SmeltingRecipe::new, itemsToSmelt, recipeCategory, cookingBookCategory, new ItemStack(result), experience, cookingTime, group, "_from_smelting");
    }

    protected void oreBlastingFromBaseModNamespace(List<ItemLike> itemsToSmelt, RecipeCategory recipeCategory, CookingBookCategory cookingBookCategory, ItemLike result, float experience, int cookingTime, String group) {
        oreCookingFromBaseModNamespace(BlastingRecipe::new, itemsToSmelt, recipeCategory, cookingBookCategory, new ItemStack(result), experience, cookingTime, group, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCookingFromBaseModNamespace(
            AbstractCookingRecipe.Factory<T> factory,
            List<ItemLike> itemsToSmelt,
            RecipeCategory recipeCategory,
            CookingBookCategory cookingBookCategory,
            ItemStack result,
            float experience,
            int cookingTime,
            String group,
            String method
    ) {
        for (ItemLike itemlike : itemsToSmelt) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), recipeCategory, cookingBookCategory, result, experience, cookingTime, factory)
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(this.output, "lithereal:" + getItemName(result.getItem()) + method + "_" + getItemName(itemlike));
        }
    }

    public void oreDual(List<ItemLike> itemsToSmelt, RecipeCategory recipeCategory, CookingBookCategory cookingBookCategory, ItemLike result, float experience, int cookingTime, String group) {
        oreSmeltingFromBaseModNamespace(itemsToSmelt, recipeCategory, cookingBookCategory, result, experience, cookingTime, group);
        oreBlastingFromBaseModNamespace(itemsToSmelt, recipeCategory, cookingBookCategory, result, experience, cookingTime / 2, group);
    }

    protected void stonecutterResultFromBaseModNamespace(RecipeCategory category, ItemLike to, ItemLike from) {
        stonecutterResultFromBaseModNamespace(category, to, from, 1);
    }

    protected void stonecutterResultFromBaseModNamespace(RecipeCategory category, ItemLike to, ItemLike from, int count) {
        SingleItemRecipeBuilder recipeBuilder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), category, to, count).unlockedBy(getHasName(from), has(from));
        String convertName = getConversionRecipeName(to, from);
        recipeBuilder.save(this.output, "lithereal:" + convertName + "_stonecutting");
    }

    protected void improvedThermalItem(RecipeCategory recipeCategory, Item base, Item improved) {
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

    protected void swordItem(Item rod, Item baseMaterial, Item sword) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, sword, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("B")
                .pattern("B")
                .pattern("R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void pickaxeItem(Item rod, Item baseMaterial, Item pickaxe) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, pickaxe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BBB")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void axeItem(Item rod, Item baseMaterial, Item axe) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, axe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BB")
                .pattern("BR")
                .pattern(" R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void shovelItem(Item rod, Item baseMaterial, Item shovel) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, shovel, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("B")
                .pattern("R")
                .pattern("R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void hoeItem(Item rod, Item baseMaterial, Item hoe) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, hoe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BB")
                .pattern(" R")
                .pattern(" R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void hammerItem(Item rod, Item baseMaterial, Item hammer) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, hammer, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BBB")
                .pattern("BBB")
                .pattern(" R ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void helmetItem(Item baseMaterial, Item helmet) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, helmet, 1)
                .define('B', baseMaterial)
                .pattern("BBB")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void chestplateItem(Item baseMaterial, Item chestplate) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, chestplate, 1)
                .define('B', baseMaterial)
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void leggingsItem(Item baseMaterial, Item leggings) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, leggings, 1)
                .define('B', baseMaterial)
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void bootsItem(Item baseMaterial, Item boots) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, boots, 1)
                .define('B', baseMaterial)
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(this.output);
    }

    protected void upgradeRecipe(boolean tool, Item template, Item base, Item addition, Item result) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(base), Ingredient.of(addition), tool ? RecipeCategory.TOOLS : RecipeCategory.COMBAT, result)
                .unlocks("has_" + getSimpleRecipeName(base), has(base))
                .save(this.output, "lithereal:" + getItemName(result) + "_smithing");
    }

    @Override
    protected void buildRecipes() {
        ModBlockFamilies.MOD_BLOCK_FAMILIES.forEach(blockFamily -> generateRecipes(blockFamily, FeatureFlagSet.of()));
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
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, ModToolItems.ODYSIUM_BOW.get(), 1)
                .define('O', ModRawMaterialItems.ODYSIUM_INGOT.get())
                .define('I', Items.IRON_NUGGET)
                .define('S', Items.STRING)
                .pattern(" OI")
                .pattern("O S")
                .pattern(" OI")
                .unlockedBy("has_odysium", has(ModRawMaterialItems.ODYSIUM_INGOT.get()))
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
        improvedThermalItem(RecipeCategory.COMBAT, ModToolItems.BURNING_LITHERITE_SWORD.get(), ModToolItems.SMOLDERING_LITHERITE_SWORD.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_PICKAXE.get(), ModToolItems.SMOLDERING_LITHERITE_PICKAXE.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_AXE.get(), ModToolItems.SMOLDERING_LITHERITE_AXE.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_SHOVEL.get(), ModToolItems.SMOLDERING_LITHERITE_SHOVEL.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_HOE.get(), ModToolItems.SMOLDERING_LITHERITE_HOE.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_HAMMER.get(), ModToolItems.SMOLDERING_LITHERITE_HAMMER.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_HELMET.get(), ModArmorItems.SMOLDERING_LITHERITE_HELMET.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get(), ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_LEGGINGS.get(), ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_BOOTS.get(), ModArmorItems.SMOLDERING_LITHERITE_BOOTS.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModToolItems.FROZEN_LITHERITE_SWORD.get(), ModToolItems.FROSTBITTEN_LITHERITE_SWORD.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_PICKAXE.get(), ModToolItems.FROSTBITTEN_LITHERITE_PICKAXE.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_AXE.get(), ModToolItems.FROSTBITTEN_LITHERITE_AXE.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_SHOVEL.get(), ModToolItems.FROSTBITTEN_LITHERITE_SHOVEL.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_HOE.get(), ModToolItems.FROSTBITTEN_LITHERITE_HOE.get());
        improvedThermalItem(RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_HAMMER.get(), ModToolItems.FROSTBITTEN_LITHERITE_HAMMER.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_HELMET.get(), ModArmorItems.FROSTBITTEN_LITHERITE_HELMET.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get(), ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get(), ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS.get());
        improvedThermalItem(RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_BOOTS.get(), ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS.get());
        swordItem(Items.STICK, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_SWORD.get());
        swordItem(Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_SWORD.get());
        swordItem(Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_SWORD.get());
        swordItem(Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_SWORD.get());
        swordItem(Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_SWORD.get());
        upgradeRecipe(false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_SWORD, ModToolItems.ODYSIUM_SWORD.get());
        pickaxeItem(Items.STICK, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_PICKAXE.get());
        pickaxeItem(Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_PICKAXE.get());
        pickaxeItem(Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_PICKAXE.get());
        pickaxeItem(Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_PICKAXE.get());
        pickaxeItem(Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_PICKAXE.get());
        upgradeRecipe(true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_PICKAXE, ModToolItems.ODYSIUM_PICKAXE.get());
        axeItem(Items.STICK, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_AXE.get());
        axeItem(Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_AXE.get());
        axeItem(Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_AXE.get());
        axeItem(Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_AXE.get());
        axeItem(Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_AXE.get());
        upgradeRecipe(true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_AXE, ModToolItems.ODYSIUM_AXE.get());
        shovelItem(Items.STICK, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_SHOVEL.get());
        shovelItem(Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_SHOVEL.get());
        shovelItem(Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_SHOVEL.get());
        shovelItem(Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_SHOVEL.get());
        shovelItem(Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_SHOVEL.get());
        upgradeRecipe(true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_SHOVEL, ModToolItems.ODYSIUM_SHOVEL.get());
        hoeItem(Items.STICK, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_HOE.get());
        hoeItem(Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_HOE.get());
        hoeItem(Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_HOE.get());
        hoeItem(Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_HOE.get());
        hoeItem(Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_HOE.get());
        upgradeRecipe(true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_HOE, ModToolItems.ODYSIUM_HOE.get());
        hammerItem(Items.STICK, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_HAMMER.get());
        hammerItem(Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_HAMMER.get());
        hammerItem(Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_HAMMER.get());
        hammerItem(Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_HAMMER.get());
        hammerItem(Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_HAMMER.get());
        hammerItem(Items.STICK, ModRawMaterialItems.ODYSIUM_INGOT.get(), ModToolItems.ODYSIUM_HAMMER.get());
        helmetItem(ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_HELMET.get());
        helmetItem(ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_HELMET.get());
        helmetItem(ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_HELMET.get());
        helmetItem(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_HELMET.get());
        helmetItem(ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_HELMET.get());
        upgradeRecipe(false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_HELMET, ModArmorItems.ODYSIUM_HELMET.get());
        chestplateItem(ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_CHESTPLATE.get());
        chestplateItem(ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get());
        chestplateItem(ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get());
        chestplateItem(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get());
        chestplateItem(ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_CHESTPLATE.get());
        upgradeRecipe(false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_CHESTPLATE, ModArmorItems.ODYSIUM_CHESTPLATE.get());
        leggingsItem(ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_LEGGINGS.get());
        leggingsItem(ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_LEGGINGS.get());
        leggingsItem(ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get());
        leggingsItem(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get());
        leggingsItem(ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_LEGGINGS.get());
        upgradeRecipe(false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_LEGGINGS, ModArmorItems.ODYSIUM_LEGGINGS.get());
        bootsItem(ModRawMaterialItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_BOOTS.get());
        bootsItem(ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_BOOTS.get());
        bootsItem(ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_BOOTS.get());
        bootsItem(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_BOOTS.get());
        bootsItem(ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_BOOTS.get());
        upgradeRecipe(false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_BOOTS, ModArmorItems.ODYSIUM_BOOTS.get());

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

        planksFromLog(ModTreeBlocks.FORTSHROOM_PLANKS.get(), ModTags.FORTSHROOM_STEMS, 4);
        woodFromLogs(ModTreeBlocks.FORTSHROOM_HYPHAE.get(), ModTreeBlocks.FORTSHROOM_STEM.get());
        woodFromLogs(ModTreeBlocks.STRIPPED_FORTSHROOM_HYPHAE.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        woodenBoat(ModItems.FORTSHROOM_BOAT.get(), ModTreeBlocks.FORTSHROOM_PLANKS.get());
        chestBoat(ModItems.FORTSHROOM_CHEST_BOAT.get(), ModItems.FORTSHROOM_BOAT.get());
        hangingSign(ModItems.FORTSHROOM_HANGING_SIGN.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
                RecipeCategory.MISC, ModVegetationBlocks.FORTSHROOM.get(), RecipeCategory.MISC, ModTreeBlocks.FORTSHROOM_BLOCK.get(), "fortshroom_from_block", "fortshroom"
        );

        planksFromLog(ModTreeBlocks.MALISHROOM_PLANKS.get(), ModTags.MALISHROOM_STEMS, 4);
        woodFromLogs(ModTreeBlocks.MALISHROOM_HYPHAE.get(), ModTreeBlocks.MALISHROOM_STEM.get());
        woodFromLogs(ModTreeBlocks.STRIPPED_MALISHROOM_HYPHAE.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
        woodenBoat(ModItems.MALISHROOM_BOAT.get(), ModTreeBlocks.MALISHROOM_PLANKS.get());
        chestBoat(ModItems.MALISHROOM_CHEST_BOAT.get(), ModItems.MALISHROOM_BOAT.get());
        hangingSign(ModItems.MALISHROOM_HANGING_SIGN.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
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
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModTreeBlocks.RED_MALISHROOM_BLOCK.get(), 3)
                .requires(ModTreeBlocks.MALISHROOM_BLOCK.get(), 3)
                .requires(Items.RED_DYE)
                .group("red_malishroom_block")
                .unlockedBy("has_malishroom_block", has(ModTreeBlocks.RED_MALISHROOM_BLOCK.get()))
                .save(this.output, Lithereal.id("red_malishroom_block").toString());
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModTreeBlocks.MALISHROOM_BLOCK.get())
                .requires(ModTreeBlocks.RED_MALISHROOM_BLOCK.get())
                .group("malishroom_block")
                .unlockedBy("has_red_malishroom_block", has(ModTreeBlocks.RED_MALISHROOM_BLOCK.get()))
                .save(this.output, Lithereal.id("malishroom_block_from_red_malishroom_block").toString());
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModRawMaterialItems.LITHERITE_CRYSTAL.get(), 4)
                .requires(ModBlocks.LITHERITE_CRYSTAL_BLOCK.get())
                .group("litherite_crystal")
                .unlockedBy("has_litherite_crystal_block", has(ModBlocks.LITHERITE_CRYSTAL_BLOCK.get()))
                .save(this.output, Lithereal.id("litherite_crystal_from_natural_crystal_block").toString());
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModRawMaterialItems.NETHERITE_NUGGET.get(), 1)
                .requires(ModRawMaterialItems.NETHERITE_FRAGMENT.get(), 4)
                .requires(Items.GOLD_NUGGET, 4)
                .group("netherite_nugget")
                .unlockedBy("has_netherite_fragment", has(ModRawMaterialItems.NETHERITE_FRAGMENT.get()))
                .save(this.output, Lithereal.id("netherite_nugget_from_fragments").toString());

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
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_SLAB.get(), ModStoneBlocks.ETHERSTONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_STAIRS.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.ETHERSTONE_WALL.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.CHISELED_ETHERSTONE.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_ETHERSTONE.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_ETHERSTONE_SLAB.get(), ModStoneBlocks.ETHERSTONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_ETHERSTONE_STAIRS.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_ETHERSTONE_WALL.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICKS.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get(), ModStoneBlocks.ETHERSTONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.ETHERSTONE_BRICK_WALL.get(), ModStoneBlocks.ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_ETHERSTONE_SLAB.get(), ModStoneBlocks.POLISHED_ETHERSTONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_ETHERSTONE_STAIRS.get(), ModStoneBlocks.POLISHED_ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_ETHERSTONE_WALL.get(), ModStoneBlocks.POLISHED_ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICKS.get(), ModStoneBlocks.POLISHED_ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get(), ModStoneBlocks.POLISHED_ETHERSTONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get(), ModStoneBlocks.POLISHED_ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.ETHERSTONE_BRICK_WALL.get(), ModStoneBlocks.POLISHED_ETHERSTONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get(), ModStoneBlocks.ETHERSTONE_BRICKS.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get(), ModStoneBlocks.ETHERSTONE_BRICKS.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.ETHERSTONE_BRICK_WALL.get(), ModStoneBlocks.ETHERSTONE_BRICKS.get());

        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE_SLAB.get(), ModStoneBlocks.PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE_STAIRS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.PAILITE_WALL.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), ModStoneBlocks.PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_WALL.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICKS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get(), ModStoneBlocks.PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), ModStoneBlocks.POLISHED_PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_WALL.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICKS.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get(), ModStoneBlocks.POLISHED_PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get(), ModStoneBlocks.POLISHED_PAILITE_BRICKS.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get(), ModStoneBlocks.POLISHED_PAILITE_BRICKS.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get(), ModStoneBlocks.POLISHED_PAILITE_BRICKS.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), ModStoneBlocks.POLISHED_PAILITE_BRICKS.get());

        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.LUMINITE_SLAB.get(), ModStoneBlocks.LUMINITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.LUMINITE_STAIRS.get(), ModStoneBlocks.LUMINITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.LUMINITE_WALL.get(), ModStoneBlocks.LUMINITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_LUMINITE.get(), ModStoneBlocks.LUMINITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_LUMINITE_SLAB.get(), ModStoneBlocks.LUMINITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_LUMINITE_STAIRS.get(), ModStoneBlocks.LUMINITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_LUMINITE_WALL.get(), ModStoneBlocks.LUMINITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_LUMINITE_SLAB.get(), ModStoneBlocks.POLISHED_LUMINITE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_LUMINITE_STAIRS.get(), ModStoneBlocks.POLISHED_LUMINITE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_LUMINITE_WALL.get(), ModStoneBlocks.POLISHED_LUMINITE.get());

        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.VERDONE_SLAB.get(), ModStoneBlocks.VERDONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.VERDONE_STAIRS.get(), ModStoneBlocks.VERDONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.VERDONE_WALL.get(), ModStoneBlocks.VERDONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_VERDONE.get(), ModStoneBlocks.VERDONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_VERDONE_SLAB.get(), ModStoneBlocks.VERDONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_VERDONE_STAIRS.get(), ModStoneBlocks.VERDONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_VERDONE_WALL.get(), ModStoneBlocks.VERDONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_VERDONE_SLAB.get(), ModStoneBlocks.POLISHED_VERDONE.get(), 2);
        stonecutterResultFromBaseModNamespace(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_VERDONE_STAIRS.get(), ModStoneBlocks.POLISHED_VERDONE.get());
        stonecutterResultFromBaseModNamespace(RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_VERDONE_WALL.get(), ModStoneBlocks.POLISHED_VERDONE.get());
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
            return new ModRecipeProvider(lookupProvider, output);
        }

        @Override
        public String getName() {
            return "Lithereal recipes";
        }
    }
}
//?}