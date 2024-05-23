package org.lithereal.fabric.item.custom.ability;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.fabric.item.custom.HammerType;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ability.AbilityHammerItem;

public class AbilityHammerItemWithType extends AbilityHammerItem implements HammerType {
    public AbilityHammerItemWithType(Ability ability, Tier tier, int damage, float attackSpeed, Properties properties) {
        super(ability, tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
