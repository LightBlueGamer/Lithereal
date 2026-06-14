package org.lithereal.data.recipes;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class FireCrucibleRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private final Ingredient primary;
    private final @Nullable Ingredient secondary;
    private int cookingTime = 200;

    private FireCrucibleRecipeBuilder(RecipeCategory category, Ingredient primary, @Nullable Ingredient secondary, ItemStackTemplate result) {
        this.category = category;
        this.primary = primary;
        this.secondary = secondary;
        this.result = result;
    }

    public static FireCrucibleRecipeBuilder noSecondary(RecipeCategory category, ItemLike primary, ItemLike result) {
        return noSecondary(category, Ingredient.of(primary), new ItemStackTemplate(result.asItem()));
    }

    public static FireCrucibleRecipeBuilder noSecondary(RecipeCategory category, Ingredient primary, ItemStackTemplate result) {
        return new FireCrucibleRecipeBuilder(category, primary, null, result);
    }

    public static FireCrucibleRecipeBuilder fullRecipe(RecipeCategory category, ItemLike primary, @NonNull ItemLike secondary, ItemLike result) {
        return fullRecipe(category, Ingredient.of(primary), Ingredient.of(secondary), new ItemStackTemplate(result.asItem()));
    }

    public static FireCrucibleRecipeBuilder fullRecipe(RecipeCategory category, Ingredient primary, @NonNull Ingredient secondary, ItemStackTemplate result) {
        return new FireCrucibleRecipeBuilder(category, primary, secondary, result);
    }

    public FireCrucibleRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public FireCrucibleRecipeBuilder group(@Nullable String group) {
        return this;
    }

    public FireCrucibleRecipeBuilder cookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        output.accept(id, new FireCrucibleRecipe(this.result, this.primary, Optional.ofNullable(this.secondary), this.cookingTime), this.advancementBuilder.build(output, id, this.category));
    }
}
