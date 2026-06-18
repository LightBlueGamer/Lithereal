package org.lithereal.item.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import org.lithereal.data.compat.CombatifyHooks;
import org.lithereal.item.ModItems;

import static org.lithereal.item.ability.Ability.*;

public class CombatifyItems {
    public static final RegistrySupplier<Item> LITHERITE_LONGSWORD = ModItems.registerSpecialItem("litherite_longsword", properties ->
            CombatifyHooks.generateLongsword(ModItems.LITHERITE, 4, properties));
    public static final RegistrySupplier<Item> LITHERITE_KNIFE = ModItems.registerSpecialItem("litherite_knife", properties ->
            CombatifyHooks.generateKnife(ModItems.LITHERITE, properties));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_LONGSWORD = ModItems.registerSpecialItem("burning_litherite_longsword", properties ->
            CombatifyHooks.generateAbilityLongsword(BURNING, ModItems.LITHERITE, 4, properties));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_KNIFE = ModItems.registerSpecialItem("burning_litherite_knife", properties ->
            CombatifyHooks.generateAbilityKnife(BURNING, ModItems.LITHERITE, properties));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LONGSWORD = ModItems.registerSpecialItem("frozen_litherite_longsword", properties ->
            CombatifyHooks.generateAbilityLongsword(FROZEN, ModItems.LITHERITE, 4, properties));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_KNIFE = ModItems.registerSpecialItem("frozen_litherite_knife", properties ->
            CombatifyHooks.generateAbilityKnife(FROZEN, ModItems.LITHERITE, properties));
    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_LONGSWORD = ModItems.registerSpecialItem("smoldering_litherite_longsword", properties ->
            CombatifyHooks.generateAbilityLongsword(SMOLDERING, ModItems.LITHERITE, 4, properties));
    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_KNIFE = ModItems.registerSpecialItem("smoldering_litherite_knife", properties ->
            CombatifyHooks.generateAbilityKnife(SMOLDERING, ModItems.LITHERITE, properties));
    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_LONGSWORD = ModItems.registerSpecialItem("frostbitten_litherite_longsword", properties ->
            CombatifyHooks.generateAbilityLongsword(FROSTBITTEN, ModItems.LITHERITE, 4, properties));
    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_KNIFE = ModItems.registerSpecialItem("frostbitten_litherite_knife", properties ->
            CombatifyHooks.generateAbilityKnife(FROSTBITTEN, ModItems.LITHERITE, properties));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LONGSWORD = ModItems.registerSpecialItem("withering_litherite_longsword", properties ->
            CombatifyHooks.generateAbilityLongsword(WITHERING, ModItems.LITHERITE, 4, properties));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_KNIFE = ModItems.registerSpecialItem("withering_litherite_knife", properties ->
            CombatifyHooks.generateAbilityKnife(WITHERING, ModItems.LITHERITE, properties));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LONGSWORD = ModItems.registerSpecialItem("infused_litherite_longsword", properties ->
            CombatifyHooks.generateInfusedLongsword(ModItems.LITHERITE, 4, properties));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_KNIFE = ModItems.registerSpecialItem("infused_litherite_knife", properties ->
            CombatifyHooks.generateInfusedKnife(ModItems.LITHERITE, properties));
    public static final RegistrySupplier<Item> ODYSIUM_LONGSWORD = ModItems.registerSpecialItem("odysium_longsword", properties ->
            CombatifyHooks.generateAbilityLongsword(ODYSIUM, ModItems.ODYSIUM, 5, properties));
    public static final RegistrySupplier<Item> ODYSIUM_KNIFE = ModItems.registerSpecialItem("odysium_knife", properties ->
            CombatifyHooks.generateAbilityKnife(ODYSIUM, ModItems.ODYSIUM, properties));
    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_LONGSWORD = ModItems.registerSpecialItem("enhanced_odysium_longsword", properties ->
            CombatifyHooks.generateAbilityLongsword(ENHANCED_ODYSIUM, ModItems.ODYSIUM, 5, properties));
    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_KNIFE = ModItems.registerSpecialItem("enhanced_odysium_knife", properties ->
            CombatifyHooks.generateAbilityKnife(ENHANCED_ODYSIUM, ModItems.ODYSIUM, properties));

    public static void init() {

    }
}
