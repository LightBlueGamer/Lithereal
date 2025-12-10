package org.lithereal.data.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.data.recipes.InfusementChamberRecipe;
import org.lithereal.item.ModRawMaterialItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InfusementChamberRecipeCategory implements IRecipeCategory<InfusementChamberRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "infusing");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/infusement_chamber_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public InfusementChamberRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(LitherealExpectPlatform.getInfusementChamberBlock()));
    }

    @Override
    public RecipeType<InfusementChamberRecipe> getRecipeType() {
        return JEILitherealPlugin.INFUSING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Infusement Chamber");
    }

    @SuppressWarnings("removal")
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
        Field[] fields = Potions.class.getDeclaredFields();

        itemStacks.add(Items.POTION.getDefaultInstance());
        itemStacksOut.add(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get().getDefaultInstance());
        for (Potion potion : BuiltInRegistries.POTION) {
            ItemStack potionItem = PotionContents.createItemStack(
                    recipe.getIngredients().get(1).getItems()[0].getItem(),
                    BuiltInRegistries.POTION.getHolderOrThrow(
                            BuiltInRegistries.POTION.getResourceKey(potion).orElseThrow()
                    ));
            itemStacks.add(potionItem);

            ItemStack itemOut = PotionContents.createItemStack(
                    recipe.output().getItem(),
                    BuiltInRegistries.POTION.getHolderOrThrow(
                            BuiltInRegistries.POTION.getResourceKey(potion).orElseThrow()
                    ));
            itemStacksOut.add(itemOut);
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 57).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 13).addItemStacks(itemStacks);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 57).addItemStacks(itemStacksOut);
    }
}
