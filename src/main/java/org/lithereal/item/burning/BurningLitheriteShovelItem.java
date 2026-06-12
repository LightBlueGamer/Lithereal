package org.lithereal.item.burning;

import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityShovelItem;

public class BurningLitheriteShovelItem extends AbilityShovelItem implements BurningItem {
    public BurningLitheriteShovelItem(Ability ability, ToolMaterial toolMaterial, Properties properties) {
        super(ability, toolMaterial, properties);
    }
    public BurningLitheriteShovelItem(ToolMaterial toolMaterial, Properties properties) {
        this(Ability.BURNING, toolMaterial, properties);
    }
}