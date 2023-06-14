package org.litecraft.lithereal.item.custom;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.litecraft.lithereal.item.ModItems;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    LITHERITE("litherite", 25,Util.make(new EnumMap<>(EquipmentSlot.class), (p_266655_) -> {
        p_266655_.put(EquipmentSlot.FEET, 4);
        p_266655_.put(EquipmentSlot.LEGS, 7);
        p_266655_.put(EquipmentSlot.CHEST, 9);
        p_266655_.put(EquipmentSlot.HEAD, 4);
    }), 19, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ModItems.LITHERITE_CRYSTAL.get());
    }),

    COOLED_LITHERITE("cooled_litherite", 25,Util.make(new EnumMap<>(EquipmentSlot.class), (p_266655_) -> {
        p_266655_.put(EquipmentSlot.FEET, 4);
        p_266655_.put(EquipmentSlot.LEGS, 7);
        p_266655_.put(EquipmentSlot.CHEST, 9);
        p_266655_.put(EquipmentSlot.HEAD, 4);
    }), 19, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ModItems.COOLED_LITHERITE_CRYSTAL.get());
    }),

    HEATED_LITHERITE("heated_litherite", 25,Util.make(new EnumMap<>(EquipmentSlot.class), (p_266655_) -> {
        p_266655_.put(EquipmentSlot.FEET, 4);
        p_266655_.put(EquipmentSlot.LEGS, 7);
        p_266655_.put(EquipmentSlot.CHEST, 9);
        p_266655_.put(EquipmentSlot.HEAD, 4);
    }), 19, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ModItems.HEATED_LITHERITE_CRYSTAL.get());
    }),

    WITHERING_LITHERITE("withering_litherite", 25,Util.make(new EnumMap<>(EquipmentSlot.class), (p_266655_) -> {
        p_266655_.put(EquipmentSlot.FEET, 4);
        p_266655_.put(EquipmentSlot.LEGS, 7);
        p_266655_.put(EquipmentSlot.CHEST, 9);
        p_266655_.put(EquipmentSlot.HEAD, 4);
    }), 19, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ModItems.HEATED_LITHERITE_CRYSTAL.get());
    });

    private static final EnumMap<EquipmentSlot, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(EquipmentSlot.class), (p_266653_) -> {
        p_266653_.put(EquipmentSlot.FEET, 13);
        p_266653_.put(EquipmentSlot.LEGS, 15);
        p_266653_.put(EquipmentSlot.CHEST, 16);
        p_266653_.put(EquipmentSlot.HEAD, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<EquipmentSlot, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private ModArmorMaterials(String p_268171_, int p_268303_, EnumMap<EquipmentSlot, Integer> p_267941_, int p_268086_, SoundEvent p_268145_, float p_268058_, float p_268180_, Supplier<Ingredient> p_268256_) {
        this.name = p_268171_;
        this.durabilityMultiplier = p_268303_;
        this.protectionFunctionForType = p_267941_;
        this.enchantmentValue = p_268086_;
        this.sound = p_268145_;
        this.toughness = p_268058_;
        this.knockbackResistance = p_268180_;
        this.repairIngredient = new LazyLoadedValue<>(p_268256_);
    }

    public int getDurabilityForType(EquipmentSlot p_266745_) {
        return HEALTH_FUNCTION_FOR_TYPE.get(p_266745_) * this.durabilityMultiplier;
    }

    public int getDefenseForType(EquipmentSlot p_266752_) {
        return this.protectionFunctionForType.get(p_266752_);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot p_40410_) {
        return 0;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot p_40411_) {
        return 0;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public String getSerializedName() {
        return this.name;
    }
}