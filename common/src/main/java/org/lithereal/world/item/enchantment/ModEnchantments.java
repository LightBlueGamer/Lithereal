package org.lithereal.world.item.enchantment;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import org.lithereal.Lithereal;
import org.lithereal.world.item.enchantment.DamageEffectEnchantment;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ENCHANTMENT);

    public static final RegistrySupplier<DamageEffectEnchantment> HEROS_EDGE = ENCHANTMENTS.register("heros_edge", () -> new DamageEffectEnchantment(Enchantment.definition(ItemTags.WEAPON_ENCHANTABLE, 1, 1, Enchantment.dynamicCost(25, 75), Enchantment.dynamicCost(75, 125), 8, EquipmentSlot.MAINHAND), (e) -> true, new MobEffectInstance[]{new MobEffectInstance(MobEffects.WITHER, 100, 1)}, (livingEntity, level) -> 100 + 50 * level, (entityType, level) -> 3 + level - 1F, false, false, true));

    public static void register() {
        ENCHANTMENTS.register();
    }
}
