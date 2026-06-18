package org.lithereal.item.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.lithereal.data.datagen.CommonModelCreators;
import org.lithereal.data.datagen.CustomRecipeProvider;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static org.lithereal.item.ModItems.registerItem;
import static org.lithereal.item.ModItems.registerSpecialItem;

public record ItemDataTemplate(Optional<Function<Supplier<? extends Item>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>>> recipeCreator, Optional<Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>>> modelCreator, ItemDataProvider.TagDataBuilder tagData) {
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> BASIC_ITEM = args -> itemModels -> CommonModelCreators.basicItem(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> INFUSED_BASIC_ITEM_NO_OVERLAY = args -> itemModels -> CommonModelCreators.basicPotionItemNoOverlay(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> HANDHELD_ITEM = args -> itemModels -> CommonModelCreators.handheldItem(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> HANDHELD_WAR_HAMMER_ITEM = args -> itemModels -> itemModels.generateFlatItem(args[0].get(), CommonModelCreators.HANDHELD_WAR_HAMMER_ITEM);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> INFUSED_HANDHELD_ITEM = args -> itemModels -> CommonModelCreators.handheldPotionItem(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> SPEAR_ITEM = args -> itemModels -> CommonModelCreators.spearItem(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> INFUSED_SPEAR_ITEM = args -> itemModels -> CommonModelCreators.spearPotionItem(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> BOW_ITEM = args -> itemModels -> CommonModelCreators.bowItem(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> BRUSH_ITEM = args -> itemModels -> CommonModelCreators.brushItem(args[0].get(), itemModels);
    public static final Function<Supplier<? extends Item>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>> NONE = _ -> _ -> {};
    public static final Function<RecipeCategory, Function<Supplier<? extends Item>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>>> IMPROVED_THERMAL_RECIPE = category -> args -> recipeProvider -> recipeProvider.improvedThermalItem(category, args[1].get(), args[0].get());
    public static final Function<Boolean, Function<Supplier<? extends Item>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>>> UPGRADE_RECIPE = tool -> args -> recipeProvider -> recipeProvider.upgradeRecipe(tool, args[1].get(), args[2].get(), args[3].get(), args[0].get());

    public ItemDataTemplate copyWithRecipeOverride(Function<Supplier<? extends Item>[], Consumer<CustomRecipeProvider<? extends RecipeProvider>>> recipeCreator) {
        return new ItemDataTemplate(Optional.ofNullable(recipeCreator), modelCreator, tagData);
    }
    public ItemDataTemplate copyWithModelOverride(Function<Supplier<? extends Item>[], Consumer<ItemModelGenerators>> modelCreator) {
        return new ItemDataTemplate(recipeCreator, Optional.ofNullable(modelCreator), tagData);
    }

    public ItemDataTemplate addTag(TagKey<Item> tagKey) {
        this.tagData.addTag(tagKey);
        return this;
    }

    @SafeVarargs
    public final ItemDataTemplate addTags(TagKey<Item>... tags) {
        this.tagData.addTags(tags);
        return this;
    }

    public RegistrySupplier<Item> consume(String name) {
        RegistrySupplier<Item> item = registerItem(name);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[]{item};
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    public RegistrySupplier<Item> consume(String name, Supplier<? extends Item> baseMaterial) {
        RegistrySupplier<Item> item = registerItem(name);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[]{item, baseMaterial};
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final RegistrySupplier<Item> consumeWithIngredients(String name, Supplier<? extends Item>... ingredients) {
        RegistrySupplier<Item> item = registerItem(name);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        finalRecipeArgs[0] = item;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final RegistrySupplier<Item> consumeWithAll(String name, Supplier<Item>[] ingredients, Supplier<? extends Item>... modelRelatives) {
        RegistrySupplier<Item> item = registerItem(name);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Item>[] finalModelArgs = new Supplier[modelRelatives.length + 1];
        finalRecipeArgs[0] = item;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        finalModelArgs[0] = item;
        System.arraycopy(modelRelatives, 0, finalModelArgs, 1, modelRelatives.length);
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    public RegistrySupplier<Item> consume(String name, UnaryOperator<Item.Properties> itemBuilder) {
        RegistrySupplier<Item> item = registerItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[]{item};
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    public RegistrySupplier<Item> consume(String name, UnaryOperator<Item.Properties> itemBuilder, Supplier<? extends Item> baseMaterial) {
        RegistrySupplier<Item> item = registerItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[]{item, baseMaterial};
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final RegistrySupplier<Item> consumeWithIngredients(String name, UnaryOperator<Item.Properties> itemBuilder, Supplier<? extends Item>... ingredients) {
        RegistrySupplier<Item> item = registerItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        finalRecipeArgs[0] = item;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final RegistrySupplier<Item> consumeWithAll(String name, UnaryOperator<Item.Properties> itemBuilder, Supplier<? extends Item>[] ingredients, Supplier<? extends Item>... modelRelatives) {
        RegistrySupplier<Item> item = registerItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Item>[] finalModelArgs = new Supplier[modelRelatives.length + 1];
        finalRecipeArgs[0] = item;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        finalModelArgs[0] = item;
        System.arraycopy(modelRelatives, 0, finalModelArgs, 1, modelRelatives.length);
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final <I extends Item> RegistrySupplier<I> consumeInternal(RegistrySupplier<I> item, Supplier<? extends Item>[] ingredients, Supplier<? extends Item>... modelRelatives) {
        new ItemDataProvider(item,
                recipeCreator.map(creator -> creator.apply(ingredients)),
                modelCreator.map(creator -> creator.apply(modelRelatives)),
                tagData.build());
        return item;
    }

    public <I extends Item> RegistrySupplier<I> consumeSpecial(String name, Function<Item.Properties, I> itemBuilder) {
        RegistrySupplier<I> item = registerSpecialItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[]{item};
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    public <I extends Item> RegistrySupplier<I> consumeSpecial(String name, Function<Item.Properties, I> itemBuilder, Supplier<? extends Item> baseMaterial) {
        RegistrySupplier<I> item = registerSpecialItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[]{item, baseMaterial};
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final <I extends Item> RegistrySupplier<I> consumeSpecialWithIngredients(String name, Function<Item.Properties, I> itemBuilder, Supplier<? extends Item>... ingredients) {
        RegistrySupplier<I> item = registerSpecialItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Item>[] finalModelArgs = new Supplier[]{item};
        finalRecipeArgs[0] = item;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }

    @SafeVarargs
    public final <I extends Item> RegistrySupplier<I> consumeSpecialWithAll(String name, Function<Item.Properties, I> itemBuilder, Supplier<? extends Item>[] ingredients, Supplier<? extends Item>... modelRelatives) {
        RegistrySupplier<I> item = registerSpecialItem(name, itemBuilder);
        Supplier<? extends Item>[] finalRecipeArgs = new Supplier[ingredients.length + 1];
        Supplier<? extends Item>[] finalModelArgs = new Supplier[modelRelatives.length + 1];
        finalRecipeArgs[0] = item;
        System.arraycopy(ingredients, 0, finalRecipeArgs, 1, ingredients.length);
        finalModelArgs[0] = item;
        System.arraycopy(modelRelatives, 0, finalModelArgs, 1, modelRelatives.length);
        return consumeInternal(item, finalRecipeArgs, finalModelArgs);
    }
}
