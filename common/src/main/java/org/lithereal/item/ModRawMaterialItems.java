package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.lithereal.item.infused.InfusedLitheriteItem;

import static org.lithereal.item.ModItems.ITEMS;

public class ModRawMaterialItems {
    public static final RegistrySupplier<Item> PHANTOM_DIAMOND = ITEMS.register("phantom_diamond", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_INGOT = ITEMS.register("odysium_ingot", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CRYSTAL = ITEMS.register("burning_litherite_crystal", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CRYSTAL = ITEMS.register("frozen_litherite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_INGOT = ITEMS.register("infused_litherite_ingot", () ->
            new InfusedLitheriteItem(new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CRYSTAL = ITEMS.register("withering_litherite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> CHARGED_LITHERITE_CRYSTAL = ITEMS.register("charged_litherite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> UNIFIER = ITEMS.register("unifier", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final RegistrySupplier<Item> IMPURE_ETHEREAL_CRYSTAL_SHARD = ITEMS.register("impure_ethereal_crystal_shard", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NERITH_INGOT = ITEMS.register("nerith_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ALLIAN_INGOT = ITEMS.register("allian_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> AURELITE_DUST = ITEMS.register("aurelite_dust", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> AURELITE_INGOT = ITEMS.register("aurelite_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> CHRYON_CRYSTAL = ITEMS.register("chryon_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> COPALITE_DUST = ITEMS.register("copalite_dust", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> COPALITE_INGOT = ITEMS.register("copalite_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> CYRUM_CRYSTAL = ITEMS.register("cyrum_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ELUNITE_CRYSTAL = ITEMS.register("elunite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> HELLIONITE_CRYSTAL = ITEMS.register("hellionite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> LUMINIUM_CRYSTAL = ITEMS.register("luminium_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> RAW_ALLIUM = ITEMS.register("raw_allium", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> RAW_NERITH = ITEMS.register("raw_nerith", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SATURNITE_CRYSTAL = ITEMS.register("saturnite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SOLIUMITE_INGOT = ITEMS.register("soliumite_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ELCRUM_INGOT = ITEMS.register("elcrum_ingot", () ->
            new Item(new Item.Properties()));

    public static void register() {

    }
}
