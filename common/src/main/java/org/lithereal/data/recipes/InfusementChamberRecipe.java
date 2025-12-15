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
import org.lithereal.util.CommonUtils;

public record InfusementChamberRecipe(ItemStack output, Ingredient secondary, Ingredient primary, Integer maxProgress) implements Recipe<ContainerRecipeInput> {
    @Override
    public @NotNull ItemStack assemble(ContainerRecipeInput container, HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel) {
        return hasSecondary(pContainer, 0) && hasPrimary(pContainer, 1);
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return CommonUtils.of(secondary, primary);
    }

    private boolean hasSecondary(ContainerRecipeInput container, int index) {
        return secondary.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    private boolean hasPrimary(ContainerRecipeInput container, int index) {
        return primary.test(container.getItem(index)) && container.getItem(index).getCount() >= 1;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.INFUSING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<InfusementChamberRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "infusing");
        public static final MapCodec<InfusementChamberRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(ItemStack.STRICT_CODEC.fieldOf("output").forGetter((arg) -> arg.output),
                                Ingredient.CODEC.fieldOf("secondary").forGetter(infusementChamberRecipe -> infusementChamberRecipe.secondary),
                                Ingredient.CODEC.fieldOf("primary").forGetter(infusementChamberRecipe -> infusementChamberRecipe.primary),
                                PrimitiveCodec.INT.fieldOf("max_progress").forGetter(infusementChamberRecipe -> infusementChamberRecipe.maxProgress))
                        .apply(instance, InfusementChamberRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, InfusementChamberRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public static @NotNull InfusementChamberRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            Ingredient bucket = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient potion = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            Integer maxProgress = ByteBufCodecs.VAR_INT.decode(buf);
            return new InfusementChamberRecipe(output, bucket, potion, maxProgress);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, InfusementChamberRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.secondary);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.primary);
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
            ByteBufCodecs.VAR_INT.encode(buf, recipe.maxProgress);
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
