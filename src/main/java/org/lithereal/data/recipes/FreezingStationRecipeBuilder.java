package org.lithereal.data.recipes;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class FreezingStationRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private final Ingredient primary;
    private int recipeTime = 200;

    private FreezingStationRecipeBuilder(RecipeCategory category, Ingredient primary, ItemStackTemplate result) {
        this.category = category;
        this.primary = primary;
        this.result = result;
    }

    public static FreezingStationRecipeBuilder freezing(RecipeCategory category, ItemLike primary, ItemLike result) {
        return freezing(category, Ingredient.of(primary), new ItemStackTemplate(result.asItem()));
    }

    public static FreezingStationRecipeBuilder freezing(RecipeCategory category, Ingredient primary, ItemStackTemplate result) {
        return new FreezingStationRecipeBuilder(category, primary, result);
    }

    public FreezingStationRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public FreezingStationRecipeBuilder group(@Nullable String group) {
        return this;
    }

    public FreezingStationRecipeBuilder recipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
        return this;
    }

    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        output.accept(id, new FreezingStationRecipe(this.result, this.primary, this.recipeTime), this.advancementBuilder.build(output, id, this.category));
    }
}
