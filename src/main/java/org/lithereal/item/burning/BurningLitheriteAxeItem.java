package org.lithereal.item.burning;

import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.AbilityAxeItem;
import org.lithereal.item.ability.Ability;

public class BurningLitheriteAxeItem extends AbilityAxeItem implements BurningItem {
    public BurningLitheriteAxeItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(ability, toolMaterial, properties);
    }
    public BurningLitheriteAxeItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}