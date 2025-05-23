package org.lithereal.data.compat;

import net.atlas.defaulted.Defaulted;
import org.lithereal.item.ModTier;

public class DefaultedHooks {
    public static ModTier registerTier(String name, ModTier tier) {
        Defaulted.baseTiers.put(name, tier);
        return tier;
    }
}
