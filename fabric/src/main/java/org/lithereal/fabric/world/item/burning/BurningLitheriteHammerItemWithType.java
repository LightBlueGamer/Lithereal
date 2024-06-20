package org.lithereal.fabric.world.item.burning;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.item.burning.BurningLitheriteHammerItem;
import org.lithereal.fabric.world.item.HammerType;

public class BurningLitheriteHammerItemWithType extends BurningLitheriteHammerItem implements HammerType {
    public BurningLitheriteHammerItemWithType(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
