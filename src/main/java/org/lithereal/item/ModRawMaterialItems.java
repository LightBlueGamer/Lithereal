package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.lithereal.item.infused.InfusedIngotItem;

import static org.lithereal.item.ModItems.registerItem;

public class ModRawMaterialItems {
    public static final RegistrySupplier<Item> PHANTOM_DIAMOND = registerItem("phantom_diamond", Item::new);

    public static final RegistrySupplier<Item> ODYSIUM_INGOT = registerItem("odysium_ingot", properties ->
            new Item(properties.fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_CRYSTAL = registerItem("litherite_crystal", LitheriteItem::new);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CRYSTAL = registerItem("burning_litherite_crystal", properties ->
            new Item(properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CRYSTAL = registerItem("frozen_litherite_crystal", Item::new);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_INGOT = registerItem("infused_litherite_ingot", InfusedIngotItem::new);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CRYSTAL = registerItem("withering_litherite_crystal", Item::new);

    public static final RegistrySupplier<Item> CHARGED_LITHERITE_CRYSTAL = registerItem("charged_litherite_crystal", Item::new);

    public static final RegistrySupplier<Item> UNIFIER = registerItem("unifier", properties ->
            new Item(properties.rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final RegistrySupplier<Item> IMPURE_ETHEREAL_CRYSTAL_SHARD = registerItem("impure_ethereal_crystal_shard", Item::new);

    public static final RegistrySupplier<Item> NERITH_INGOT = registerItem("nerith_ingot", Item::new);

    public static final RegistrySupplier<Item> ALLIAN_INGOT = registerItem("allian_ingot", Item::new);

    public static final RegistrySupplier<Item> AURELITE_DUST = registerItem("aurelite_dust", Item::new);

    public static final RegistrySupplier<Item> AURELITE_INGOT = registerItem("aurelite_ingot", Item::new);

    public static final RegistrySupplier<Item> CHRYON_CRYSTAL = registerItem("chryon_crystal", Item::new);

    public static final RegistrySupplier<Item> COPALITE_DUST = registerItem("copalite_dust", Item::new);

    public static final RegistrySupplier<Item> COPALITE_INGOT = registerItem("copalite_ingot", Item::new);

    public static final RegistrySupplier<Item> CYRUM_CRYSTAL = registerItem("cyrum_crystal", Item::new);

    public static final RegistrySupplier<Item> ELUNITE_CRYSTAL = registerItem("elunite_crystal", Item::new);

    public static final RegistrySupplier<Item> HELLIONITE_CRYSTAL = registerItem("hellionite_crystal", Item::new);

    public static final RegistrySupplier<Item> LUMINIUM_CRYSTAL = registerItem("luminium_crystal", Item::new);

    public static final RegistrySupplier<Item> NETHERITE_NUGGET = registerItem("netherite_nugget", properties ->
            new Item(properties.fireResistant()));

    public static final RegistrySupplier<Item> NETHERITE_FRAGMENT = registerItem("netherite_fragment", properties ->
            new Item(properties.fireResistant()));

    public static final RegistrySupplier<Item> RAW_ALLIUM = registerItem("raw_allium", Item::new);

    public static final RegistrySupplier<Item> RAW_NERITH = registerItem("raw_nerith", Item::new);

    public static final RegistrySupplier<Item> SATURNITE_CRYSTAL = registerItem("saturnite_crystal", Item::new);

    public static final RegistrySupplier<Item> SOLIUMITE_INGOT = registerItem("soliumite_ingot", Item::new);

    public static final RegistrySupplier<Item> ELCRUM_INGOT = registerItem("elcrum_ingot", Item::new);

    public static void register() {

    }
}
