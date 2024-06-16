package org.lithereal.fabric.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.world.item.HammerItem;

public class HammerItemWithType extends HammerItem implements HammerType {
    public HammerItemWithType(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
