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
import net.minecraft.world.level.block.Blocks;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModBlocks;
import org.lithereal.data.recipes.FireCrucibleRecipe;
import org.lithereal.integration.JEILitherealPlugin;

public class FireCrucibleRecipeCategory implements IRecipeCategory<FireCrucibleRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "burning");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/fire_crucible_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FireCrucibleRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(LitherealExpectPlatform.getFireCrucibleBlock()));
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
        if(recipe.getIngredients().size() > 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 94, 57).addIngredients(recipe.getIngredients().getFirst());
            builder.addSlot(RecipeIngredientRole.INPUT, 66, 57).addIngredients(recipe.getIngredients().get(1));

            builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 13).addItemStack(recipe.output());

        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 94, 57).addIngredients(recipe.getIngredients().getFirst());

            builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 13).addItemStack(recipe.output());
        }
    }
}