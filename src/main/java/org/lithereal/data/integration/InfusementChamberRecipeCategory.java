package org.lithereal.data.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.data.recipes.InfusementChamberRecipe;

import java.util.ArrayList;
import java.util.List;

public class InfusementChamberRecipeCategory implements IRecipeCategory<InfusementChamberRecipe> {
    public final static Identifier UID = Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "infusing");
    public final static Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/infusement_chamber_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public InfusementChamberRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 68, 76).setTextureSize(68, 76).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INFUSEMENT_CHAMBER.get()));
    }

    @Override
    public @NotNull IRecipeType<InfusementChamberRecipe> getRecipeType() {
        return JEILitherealPlugin.INFUSING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Infusement Chamber");
    }

    @Override
    public int getWidth() {
        return 68;
    }

    @Override
    public int getHeight() {
        return 76;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(InfusementChamberRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfusementChamberRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder potionSlot = builder.addSlot(RecipeIngredientRole.INPUT, 8, 8);
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 8).add(recipe.secondary());
        IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 26, 52);

        for (Potion potion : BuiltInRegistries.POTION) {
            List<ItemStack> inputPots = new ArrayList<>();
            for (Holder<Item> primary : recipe.primary().items().toList()) {
                ItemStack potionItem = PotionContents.createItemStack(
                        primary.value(),
                        BuiltInRegistries.POTION.wrapAsHolder(potion));
                inputPots.add(potionItem);
            }
            potionSlot.addItemStacks(inputPots);

            ItemStack outputItem = recipe.output().create();
            outputItem.set(DataComponents.POTION_CONTENTS, new PotionContents(BuiltInRegistries.POTION.wrapAsHolder(potion)));
            outputSlot.add(outputItem);
        }
    }
}
