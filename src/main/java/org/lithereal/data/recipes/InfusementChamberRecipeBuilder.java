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

public class InfusementChamberRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private final Ingredient primary;
    private final Ingredient secondary;
    private int recipeTime = 6000;

    private InfusementChamberRecipeBuilder(RecipeCategory category, Ingredient primary, Ingredient secondary, ItemStackTemplate result) {
        this.category = category;
        this.primary = primary;
        this.secondary = secondary;
        this.result = result;
    }

    public static InfusementChamberRecipeBuilder infusing(RecipeCategory category, ItemLike primary, ItemLike secondary, ItemLike result) {
        return infusing(category, Ingredient.of(primary), Ingredient.of(secondary), new ItemStackTemplate(result.asItem()));
    }

    public static InfusementChamberRecipeBuilder infusing(RecipeCategory category, Ingredient primary, Ingredient secondary, ItemStackTemplate result) {
        return new InfusementChamberRecipeBuilder(category, primary, secondary, result);
    }

    public InfusementChamberRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public InfusementChamberRecipeBuilder group(@Nullable String group) {
        return this;
    }

    public InfusementChamberRecipeBuilder recipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
        return this;
    }

    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        output.accept(id, new InfusementChamberRecipe(this.result, this.primary, this.secondary, this.recipeTime), this.advancementBuilder.build(output, id, this.category));
    }
}
