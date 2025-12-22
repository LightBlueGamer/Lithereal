package org.lithereal.item.burning;

import net.minecraft.world.item.Tier;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityShovelItem;

public class BurningLitheriteShovelItem extends AbilityShovelItem implements BurningItem {
    public BurningLitheriteShovelItem(Ability ability, Tier tier, Properties properties) {
        super(ability, tier, properties);
    }
    public BurningLitheriteShovelItem(Tier tier, Properties properties) {
        this(Ability.BURNING, tier, properties);
    }
}