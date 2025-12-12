package org.lithereal.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.lithereal.Lithereal;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> BURN = ResourceKey.create(Registries.DAMAGE_TYPE, Lithereal.id("burn"));
    public static final ResourceKey<DamageType> FROSTBURN = ResourceKey.create(Registries.DAMAGE_TYPE, Lithereal.id("frostburn"));
    public static final ResourceKey<DamageType> FROST = ResourceKey.create(Registries.DAMAGE_TYPE, Lithereal.id("frost"));
}
