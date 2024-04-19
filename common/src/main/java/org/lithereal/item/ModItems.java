package org.lithereal.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.item.compat.CompatInit;
import org.lithereal.item.custom.*;
import org.lithereal.item.custom.ability.*;
import org.lithereal.item.custom.burning.*;
import org.lithereal.item.custom.infused.*;
import org.lithereal.item.custom.obscured.MysteriousItem;

import static dev.architectury.platform.Platform.isModLoaded;
import static org.lithereal.item.custom.Ability.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ITEM);

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

    public static final Tier LITHERITE = new ModTier(5, 2701, 11.0F, 5.0F, 20, () -> Ingredient.of(LitherealExpectPlatform.getLitheriteItem()));

    public static final Tier ODYSIUM = new ModTier(6, 3000, 13.0F, 6.0F, 30, () -> Ingredient.of(LitherealExpectPlatform.getLitheriteItem()));
    public static final RegistrySupplier<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword", () ->
            new SwordItem(LITHERITE, 3, -2.4f, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SWORD = ITEMS.register("burning_litherite_sword", () ->
            new AbilitySword(BURNING, LITHERITE, 3, -2.4f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SWORD = ITEMS.register("frozen_litherite_sword", () ->
            new AbilitySword(FROZEN, LITHERITE, 3, -2.4f, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SWORD = ITEMS.register("infused_litherite_sword", () ->
            new InfusedLitheriteSword(LITHERITE, 3, -2.4f, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SWORD = ITEMS.register("withering_litherite_sword", () ->
            new AbilitySword(WITHERING, LITHERITE, 3, -2.4f, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_SWORD = ITEMS.register("odysium_sword", () ->
            new AbilitySword(ENHANCED, ODYSIUM, 3, -2.4f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe", () ->
            new PickaxeItem(LITHERITE, 1, -2.8f, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_PICKAXE = ITEMS.register("burning_litherite_pickaxe", () ->
            new BurningLitheritePickaxe(LITHERITE, 1, -2.8f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_PICKAXE = ITEMS.register("frozen_litherite_pickaxe", () ->
            new AbilityPickaxe(FROZEN, LITHERITE, 1, -2.8f, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_PICKAXE = ITEMS.register("infused_litherite_pickaxe", () ->
            new InfusedLitheritePickaxe(LITHERITE, 1, -2.8f, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_PICKAXE = ITEMS.register("withering_litherite_pickaxe", () ->
            new AbilityPickaxe(WITHERING, LITHERITE, 1, -2.8f, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_PICKAXE = ITEMS.register("odysium_pickaxe", () ->
            new AbilityPickaxe(ENHANCED, ODYSIUM, 1, -2.8f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_AXE = ITEMS.register("litherite_axe", () ->
            new AxeItem(LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_AXE = ITEMS.register("burning_litherite_axe", () ->
            new BurningLitheriteAxe(LITHERITE, 5, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_AXE = ITEMS.register("frozen_litherite_axe", () ->
            new AbilityAxe(FROZEN, LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_AXE = ITEMS.register("infused_litherite_axe", () ->
            new InfusedLitheriteAxe(LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_AXE = ITEMS.register("withering_litherite_axe", () ->
            new AbilityAxe(WITHERING, LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_AXE = ITEMS.register("odysium_axe", () ->
            new AbilityAxe(ENHANCED, ODYSIUM, 5, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel", () ->
            new ShovelItem(LITHERITE, 1.5f, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SHOVEL = ITEMS.register("burning_litherite_shovel", () ->
            new BurningLitheriteShovel(LITHERITE, 1.5f, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SHOVEL = ITEMS.register("frozen_litherite_shovel", () ->
            new AbilityShovel(FROZEN, LITHERITE, 1.5f, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SHOVEL = ITEMS.register("infused_litherite_shovel", () ->
            new InfusedLitheriteShovel(LITHERITE, 1.5f, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SHOVEL = ITEMS.register("withering_litherite_shovel", () ->
            new AbilityShovel(WITHERING, LITHERITE, 1.5f, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_SHOVEL = ITEMS.register("odysium_shovel", () ->
            new AbilityShovel(ENHANCED, ODYSIUM, 1.5f, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe", () ->
            new HoeItem(LITHERITE, -5, 0, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = ITEMS.register("burning_litherite_hoe", () ->
            new BurningLitheriteHoe(LITHERITE, -5, 0, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = ITEMS.register("frozen_litherite_hoe", () ->
            new AbilityHoe(FROZEN, LITHERITE, -5, 0, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = ITEMS.register("infused_litherite_hoe", () ->
            new InfusedLitheriteHoe(LITHERITE, -5, 0, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HOE = ITEMS.register("withering_litherite_hoe", () ->
            new AbilityHoe(WITHERING, LITHERITE, -5, 0, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_HOE = ITEMS.register("odysium_hoe", () ->
            new AbilityHoe(ENHANCED, ODYSIUM, -6, 0, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer", () ->
            new Hammer(LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = ITEMS.register("burning_litherite_hammer", () ->
            new BurningLitheriteHammer(LITHERITE, 5, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = ITEMS.register("frozen_litherite_hammer", () ->
            new AbilityHammer(FROZEN, LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = ITEMS.register("infused_litherite_hammer", () ->
            new InfusedLitheriteHammer(LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HAMMER = ITEMS.register("withering_litherite_hammer", () ->
            new AbilityHammer(WITHERING, LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_HAMMER = ITEMS.register("odysium_hammer", () ->
            new AbilityHammer(ENHANCED, ODYSIUM, 5, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> LITHERITE_BRUSH = ITEMS.register("litherite_brush", () ->
            new BrushItem(new Item.Properties().stacksTo(1).durability(256)));

    public static final RegistrySupplier<Item> LITHERITE_WRENCH = ITEMS.register("litherite_wrench", () ->
            new Wrench(new Item.Properties().stacksTo(1).durability(128)));

    public static final RegistrySupplier<Item> LITHERITE_HELMET = ITEMS.register("litherite_helmet", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HELMET = ITEMS.register("burning_litherite_helmet", () ->
            new AbilityArmor(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.HELMET, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HELMET = ITEMS.register("frozen_litherite_helmet", () ->
            new AbilityArmor(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HELMET = ITEMS.register("infused_litherite_helmet", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HELMET = ITEMS.register("withering_litherite_helmet", () ->
            new AbilityArmor(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = ITEMS.register("burning_litherite_chestplate", () ->
            new AbilityArmor(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = ITEMS.register("frozen_litherite_chestplate", () ->
            new AbilityArmor(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = ITEMS.register("infused_litherite_chestplate", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CHESTPLATE = ITEMS.register("withering_litherite_chestplate", () ->
            new AbilityArmor(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = ITEMS.register("burning_litherite_leggings", () ->
            new AbilityArmor(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = ITEMS.register("frozen_litherite_leggings", () ->
            new AbilityArmor(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = ITEMS.register("infused_litherite_leggings", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LEGGINGS = ITEMS.register("withering_litherite_leggings", () ->
            new AbilityArmor(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = ITEMS.register("burning_litherite_boots", () ->
            new AbilityArmor(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = ITEMS.register("frozen_litherite_boots", () ->
            new AbilityArmor(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = ITEMS.register("infused_litherite_boots", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_BOOTS = ITEMS.register("withering_litherite_boots", () ->
            new AbilityArmor(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> MOLTEN_LITHERITE_BUCKET = ITEMS.register("molten_litherite_bucket", () ->
            new Item(new Item.Properties().stacksTo(1)));

    public static final RegistrySupplier<Item> LITHER_CHARGE = ITEMS.register("lither_charge", () ->
            new LitherCharge(new Item.Properties()));

    public static final RegistrySupplier<Item> LITHEREAL_KEY = ITEMS.register("lithereal_key", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> UNIDENTIFIED_ROD = ITEMS.register("unidentified_rod", () ->
            new MysteriousItem(new Item.Properties()));

    public static final RegistrySupplier<Item> WAR_HAMMER = ITEMS.register("war_hammer", () ->
            LitherealExpectPlatform.createWarHammer(ODYSIUM, 6, -3f, new Item.Properties().durability(512)));

    public static final RegistrySupplier<Item> BOSS_ESSENCE_NETHER_STAR = ITEMS.register("boss_essence_nether_star", () ->
            new SimpleFoiledItem(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistrySupplier<Item> UNIFIER = ITEMS.register("unifier", () ->
            new SimpleFoiledItem(new Item.Properties().rarity(Rarity.RARE)));

    public static void register() {
        if (isModLoaded("combatify"))
            CompatInit.initCombatify();
        ITEMS.register();
    }
}
