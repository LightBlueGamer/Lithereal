package org.lithereal.forge.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.item.ModItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.INFUSED_LITHERITE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, LitherealExpectPlatform.getInfusedLitheriteBlock());

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.INFUSED_LITHERITE_HELMET.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .pattern("LLL")
                .pattern("L L")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.INFUSED_LITHERITE_CHESTPLATE.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .pattern("L L")
                .pattern("LLL")
                .pattern("LLL")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.INFUSED_LITHERITE_LEGGINGS.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .pattern("LLL")
                .pattern("L L")
                .pattern("L L")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.INFUSED_LITHERITE_BOOTS.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .pattern("L L")
                .pattern("L L")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.INFUSED_LITHERITE_SWORD.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("L")
                .pattern("L")
                .pattern("S")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.INFUSED_LITHERITE_PICKAXE.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("LLL")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.INFUSED_LITHERITE_AXE.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("LL")
                .pattern("LS")
                .pattern(" S")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.INFUSED_LITHERITE_SHOVEL.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("L")
                .pattern("S")
                .pattern("S")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.INFUSED_LITHERITE_HOE.get())
                .define('L', ModItems.INFUSED_LITHERITE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("LL")
                .pattern(" S")
                .pattern(" S")
                .unlockedBy("has_infused_litherite", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_LITHERITE_INGOT.get()).build()))
                .save(consumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.BLASTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_)
                    .unlockedBy(getHasName(itemlike), has(itemlike)).save(p_250791_, new ResourceLocation(Lithereal.MOD_ID, getItemName(p_250066_)) + p_249236_ + "_" + getItemName(itemlike));
        }
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_249580_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_) {
        nineBlockStorageRecipes(p_249580_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), (String)null, getSimpleRecipeName(p_251689_), (String)null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_250423_, RecipeCategory p_250083_, ItemLike p_250042_,
                                                  RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_,
                                                  String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 9).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_))
                .save(p_250423_, new ResourceLocation(Lithereal.MOD_ID, p_252237_+"_to_ingot"));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("###").pattern("###").pattern("###").group(p_248641_)
                .unlockedBy(getHasName(p_250042_), has(p_250042_)).save(p_250423_, new ResourceLocation(Lithereal.MOD_ID, p_250475_+"_to_block"));
    }
}