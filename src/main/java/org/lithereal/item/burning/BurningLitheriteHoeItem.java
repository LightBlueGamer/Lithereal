package org.lithereal.item.burning;

import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.AbilityHoeItem;
import org.lithereal.item.ability.Ability;

public class BurningLitheriteHoeItem extends AbilityHoeItem implements BurningItem {
    public BurningLitheriteHoeItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(ability, toolMaterial, properties);
    }
    public BurningLitheriteHoeItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}