package org.lithereal.item.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import org.lithereal.item.ModItems;

import static org.lithereal.LitherealExpectPlatform.*;
import static org.lithereal.item.ability.Ability.*;

public class CombatifyItems {
    public static final RegistrySupplier<Item> LITHERITE_LONGSWORD = ModItems.ITEMS.register("litherite_longsword", () ->
            createLongsword(ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> LITHERITE_KNIFE = ModItems.ITEMS.register("litherite_knife", () ->
            createKnife(ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_LONGSWORD = ModItems.ITEMS.register("burning_litherite_longsword", () ->
            createAbilityLongsword(BURNING, ModItems.LITHERITE, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> BURNING_LITHERITE_KNIFE = ModItems.ITEMS.register("burning_litherite_knife", () ->
            createAbilityKnife(BURNING, ModItems.LITHERITE, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LONGSWORD = ModItems.ITEMS.register("frozen_litherite_longsword", () ->
            createAbilityLongsword(FROZEN, ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> FROZEN_LITHERITE_KNIFE = ModItems.ITEMS.register("frozen_litherite_knife", () ->
            createAbilityKnife(FROZEN, ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LONGSWORD = ModItems.ITEMS.register("withering_litherite_longsword", () ->
            createAbilityLongsword(WITHERING, ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> WITHERING_LITHERITE_KNIFE = ModItems.ITEMS.register("withering_litherite_knife", () ->
            createAbilityKnife(WITHERING, ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LONGSWORD = ModItems.ITEMS.register("infused_litherite_longsword", () ->
            createInfusedLongsword(ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> INFUSED_LITHERITE_KNIFE = ModItems.ITEMS.register("infused_litherite_knife", () ->
            createInfusedKnife(ModItems.LITHERITE, new Item.Properties()));
    public static final RegistrySupplier<Item> ODYSIUM_LONGSWORD = ModItems.ITEMS.register("odysium_longsword", () ->
            createAbilityLongsword(ENHANCED, ModItems.ODYSIUM, new Item.Properties().fireResistant()));
    public static final RegistrySupplier<Item> ODYSIUM_KNIFE = ModItems.ITEMS.register("odysium_knife", () ->
            createAbilityKnife(ENHANCED, ModItems.ODYSIUM, new Item.Properties().fireResistant()));

    public static void init() {

    }
}
