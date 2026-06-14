package org.lithereal.data.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.data.recipes.FireCrucibleRecipe;

public class FireCrucibleRecipeCategory implements IRecipeCategory<FireCrucibleRecipe> {
    public final static Identifier UID = Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "burning");
    public final static Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/crucible_recipe_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FireCrucibleRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 60, 80).setTextureSize(60, 80).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FIRE_CRUCIBLE.get()));
    }

    @Override
    public @NotNull IRecipeType<FireCrucibleRecipe> getRecipeType() {
        return JEILitherealPlugin.BURNING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Fire Crucible");
    }

    @Override
    public int getWidth() {
        return 60;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(FireCrucibleRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FireCrucibleRecipe recipe, IFocusGroup focuses) {
        if (recipe.secondary().isPresent()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 36, 56).add(recipe.primary());
            builder.addSlot(RecipeIngredientRole.INPUT, 8, 56).add(recipe.secondary().get());

            builder.addSlot(RecipeIngredientRole.OUTPUT, 22, 12).add(recipe.output());
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 36, 56).add(recipe.primary());

            builder.addSlot(RecipeIngredientRole.OUTPUT, 22, 12).add(recipe.output());
        }
    }
}