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

import java.util.Optional;

public record FireCrucibleRecipe(ItemStackTemplate output, Ingredient primary, Optional<Ingredient> secondary, Integer maxProgress) implements Recipe<ContainerRecipeInput> {
    public static final MapCodec<FireCrucibleRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(ItemStackTemplate.CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                            Ingredient.CODEC.fieldOf("primary").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.primary),
                            Ingredient.CODEC.optionalFieldOf("secondary").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.secondary),
                            PrimitiveCodec.INT.fieldOf("max_progress").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.maxProgress))
                    .apply(instance, FireCrucibleRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FireCrucibleRecipe> STREAM_CODEC = StreamCodec.of(FireCrucibleRecipe::toNetwork, FireCrucibleRecipe::fromNetwork);
    public static final RecipeSerializer<FireCrucibleRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel) {
        return hasPrimary(pContainer) && hasSecondary(pContainer);
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

    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ret = NonNullList.withSize(2, Ingredient.of());
        ret.set(0, primary);
        secondary.ifPresent(ingredient -> ret.set(1, ingredient));
        return ret;
    }

    private boolean hasSecondary(ContainerRecipeInput container) {
        return secondary.map(ingredient -> ingredient.test(container.getItem(1)) && container.getItem(1).getCount() >= 1).orElse(container.getItem(1).isEmpty());
    }

    private boolean hasPrimary(ContainerRecipeInput container) {
        return primary.test(container.getItem(0)) && container.getItem(0).getCount() >= 1;
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<ContainerRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<ContainerRecipeInput>> getType() {
        return ModRecipes.BURNING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.BURNING_CATEGORY.get();
    }

    public static @NotNull FireCrucibleRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        boolean hasBucket = buf.readBoolean();
        Ingredient crystal = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
        Optional<Ingredient> bucket = Optional.empty();
        if (hasBucket)
            bucket = Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));

        ItemStackTemplate output = ItemStackTemplate.STREAM_CODEC.decode(buf);
        Integer maxProgress = ByteBufCodecs.VAR_INT.decode(buf);
        return new FireCrucibleRecipe(output, crystal, bucket, maxProgress);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buf, FireCrucibleRecipe recipe) {
        boolean hasBucket = recipe.secondary.isPresent();
        buf.writeBoolean(hasBucket);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.primary);
        if (hasBucket) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.secondary.get());
        ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.output);
        ByteBufCodecs.VAR_INT.encode(buf, recipe.maxProgress);
    }
}
