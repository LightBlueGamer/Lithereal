package org.lithereal.item.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class ModArmorItem extends Item {
    private final ArmorMaterial armorMaterial;

    public ModArmorItem(ArmorMaterial armorMaterial, ArmorType type, Properties properties) {
        super(properties.humanoidArmor(armorMaterial, type));
        this.armorMaterial = armorMaterial;
    }

    public ArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }
}