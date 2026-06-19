package org.lithereal.block.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.lithereal.data.datagen.CommonModelCreators;
import org.lithereal.data.datagen.CustomRecipeProvider;
import org.lithereal.data.datagen.ItemLikeDataProvider;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.lithereal.data.datagen.CommonModelCreators.*;

public record BlockDataTemplate(Optional<Function<Supplier<? extends ItemLike>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>>> recipeCreator, Optional<Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>>> modelCreator, ItemLikeDataProvider.TagDataBuilder tagData) {
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> BASIC_BLOCK = args -> blockModels -> CommonModelCreators.blockWithItem(args[0], blockModels);
    public static final Function<ItemTintSource, Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>>> BASIC_BLOCK_WITH_TINTED_ITEM = tintSource -> args -> blockModels -> CommonModelCreators.blockWithTintedItem(args[0], tintSource, blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> RIFT_BLOCK = args -> blockModels -> riftBlock(args[0].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> RIFT_LIKE_PORTAL_BLOCK = args -> blockModels -> riftLikePortalBlock(args[0].get(), args[1].get(), blockModels);
    public static final Function<ItemTintSource, Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>>> GRASS_LIKE_BLOCK = tintSource -> args -> blockModels -> grassLikeBlock(args[0], args[1], tintSource, blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> FARMLAND_BLOCK = args -> blockModels -> createFarmland(args[0].get(), args[1].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> EXISTING_CREATE_STATES = args -> blockModels -> existingBlockWithItem(args[0], blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> EXISTING_CREATE_STATES_WITH_FACING = args -> blockModels -> existingBlockWithFacing(args[0].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> FIRE_BLOCK = args -> blockModels -> createFire(args[0].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> LANTERN_BLOCK = args -> blockModels -> blockModels.createLantern(args[0].get());
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> NYLIUM_BLOCK = args -> blockModels -> nyliumBlock(args[0].get(), args[1].get(), args[2].get(), blockModels);
    public static final Function<Boolean, Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>>> VAULT_BLOCK = isOminous -> args -> blockModels -> createVault(args[0].get(), blockModels, isOminous);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> ELECTRIC_CRUCIBLE_BLOCK = args -> blockModels -> electricCrucibleBlock(args[0].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> FIRE_CRUCIBLE_BLOCK = args -> blockModels -> fireCrucibleBlock(args[0].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> INFUSEMENT_CHAMBER_BLOCK = args -> blockModels -> infusementChamberBlock(args[0].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> CORNERED_BLOCK = args -> blockModels -> corneredBlock(args[0].get(), blockModels);
    public static final Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> LEAVES_BLOCK = args -> blockModels -> {
        blockModels.createTrivialBlock(args[0].get(), TexturedModel.LEAVES);
        itemForBlockModel(args[0].get(), blockModels);
    };
    public static final Function<Supplier<? extends Block>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>> NONE = _ -> _ -> {};

    public BlockDataTemplate copyWithRecipeOverride(Function<Supplier<? extends ItemLike>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>> recipeCreator) {
        return new BlockDataTemplate(Optional.ofNullable(recipeCreator), modelCreator, tagData);
    }
    public BlockDataTemplate copyWithModelOverride(Function<Supplier<? extends Block>[], Consumer<BlockModelGenerators>> modelCreator) {
        return new BlockDataTemplate(recipeCreator, Optional.ofNullable(modelCreator), tagData);
    }
    public BlockDataTemplate copyWithTagTypeOverride(BlockDataProvider.TagType tagType) {
        return new BlockDataTemplate(recipeCreator, modelCreator, new ItemLikeDataProvider.TagDataBuilder(tagType.tags()));
    }
    public BlockDataTemplate and(BlockDataProvider.TagType tagType) {
        this.tagData.addAllTags(tagType.tags());
        return this;
    }

    public BlockDataTemplate addTag(TagKey<Block> tagKey) {
        this.tagData.addBlockTag(tagKey);
        return this;
    }

    @SafeVarargs
    public final BlockDataTemplate addTags(TagKey<Block>... tags) {
        this.tagData.addBlockTags(tags);
        return this;
    }

    public BlockDataTemplate addItemTag(TagKey<Item> tagKey) {
        this.tagData.addTag(tagKey);
        return this;
    }

    @SafeVarargs
    public final BlockDataTemplate addItemTags(TagKey<Item>... tags) {
        this.tagData.addTags(tags);
        return this;
    }

    public <T extends Block> RegistrySupplier<T> consume(RegistrySupplier<T> block) {
        Supplier<? extends ItemLike>[] finalRecipeArgs = new Supplier[]{block};
        Supplier<? extends Block>[] finalModelArgs = new Supplier[]{block};
        return consumeInternal(block, finalRecipeArgs, finalModelArgs);
    }

    public <T extends Block> RegistrySupplier<T> consume(RegistrySupplier<T> block, Supplier<? extends ItemLike> baseMaterial) {
        Supplier<? extends ItemLike>[] finalRecipeArgs = new Supplier[]{block, baseMaterial};
        Supplier<? extends Block>[] finalModelArgs = new Supplier[]{block};
        return consumeInternal(block, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final <T extends Block> RegistrySupplier<T> consumeWithIngredients(RegistrySupplier<T> block, Supplier<? extends ItemLike>... ingredients) {
        Supplier<? extends ItemLike>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Block>[] finalModelArgs = new Supplier[]{block};
        finalRecipeArgs[0] = block;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        return consumeInternal(block, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final <T extends Block> RegistrySupplier<T> consumeWithModelRelatives(RegistrySupplier<T> block, Supplier<? extends Block>... modelRelatives) {
        Supplier<? extends ItemLike>[] ingredients = new Supplier[]{block};
        Supplier<? extends Block>[] finalModelArgs = new Supplier[modelRelatives.length + 1];
        finalModelArgs[0] = block;
        System.arraycopy(modelRelatives, 0, finalModelArgs, 1, modelRelatives.length);
        return consumeInternal(block, ingredients, finalModelArgs);
    }

    @SafeVarargs
    public final <T extends Block> RegistrySupplier<T> consumeWithAll(RegistrySupplier<T> block, Supplier<ItemLike>[] ingredients, Supplier<? extends Block>... modelRelatives) {
        Supplier<? extends ItemLike>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Block>[] finalModelArgs = new Supplier[modelRelatives.length + 1];
        finalRecipeArgs[0] = block;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        finalModelArgs[0] = block;
        System.arraycopy(modelRelatives, 0, finalModelArgs, 1, modelRelatives.length);
        return consumeInternal(block, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final <T extends Block> RegistrySupplier<T> consumeInternal(RegistrySupplier<T> block, Supplier<? extends ItemLike>[] ingredients, Supplier<? extends Block>... modelRelatives) {
        new BlockDataProvider(block,
                recipeCreator.map(creator -> creator.apply(ingredients)),
                modelCreator.map(creator -> creator.apply(modelRelatives)),
                tagData.build());
        return block;
    }
}
