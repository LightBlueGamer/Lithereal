package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorType;
import org.lithereal.item.ability.AbilityArmorItem;
import org.lithereal.item.base.ModArmorItem;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;

import static org.lithereal.item.ModItems.registerItem;
import static org.lithereal.item.ability.Ability.*;

public class ModArmorItems {
    public static final RegistrySupplier<Item> LITHERITE_HELMET = registerItem("litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.HELMET, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HELMET = registerItem("burning_litherite_helmet", properties ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorType.HELMET, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HELMET = registerItem("frozen_litherite_helmet", properties ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorType.HELMET, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HELMET = registerItem("smoldering_litherite_helmet", properties ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.HELMET, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HELMET = registerItem("frostbitten_litherite_helmet", properties ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.HELMET, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HELMET = registerItem("infused_litherite_helmet", properties ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.HELMET, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HELMET = registerItem("withering_litherite_helmet", properties ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorType.HELMET, properties));

    public static final RegistrySupplier<Item> ODYSIUM_HELMET = registerItem("odysium_helmet", properties ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorType.HELMET, properties.rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HELMET = registerItem("enhanced_odysium_helmet", properties ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.HELMET, properties.rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = registerItem("litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.CHESTPLATE, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = registerItem("burning_litherite_chestplate", properties ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorType.CHESTPLATE, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = registerItem("frozen_litherite_chestplate", properties ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorType.CHESTPLATE, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_CHESTPLATE = registerItem("smoldering_litherite_chestplate", properties ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.CHESTPLATE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_CHESTPLATE = registerItem("frostbitten_litherite_chestplate", properties ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.CHESTPLATE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = registerItem("infused_litherite_chestplate", properties ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.CHESTPLATE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CHESTPLATE = registerItem("withering_litherite_chestplate", properties ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorType.CHESTPLATE, properties));

    public static final RegistrySupplier<Item> ODYSIUM_CHESTPLATE = registerItem("odysium_chestplate", properties ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorType.CHESTPLATE, properties.rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_CHESTPLATE = registerItem("enhanced_odysium_chestplate", properties ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.CHESTPLATE, properties.rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = registerItem("litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.LEGGINGS, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = registerItem("burning_litherite_leggings", properties ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorType.LEGGINGS, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = registerItem("frozen_litherite_leggings", properties ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorType.LEGGINGS, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_LEGGINGS = registerItem("smoldering_litherite_leggings", properties ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.LEGGINGS, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_LEGGINGS = registerItem("frostbitten_litherite_leggings", properties ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.LEGGINGS, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = registerItem("infused_litherite_leggings", properties ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.LEGGINGS, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LEGGINGS = registerItem("withering_litherite_leggings", properties ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorType.LEGGINGS, properties));

    public static final RegistrySupplier<Item> ODYSIUM_LEGGINGS = registerItem("odysium_leggings", properties ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorType.LEGGINGS, properties.rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_LEGGINGS = registerItem("enhanced_odysium_leggings", properties ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.LEGGINGS, properties.rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = registerItem("litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.BOOTS, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = registerItem("burning_litherite_boots", properties ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorType.BOOTS, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = registerItem("frozen_litherite_boots", properties ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorType.BOOTS, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_BOOTS = registerItem("smoldering_litherite_boots", properties ->
            new AbilityArmorItem(SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.BOOTS, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_BOOTS = registerItem("frostbitten_litherite_boots", properties ->
            new AbilityArmorItem(FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.BOOTS, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = registerItem("infused_litherite_boots", properties ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.BOOTS, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_BOOTS = registerItem("withering_litherite_boots", properties ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorType.BOOTS, properties));

    public static final RegistrySupplier<Item> ODYSIUM_BOOTS = registerItem("odysium_boots", properties ->
            new AbilityArmorItem(ODYSIUM, ModArmorMaterials.ODYSIUM, ArmorType.BOOTS, properties.rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_BOOTS = registerItem("enhanced_odysium_boots", properties ->
            new AbilityArmorItem(ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.BOOTS, properties.rarity(Rarity.UNCOMMON)));

    public static void register() {

    }
}
