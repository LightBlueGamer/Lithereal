package org.litecraft.lithereal.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.recipe.FireCrucibleRecipe;
import org.litecraft.lithereal.recipe.FreezingStationRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEILitherealPlugin implements IModPlugin {
    public static RecipeType<FreezingStationRecipe> FREEZING_TYPE =
            new RecipeType<>(FreezingStationRecipeCategory.UID, FreezingStationRecipe.class);

    public static RecipeType<FireCrucibleRecipe> BURNING_TYPE =
            new RecipeType<>(FireCrucibleRecipeCategory.UID, FireCrucibleRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Lithereal.MOD_ID, "jei_compat");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new FireCrucibleRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new FreezingStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<FreezingStationRecipe> recipesInfusing = rm.getAllRecipesFor(FreezingStationRecipe.Type.INSTANCE);
        List<FireCrucibleRecipe> recipesBurning = rm.getAllRecipesFor(FireCrucibleRecipe.Type.INSTANCE);
        List<SmeltingRecipe> recipesFurnace = rm.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING);

        registration.addRecipes(FREEZING_TYPE, recipesInfusing);
        registration.addRecipes(BURNING_TYPE, recipesBurning);
    }
}
