package org.lithereal.fabric.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.item.custom.HammerItem;

public class HammerItemWithType extends HammerItem implements HammerType {
    public HammerItemWithType(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
