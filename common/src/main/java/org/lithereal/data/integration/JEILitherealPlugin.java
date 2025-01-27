package org.lithereal.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.data.integration.FireCrucibleRecipeCategory;
import org.lithereal.data.integration.FreezingStationRecipeCategory;
import org.lithereal.data.integration.InfusementChamberRecipeCategory;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.item.ModItems;
import org.lithereal.data.recipes.FireCrucibleRecipe;
import org.lithereal.data.recipes.FreezingStationRecipe;
import org.lithereal.data.recipes.InfusementChamberRecipe;
import org.lithereal.client.gui.screens.inventory.FireCrucibleScreen;
import org.lithereal.client.gui.screens.inventory.FreezingStationScreen;
import org.lithereal.client.gui.screens.inventory.InfusementChamberScreen;

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
        return ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "jei_compat");
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

        List<FreezingStationRecipe> recipesInfusing = rm.getAllRecipesFor(ModRecipes.FREEZING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();
        List<FireCrucibleRecipe> recipesBurning = rm.getAllRecipesFor(ModRecipes.BURNING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();
        List<InfusementChamberRecipe> recipesInfChamber = rm.getAllRecipesFor(ModRecipes.INFUSING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();
        List<SmeltingRecipe> recipesFurnace = rm.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING)
                .stream()
                .map(RecipeHolder::value)
                .toList();

        registration.addRecipes(FREEZING_TYPE, recipesInfusing);
        registration.addRecipes(BURNING_TYPE, recipesBurning);
        registration.addRecipes(INFUSING_TYPE, recipesInfChamber);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        IIngredientSubtypeInterpreter<ItemStack> nbtInterpreter = (itemStack, uidContext) -> {
            if (itemStack.has(DataComponents.POTION_CONTENTS)) {
                PotionContents potionContents = itemStack.get(DataComponents.POTION_CONTENTS);

                if (potionContents != null && potionContents.hasEffects()) {
                    return potionContents.getAllEffects().toString();
                }
            }
            return IIngredientSubtypeInterpreter.NONE;
        };

        // Register the interpreter for all the items
        registration.registerSubtypeInterpreter(LitherealExpectPlatform.getInfusedLitheriteBlock().asItem(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_INGOT.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_SWORD.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_SHOVEL.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_PICKAXE.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_AXE.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_HOE.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_HAMMER.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_HELMET.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_LEGGINGS.get(), nbtInterpreter);
        registration.registerSubtypeInterpreter(ModItems.INFUSED_LITHERITE_BOOTS.get(), nbtInterpreter);

        // Call the superclass method to ensure compatibility
        IModPlugin.super.registerItemSubtypes(registration);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(InfusementChamberScreen.class, 96, 34, 21, 15, INFUSING_TYPE);
        registration.addRecipeClickArea(FreezingStationScreen.class, 96, 34, 21, 15, FREEZING_TYPE);
        registration.addRecipeClickArea(FireCrucibleScreen.class, 81, 38, 13, 13, BURNING_TYPE);
        IModPlugin.super.registerGuiHandlers(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(LitherealExpectPlatform.getInfusementChamberBlock()), INFUSING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(LitherealExpectPlatform.getFreezingStationBlock()), FREEZING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(LitherealExpectPlatform.getFireCrucibleBlock()), BURNING_TYPE);
        IModPlugin.super.registerRecipeCatalysts(registration);
    }
}