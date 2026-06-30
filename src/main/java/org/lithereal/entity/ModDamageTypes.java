package org.lithereal.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.lithereal.Lithereal;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> BURN = Lithereal.key(Registries.DAMAGE_TYPE, "burn");
    public static final ResourceKey<DamageType> BURNING_ITEM = Lithereal.key(Registries.DAMAGE_TYPE, "burning_item");
    public static final ResourceKey<DamageType> BURNING_SPEAR = Lithereal.key(Registries.DAMAGE_TYPE, "burning_spear");
    public static final ResourceKey<DamageType> FROSTBURN = Lithereal.key(Registries.DAMAGE_TYPE, "frostburn");
    public static final ResourceKey<DamageType> FROSTBITTEN_ITEM = Lithereal.key(Registries.DAMAGE_TYPE, "frostbitten_item");
    public static final ResourceKey<DamageType> FROSTBITTEN_SPEAR = Lithereal.key(Registries.DAMAGE_TYPE, "frostbitten_spear");
    public static final ResourceKey<DamageType> FROST = Lithereal.key(Registries.DAMAGE_TYPE, "frost");
    public static final ResourceKey<DamageType> FREEZING_ITEM = Lithereal.key(Registries.DAMAGE_TYPE, "freezing_item");
    public static final ResourceKey<DamageType> FREEZING_SPEAR = Lithereal.key(Registries.DAMAGE_TYPE, "freezing_spear");
    public static final ResourceKey<DamageType> HOLY = Lithereal.key(Registries.DAMAGE_TYPE, "holy");
    public static final ResourceKey<DamageType> HOLY_ITEM = Lithereal.key(Registries.DAMAGE_TYPE, "holy_item");
    public static final ResourceKey<DamageType> HOLY_SPEAR = Lithereal.key(Registries.DAMAGE_TYPE, "holy_spear");
}
