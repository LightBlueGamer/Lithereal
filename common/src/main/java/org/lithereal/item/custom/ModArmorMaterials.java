package org.lithereal.item.custom;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.item.ModItems;

import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ARMOR_MATERIAL);
    public static final Holder<ArmorMaterial> LITHERITE = register("litherite", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,3.0F, 0F, () -> Ingredient.of(LitherealExpectPlatform.getLitheriteItem()));

    public static final Holder<ArmorMaterial> BURNING_LITHERITE = register("burning_litherite", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,3.0F, 0F, () -> Ingredient.of(ModItems.BURNING_LITHERITE_CRYSTAL.get()));

    public static final Holder<ArmorMaterial> FROZEN_LITHERITE = register("frozen_litherite", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,3.0F, 0F, () -> Ingredient.of(ModItems.FROZEN_LITHERITE_CRYSTAL.get()));

    public static final Holder<ArmorMaterial> INFUSED_LITHERITE = register("infused_litherite", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,3.0F, 0F, () -> Ingredient.of(ModItems.INFUSED_LITHERITE_INGOT.get()));

    public static final Holder<ArmorMaterial> WITHERING_LITHERITE = register("withering_litherite", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND,3.0F, 0F, () -> Ingredient.of(ModItems.WITHERING_LITHERITE_CRYSTAL.get()));
    public static final Holder<ArmorMaterial> ODYSIUM = register("odysium", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 4);
        map.put(ArmorItem.Type.LEGGINGS, 7);
        map.put(ArmorItem.Type.CHESTPLATE, 9);
        map.put(ArmorItem.Type.HELMET, 4);
    }), 25, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.2F, () -> Ingredient.of(ModItems.ODYSIUM_INGOT.get()));


    public static final BiFunction<ArmorItem.Type, Integer, Integer> HEALTH_FUNCTION_FOR_TYPE = ArmorItem.Type::getDurability;

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defenseBase, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(new ResourceLocation(name)));
        return register(name, defenseBase, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, layers);
    }

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defenseBase, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        ArmorItem.Type[] var9 = ArmorItem.Type.values();

        for (ArmorItem.Type type : var9) {
            defense.put(type, defenseBase.get(type));
        }

        return MATERIALS.register(name, () -> new ArmorMaterial(defense, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }
    public static void register() {
        MATERIALS.register();
    }
}
