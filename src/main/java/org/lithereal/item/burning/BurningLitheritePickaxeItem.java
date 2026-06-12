package org.lithereal.item.burning;

import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.AbilityPickaxeItem;
import org.lithereal.item.ability.Ability;

public class BurningLitheritePickaxeItem extends AbilityPickaxeItem implements BurningItem {
    public BurningLitheritePickaxeItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(ability, toolMaterial, properties);
    }
    public BurningLitheritePickaxeItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}