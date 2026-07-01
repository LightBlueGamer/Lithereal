package org.lithereal.item.ability;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import org.lithereal.Lithereal;
import org.lithereal.core.component.ModComponents;
import org.lithereal.core.component.Smelter;
import org.lithereal.core.component.SpecialAbility;
import org.lithereal.entity.ModDamageTypes;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.mob_effect.ModMobEffects;

import java.util.*;

import static org.lithereal.util.CommonUtils.CROSS_COMPATIBLE_LITHERITE;
import static org.lithereal.util.CommonUtils.applyHammerProperties;

public enum Ability {
    BURNING(new ThermalAbility(0, 1, ThermalAbility.EffectDetails.BURNING,
            List.of(ModArmorMaterials.BURNING_LITHERITE.assetId(), ModArmorMaterials.SMOLDERING_LITHERITE.assetId()),
            List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0)))),
    FROZEN(new ThermalAbility(0, 1, ThermalAbility.EffectDetails.FREEZING,
            List.of(ModArmorMaterials.FROZEN_LITHERITE.assetId(), ModArmorMaterials.FROSTBITTEN_LITHERITE.assetId()),
            List.of(new MobEffectInstance(Lithereal.asHolder(ModMobEffects.FREEZE_RESISTANCE), 200, 0)))),
    SMOLDERING(new ThermalAbility(2, 1.5F, ThermalAbility.EffectDetails.SMOLDERING,
            CROSS_COMPATIBLE_LITHERITE,
            List.of(new MobEffectInstance(Lithereal.asHolder(ModMobEffects.PROTECTED), 200, 0),
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0)))),
    FROSTBITTEN(new ThermalAbility(1, 1.5F, ThermalAbility.EffectDetails.FROSTBURN,
            CROSS_COMPATIBLE_LITHERITE,
            List.of(new MobEffectInstance(Lithereal.asHolder(ModMobEffects.PROTECTED), 200, 0),
                    new MobEffectInstance(Lithereal.asHolder(ModMobEffects.FREEZE_RESISTANCE), 200, 0)))),
    WITHERING(new StandardAbility(CROSS_COMPATIBLE_LITHERITE,
            Collections.singletonList(new MobEffectInstance(MobEffects.WITHER, 200, 1)),
            Collections.singletonList(new MobEffectInstance(MobEffects.WITHER, 200)))),
    INFUSED(new InfusedAbility(CROSS_COMPATIBLE_LITHERITE)),
    ODYSIUM(new StandardAbility(List.of(ModArmorMaterials.ODYSIUM.assetId(), ModArmorMaterials.ENHANCED_ODYSIUM.assetId()),
            Collections.emptyList(),
            List.of(new MobEffectInstance(MobEffects.RESISTANCE, 200, 0),
                    new MobEffectInstance(MobEffects.HASTE, 200, 0)))),
    ENHANCED_ODYSIUM(new StandardAbility(Collections.singletonList(ModArmorMaterials.ENHANCED_ODYSIUM.assetId()),
            Collections.singletonList(new MobEffectInstance(Lithereal.asHolder(ModMobEffects.RETRIBUTION), 150)),
            List.of(new MobEffectInstance(MobEffects.RESISTANCE, 200, 1),
                    new MobEffectInstance(MobEffects.HASTE, 200, 0),
                    new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 200, 2))));
    public final IAbility ability;
    Ability(IAbility ability) {
        this.ability = ability;
    }
    public Item.Properties createToolComponent(Item.Properties properties) {
        properties.component(ModComponents.SPECIAL_ABILITY.get(), new SpecialAbility(ability, SpecialAbility.Type.TOOL));
        return switch (this) {
            case BURNING -> properties.fireResistant().component(ModComponents.SMELTER.get(), Smelter.DEFAULT).delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.BURNING_ITEM);
            case SMOLDERING -> properties.fireResistant().component(ModComponents.SMELTER.get(), Smelter.DEFAULT).delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.BURNING_ITEM).rarity(Rarity.UNCOMMON);
            case FROZEN -> properties.delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.FREEZING_ITEM);
            case FROSTBITTEN -> properties.fireResistant().delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.FROSTBITTEN_ITEM).rarity(Rarity.UNCOMMON);
            case ODYSIUM -> properties.fireResistant().rarity(Rarity.UNCOMMON);
            case ENHANCED_ODYSIUM -> properties.fireResistant().delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.HOLY_ITEM).rarity(Rarity.UNCOMMON);
            default -> properties;
        };
    }
    public Item.Properties createSpearComponent(Item.Properties properties, ToolMaterial material, float attackDuration, float damageMultiplier, float delay, float dismountTime, float dismountThreshold, float knockbackTime, float knockbackThreshold, float damageTime, float damageThreshold) {
        properties.component(ModComponents.SPECIAL_ABILITY.get(), new SpecialAbility(ability, SpecialAbility.Type.TOOL))
                .spear(material,
                        attackDuration, damageMultiplier, delay, dismountTime, dismountThreshold, knockbackTime, knockbackThreshold, damageTime, damageThreshold);
        return switch (this) {
            case BURNING -> properties.fireResistant().delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.BURNING_SPEAR);
            case SMOLDERING -> properties.fireResistant().delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.BURNING_SPEAR).rarity(Rarity.UNCOMMON);
            case FROZEN -> properties.delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.FREEZING_SPEAR);
            case FROSTBITTEN -> properties.fireResistant().delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.FROSTBITTEN_SPEAR).rarity(Rarity.UNCOMMON);
            case ODYSIUM -> properties.fireResistant().rarity(Rarity.UNCOMMON);
            case ENHANCED_ODYSIUM -> properties.fireResistant().delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.HOLY_SPEAR).rarity(Rarity.UNCOMMON);
            default -> properties;
        };
    }
    public Item.Properties createPickaxeComponent(ToolMaterial toolMaterial, Item.Properties properties) {
        return createToolComponent(properties).pickaxe(toolMaterial, 1, -2.8F);
    }
    public Item.Properties createSwordComponent(ToolMaterial toolMaterial, Item.Properties properties) {
        return createToolComponent(properties).sword(toolMaterial, 3, -2.4f);
    }
    public Item.Properties createHammerComponent(ToolMaterial toolMaterial, int damage, float attackSpeed, int weaponLevel, Item.Properties properties) {
        return applyHammerProperties(toolMaterial, damage, attackSpeed, weaponLevel, createToolComponent(properties));
    }
    public Item.Properties createArmorComponent(Item.Properties properties) {
        properties.component(ModComponents.SPECIAL_ABILITY.get(), new SpecialAbility(ability, SpecialAbility.Type.ARMOR));
        return switch (this) {
            case BURNING -> properties.fireResistant();
            case SMOLDERING, FROSTBITTEN, ODYSIUM, ENHANCED_ODYSIUM -> properties.fireResistant().rarity(Rarity.UNCOMMON);
            default -> properties;
        };
    }
}
