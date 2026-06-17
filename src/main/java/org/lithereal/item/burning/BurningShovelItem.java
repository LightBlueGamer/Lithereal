package org.lithereal.item.burning;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.Ability;

public class BurningShovelItem extends ShovelItem implements BurningItem {
    public BurningShovelItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(toolMaterial, 1.5f, -3f, ability.createToolComponent(properties));
    }
    public BurningShovelItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}