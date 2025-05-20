package org.lithereal.mob_effect.potion;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import org.lithereal.Lithereal;
import org.lithereal.mob_effect.ModMobEffects;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Lithereal.MOD_ID, Registries.POTION);

    public static final RegistrySupplier<Potion> STURDINESS = POTIONS.register("sturdiness", () ->
            new Potion(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModMobEffects.PROTECTED.get()), 3600, 1)));
    public static final RegistrySupplier<Potion> STURDINESS_LONG = POTIONS.register("sturdiness_long", () ->
            new Potion("sturdiness", new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModMobEffects.PROTECTED.get()), 6600, 1)));

    public static void register() {
        POTIONS.register();
    }
}
