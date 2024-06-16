package org.lithereal.fabric.world.item.infused;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.fabric.world.item.HammerType;
import org.lithereal.world.item.infused.InfusedLitheriteHammerItem;

public class InfusedLitheriteHammerItemWithType extends InfusedLitheriteHammerItem implements HammerType {
    public InfusedLitheriteHammerItemWithType(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
