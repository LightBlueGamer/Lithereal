package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.lithereal.item.datagen.ItemDataProvider;
import org.lithereal.item.datagen.ItemDataTemplate;
import org.lithereal.item.datagen.ItemDataTemplates;
import org.lithereal.item.infused.InfusedIngotItem;
import org.lithereal.tags.ModTags;

public class ModRawMaterialItems {
    public static final RegistrySupplier<Item> PHANTOM_DIAMOND = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS, ItemTags.BEACON_PAYMENT_ITEMS, ItemDataProvider.cItemTag("gems/diamond")).consume("phantom_diamond");

    public static final RegistrySupplier<Item> ODYSIUM_INGOT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS, ItemTags.BEACON_PAYMENT_ITEMS, ModTags.ODYSIUM_REPAIR_ITEMS).consume("odysium_ingot", Item.Properties::fireResistant);

    public static final RegistrySupplier<Item> LITHERITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS, ItemTags.BEACON_PAYMENT_ITEMS, ModTags.LITHERITE_REPAIR_ITEMS).consumeSpecial("litherite_crystal", LitheriteItem::new);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.BEACON_PAYMENT_ITEMS, ModTags.BURNING_LITHERITE_REPAIR_ITEMS).consume("burning_litherite_crystal", Item.Properties::fireResistant);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.BEACON_PAYMENT_ITEMS, ModTags.FROZEN_LITHERITE_REPAIR_ITEMS).consume("frozen_litherite_crystal");

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_INGOT = ItemDataTemplates.STANDARD
            .copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY)
            .addTags(ItemTags.BEACON_PAYMENT_ITEMS, ModTags.INFUSED_LITHERITE_REPAIR_ITEMS).consumeSpecial("infused_litherite_ingot", InfusedIngotItem::new);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.BEACON_PAYMENT_ITEMS, ModTags.WITHERING_LITHERITE_REPAIR_ITEMS).consume("withering_litherite_crystal");

    public static final RegistrySupplier<Item> CHARGED_LITHERITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.BEACON_PAYMENT_ITEMS).consume("charged_litherite_crystal");

    public static final RegistrySupplier<Item> UNIFIER = ItemDataTemplates.STANDARD.consume("unifier", properties ->
                    properties.rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));

    public static final RegistrySupplier<Item> IMPURE_ETHEREAL_CRYSTAL_SHARD = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("impure_ethereal_crystal_shard");

    public static final RegistrySupplier<Item> NERITH_INGOT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("nerith_ingot");

    public static final RegistrySupplier<Item> ALLIAN_INGOT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("allian_ingot");

    public static final RegistrySupplier<Item> AURELITE_DUST = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("aurelite_dust");

    public static final RegistrySupplier<Item> AURELITE_INGOT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("aurelite_ingot");

    public static final RegistrySupplier<Item> CHRYON_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("chryon_crystal");

    public static final RegistrySupplier<Item> COPALITE_DUST = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("copalite_dust");

    public static final RegistrySupplier<Item> COPALITE_INGOT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("copalite_ingot");

    public static final RegistrySupplier<Item> CYRUM_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("cyrum_crystal");

    public static final RegistrySupplier<Item> ELUNITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("elunite_crystal");

    public static final RegistrySupplier<Item> HELLIONITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("hellionite_crystal");

    public static final RegistrySupplier<Item> LUMINIUM_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("luminium_crystal");

    public static final RegistrySupplier<Item> NETHERITE_NUGGET = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("netherite_nugget", Item.Properties::fireResistant);

    public static final RegistrySupplier<Item> NETHERITE_FRAGMENT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("netherite_fragment", Item.Properties::fireResistant);

    public static final RegistrySupplier<Item> RAW_ALLIUM = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("raw_allium");

    public static final RegistrySupplier<Item> RAW_NERITH = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("raw_nerith");

    public static final RegistrySupplier<Item> SATURNITE_CRYSTAL = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("saturnite_crystal");

    public static final RegistrySupplier<Item> SOLIUMITE_INGOT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("soliumite_ingot");

    public static final RegistrySupplier<Item> ELCRUM_INGOT = ItemDataTemplates.STANDARD
            .addTags(ItemTags.TRIM_MATERIALS).consume("elcrum_ingot");

    public static void register() {

    }
}
