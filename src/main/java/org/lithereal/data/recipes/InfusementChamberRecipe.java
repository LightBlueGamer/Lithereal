package org.lithereal.data.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public record InfusementChamberRecipe(ItemStackTemplate output, Ingredient primary, Ingredient secondary, Integer maxProgress) implements Recipe<ContainerRecipeInput> {
    public static final MapCodec<InfusementChamberRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(ItemStackTemplate.CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                            Ingredient.CODEC.fieldOf("primary").forGetter(infusementChamberRecipe -> infusementChamberRecipe.primary),
                            Ingredient.CODEC.fieldOf("secondary").forGetter(infusementChamberRecipe -> infusementChamberRecipe.secondary),
                            PrimitiveCodec.INT.fieldOf("max_progress").forGetter(infusementChamberRecipe -> infusementChamberRecipe.maxProgress))
                    .apply(instance, InfusementChamberRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, InfusementChamberRecipe> STREAM_CODEC = StreamCodec.of(InfusementChamberRecipe::toNetwork, InfusementChamberRecipe::fromNetwork);
    public static final RecipeSerializer<InfusementChamberRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    public static @NotNull InfusementChamberRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        Ingredient potion = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
        Ingredient bucket = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

        ItemStackTemplate output = ItemStackTemplate.STREAM_CODEC.decode(buf);
        Integer maxProgress = ByteBufCodecs.VAR_INT.decode(buf);
        return new InfusementChamberRecipe(output, potion, bucket, maxProgress);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buf, InfusementChamberRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.primary);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.secondary);
        ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.output);
        ByteBufCodecs.VAR_INT.encode(buf, recipe.maxProgress);
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel) {
        return hasSecondary(pContainer, 0) && hasPrimary(pContainer, 1);
    }

    @Override
    public ItemStack assemble(ContainerRecipeInput input) {
        return output.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    private boolean hasSecondary(ContainerRecipeInput container, int index) {
        return secondary.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    private boolean hasPrimary(ContainerRecipeInput container, int index) {
        return primary.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<ContainerRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<ContainerRecipeInput>> getType() {
        return ModRecipes.INFUSING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.INFUSING_CATEGORY.get();
    }
}
