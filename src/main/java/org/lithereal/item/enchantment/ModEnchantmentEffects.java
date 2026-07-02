package org.lithereal.item.enchantment;

import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import org.lithereal.Lithereal;
import org.lithereal.item.enchantment.effect.Chill;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECTS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE);

    public static final RegistrySupplier<MapCodec<Chill>> CHILL_ENTITY_EFFECT = ENCHANTMENT_ENTITY_EFFECTS.register("chill", () -> Chill.CODEC);

    public static void register() {
        ENCHANTMENT_ENTITY_EFFECTS.register();
    }
}
