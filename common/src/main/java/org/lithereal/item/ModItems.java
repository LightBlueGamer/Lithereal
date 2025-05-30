package org.lithereal.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.item.ability.*;
import org.lithereal.item.burning.*;
import org.lithereal.item.infused.*;
import org.lithereal.sounds.ModSounds;
import org.lithereal.tags.ModTags;
import org.lithereal.item.compat.CompatInit;
import org.lithereal.item.obscured.MysteriousItem;
import org.lithereal.util.BaseBossEssence;


import java.util.Collections;
import java.util.List;

import static dev.architectury.platform.Platform.isModLoaded;
import static org.lithereal.item.ability.Ability.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> PHANTOM_DIAMOND = ITEMS.register("phantom_diamond", () ->
            new MysteriousItem(new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_INGOT = ITEMS.register("odysium_ingot", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CRYSTAL = ITEMS.register("burning_litherite_crystal", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CRYSTAL = ITEMS.register("frozen_litherite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_INGOT = ITEMS.register("infused_litherite_ingot", () ->
            new InfusedLitheriteItem(new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CRYSTAL = ITEMS.register("withering_litherite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> CHARGED_LITHERITE_CRYSTAL = ITEMS.register("charged_litherite_crystal", () ->
            new Item(new Item.Properties()));

    public static final Tier LITHERITE = ModTier.create("litherite", 4, 1791, 11.0F, 4.0F, 20, () -> Ingredient.of(LitherealExpectPlatform.getLitheriteItem()), ModTags.INCORRECT_FOR_LITHERITE_TOOL);

    public static final Tier ODYSIUM = ModTier.create("odysium", 5, 2431, 13.0F, 5.0F, 22, () -> Ingredient.of(ODYSIUM_INGOT.get()), ModTags.INCORRECT_FOR_ODYSIUM_TOOL);

    public static final RegistrySupplier<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword", () ->
            new SwordItem(LITHERITE, new Item.Properties().attributes(SwordItem.createAttributes(LITHERITE, 3, -2.4f))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SWORD = ITEMS.register("burning_litherite_sword", () ->
            new AbilitySwordItem(BURNING, LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SWORD = ITEMS.register("frozen_litherite_sword", () ->
            new AbilitySwordItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SWORD = ITEMS.register("infused_litherite_sword", () ->
            new InfusedLitheriteSwordItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SWORD = ITEMS.register("withering_litherite_sword", () ->
            new AbilitySwordItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_SWORD = ITEMS.register("odysium_sword", () ->
            new AbilitySwordItem(ENHANCED, ODYSIUM, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe", () ->
            new PickaxeItem(LITHERITE, new Item.Properties().attributes(PickaxeItem.createAttributes(LITHERITE, 1, -2.8F))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_PICKAXE = ITEMS.register("burning_litherite_pickaxe", () ->
            new BurningLitheritePickaxeItem(LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_PICKAXE = ITEMS.register("frozen_litherite_pickaxe", () ->
            new AbilityPickaxeItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_PICKAXE = ITEMS.register("infused_litherite_pickaxe", () ->
            new InfusedLitheritePickaxeItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_PICKAXE = ITEMS.register("withering_litherite_pickaxe", () ->
            new AbilityPickaxeItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_PICKAXE = ITEMS.register("odysium_pickaxe", () ->
            new AbilityPickaxeItem(ENHANCED, ODYSIUM, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_AXE = ITEMS.register("litherite_axe", () ->
            new AxeItem(LITHERITE, new Item.Properties().attributes(AxeItem.createAttributes(LITHERITE, 5, -3))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_AXE = ITEMS.register("burning_litherite_axe", () ->
            new BurningLitheriteAxeItem(LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_AXE = ITEMS.register("frozen_litherite_axe", () ->
            new AbilityAxeItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_AXE = ITEMS.register("infused_litherite_axe", () ->
            new InfusedLitheriteAxeItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_AXE = ITEMS.register("withering_litherite_axe", () ->
            new AbilityAxeItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_AXE = ITEMS.register("odysium_axe", () ->
            new AbilityAxeItem(ENHANCED, ODYSIUM, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel", () ->
            new ShovelItem(LITHERITE, new Item.Properties().attributes(ShovelItem.createAttributes(LITHERITE, 1.5F, -3F))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SHOVEL = ITEMS.register("burning_litherite_shovel", () ->
            new BurningLitheriteShovelItem(LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SHOVEL = ITEMS.register("frozen_litherite_shovel", () ->
            new AbilityShovelItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SHOVEL = ITEMS.register("infused_litherite_shovel", () ->
            new InfusedLitheriteShovelItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SHOVEL = ITEMS.register("withering_litherite_shovel", () ->
            new AbilityShovelItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_SHOVEL = ITEMS.register("odysium_shovel", () ->
            new AbilityShovelItem(ENHANCED, ODYSIUM, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe", () ->
            new HoeItem(LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = ITEMS.register("burning_litherite_hoe", () ->
            new BurningLitheriteHoeItem(LITHERITE, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = ITEMS.register("frozen_litherite_hoe", () ->
            new AbilityHoeItem(FROZEN, LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = ITEMS.register("infused_litherite_hoe", () ->
            new InfusedLitheriteHoeItem(LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HOE = ITEMS.register("withering_litherite_hoe", () ->
            new AbilityHoeItem(WITHERING, LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> ODYSIUM_HOE = ITEMS.register("odysium_hoe", () ->
            new AbilityHoeItem(ENHANCED, ODYSIUM, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(ODYSIUM, -5, 0))));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer", () ->
            new HammerItem(LITHERITE, 5, -3.0F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = ITEMS.register("burning_litherite_hammer", () ->
            new BurningLitheriteHammerItem(LITHERITE, 5, -3.0F, 4, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = ITEMS.register("frozen_litherite_hammer", () ->
            new AbilityHammerItem(FROZEN, LITHERITE, 5, -3F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = ITEMS.register("infused_litherite_hammer", () ->
            new InfusedLitheriteHammerItem(LITHERITE, 5, -3.0F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HAMMER = ITEMS.register("withering_litherite_hammer", () ->
            new AbilityHammerItem(WITHERING, LITHERITE, 5, -3.0F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_HAMMER = ITEMS.register("odysium_hammer", () ->
            new AbilityHammerItem(ENHANCED, ODYSIUM, 5, -3.0F, 4, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> MYSTERIOUS_ROD = ITEMS.register("mysterious_rod", () ->
            new MysteriousItem(new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> WAR_HAMMER = ITEMS.register("war_hammer", () ->
            LitherealExpectPlatform.createWarHammer(ODYSIUM, 1, -3.4F, new Item.Properties().durability(500).fireResistant().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> LITHERITE_BRUSH = ITEMS.register("litherite_brush", () ->
            new LitheriteBrushItem(new Item.Properties().durability(256)));

    public static final RegistrySupplier<Item> LITHERITE_WRENCH = ITEMS.register("litherite_wrench", () ->
            new WrenchItem(new Item.Properties().stacksTo(1).durability(128)));

    public static final RegistrySupplier<Item> LITHERITE_HELMET = ITEMS.register("litherite_helmet", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HELMET = ITEMS.register("burning_litherite_helmet", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HELMET = ITEMS.register("frozen_litherite_helmet", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HELMET = ITEMS.register("infused_litherite_helmet", () ->
            LitherealExpectPlatform.createInfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HELMET = ITEMS.register("withering_litherite_helmet", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_HELMET = ITEMS.register("odysium_helmet", () ->
            new ArmorItem(ModArmorMaterials.ODYSIUM, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(50))));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = ITEMS.register("burning_litherite_chestplate", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = ITEMS.register("frozen_litherite_chestplate", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = ITEMS.register("infused_litherite_chestplate", () ->
            LitherealExpectPlatform.createInfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CHESTPLATE = ITEMS.register("withering_litherite_chestplate", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_CHESTPLATE = ITEMS.register("odysium_chestplate", () ->
            new ArmorItem(ModArmorMaterials.ODYSIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(50))));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = ITEMS.register("burning_litherite_leggings", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = ITEMS.register("frozen_litherite_leggings", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = ITEMS.register("infused_litherite_leggings", () ->
            LitherealExpectPlatform.createInfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LEGGINGS = ITEMS.register("withering_litherite_leggings", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_LEGGINGS = ITEMS.register("odysium_leggings", () ->
            new ArmorItem(ModArmorMaterials.ODYSIUM, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(50))));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = ITEMS.register("burning_litherite_boots", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = ITEMS.register("frozen_litherite_boots", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = ITEMS.register("infused_litherite_boots", () ->
            LitherealExpectPlatform.createInfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_BOOTS = ITEMS.register("withering_litherite_boots", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_BOOTS = ITEMS.register("odysium_boots", () ->
            new ArmorItem(ModArmorMaterials.ODYSIUM, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(50))));

    public static final RegistrySupplier<Item> MOLTEN_LITHERITE_BUCKET = ITEMS.register("molten_litherite_bucket", () ->
            new Item(new Item.Properties().stacksTo(1)));

    public static final RegistrySupplier<Item> LITHER_CHARGE = ITEMS.register("lither_charge", () ->
            new LitherChargeItem(new Item.Properties()));

    public static final RegistrySupplier<Item> LITHEREAL_KEY = ITEMS.register("lithereal_key", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MUSIC_DISC_SPARKLE = ITEMS.register("music_disc_sparkle", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).jukeboxPlayable(ModSounds.SPARKLE)));

    public static final RegistrySupplier<Item> BOSS_ESSENCE_NETHER_STAR = ITEMS.register("boss_essence_nether_star", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final RegistrySupplier<Item> UNIFIER = ITEMS.register("unifier", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final RegistrySupplier<Item> IMPURE_ETHEREAL_CRYSTAL_SHARD = ITEMS.register("impure_ethereal_crystal_shard", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NERITH_INGOT = ITEMS.register("nerith_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ALLIAN_INGOT = ITEMS.register("allian_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> AURELITE_DUST = ITEMS.register("aurelite_dust", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> AURELITE_INGOT = ITEMS.register("aurelite_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> CHRYON_CRYSTAL = ITEMS.register("chryon_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> COPALITE_DUST = ITEMS.register("copalite_dust", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> COPALITE_INGOT = ITEMS.register("copalite_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> CYRUM_CRYSTAL = ITEMS.register("cyrum_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ELUNITE_CRYSTAL = ITEMS.register("elunite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> HELLIONITE_CRYSTAL = ITEMS.register("hellionite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> LUMINIUM_CRYSTAL = ITEMS.register("luminium_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> RAW_ALLIUM = ITEMS.register("raw_allium", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> RAW_NERITH = ITEMS.register("raw_nerith", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SATURNITE_CRYSTAL = ITEMS.register("saturnite_crystal", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SOLIUMITE_INGOT = ITEMS.register("soliumite_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ELCRUM_INGOT = ITEMS.register("elcrum_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BOSS_ESSENCE = ITEMS.register("boss_essence", () ->
            new BossEssenceItem(new Item.Properties().rarity(Rarity.RARE), new BaseBossEssence(14100520, 0, Collections.emptyList())));

    public static final RegistrySupplier<Item> AWAKENED_BOSS_ESSENCE = ITEMS.register("awakened_boss_essence", () ->
            new BossEssenceItem(new Item.Properties().rarity(Rarity.RARE), new BaseBossEssence(4726207, 1, Collections.emptyList())));

    public static final RegistrySupplier<Item> PURE_BOSS_ESSENCE = ITEMS.register("pure_boss_essence", () ->
            new BossEssenceItem(new Item.Properties().rarity(Rarity.RARE), new BaseBossEssence(13985849, 2, List.of(new MobEffectInstance(MobEffects.REGENERATION, 6000)))));

    public static final RegistrySupplier<Item> ODYSIUM_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("odysium_upgrade_smithing_template",
            ModSmithingTemplateItem::createOdysiumUpgradeTemplate);



    public static void register() {
        if (isModLoaded("combatify"))
            CompatInit.initCombatify();
        ITEMS.register();
    }
}
