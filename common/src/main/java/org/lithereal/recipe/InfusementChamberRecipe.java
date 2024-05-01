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

public class InfusementChamberRecipe implements Recipe<SimpleContainer> {
    @Override
    public ItemStack assemble(SimpleContainer container, HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    public final ItemStack output;
    public final NonNullList<Ingredient> recipeItems;

    public InfusementChamberRecipe(ItemStack output, Ingredient bucket, Ingredient potion) {
        this.output = output;
        this.recipeItems = NonNullList.of(bucket, potion);
    }
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        return hasItem(pContainer, 0) && hasItem(pContainer, 1);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    private boolean hasItem(SimpleContainer container, int index) {
        return recipeItems.get(index).test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.INFUSING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<InfusementChamberRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Lithereal.MOD_ID, "infusing");
        public static final MapCodec<InfusementChamberRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(ItemStack.STRICT_CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                        Ingredient.CODEC.fieldOf("bucket").forGetter(infusementChamberRecipe -> infusementChamberRecipe.recipeItems.getFirst()),
                        Ingredient.CODEC.fieldOf("potion").forGetter(infusementChamberRecipe -> infusementChamberRecipe.recipeItems.get(1)))
                        .apply(instance, InfusementChamberRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, InfusementChamberRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public static @NotNull InfusementChamberRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));

            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new InfusementChamberRecipe(output, inputs.get(0), inputs.get(1));
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, InfusementChamberRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients())
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }

        @Override
        public @NotNull MapCodec<InfusementChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, InfusementChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
