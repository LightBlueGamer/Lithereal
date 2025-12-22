package org.lithereal.item.burning;

import net.minecraft.world.item.Tier;
import org.lithereal.item.ability.AbilityPickaxeItem;
import org.lithereal.item.ability.Ability;

public class BurningLitheritePickaxeItem extends AbilityPickaxeItem implements BurningItem {
    public BurningLitheritePickaxeItem(Ability ability, Tier tier, Properties properties) {
        super(ability, tier, properties);
    }
    public BurningLitheritePickaxeItem(Tier tier, Properties properties) {
        this(Ability.BURNING, tier, properties);
    }
}