package org.lithereal.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.lithereal.Lithereal;

public class ModDimensions {
    public static final ResourceKey<Level> ETHEREAL_CORE = ResourceKey.create(Registries.DIMENSION, Lithereal.id("ethereal_core"));
}
