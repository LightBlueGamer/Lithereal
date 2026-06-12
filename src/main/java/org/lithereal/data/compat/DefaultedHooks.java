package org.lithereal.data.compat;

import net.atlas.defaulted.Defaulted;
import net.minecraft.world.item.ToolMaterial;

public class DefaultedHooks {
    public static ToolMaterial registerToolMaterial(String name, ToolMaterial toolMaterial) {
        Defaulted.baseTiers.put(name, toolMaterial);
        return toolMaterial;
    }
}
