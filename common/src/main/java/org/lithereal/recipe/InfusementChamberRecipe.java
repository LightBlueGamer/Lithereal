package org.litecraft.lithereal.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import org.litecraft.lithereal.Lithereal;

import java.util.Arrays;

public class InfusementChamberRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    public final ItemStack output;
    public final NonNullList<Ingredient> recipeItems;

    public InfusementChamberRecipe(ResourceLocation id, ItemStack output,
                                   NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
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
        return recipeItems.get(index).test(container.getItem(index)) && container.getItem(index).getCount() >= recipeItems.get(index).getItems()[0].getCount();
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<InfusementChamberRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "infusing";
    }

    public static class Serializer implements RecipeSerializer<InfusementChamberRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Lithereal.MOD_ID, "infusing");

        @Override
        public InfusementChamberRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                Ingredient ingredient = getIngredient(ingredients.get(i).getAsJsonObject());
                inputs.set(i, ingredient);
            }

            return new InfusementChamberRecipe(pRecipeId, output, inputs);
        }

        private Ingredient getIngredient(JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json);
            int count = 1;

            if (json.getAsJsonObject().has("count")) {
                count = GsonHelper.getAsInt(json, "count");
            }

            ItemStack itemStack = ingredient.getItems()[0];
            itemStack.setCount(count);

            return  ingredient;
        }

        @Override
        public @Nullable InfusementChamberRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromNetwork(buf));

            ItemStack output = buf.readItem();
            return new InfusementChamberRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, InfusementChamberRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.output, false);
        }
    }
}
