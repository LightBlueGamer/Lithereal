package org.lithereal.item.burning;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.Ability;

public class BurningPickaxeItem extends Item implements BurningItem {
    public BurningPickaxeItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(ability.createPickaxeComponent(toolMaterial, properties));
    }
    public BurningPickaxeItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}