package org.lithereal.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.lithereal.Lithereal;
import org.lithereal.tags.ModTags;

import java.util.EnumMap;
import java.util.function.BiFunction;

public class ModArmorMaterials {
    public static final ArmorMaterial LITHERITE = register("litherite", 25, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,2.0F, 0F, ModTags.LITHERITE_REPAIR_ITEMS);

    public static final ArmorMaterial BURNING_LITHERITE = register("burning_litherite", 25, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,2.0F, 0F, ModTags.BURNING_LITHERITE_REPAIR_ITEMS);

    public static final ArmorMaterial FROZEN_LITHERITE = register("frozen_litherite", 25, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,2.0F, 0F, ModTags.FROZEN_LITHERITE_REPAIR_ITEMS);

    public static final ArmorMaterial SMOLDERING_LITHERITE = register("smoldering_litherite", 25, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,2.0F, 0F, ModTags.BURNING_LITHERITE_REPAIR_ITEMS);

    public static final ArmorMaterial FROSTBITTEN_LITHERITE = register("frostbitten_litherite", 25, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,2.0F, 0F, ModTags.FROZEN_LITHERITE_REPAIR_ITEMS);

    public static final ArmorMaterial INFUSED_LITHERITE = register("infused_litherite", 25, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,2.0F, 0F, ModTags.INFUSED_LITHERITE_REPAIR_ITEMS);

    public static final ArmorMaterial WITHERING_LITHERITE = register("withering_litherite", 25, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,2.0F, 0F, ModTags.WITHERING_LITHERITE_REPAIR_ITEMS);

    public static final ArmorMaterial ODYSIUM = register("odysium", 50, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 4);
        map.put(ArmorType.LEGGINGS, 7);
        map.put(ArmorType.CHESTPLATE, 9);
        map.put(ArmorType.HELMET, 4);
    }), 25, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.2F, ModTags.ODYSIUM_REPAIR_ITEMS);

    public static final ArmorMaterial ENHANCED_ODYSIUM = register("enhanced_odysium", 50, Util.make(new EnumMap<>(ArmorType.class), (map) -> {
        map.put(ArmorType.BOOTS, 4);
        map.put(ArmorType.LEGGINGS, 7);
        map.put(ArmorType.CHESTPLATE, 9);
        map.put(ArmorType.HELMET, 4);
    }), 25, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.2F, ModTags.ODYSIUM_REPAIR_ITEMS);


    public static final BiFunction<ArmorType, Integer, Integer> HEALTH_FUNCTION_FOR_TYPE = ArmorType::getDurability;

    private static ArmorMaterial register(String name, int durability, EnumMap<ArmorType, Integer> defenseBase, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, TagKey<Item> repairIngredient) {
        return register(durability, defenseBase, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, ResourceKey.create(EquipmentAssets.ROOT_ID, Lithereal.id(name)));
    }

    private static ArmorMaterial register(int durability, EnumMap<ArmorType, Integer> defenseBase, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, TagKey<Item> repairIngredient, ResourceKey<EquipmentAsset> assetId) {
        EnumMap<ArmorType, Integer> defense = new EnumMap<>(ArmorType.class);

        for (ArmorType type : ArmorType.values()) {
            defense.put(type, defenseBase.get(type));
        }

        return new ArmorMaterial(durability, defense, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, assetId);
    }
    public static void register() {
    }
}
