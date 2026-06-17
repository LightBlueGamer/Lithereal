package org.lithereal.item.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.BiFunction;

public class ModArmorItem extends Item {
    public ModArmorItem(ArmorMaterial armorMaterial, ArmorType type, Properties properties) {
        this(armorMaterial, (properties1, armorMaterial1) -> properties1.humanoidArmor(armorMaterial1, type), properties);
    }

    public ModArmorItem(ArmorMaterial armorMaterial, BiFunction<Properties, ArmorMaterial, Properties> toMakeArmor, Properties properties) {
        super(toMakeArmor.apply(properties, armorMaterial));
    }
}