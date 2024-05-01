package org.lithereal.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.lithereal.Lithereal;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Lithereal.MOD_ID, Registries.RECIPE_SERIALIZER);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Lithereal.MOD_ID, Registries.RECIPE_TYPE);

    public static final RegistrySupplier<RecipeSerializer<FireCrucibleRecipe>> BURNING_SERIALIZER =
            SERIALIZERS.register("burning", () -> FireCrucibleRecipe.Serializer.INSTANCE);

    public static final RegistrySupplier<RecipeSerializer<FreezingStationRecipe>> FREEZING_SERIALIZER =
            SERIALIZERS.register("freezing", () -> FreezingStationRecipe.Serializer.INSTANCE);

    public static final RegistrySupplier<RecipeSerializer<InfusementChamberRecipe>> INFUSING_SERIALIZER =
            SERIALIZERS.register("infusing", () -> InfusementChamberRecipe.Serializer.INSTANCE);

    public static final RegistrySupplier<RecipeType<FireCrucibleRecipe>> BURNING_TYPE =
            register("burning");

    public static final RegistrySupplier<RecipeType<FreezingStationRecipe>> FREEZING_TYPE =
            register("freezing");

    public static final RegistrySupplier<RecipeType<InfusementChamberRecipe>> INFUSING_TYPE =
            register("infusing");
    static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> register(final String string) {
        return RECIPE_TYPES.register(string, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return string;
            }
        });
    }

    public static void register() {
        SERIALIZERS.register();
        RECIPE_TYPES.register();
    }
}