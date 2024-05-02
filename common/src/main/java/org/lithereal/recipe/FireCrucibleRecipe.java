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

public record FireCrucibleRecipe(ItemStack output, Ingredient crystal, Optional<Ingredient> bucket) implements Recipe<SimpleContainer> {
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        return hasCrystal(pContainer, 0) && hasBucket(pContainer, 3);
    }

    @Override
    public ItemStack assemble(SimpleContainer container, HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ret = NonNullList.withSize(2, Ingredient.EMPTY);
        ret.set(0, crystal);
        bucket.ifPresent(ingredient -> ret.set(1, ingredient));
        return ret;
    }

    private boolean hasBucket(SimpleContainer container, int index) {
        return bucket.map(ingredient -> ingredient.test(container.getItem(index)) && container.getItem(index).getCount() >= 1).orElse(true);
    }

    private boolean hasCrystal(SimpleContainer container, int index) {
        return crystal.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
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
                        Ingredient.CODEC.fieldOf("crystal").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.crystal),
                        Ingredient.CODEC.optionalFieldOf("bucket").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.bucket))
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
            boolean hasBucket = recipe.bucket.isPresent();
            buf.writeBoolean(hasBucket);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.crystal);
            if (hasBucket) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.bucket.get());
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
