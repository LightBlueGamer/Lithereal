package org.litecraft.lithereal.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;
import org.litecraft.lithereal.recipe.FireCrucibleRecipe;
import org.litecraft.lithereal.recipe.FreezingStationRecipe;
import org.litecraft.lithereal.recipe.InfusementChamberRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEILitherealPlugin implements IModPlugin {
    public static RecipeType<FreezingStationRecipe> FREEZING_TYPE =
            new RecipeType<>(FreezingStationRecipeCategory.UID, FreezingStationRecipe.class);

    public static RecipeType<FireCrucibleRecipe> BURNING_TYPE =
            new RecipeType<>(FireCrucibleRecipeCategory.UID, FireCrucibleRecipe.class);

    public static RecipeType<InfusementChamberRecipe> INFUSING_TYPE =
            new RecipeType<>(InfusementChamberRecipeCategory.UID, InfusementChamberRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Lithereal.MOD_ID, "jei_compat");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new FireCrucibleRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new InfusementChamberRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new FreezingStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<FreezingStationRecipe> recipesInfusing = rm.getAllRecipesFor(FreezingStationRecipe.Type.INSTANCE);
        List<FireCrucibleRecipe> recipesBurning = rm.getAllRecipesFor(FireCrucibleRecipe.Type.INSTANCE);
        List<InfusementChamberRecipe> recipesInfChamber = rm.getAllRecipesFor(InfusementChamberRecipe.Type.INSTANCE);
        List<SmeltingRecipe> recipesFurnace = rm.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING);

        registration.addRecipes(FREEZING_TYPE, recipesInfusing);
        registration.addRecipes(BURNING_TYPE, recipesBurning);
        registration.addRecipes(INFUSING_TYPE, recipesInfChamber);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(
                ModBlocks.INFUSED_LITHERITE_BLOCK.get().asItem(),
                ModItems.INFUSED_LITHERITE_CRYSTAL.get(),
                ModItems.INFUSED_LITHERITE_SWORD.get(),
                ModItems.INFUSED_LITHERITE_SHOVEL.get(),
                ModItems.INFUSED_LITHERITE_PICKAXE.get(),
                ModItems.INFUSED_LITHERITE_AXE.get(),
                ModItems.INFUSED_LITHERITE_HOE.get(),
                ModItems.INFUSED_LITHERITE_HAMMER.get(),
                ModItems.INFUSED_LITHERITE_HELMET.get(),
                ModItems.INFUSED_LITHERITE_CHESTPLATE.get(),
                ModItems.INFUSED_LITHERITE_LEGGINGS.get(),
                ModItems.HEATED_LITHERITE_BOOTS.get());
        IModPlugin.super.registerItemSubtypes(registration);
    }
}
