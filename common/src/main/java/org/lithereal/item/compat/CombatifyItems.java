package org.lithereal.item.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

import static org.lithereal.LitherealExpectPlatform.*;
import static org.lithereal.item.ModItems.*;
import static org.lithereal.item.custom.Ability.*;

public class CombatifyItems {
    public static final RegistrySupplier<Item> LITHERITE_LONGSWORD = ITEMS.register("litherite_longsword", () ->
            createLongsword(LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> LITHERITE_KNIFE = ITEMS.register("litherite_knife", () ->
            createKnife(LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_LONGSWORD = ITEMS.register("burning_litherite_longsword", () ->
            createAbilityLongsword(BURNING, LITHERITE, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_KNIFE = ITEMS.register("burning_litherite_knife", () ->
            createAbilityKnife(BURNING, LITHERITE, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LONGSWORD = ITEMS.register("frozen_litherite_longsword", () ->
            createAbilityLongsword(FROZEN, LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_KNIFE = ITEMS.register("frozen_litherite_knife", () ->
            createAbilityKnife(FROZEN, LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LONGSWORD = ITEMS.register("withering_litherite_longsword", () ->
            createAbilityLongsword(WITHERING, LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_KNIFE = ITEMS.register("withering_litherite_knife", () ->
            createAbilityKnife(WITHERING, LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LONGSWORD = ITEMS.register("infused_litherite_longsword", () ->
            createInfusedLongsword(LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_KNIFE = ITEMS.register("infused_litherite_knife", () ->
            createInfusedKnife(LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> ODYSIUM_LONGSWORD = ITEMS.register("odysium_longsword", () ->
            createAbilityLongsword(ENHANCED, ODYSIUM, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> ODYSIUM_KNIFE = ITEMS.register("odysium_knife", () ->
            createAbilityKnife(ENHANCED, ODYSIUM, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> WAR_HAMMER = ITEMS.register("war_hammer", () ->
            createWarHammer(LITHERITE, 9, 2, new Item.Properties().fireResistant()));

    public static void init() {

    }
}
