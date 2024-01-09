package org.lithereal.integration;

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
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.recipe.FreezingStationRecipe;

public class FreezingStationRecipeCategory implements IRecipeCategory<FreezingStationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Lithereal.MOD_ID, "freezing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Lithereal.MOD_ID, "textures/gui/freezing_station_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FreezingStationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(LitherealExpectPlatform.getFreezingStationBlock()));
    }

    @Override
    public RecipeType<FreezingStationRecipe> getRecipeType() {
        return JEILitherealPlugin.FREEZING_TYPE;
    }

    @Override
    public Component getTitle() {
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
        builder.addSlot(RecipeIngredientRole.INPUT, 43, 34).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 68, 34).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 129, 34).addItemStack(recipe.output);
    }
}