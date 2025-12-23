package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.lithereal.item.ability.AbilityArmorItem;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;

import static org.lithereal.item.ModItems.ITEMS;
import static org.lithereal.item.ability.Ability.*;

public class ModArmorItems {
    public static final RegistrySupplier<Item> LITHERITE_HELMET = ITEMS.register("litherite_helmet", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HELMET = ITEMS.register("burning_litherite_helmet", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HELMET = ITEMS.register("frozen_litherite_helmet", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HELMET = ITEMS.register("smoldering_litherite_helmet", () ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HELMET = ITEMS.register("frostbitten_litherite_helmet", () ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HELMET = ITEMS.register("infused_litherite_helmet", () ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HELMET = ITEMS.register("withering_litherite_helmet", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_HELMET = ITEMS.register("odysium_helmet", () ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorItem.Type.HELMET, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HELMET = ITEMS.register("enhanced_odysium_helmet", () ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorItem.Type.HELMET, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = ITEMS.register("burning_litherite_chestplate", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = ITEMS.register("frozen_litherite_chestplate", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_CHESTPLATE = ITEMS.register("smoldering_litherite_chestplate", () ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_CHESTPLATE = ITEMS.register("frostbitten_litherite_chestplate", () ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = ITEMS.register("infused_litherite_chestplate", () ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CHESTPLATE = ITEMS.register("withering_litherite_chestplate", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_CHESTPLATE = ITEMS.register("odysium_chestplate", () ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorItem.Type.CHESTPLATE, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_CHESTPLATE = ITEMS.register("enhanced_odysium_chestplate", () ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorItem.Type.CHESTPLATE, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = ITEMS.register("burning_litherite_leggings", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = ITEMS.register("frozen_litherite_leggings", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_LEGGINGS = ITEMS.register("smoldering_litherite_leggings", () ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_LEGGINGS = ITEMS.register("frostbitten_litherite_leggings", () ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = ITEMS.register("infused_litherite_leggings", () ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LEGGINGS = ITEMS.register("withering_litherite_leggings", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_LEGGINGS = ITEMS.register("odysium_leggings", () ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorItem.Type.LEGGINGS, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_LEGGINGS = ITEMS.register("enhanced_odysium_leggings", () ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorItem.Type.LEGGINGS, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = ITEMS.register("burning_litherite_boots", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = ITEMS.register("frozen_litherite_boots", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_BOOTS = ITEMS.register("smoldering_litherite_boots", () ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_BOOTS = ITEMS.register("frostbitten_litherite_boots", () ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = ITEMS.register("infused_litherite_boots", () ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_BOOTS = ITEMS.register("withering_litherite_boots", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_BOOTS = ITEMS.register("odysium_boots", () ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorItem.Type.BOOTS, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_BOOTS = ITEMS.register("enhanced_odysium_boots", () ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorItem.Type.BOOTS, 50, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static void register() {

    }
}
