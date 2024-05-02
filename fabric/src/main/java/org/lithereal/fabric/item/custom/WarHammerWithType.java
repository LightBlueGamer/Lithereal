package org.lithereal.fabric.item.custom;

import net.atlas.combatify.Combatify;
import net.atlas.combatify.extensions.WeaponWithType;
import net.atlas.combatify.item.WeaponType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.fabric.compat.ModWeaponType;
import org.lithereal.item.custom.WarHammer;

public class WarHammerWithType extends WarHammer implements WeaponWithType {
    public WarHammerWithType(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public WeaponType getWeaponType() {
        if (Combatify.ITEMS != null && Combatify.ITEMS.configuredItems.containsKey(this)) {
            WeaponType type = Combatify.ITEMS.configuredItems.get(this).type;
            if (type != null) {
                return type;
            }
        }

        return ModWeaponType.WAR_HAMMER;
    }

    @Override
    public Item self() {
        return this;
    }
}
