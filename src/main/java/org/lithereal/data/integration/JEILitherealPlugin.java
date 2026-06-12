package org.lithereal.data.integration;

import dev.architectury.platform.Platform;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.*;
import mezz.jei.common.Internal;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModStorageBlocks;
import org.lithereal.client.gui.screens.inventory.ElectricCrucibleScreen;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.item.ModArmorItems;
import org.lithereal.data.recipes.FireCrucibleRecipe;
import org.lithereal.data.recipes.FreezingStationRecipe;
import org.lithereal.data.recipes.InfusementChamberRecipe;
import org.lithereal.client.gui.screens.inventory.FireCrucibleScreen;
import org.lithereal.client.gui.screens.inventory.FreezingStationScreen;
import org.lithereal.client.gui.screens.inventory.InfusementChamberScreen;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.item.ModToolItems;
import org.lithereal.item.compat.CompatInit;

import java.util.List;

@JeiPlugin
public class JEILitherealPlugin implements IModPlugin {
    public static IRecipeType<FreezingStationRecipe> FREEZING_TYPE =
            IRecipeType.create(FreezingStationRecipeCategory.UID, FreezingStationRecipe.class);

    public static IRecipeType<FireCrucibleRecipe> BURNING_TYPE =
            IRecipeType.create(FireCrucibleRecipeCategory.UID, FireCrucibleRecipe.class);

    public static IRecipeType<InfusementChamberRecipe> INFUSING_TYPE =
            IRecipeType.create(InfusementChamberRecipeCategory.UID, InfusementChamberRecipe.class);

    @Override
    public @NotNull Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "jei_compat");
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
        RecipeMap clientSyncedRecipes = Internal.getClientSyncedRecipes();

        List<FreezingStationRecipe> recipesInfusing = clientSyncedRecipes.byType(ModRecipes.FREEZING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();
        List<FireCrucibleRecipe> recipesBurning = clientSyncedRecipes.byType(ModRecipes.BURNING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();
        List<InfusementChamberRecipe> recipesInfChamber = clientSyncedRecipes.byType(ModRecipes.INFUSING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();

        registration.addRecipes(FREEZING_TYPE, recipesInfusing);
        registration.addRecipes(BURNING_TYPE, recipesBurning);
        registration.addRecipes(INFUSING_TYPE, recipesInfChamber);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        ISubtypeInterpreter<ItemStack> interpreter = new ISubtypeInterpreter<>() {
            @Override
            public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
                PotionContents contents = ingredient.get(DataComponents.POTION_CONTENTS);
                if (contents == null) {
                    return null;
                }
                return contents.potion()
                        .orElse(null);
            }
        };

        // Register the interpreter for all the items
        registration.registerSubtypeInterpreter(ModStorageBlocks.INFUSED_LITHERITE_BLOCK.get().asItem(), interpreter);
        registration.registerSubtypeInterpreter(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), interpreter);
        registration.registerSubtypeInterpreter(ModToolItems.INFUSED_LITHERITE_SWORD.get(), interpreter);
        registration.registerSubtypeInterpreter(ModToolItems.INFUSED_LITHERITE_SHOVEL.get(), interpreter);
        registration.registerSubtypeInterpreter(ModToolItems.INFUSED_LITHERITE_PICKAXE.get(), interpreter);
        registration.registerSubtypeInterpreter(ModToolItems.INFUSED_LITHERITE_AXE.get(), interpreter);
        registration.registerSubtypeInterpreter(ModToolItems.INFUSED_LITHERITE_HOE.get(), interpreter);
        registration.registerSubtypeInterpreter(ModToolItems.INFUSED_LITHERITE_HAMMER.get(), interpreter);
        registration.registerSubtypeInterpreter(ModArmorItems.INFUSED_LITHERITE_HELMET.get(), interpreter);
        registration.registerSubtypeInterpreter(ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(), interpreter);
        registration.registerSubtypeInterpreter(ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(), interpreter);
        registration.registerSubtypeInterpreter(ModArmorItems.INFUSED_LITHERITE_BOOTS.get(), interpreter);
        if (Platform.isModLoaded("combatify"))
            CompatInit.addInfusedSubtypesForCombatify(registration, interpreter);

        // Call the superclass method to ensure compatibility
        IModPlugin.super.registerItemSubtypes(registration);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(InfusementChamberScreen.class, 81, 19, 14, 35, INFUSING_TYPE);
        registration.addRecipeClickArea(FreezingStationScreen.class, 96, 34, 21, 15, FREEZING_TYPE);
        registration.addRecipeClickArea(FireCrucibleScreen.class, 81, 38, 13, 13, BURNING_TYPE);
        registration.addRecipeClickArea(ElectricCrucibleScreen.class, 81, 38, 13, 13, BURNING_TYPE);
        IModPlugin.super.registerGuiHandlers(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(INFUSING_TYPE, new ItemStack(ModBlocks.INFUSEMENT_CHAMBER.get()));
        registration.addCraftingStation(FREEZING_TYPE, new ItemStack(ModBlocks.FREEZING_STATION.get()));
        registration.addCraftingStation(BURNING_TYPE, new ItemStack(ModBlocks.FIRE_CRUCIBLE.get()), new ItemStack(ModBlocks.ELECTRIC_CRUCIBLE.get()));
        registration.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(ModBlocks.FIRE_CRUCIBLE.get()), new ItemStack(ModBlocks.ELECTRIC_CRUCIBLE.get()));
        IModPlugin.super.registerRecipeCatalysts(registration);
    }
}