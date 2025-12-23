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
import org.lithereal.data.recipes.FireCrucibleRecipe;

public class FireCrucibleRecipeCategory implements IRecipeCategory<FireCrucibleRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "burning");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/crucible_recipe_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FireCrucibleRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 60, 80).setTextureSize(60, 80).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(LitherealExpectPlatform.getFireCrucibleBlock()));
    }

    @Override
    public @NotNull RecipeType<FireCrucibleRecipe> getRecipeType() {
        return JEILitherealPlugin.BURNING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
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
        if (recipe.getIngredients().size() > 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 36, 56).addIngredients(recipe.getIngredients().getFirst());
            builder.addSlot(RecipeIngredientRole.INPUT, 8, 56).addIngredients(recipe.getIngredients().get(1));

            builder.addSlot(RecipeIngredientRole.OUTPUT, 22, 12).addItemStack(recipe.output());
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 36, 56).addIngredients(recipe.getIngredients().getFirst());

            builder.addSlot(RecipeIngredientRole.OUTPUT, 22, 12).addItemStack(recipe.output());
        }
    }
}