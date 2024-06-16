package org.lithereal.fabric.world.item;

import net.atlas.combatify.Combatify;
import net.atlas.combatify.extensions.WeaponWithType;
import net.atlas.combatify.item.WeaponType;
import org.lithereal.fabric.data.compat.ModWeaponType;

public interface HammerType extends WeaponWithType {
    @Override
    default WeaponType getWeaponType() {
        if (Combatify.ITEMS != null && Combatify.ITEMS.configuredItems.containsKey(self())) {
            WeaponType type = Combatify.ITEMS.configuredItems.get(self()).type;
            if (type != null) {
                return type;
            }
        }

        return ModWeaponType.HAMMER;
    }
}
