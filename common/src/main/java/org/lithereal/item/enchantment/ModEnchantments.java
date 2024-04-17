package org.lithereal.item.enchantment;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import org.lithereal.Lithereal;
import org.lithereal.item.enchantment.custom.DamageEffectEnchantment;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ENCHANTMENT);

    public static final RegistrySupplier<DamageEffectEnchantment> HEROS_EDGE = ENCHANTMENTS.register("heros_edge", () -> new DamageEffectEnchantment(Enchantment.Rarity.VERY_RARE, (e) -> true, new MobEffectInstance[]{new MobEffectInstance(MobEffects.WITHER, 100, 1)}, (livingEntity, level) -> 100 + 50 * level, (mobType, level) -> 3 + level - 1F, 1));

    public static void register() {
        ENCHANTMENTS.register();
    }
}
