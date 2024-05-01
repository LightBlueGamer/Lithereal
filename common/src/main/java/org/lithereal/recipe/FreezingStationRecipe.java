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

public class FreezingStationRecipe implements Recipe<SimpleContainer> {
    public final ItemStack output;
    public final NonNullList<Ingredient> recipeItems;

    public FreezingStationRecipe(ItemStack output, Ingredient cooler, Ingredient crystal) {
        this.output = output;
        this.recipeItems = NonNullList.of(cooler, crystal);
    }
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        return hasItem(pContainer, 0) && hasItem(pContainer, 1);
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
        return recipeItems.get(index).test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
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
        return ModRecipes.FREEZING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FreezingStationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Lithereal.MOD_ID, "freezing");
        public static final MapCodec<FreezingStationRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(ItemStack.STRICT_CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                        Ingredient.CODEC.fieldOf("cooler").forGetter(freezingStationRecipe -> freezingStationRecipe.recipeItems.getFirst()),
                        Ingredient.CODEC.fieldOf("crystal").forGetter(freezingStationRecipe -> freezingStationRecipe.recipeItems.get(1)))
                        .apply(instance, FreezingStationRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FreezingStationRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public static @NotNull FreezingStationRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));

            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new FreezingStationRecipe(output, inputs.get(0), inputs.get(1));
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, FreezingStationRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients())
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }

        @Override
        public @NotNull MapCodec<FreezingStationRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, FreezingStationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
