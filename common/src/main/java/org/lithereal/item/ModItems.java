package org.lithereal.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.item.compat.CompatInit;
import org.lithereal.item.custom.*;
import org.lithereal.item.custom.ability.*;
import org.lithereal.item.custom.burning.BurningLitheriteAxeItem;
import org.lithereal.item.custom.burning.BurningLitheriteHoeItem;
import org.lithereal.item.custom.burning.BurningLitheritePickaxeItem;
import org.lithereal.item.custom.burning.BurningLitheriteShovelItem;
import org.lithereal.item.custom.infused.*;
import org.lithereal.item.custom.obscured.MysteriousItem;
import org.lithereal.item.tag.ModTags;
import org.lithereal.sound.ModSounds;

import static dev.architectury.platform.Platform.isModLoaded;
import static org.lithereal.item.custom.Ability.*;
import static org.lithereal.util.CommonUtils.*;

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

    public static final Tier LITHERITE = ModTier.create("litherite", 5, 1791, 11.0F, 4.0F, 20, () -> Ingredient.of(LitherealExpectPlatform.getLitheriteItem()), ModTags.INCORRECT_FOR_LITHERITE_TOOL);
    public static final Tier ODYSIUM = ModTier.create("odysium", 6, 2431, 13.0F, 5.0F, 30, () -> Ingredient.of(LitherealExpectPlatform.getLitheriteItem()), ModTags.INCORRECT_FOR_ODYSIUM_TOOL);

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
            new PickaxeItem(LITHERITE, new Item.Properties().attributes(PickaxeItem.createAttributes(LITHERITE, 1, -2.8f))));

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
            new ShovelItem(LITHERITE, new Item.Properties().attributes(ShovelItem.createAttributes(LITHERITE, 1.5f, -3f))));

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
            new HoeItem(LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -5, 0))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = ITEMS.register("burning_litherite_hoe", () ->
            new BurningLitheriteHoeItem(LITHERITE, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(LITHERITE, -5, 0))));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = ITEMS.register("frozen_litherite_hoe", () ->
            new AbilityHoeItem(FROZEN, LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -5, 0))));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = ITEMS.register("infused_litherite_hoe", () ->
            new InfusedLitheriteHoeItem(LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -5, 0))));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HOE = ITEMS.register("withering_litherite_hoe", () ->
            new AbilityHoeItem(WITHERING, LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -5, 0))));

    public static final RegistrySupplier<Item> ODYSIUM_HOE = ITEMS.register("odysium_hoe", () ->
            new AbilityHoeItem(ENHANCED, ODYSIUM, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(ODYSIUM, -6, 0))));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer", () ->
            createHammer(LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = ITEMS.register("burning_litherite_hammer", () ->
            createBurningHammer(LITHERITE, 5, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = ITEMS.register("frozen_litherite_hammer", () ->
            createAbilityHammer(FROZEN, LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = ITEMS.register("infused_litherite_hammer", () ->
            createInfusedHammer(LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HAMMER = ITEMS.register("withering_litherite_hammer", () ->
            createAbilityHammer(WITHERING, LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_HAMMER = ITEMS.register("odysium_hammer", () ->
            createAbilityHammer(ENHANCED, ODYSIUM, 5, -3f, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> MYSTERIOUS_ROD = ITEMS.register("mysterious_rod", () ->
            new MysteriousItem(new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> WAR_HAMMER = ITEMS.register("war_hammer", () ->
            LitherealExpectPlatform.createWarHammer(ODYSIUM, 1, -3.4f, new Item.Properties().durability(500).fireResistant().rarity(Rarity.EPIC)));

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
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HELMET = ITEMS.register("withering_litherite_helmet", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.HELMET, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = ITEMS.register("burning_litherite_chestplate", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = ITEMS.register("frozen_litherite_chestplate", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = ITEMS.register("infused_litherite_chestplate", () ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CHESTPLATE = ITEMS.register("withering_litherite_chestplate", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.CHESTPLATE, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = ITEMS.register("burning_litherite_leggings", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = ITEMS.register("frozen_litherite_leggings", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = ITEMS.register("infused_litherite_leggings", () ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LEGGINGS = ITEMS.register("withering_litherite_leggings", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.LEGGINGS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(25))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = ITEMS.register("burning_litherite_boots", () ->
            new AbilityArmorItem(BURNING, ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = ITEMS.register("frozen_litherite_boots", () ->
            new AbilityArmorItem(FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = ITEMS.register("infused_litherite_boots", () ->
            new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_BOOTS = ITEMS.register("withering_litherite_boots", () ->
            new AbilityArmorItem(WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.BOOTS, 25, new Item.Properties()));

    public static final RegistrySupplier<Item> MOLTEN_LITHERITE_BUCKET = ITEMS.register("molten_litherite_bucket", () ->
            new Item(new Item.Properties().stacksTo(1)));

    public static final RegistrySupplier<Item> LITHER_CHARGE = ITEMS.register("lither_charge", () ->
            new LitherChargeItem(new Item.Properties()));

    public static final RegistrySupplier<Item> LITHEREAL_KEY = ITEMS.register("lithereal_key", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MUSIC_DISC_SPARKLE = ITEMS.register("music_disc_sparkle", () ->
            new RecordItem(16, ModSounds.MUSIC_DISC_SPARKLE, new Item.Properties().rarity(Rarity.RARE).stacksTo(1), 135));

    public static final RegistrySupplier<Item> BOSS_ESSENCE_NETHER_STAR = ITEMS.register("boss_essence_nether_star", () ->
            new Item(new Item.Properties().rarity(Rarity.UNCOMMON).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final RegistrySupplier<Item> UNIFIER = ITEMS.register("unifier", () ->
            new Item(new Item.Properties().rarity(Rarity.UNCOMMON).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static void register() {
        if (isModLoaded("combatify"))
            CompatInit.initCombatify();
        ITEMS.register();
    }
}
