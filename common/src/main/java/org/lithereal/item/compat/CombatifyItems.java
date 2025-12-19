package org.lithereal.item.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.lithereal.data.compat.CombatifyHooks;
import org.lithereal.item.ModItems;

import static org.lithereal.item.ability.Ability.*;

public class CombatifyItems {
    public static final RegistrySupplier<Item> LITHERITE_LONGSWORD = ModItems.ITEMS.register("litherite_longsword", () ->
            CombatifyHooks.generateLongsword(ModItems.LITHERITE, 4, new Item.Properties()));
    public static final RegistrySupplier<Item> LITHERITE_KNIFE = ModItems.ITEMS.register("litherite_knife", () ->
            CombatifyHooks.generateKnife(ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_LONGSWORD = ModItems.ITEMS.register("burning_litherite_longsword", () ->
            CombatifyHooks.generateAbilityLongsword(BURNING, ModItems.LITHERITE, 4, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_KNIFE = ModItems.ITEMS.register("burning_litherite_knife", () ->
            CombatifyHooks.generateAbilityKnife(BURNING, ModItems.LITHERITE, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LONGSWORD = ModItems.ITEMS.register("frozen_litherite_longsword", () ->
            CombatifyHooks.generateAbilityLongsword(FROZEN, ModItems.LITHERITE, 4, new Item.Properties()));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_KNIFE = ModItems.ITEMS.register("frozen_litherite_knife", () ->
            CombatifyHooks.generateAbilityKnife(FROZEN, ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_LONGSWORD = ModItems.ITEMS.register("smoldering_litherite_longsword", () ->
            CombatifyHooks.generateAbilityLongsword(SMOLDERING, ModItems.LITHERITE, 4, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_KNIFE = ModItems.ITEMS.register("smoldering_litherite_knife", () ->
            CombatifyHooks.generateAbilityKnife(SMOLDERING, ModItems.LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_LONGSWORD = ModItems.ITEMS.register("frostbitten_litherite_longsword", () ->
            CombatifyHooks.generateAbilityLongsword(FROSTBITTEN, ModItems.LITHERITE, 4, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_KNIFE = ModItems.ITEMS.register("frostbitten_litherite_knife", () ->
            CombatifyHooks.generateAbilityKnife(FROSTBITTEN, ModItems.LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LONGSWORD = ModItems.ITEMS.register("withering_litherite_longsword", () ->
            CombatifyHooks.generateAbilityLongsword(WITHERING, ModItems.LITHERITE, 4, new Item.Properties()));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_KNIFE = ModItems.ITEMS.register("withering_litherite_knife", () ->
            CombatifyHooks.generateAbilityKnife(WITHERING, ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LONGSWORD = ModItems.ITEMS.register("infused_litherite_longsword", () ->
            CombatifyHooks.generateInfusedLongsword(ModItems.LITHERITE, 4, new Item.Properties()));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_KNIFE = ModItems.ITEMS.register("infused_litherite_knife", () ->
            CombatifyHooks.generateInfusedKnife(ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> ODYSIUM_LONGSWORD = ModItems.ITEMS.register("odysium_longsword", () ->
            CombatifyHooks.generateAbilityLongsword(ODYSIUM, ModItems.ODYSIUM, 5, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> ODYSIUM_KNIFE = ModItems.ITEMS.register("odysium_knife", () ->
            CombatifyHooks.generateAbilityKnife(ODYSIUM, ModItems.ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_LONGSWORD = ModItems.ITEMS.register("enhanced_odysium_longsword", () ->
            CombatifyHooks.generateAbilityLongsword(ENHANCED_ODYSIUM, ModItems.ODYSIUM, 5, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_KNIFE = ModItems.ITEMS.register("enhanced_odysium_knife", () ->
            CombatifyHooks.generateAbilityKnife(ENHANCED_ODYSIUM, ModItems.ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static void init() {

    }
}
