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
import org.lithereal.util.CommonUtils;

public record FreezingStationRecipe(ItemStack output, Ingredient cooler, Ingredient crystal) implements Recipe<SimpleContainer> {
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        return hasCooler(pContainer, 0) && hasCrystal(pContainer, 1);
    }

    private boolean hasCooler(SimpleContainer container, int index) {
        return cooler.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    private boolean hasCrystal(SimpleContainer container, int index) {
        return crystal.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return CommonUtils.of(cooler, crystal);
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
                        Ingredient.CODEC.fieldOf("cooler").forGetter(freezingStationRecipe -> freezingStationRecipe.cooler),
                        Ingredient.CODEC.fieldOf("crystal").forGetter(freezingStationRecipe -> freezingStationRecipe.crystal))
                        .apply(instance, FreezingStationRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FreezingStationRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public static @NotNull FreezingStationRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            Ingredient cooler = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient crystal = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new FreezingStationRecipe(output, cooler, crystal);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, FreezingStationRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.cooler);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.crystal);
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
