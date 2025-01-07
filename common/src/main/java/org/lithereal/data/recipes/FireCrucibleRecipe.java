package org.lithereal.data.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;

import java.util.Optional;

public record FireCrucibleRecipe(ItemStack output, Ingredient crystal, Optional<Ingredient> bucket, Integer maxProgress) implements Recipe<ContainerRecipeInput> {
    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        return hasCrystal(pContainer) && hasBucket(pContainer);
    }

    @Override
    public @NotNull ItemStack assemble(ContainerRecipeInput container, HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ret = NonNullList.withSize(2, Ingredient.EMPTY);
        ret.set(0, crystal);
        bucket.ifPresent(ingredient -> ret.set(1, ingredient));
        return ret;
    }

    private boolean hasBucket(ContainerRecipeInput container) {
        return bucket.map(ingredient -> ingredient.test(container.getItem(3)) && container.getItem(3).getCount() >= 1).orElse(container.getItem(3).isEmpty());
    }

    private boolean hasCrystal(ContainerRecipeInput container) {
        return crystal.test(container.getItem(0)) && container.getItem(0).getCount() >= 1;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.BURNING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FireCrucibleRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "burning");
        public static final MapCodec<FireCrucibleRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(ItemStack.STRICT_CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                                Ingredient.CODEC.fieldOf("crystal").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.crystal),
                                Ingredient.CODEC.optionalFieldOf("bucket").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.bucket),
                                PrimitiveCodec.INT.fieldOf("max_progress").forGetter(fireCrucibleRecipe -> fireCrucibleRecipe.maxProgress))
                        .apply(instance, FireCrucibleRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FireCrucibleRecipe> STREAM_CODEC = StreamCodec.of(FireCrucibleRecipe.Serializer::toNetwork, FireCrucibleRecipe.Serializer::fromNetwork);

        public static @NotNull FireCrucibleRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            boolean hasBucket = buf.readBoolean();
            Ingredient crystal = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Optional<Ingredient> bucket = Optional.empty();
            if (hasBucket)
                bucket = Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));

            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            Integer maxProgress = ByteBufCodecs.VAR_INT.decode(buf);
            return new FireCrucibleRecipe(output, crystal, bucket, maxProgress);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, FireCrucibleRecipe recipe) {
            boolean hasBucket = recipe.bucket.isPresent();
            buf.writeBoolean(hasBucket);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.crystal);
            if (hasBucket) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.bucket.get());
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
            ByteBufCodecs.VAR_INT.encode(buf, recipe.maxProgress);
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
