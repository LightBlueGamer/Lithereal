package org.lithereal.util;

import net.minecraft.world.entity.Entity;
import org.lithereal.tags.ModTags;
import org.lithereal.world.ModDimensions;

public class EtherealUtils {
    public static boolean isEntityPhantom(Entity entity) {
        return !entity.getType().is(ModTags.IS_ETHEREAL) && entity.level().dimension().equals(ModDimensions.ETHEREAL_CORE);
    }
}
