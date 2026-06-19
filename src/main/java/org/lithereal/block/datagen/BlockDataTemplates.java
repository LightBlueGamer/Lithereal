package org.lithereal.block.datagen;

import net.minecraft.data.recipes.RecipeCategory;
import org.lithereal.data.datagen.ItemLikeDataProvider;

import java.util.Optional;

import static org.lithereal.block.datagen.BlockDataTemplate.*;

public class BlockDataTemplates {
    public static final BlockDataTemplate EMPTY =
            new BlockDataTemplate(Optional.empty(),
                    Optional.empty(),
                    new ItemLikeDataProvider.TagDataBuilder(BlockDataProvider.TagType.NONE.tags()));
    public static final BlockDataTemplate RIFT =
            new BlockDataTemplate(Optional.empty(),
                    Optional.of(RIFT_BLOCK),
                    new ItemLikeDataProvider.TagDataBuilder(BlockDataProvider.TagType.NONE.tags()));
    public static final BlockDataTemplate LEAVES =
            new BlockDataTemplate(Optional.empty(),
                    Optional.of(LEAVES_BLOCK),
                    new ItemLikeDataProvider.TagDataBuilder(BlockDataProvider.TagType.LEAVES.tags()));
    public static final BlockDataTemplate STANDARD =
            new BlockDataTemplate(Optional.empty(),
                    Optional.of(BASIC_BLOCK),
                    new ItemLikeDataProvider.TagDataBuilder(BlockDataProvider.TagType.NONE.tags()));
    public static final BlockDataTemplate NYLIUM =
            new BlockDataTemplate(Optional.empty(),
                    Optional.of(NYLIUM_BLOCK),
                    new ItemLikeDataProvider.TagDataBuilder(BlockDataProvider.TagType.NYLIUM.tags()));
    public static final BlockDataTemplate STORAGE =
            new BlockDataTemplate(Optional.of(args -> recipeProvider -> recipeProvider.nineBlockStorageRecipesWithCustomUnpackingFromBaseModNamespace(RecipeCategory.MISC, args[1].get(), RecipeCategory.MISC, args[0].get(), recipeProvider.getSimpleRecipeNameAccess(args[1].get()) + "_from_block", recipeProvider.getSimpleRecipeNameAccess(args[1].get()))),
                    Optional.of(BASIC_BLOCK),
                    new ItemLikeDataProvider.TagDataBuilder(BlockDataProvider.TagType.NONE.tags()));
}
