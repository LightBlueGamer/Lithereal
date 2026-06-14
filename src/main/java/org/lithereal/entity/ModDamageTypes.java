package org.lithereal.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.lithereal.Lithereal;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> BURN = Lithereal.key(Registries.DAMAGE_TYPE, "burn");
    public static final ResourceKey<DamageType> FROSTBURN = Lithereal.key(Registries.DAMAGE_TYPE, "frostburn");
    public static final ResourceKey<DamageType> FROST = Lithereal.key(Registries.DAMAGE_TYPE, "frost");
    public static final ResourceKey<DamageType> HOLY = Lithereal.key(Registries.DAMAGE_TYPE, "holy");
}
