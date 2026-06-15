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
import org.lithereal.data.recipes.FreezingStationRecipe;

public class FreezingStationRecipeCategory implements IRecipeCategory<FreezingStationRecipe> {
    public final static Identifier UID = Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "freezing");
    public final static Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/freezing_recipe_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FreezingStationRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 98, 40).setTextureSize(98, 40).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FREEZING_STATION.get()));
    }

    @Override
    public @NotNull IRecipeType<FreezingStationRecipe> getRecipeType() {
        return JEILitherealPlugin.FREEZING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Freezing Station");
    }

    @Override
    public int getWidth() {
        return 98;
    }

    @Override
    public int getHeight() {
        return 40;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(FreezingStationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FreezingStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 9, 12).add(recipe.primary());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 70, 12).add(recipe.output());
    }
}