package org.lithereal.item.burning;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.Ability;

public class BurningAxeItem extends AxeItem implements BurningItem {
    public BurningAxeItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(toolMaterial, 5, -3, ability.createToolComponent(properties));
    }
    public BurningAxeItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}