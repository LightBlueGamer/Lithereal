package org.lithereal.item.burning;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.Ability;

public class BurningHoeItem extends HoeItem implements BurningItem {
    public BurningHoeItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(toolMaterial, -toolMaterial.attackDamageBonus(), 0, ability.createToolComponent(properties));
    }
    public BurningHoeItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}