package org.litecraft.lithereal.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.ForgeRegistries;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;
import org.litecraft.lithereal.recipe.FreezingStationRecipe;
import org.litecraft.lithereal.recipe.InfusementChamberRecipe;

import java.util.ArrayList;
import java.util.List;

public class InfusementChamberRecipeCategory implements IRecipeCategory<InfusementChamberRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Lithereal.MOD_ID, "infusing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Lithereal.MOD_ID, "textures/gui/infusement_chamber_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public InfusementChamberRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER.get()));
    }

    @Override
    public RecipeType<InfusementChamberRecipe> getRecipeType() {
        return JEILitherealPlugin.INFUSING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Infusement Chamber");
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
    public void setRecipe(IRecipeLayoutBuilder builder, InfusementChamberRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> itemStacks = new ArrayList<>();
        List<ItemStack> itemStacksOut = new ArrayList<>();
        for(Potion potion : ForgeRegistries.POTIONS) {
            ItemStack item = new ItemStack(recipe.getIngredients().get(1).getItems()[0].getItem());
            PotionUtils.setPotion(item, potion);
            itemStacks.add(item);

            ItemStack itemOut = new ItemStack(recipe.output.getItem());
            PotionUtils.setPotion(itemOut, potion);
            itemStacksOut.add(itemOut);
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 43, 34).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 68, 34).addItemStacks(itemStacks);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 129, 34).addItemStacks(itemStacksOut);
    }
}
