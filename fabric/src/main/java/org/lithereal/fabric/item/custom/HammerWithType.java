package org.lithereal.fabric.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.item.custom.Hammer;

public class HammerWithType extends Hammer implements HammerType {
    public HammerWithType(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
