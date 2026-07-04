package org.lithereal.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public record SpearData(float attackDuration, float damageMultiplier, float delay, float dismountTime, float dismountThreshold, float knockbackTime, float knockbackThreshold, float damageTime, float damageThreshold) {
    public Item.Properties consume(Item.Properties properties, ToolMaterial toolMaterial) {
        return properties.spear(toolMaterial, attackDuration, damageMultiplier, delay, dismountTime, dismountThreshold, knockbackTime, knockbackThreshold, damageTime, damageThreshold);
    }
}
