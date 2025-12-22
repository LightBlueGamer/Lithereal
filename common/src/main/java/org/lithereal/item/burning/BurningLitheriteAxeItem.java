package org.lithereal.item.burning;

import net.minecraft.world.item.Tier;
import org.lithereal.item.ability.AbilityAxeItem;
import org.lithereal.item.ability.Ability;

public class BurningLitheriteAxeItem extends AbilityAxeItem implements BurningItem {
    public BurningLitheriteAxeItem(Ability ability, Tier tier, Properties properties) {
        super(ability, tier, properties);
    }
    public BurningLitheriteAxeItem(Tier tier, Properties properties) {
        this(Ability.BURNING, tier, properties);
    }
}