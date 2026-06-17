package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import org.lithereal.item.base.ModArmorItem;
import org.lithereal.item.infused.InfusedArmorItem;

import static org.lithereal.item.ModItems.registerItem;
import static org.lithereal.item.ability.Ability.*;

public class ModArmorItems {
    public static final RegistrySupplier<Item> LITHERITE_HELMET = registerItem("litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.HELMET, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HELMET = registerItem("burning_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.HELMET, BURNING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HELMET = registerItem("frozen_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.HELMET, FROZEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HELMET = registerItem("smoldering_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.HELMET, SMOLDERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HELMET = registerItem("frostbitten_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.HELMET, FROSTBITTEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HELMET = registerItem("infused_litherite_helmet", properties ->
            new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.HELMET, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HELMET = registerItem("withering_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.HELMET, WITHERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_HELMET = registerItem("odysium_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.HELMET, ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HELMET = registerItem("enhanced_odysium_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.HELMET, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = registerItem("litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.CHESTPLATE, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = registerItem("burning_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.CHESTPLATE, BURNING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = registerItem("frozen_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.CHESTPLATE, FROZEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_CHESTPLATE = registerItem("smoldering_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.CHESTPLATE, SMOLDERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_CHESTPLATE = registerItem("frostbitten_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.CHESTPLATE, FROSTBITTEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = registerItem("infused_litherite_chestplate", properties ->
            new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.CHESTPLATE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CHESTPLATE = registerItem("withering_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.CHESTPLATE, WITHERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_CHESTPLATE = registerItem("odysium_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.CHESTPLATE, ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_CHESTPLATE = registerItem("enhanced_odysium_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.CHESTPLATE, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = registerItem("litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.LEGGINGS, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = registerItem("burning_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.LEGGINGS, BURNING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = registerItem("frozen_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.LEGGINGS, FROZEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_LEGGINGS = registerItem("smoldering_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.LEGGINGS, SMOLDERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_LEGGINGS = registerItem("frostbitten_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.LEGGINGS, FROSTBITTEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = registerItem("infused_litherite_leggings", properties ->
            new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.LEGGINGS, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LEGGINGS = registerItem("withering_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.LEGGINGS, WITHERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_LEGGINGS = registerItem("odysium_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.LEGGINGS, ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_LEGGINGS = registerItem("enhanced_odysium_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.LEGGINGS, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = registerItem("litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.BOOTS, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = registerItem("burning_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.BOOTS, BURNING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = registerItem("frozen_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.BOOTS, FROZEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_BOOTS = registerItem("smoldering_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.BOOTS, SMOLDERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_BOOTS = registerItem("frostbitten_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.BOOTS, FROSTBITTEN.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = registerItem("infused_litherite_boots", properties ->
            new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.BOOTS, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_BOOTS = registerItem("withering_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.BOOTS, WITHERING.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_BOOTS = registerItem("odysium_boots", properties ->
            new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.BOOTS, ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_BOOTS = registerItem("enhanced_odysium_boots", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.BOOTS, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_HORSE_ARMOR = registerItem("odysium_horse_armor", properties ->
            new ModArmorItem(ModArmorMaterials.ODYSIUM, Item.Properties::horseArmor, ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HORSE_ARMOR = registerItem("enhanced_odysium_horse_armor", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, Item.Properties::horseArmor, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_NAUTILUS_ARMOR = registerItem("odysium_nautilus_armor", properties ->
            new ModArmorItem(ModArmorMaterials.ODYSIUM, Item.Properties::nautilusArmor, ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_NAUTILUS_ARMOR = registerItem("enhanced_odysium_nautilus_armor", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, Item.Properties::nautilusArmor, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static void register() {

    }
}
