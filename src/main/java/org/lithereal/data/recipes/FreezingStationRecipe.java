package org.lithereal.data.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.lithereal.util.CommonUtils;

public record FreezingStationRecipe(ItemStackTemplate output, Ingredient cooler, Ingredient primary, Integer maxProgress) implements Recipe<ContainerRecipeInput> {
    public static final MapCodec<FreezingStationRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(ItemStackTemplate.CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                            Ingredient.CODEC.fieldOf("cooler").forGetter(freezingStationRecipe -> freezingStationRecipe.cooler),
                            Ingredient.CODEC.fieldOf("primary").forGetter(freezingStationRecipe -> freezingStationRecipe.primary),
                            PrimitiveCodec.INT.fieldOf("max_progress").forGetter(freezingStationRecipe -> freezingStationRecipe.maxProgress))
                    .apply(instance, FreezingStationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FreezingStationRecipe> STREAM_CODEC = StreamCodec.of(FreezingStationRecipe::toNetwork, FreezingStationRecipe::fromNetwork);
    public static final RecipeSerializer<FreezingStationRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    public static @NotNull FreezingStationRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        Ingredient cooler = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
        Ingredient crystal = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

        ItemStackTemplate output = ItemStackTemplate.STREAM_CODEC.decode(buf);
        Integer maxProgress = ByteBufCodecs.VAR_INT.decode(buf);
        return new FreezingStationRecipe(output, cooler, crystal, maxProgress);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buf, FreezingStationRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.cooler);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.primary);
        ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.output);
        ByteBufCodecs.VAR_INT.encode(buf, recipe.maxProgress);
    }
    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel) {
        return hasCooler(pContainer, 0) && hasPrimary(pContainer, 1);
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

    private boolean hasCooler(ContainerRecipeInput container, int index) {
        return cooler.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    private boolean hasPrimary(ContainerRecipeInput container, int index) {
        return primary.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return CommonUtils.of(cooler, primary);
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<ContainerRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<ContainerRecipeInput>> getType() {
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
