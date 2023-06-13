package com.haru.lithereal.integration;

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
import com.haru.lithereal.Lithereal;
import com.haru.lithereal.block.ModBlocks;
import com.haru.lithereal.recipe.FireCrucibleRecipe;

public class FireCrucibleRecipeCategory implements IRecipeCategory<FireCrucibleRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Lithereal.MOD_ID, "burning");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Lithereal.MOD_ID, "textures/gui/fire_crucible_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FireCrucibleRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FIRE_CRUCIBLE.get()));
    }

    @Override
    public RecipeType<FireCrucibleRecipe> getRecipeType() {
        return JEILitherealPlugin.BURNING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Fire Crucible");
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
    public void setRecipe(IRecipeLayoutBuilder builder, FireCrucibleRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 57).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 13).addItemStack(recipe.output);
    }
}