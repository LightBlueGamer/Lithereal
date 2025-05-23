package org.lithereal.data.compat;

import net.atlas.combatify.Combatify;
import net.atlas.combatify.item.WeaponType;

import static net.atlas.combatify.item.WeaponType.createBasic;

public class ModWeaponType {
    public static final WeaponType HAMMER = createBasic("hammer", 3.0, -0.75, 0.5);
    public static final WeaponType WAR_HAMMER = createBasic("war_hammer", 3.0, -1.5, 0);
    public static void init() {
        Combatify.defineDefaultWeaponType(HAMMER);
        Combatify.defineDefaultWeaponType(WAR_HAMMER);
    }
}
