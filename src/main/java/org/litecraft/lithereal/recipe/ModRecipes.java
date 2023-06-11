package org.litecraft.lithereal.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Lithereal.MOD_ID);

    public static final RegistryObject<RecipeSerializer<FreezingStationRecipe>> FREEZING_SERIALIZER =
            SERIALIZERS.register("freezing", () -> FreezingStationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<FireCrucibleRecipe>> BURNING_SERIALIZER =
            SERIALIZERS.register("burning", () -> FireCrucibleRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}