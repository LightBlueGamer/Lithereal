package org.lithereal.fabric.item.custom.burning;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.fabric.item.custom.HammerType;
import org.lithereal.item.custom.burning.BurningLitheriteHammerItem;

public class BurningLitheriteHammerItemWithType extends BurningLitheriteHammerItem implements HammerType {
    public BurningLitheriteHammerItemWithType(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
