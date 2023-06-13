package com.haru.lithereal.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import com.haru.lithereal.item.ModItems;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    LITHERITE("litherite", 25, new int[]{4, 7, 9, 4}, 19, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ModItems.LITHERITE_CRYSTAL.get());
    }),
    HEATED_LITHERITE("heated_litherite", 25, new int[]{4, 7, 9, 4}, 19, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ModItems.LITHERITE_CRYSTAL.get());
    }),
    COOLED_LITHERITE("cooled_litherite", 25, new int[]{4, 7, 9, 4}, 19, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ModItems.LITHERITE_CRYSTAL.get());
    });

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;
    private ModArmorMaterials(String p_268171_, int p_268303_, int[] p_267941_, int p_268086_, SoundEvent p_268145_, float p_268058_, float p_268180_, Supplier<Ingredient> p_268256_) {
        this.name = getName();
        this.durabilityMultiplier = p_268303_;
        this.slotProtections = p_267941_;
        this.enchantmentValue = getEnchantmentValue();
        this.sound = getEquipSound();
        this.toughness = getToughness();
        this.knockbackResistance = getKnockbackResistance();
        this.repairIngredient = new LazyLoadedValue<>(this::getRepairIngredient);
    }

    public int getDurabilityForType(EquipmentSlot p_40484_) {
        return HEALTH_PER_SLOT[p_40484_.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForType(EquipmentSlot p_40487_) {
        return this.slotProtections[p_40487_.getIndex()] * this.durabilityMultiplier;
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
    public SoundEvent getEquipSound() { return this.sound;}
    public Ingredient getRepairIngredient() { return this.repairIngredient.get();}
    public String getName() {
        return this.name;
    }
    public float getToughness() {
        return this.toughness;
    }
    public float getKnockbackResistance() { return this.knockbackResistance;}
}