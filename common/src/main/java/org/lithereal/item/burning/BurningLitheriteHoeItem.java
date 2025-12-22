package org.lithereal.item.burning;

import net.minecraft.world.item.Tier;
import org.lithereal.item.ability.AbilityHoeItem;
import org.lithereal.item.ability.Ability;

public class BurningLitheriteHoeItem extends AbilityHoeItem implements BurningItem {
    public BurningLitheriteHoeItem(Ability ability, Tier tier, Properties properties) {
        super(ability, tier, properties);
    }
    public BurningLitheriteHoeItem(Tier tier, Properties properties) {
        this(Ability.BURNING, tier, properties);
    }
}