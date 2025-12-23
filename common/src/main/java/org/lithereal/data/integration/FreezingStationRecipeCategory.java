package org.lithereal.data.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.data.recipes.FreezingStationRecipe;

public class FreezingStationRecipeCategory implements IRecipeCategory<FreezingStationRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "freezing");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/freezing_recipe_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FreezingStationRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 102, 32).setTextureSize(102, 32).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(LitherealExpectPlatform.getFreezingStationBlock()));
    }

    @Override
    public @NotNull RecipeType<FreezingStationRecipe> getRecipeType() {
        return JEILitherealPlugin.FREEZING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Freezing Station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FreezingStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 8).addIngredients(recipe.cooler());
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 8).addIngredients(recipe.primary());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 78, 8).addItemStack(recipe.output());
    }
}