package org.lithereal.core.component.miner_shape;

import net.minecraft.resources.ResourceKey;
import org.lithereal.Lithereal;
import org.lithereal.registry.ModRegistries;

public class MinerShapes {
    public static final ResourceKey<MinerShape> RECTANGULAR_PRISM = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "rectangular_prism");
    public static final ResourceKey<MinerShape> CYLINDER = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "cylinder");
    public static final ResourceKey<MinerShape> SPHERE = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "sphere");
    public static final ResourceKey<MinerShape> OCTAHEDRON = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "octahedron");
    public static final ResourceKey<MinerShape> DIAMOND_PRISM = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "diamond_prism");
    public static final ResourceKey<MinerShape> CROSS = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "cross");
    public static final ResourceKey<MinerShape> TRICROSS = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "tricross");
    public static final ResourceKey<MinerShape> STRAIGHT = Lithereal.key(ModRegistries.MINER_SHAPE_REGISTRY, "straight");
}
