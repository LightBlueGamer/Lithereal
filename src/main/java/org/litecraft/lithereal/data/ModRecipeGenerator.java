package org.litecraft.lithereal.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, List.of(ModBlocks.LITHERITE_ORE), RecipeCategory.MISC, ModItems.LITHERITE_CRYSTAL,
                0.7f, 200, "litherite");
        offerSmelting(exporter, List.of(ModBlocks.DEEPSLATE_LITHERITE_ORE), RecipeCategory.MISC, ModItems.LITHERITE_CRYSTAL,
                0.7f, 200, "litherite_deepslate");

        offerBlasting(exporter, List.of(ModBlocks.LITHERITE_ORE), RecipeCategory.MISC, ModItems.LITHERITE_CRYSTAL,
                0.7f, 100, "litherite_blasting");
        offerBlasting(exporter, List.of(ModBlocks.DEEPSLATE_LITHERITE_ORE), RecipeCategory.MISC, ModItems.LITHERITE_CRYSTAL,
                0.7f, 100, "litherite_blasting_deepslate");

        offerBlasting(exporter, List.of(ModItems.LITHERITE_CRYSTAL), RecipeCategory.MISC, ModItems.BURNING_LITHERITE_CRYSTAL,
                0.7f, 300, "burning_litherite");
        offerBlasting(exporter, List.of(ModBlocks.LITHERITE_BLOCK), RecipeCategory.MISC, ModBlocks.BURNING_LITHERITE_BLOCK,
                0.7f, 500, "burning_litherite_block");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.LITHERITE_CRYSTAL, RecipeCategory.DECORATIONS,
                ModBlocks.LITHERITE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.FROZEN_LITHERITE_AXE, RecipeCategory.DECORATIONS,
                ModBlocks.FROZEN_LITHERITE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.BURNING_LITHERITE_CRYSTAL, RecipeCategory.DECORATIONS,
                ModBlocks.BURNING_LITHERITE_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_SWORD)
                .pattern("L")
                .pattern("L")
                .pattern("S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_CRYSTAL)
                .pattern("L")
                .pattern("L")
                .pattern("S")
                .input('S', Items.STICK)
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_SWORD)
                .pattern("L")
                .pattern("L")
                .pattern("S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_PICKAXE)
                .pattern("LLL")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_PICKAXE)
                .pattern("LLL")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_PICKAXE)
                .pattern("LLL")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_AXE)
                .pattern("LL")
                .pattern("LS")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_AXE)
                .pattern("LL")
                .pattern("LS")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_AXE)
                .pattern("LL")
                .pattern("LS")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_HOE)
                .pattern("LL")
                .pattern(" S")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_HOE)
                .pattern("LL")
                .pattern(" S")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_HOE)
                .pattern("LL")
                .pattern(" S")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_SHOVEL)
                .pattern("L")
                .pattern("S")
                .pattern("S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_SHOVEL)
                .pattern("L")
                .pattern("S")
                .pattern("S")
                .input('S', Items.STICK)
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_SHOVEL)
                .pattern("L")
                .pattern("S")
                .pattern("S")
                .input('S', Items.STICK)
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_HELMET)
                .pattern("LLL")
                .pattern("L L")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_HELMET)
                .pattern("LLL")
                .pattern("L L")
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_HELMET)
                .pattern("LLL")
                .pattern("L L")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_CHESTPLATE)
                .pattern("L L")
                .pattern("LLL")
                .pattern("LLL")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_CHESTPLATE)
                .pattern("L L")
                .pattern("LLL")
                .pattern("LLL")
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_CHESTPLATE)
                .pattern("L L")
                .pattern("LLL")
                .pattern("LLL")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_LEGGINGS)
                .pattern("LLL")
                .pattern("L L")
                .pattern("L L")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_LEGGINGS)
                .pattern("LLL")
                .pattern("L L")
                .pattern("L L")
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_LEGGINGS)
                .pattern("LLL")
                .pattern("L L")
                .pattern("L L")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LITHERITE_BOOTS)
                .pattern("L L")
                .pattern("L L")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURNING_LITHERITE_BOOTS)
                .pattern("L L")
                .pattern("L L")
                .input('L', ModItems.BURNING_LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BURNING_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BURNING_LITHERITE_CRYSTAL))
                ;

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROZEN_LITHERITE_BOOTS)
                .pattern("L L")
                .pattern("L L")
                .input('L', ModItems.LITHERITE_CRYSTAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROZEN_LITHERITE_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.FROZEN_LITHERITE_CRYSTAL))
                ;
    }
}