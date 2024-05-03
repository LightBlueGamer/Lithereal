package org.lithereal.fabric.item.custom.infused;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.fabric.item.custom.HammerType;
import org.lithereal.item.custom.infused.InfusedLitheriteHammer;

public class InfusedLitheriteHammerWithType extends InfusedLitheriteHammer implements HammerType {
    public InfusedLitheriteHammerWithType(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public Item self() {
        return this;
    }
}
