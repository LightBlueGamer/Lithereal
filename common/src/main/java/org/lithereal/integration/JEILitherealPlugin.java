//package org.lithereal.integration;
//
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.registration.*;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeManager;
//import net.minecraft.world.item.crafting.SmeltingRecipe;
//import org.lithereal.Lithereal;
//import org.lithereal.LitherealExpectPlatform;
//import org.lithereal.world.item.ModItems;
//import org.lithereal.world.item.compat.CompatInit;
//import org.lithereal.recipe.FireCrucibleRecipe;
//import org.lithereal.recipe.FreezingStationRecipe;
//import org.lithereal.recipe.InfusementChamberRecipe;
//import org.lithereal.screen.FireCrucibleScreen;
//import org.lithereal.screen.FreezingStationScreen;
//import org.lithereal.screen.InfusementChamberScreen;
//
//import java.util.List;
//import java.util.Objects;
//
//@JeiPlugin
//public class JEILitherealPlugin implements IModPlugin {
//    public static RecipeType<FreezingStationRecipe> FREEZING_TYPE =
//            new RecipeType<>(FreezingStationRecipeCategory.UID, FreezingStationRecipe.class);
//
//    public static RecipeType<FireCrucibleRecipe> BURNING_TYPE =
//            new RecipeType<>(FireCrucibleRecipeCategory.UID, FireCrucibleRecipe.class);
//
//    public static RecipeType<InfusementChamberRecipe> INFUSING_TYPE =
//            new RecipeType<>(InfusementChamberRecipeCategory.UID, InfusementChamberRecipe.class);
//
//    @Override
//    public ResourceLocation getPluginUid() {
//        return new ResourceLocation(Lithereal.MOD_ID, "jei_compat");
//    }
//
//    @Override
//    public void registerCategories(IRecipeCategoryRegistration registration) {
//        registration.addRecipeCategories(
//                new FireCrucibleRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
//                new InfusementChamberRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
//                new FreezingStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration registration) {
//        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
//
//        List<FreezingStationRecipe> recipesInfusing = rm.getAllRecipesFor(ModRecipes.FREEZING_TYPE.get());
//        List<FireCrucibleRecipe> recipesBurning = rm.getAllRecipesFor(ModRecipes.BURNING_TYPE.get());
//        List<InfusementChamberRecipe> recipesInfChamber = rm.getAllRecipesFor(ModRecipes.INFUSING_TYPE.get());
//        List<SmeltingRecipe> recipesFurnace = rm.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING);
//
//        registration.addRecipes(FREEZING_TYPE, recipesInfusing);
//        registration.addRecipes(BURNING_TYPE, recipesBurning);
//        registration.addRecipes(INFUSING_TYPE, recipesInfChamber);
//    }
//
//    @Override
//    public void registerItemSubtypes(ISubtypeRegistration registration) {
//        registration.useNbtForSubtypes(LitherealExpectPlatform.getInfusedLitheriteBlock().asItem(),
//                ModItems.INFUSED_LITHERITE_INGOT.get(),
//                ModItems.INFUSED_LITHERITE_SWORD.get(),
//                ModItems.INFUSED_LITHERITE_SHOVEL.get(),
//                ModItems.INFUSED_LITHERITE_PICKAXE.get(),
//                ModItems.INFUSED_LITHERITE_AXE.get(),
//                ModItems.INFUSED_LITHERITE_HOE.get(),
//                ModItems.INFUSED_LITHERITE_HAMMER.get(),
//                ModItems.INFUSED_LITHERITE_HELMET.get(),
//                ModItems.INFUSED_LITHERITE_CHESTPLATE.get(),
//                ModItems.INFUSED_LITHERITE_LEGGINGS.get(),
//                ModItems.INFUSED_LITHERITE_BOOTS.get());
//        CompatInit.addInfusedNbtSubtypesForCombatify(registration);
//        IModPlugin.super.registerItemSubtypes(registration);
//    }
//
//    @Override
//    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
//        registration.addRecipeClickArea(InfusementChamberScreen.class, 96, 34, 21, 15, INFUSING_TYPE);
//        registration.addRecipeClickArea(FreezingStationScreen.class, 96, 34, 21, 15, FREEZING_TYPE);
//        registration.addRecipeClickArea(FireCrucibleScreen.class, 81, 38, 13, 13, BURNING_TYPE);
//        IModPlugin.super.registerGuiHandlers(registration);
//    }
//
//    @Override
//    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//        registration.addRecipeCatalyst(new ItemStack(LitherealExpectPlatform.getInfusementChamberBlock()), INFUSING_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(LitherealExpectPlatform.getFreezingStationBlock()), FREEZING_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(LitherealExpectPlatform.getFireCrucibleBlock()), BURNING_TYPE);
//        IModPlugin.super.registerRecipeCatalysts(registration);
//    }
//}