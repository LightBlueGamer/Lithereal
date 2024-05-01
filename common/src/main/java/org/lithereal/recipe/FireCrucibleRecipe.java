package org.lithereal.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;

import java.util.Optional;

public class FireCrucibleRecipe implements Recipe<SimpleContainer> {
    public final ItemStack output;
    public final NonNullList<Ingredient> recipeItems;

    public FireCrucibleRecipe(ItemStack output, Ingredient crystal, Optional<Ingredient> bucket) {
        this.output = output;
        if (bucket.isPresent())
            this.recipeItems = NonNullList.of(crystal, bucket.get());
        else
            recipeItems = NonNullList.of(crystal);
    }
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        if (recipeItems.size() > 1 && !recipeItems.get(1).isEmpty()) {
            return hasItem(pContainer, 0) && hasItem(pContainer, 1);
        } else return hasItem(pContainer, 0);
    }

    @Override
    public ItemStack assemble(SimpleContainer container, HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    private boolean hasItem(SimpleContainer container, int index) {
        return recipeItems.get(index).test(container.getItem(index * 3)) && container.getItem(index * 3).getCount() >= 1;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BURNING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FireCrucibleRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Lithereal.MOD_ID, "burning");
        public static final MapCodec<FireCrucibleRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(ItemStack.STRICT_CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                        Ingredient.CODEC.fieldOf("crystal").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.recipeItems.getFirst()),
                        Ingredient.CODEC.optionalFieldOf("bucket").forGetter(fireCrucibleRecipe -> Optional.of(fireCrucibleRecipe.recipeItems.get(1))))
                        .apply(instance, FireCrucibleRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FireCrucibleRecipe> STREAM_CODEC = StreamCodec.of(FireCrucibleRecipe.Serializer::toNetwork, FireCrucibleRecipe.Serializer::fromNetwork);

        public static @NotNull FireCrucibleRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            boolean hasBucket = buf.readBoolean();
            Ingredient crystal = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Optional<Ingredient> bucket = Optional.empty();
            if (hasBucket)
                bucket = Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));

            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new FireCrucibleRecipe(output, crystal, bucket);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, FireCrucibleRecipe recipe) {
            boolean hasBucket = recipe.recipeItems.size() > 1;
            buf.writeBoolean(hasBucket);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getIngredients().getFirst());
            if (hasBucket) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getIngredients().get(1));
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }

        @Override
        public @NotNull MapCodec<FireCrucibleRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, FireCrucibleRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
