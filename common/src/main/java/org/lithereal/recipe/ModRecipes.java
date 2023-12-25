package org.lithereal.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.lithereal.Lithereal;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Lithereal.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeSerializer<FireCrucibleRecipe>> BURNING_SERIALIZER =
            SERIALIZERS.register("burning", () -> FireCrucibleRecipe.Serializer.INSTANCE);

    public static void register() {
        SERIALIZERS.register();
    }
}