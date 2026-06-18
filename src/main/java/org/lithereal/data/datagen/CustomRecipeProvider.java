package org.lithereal.data.datagen;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;

public interface CustomRecipeProvider<S extends RecipeProvider> {
    S self();
    void nineBlockStorageRecipesFromBaseModNamespace(RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed);

    void nineBlockStorageRecipesWithCustomPackingFromBaseModNamespace(
            RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String forPacking, String packingGroup
    );

    void nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(
            RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String forUnpacking, String unpackingGroup
    );

    void nineBlockStorageRecipesFromBaseModNamespace(
            RecipeCategory unpackCategory,
            ItemLike unpacked,
            RecipeCategory packCategory,
            ItemLike packed,
            String forPacking,
            @Nullable String packingGroup,
            String forUnpacking,
            @Nullable String unpackingGroup
    );

    default void oreSmeltingFromBaseModNamespace(List<ItemLike> itemsToSmelt, RecipeCategory recipeCategory, CookingBookCategory cookingBookCategory, ItemLike result, float experience, int cookingTime, String group) {
        oreCookingFromBaseModNamespace(SmeltingRecipe::new, itemsToSmelt, recipeCategory, cookingBookCategory, result, experience, cookingTime, group, "_from_smelting");
    }

    default void oreBlastingFromBaseModNamespace(List<ItemLike> itemsToSmelt, RecipeCategory recipeCategory, CookingBookCategory cookingBookCategory, ItemLike result, float experience, int cookingTime, String group) {
        oreCookingFromBaseModNamespace(BlastingRecipe::new, itemsToSmelt, recipeCategory, cookingBookCategory, result, experience, cookingTime, group, "_from_blasting");
    }

    <T extends AbstractCookingRecipe> void oreCookingFromBaseModNamespace(
            AbstractCookingRecipe.Factory<T> factory,
            List<ItemLike> itemsToSmelt,
            RecipeCategory recipeCategory,
            CookingBookCategory cookingBookCategory,
            ItemLike result,
            float experience,
            int cookingTime,
            String group,
            String method
    );

    default void oreDual(List<ItemLike> itemsToSmelt, RecipeCategory recipeCategory, CookingBookCategory cookingBookCategory, ItemLike result, float experience, int cookingTime, String group) {
        oreSmeltingFromBaseModNamespace(itemsToSmelt, recipeCategory, cookingBookCategory, result, experience, cookingTime, group);
        oreBlastingFromBaseModNamespace(itemsToSmelt, recipeCategory, cookingBookCategory, result, experience, cookingTime / 2, group);
    }

    default void stonecutterResultFromBaseModNamespace(RecipeCategory category, ItemLike to, ItemLike from) {
        stonecutterResultFromBaseModNamespace(category, to, from, 1);
    }

    void stonecutterResultFromBaseModNamespace(RecipeCategory category, ItemLike to, ItemLike from, int count);

    void improvedThermalItem(RecipeCategory recipeCategory, Item base, Item improved);

    void swordItem(Item rod, Item baseMaterial, Item sword);

    void spearItem(Item rod, Item baseMaterial, Item spear);

    void pickaxeItem(Item rod, Item baseMaterial, Item pickaxe);

    void axeItem(Item rod, Item baseMaterial, Item axe);

    void shovelItem(Item rod, Item baseMaterial, Item shovel);

    void hoeItem(Item rod, Item baseMaterial, Item hoe);

    void hammerItem(Item rod, Item baseMaterial, Item hammer);

    void warHammerItem(Item rod, Item baseMaterial, Item warHammer);

    void helmetItem(Item baseMaterial, Item helmet);

    void chestplateItem(Item baseMaterial, Item chestplate);

    void leggingsItem(Item baseMaterial, Item leggings);

    void bootsItem(Item baseMaterial, Item boots);

    void bowItem(Item baseMaterial, Item string, Item bow);

    void bowItem(Item baseMaterial, Item stringAttached, Item string, Item bow);

    void brushItem(Item baseMaterial, Item brush);

    void wrenchItem(Item baseMaterialA, Item baseMaterialB, Item wrench);

    void upgradeRecipe(boolean tool, Item template, Item base, Item addition, Item result);
}
