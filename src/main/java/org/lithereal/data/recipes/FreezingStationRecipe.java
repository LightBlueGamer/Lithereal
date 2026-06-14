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

public record FreezingStationRecipe(ItemStackTemplate output, Ingredient primary, Integer maxProgress) implements Recipe<SingleRecipeInput> {
    public static final MapCodec<FreezingStationRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(ItemStackTemplate.CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                            Ingredient.CODEC.fieldOf("primary").forGetter(freezingStationRecipe -> freezingStationRecipe.primary),
                            PrimitiveCodec.INT.fieldOf("max_progress").forGetter(freezingStationRecipe -> freezingStationRecipe.maxProgress))
                    .apply(instance, FreezingStationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FreezingStationRecipe> STREAM_CODEC = StreamCodec.of(FreezingStationRecipe::toNetwork, FreezingStationRecipe::fromNetwork);
    public static final RecipeSerializer<FreezingStationRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    public static @NotNull FreezingStationRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        Ingredient crystal = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

        ItemStackTemplate output = ItemStackTemplate.STREAM_CODEC.decode(buf);
        Integer maxProgress = ByteBufCodecs.VAR_INT.decode(buf);
        return new FreezingStationRecipe(output, crystal, maxProgress);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buf, FreezingStationRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.primary);
        ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.output);
        ByteBufCodecs.VAR_INT.encode(buf, recipe.maxProgress);
    }
    @Override
    public boolean matches(SingleRecipeInput input, Level pLevel) {
        return hasPrimary(input);
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input) {
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

    private boolean hasPrimary(SingleRecipeInput container) {
        return primary.test(container.item()) && container.item().getCount() >= 1;
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ModRecipes.FREEZING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.FREEZING_CATEGORY.get();
    }
}
