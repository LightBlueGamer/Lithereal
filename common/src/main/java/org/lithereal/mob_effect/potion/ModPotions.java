package org.lithereal.mob_effect.potion;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import org.lithereal.Lithereal;
import org.lithereal.mob_effect.ModMobEffects;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Lithereal.MOD_ID, Registries.POTION);

    public static final RegistrySupplier<Potion> UNLUCK = POTIONS.register("unluck", () ->
            new Potion("unluck", new MobEffectInstance(MobEffects.UNLUCK, 3000)));
    public static final RegistrySupplier<Potion> LONG_UNLUCK = POTIONS.register("unluck_long", () ->
            new Potion("unluck", new MobEffectInstance(MobEffects.UNLUCK, 4800)));
    public static final RegistrySupplier<Potion> LONG_LUCK = POTIONS.register("luck_long", () ->
            new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 9000)));
    public static final RegistrySupplier<Potion> STRONG_LUCK = POTIONS.register("strong_luck", () ->
            new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 1800, 1)));
    public static final RegistrySupplier<Potion> STURDINESS = POTIONS.register("sturdiness", () ->
            new Potion("sturdiness", new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModMobEffects.PROTECTED.get()), 3600, 1)));
    public static final RegistrySupplier<Potion> LONG_STURDINESS = POTIONS.register("sturdiness_long", () ->
            new Potion("sturdiness", new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModMobEffects.PROTECTED.get()), 6600, 1)));
    public static final RegistrySupplier<Potion> STRONG_STURDINESS = POTIONS.register("strong_sturdiness", () ->
            new Potion("sturdiness", new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModMobEffects.PROTECTED.get()), 1800, 2)));

    public static void register() {
        POTIONS.register();
    }
}
