package org.lithereal.fabric.world.item.ability;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.fabric.world.item.HammerType;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityHammerItem;

public class AbilityHammerItemWithType extends AbilityHammerItem implements HammerType {
    public AbilityHammerItemWithType(Ability ability, Tier tier, int damage, float attackSpeed, Properties properties) {
        super(ability, tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
