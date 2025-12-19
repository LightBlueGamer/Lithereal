package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.*;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.item.ability.*;
import org.lithereal.item.burning.*;
import org.lithereal.item.infused.*;

import static org.lithereal.item.ModItems.*;
import static org.lithereal.item.ModItems.ODYSIUM;
import static org.lithereal.item.ability.Ability.*;

public class ModToolItems {
    public static final RegistrySupplier<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword", () ->
            new SwordItem(LITHERITE, new Item.Properties().attributes(SwordItem.createAttributes(LITHERITE, 3, -2.4f))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SWORD = ITEMS.register("burning_litherite_sword", () ->
            new AbilitySwordItem(BURNING, LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SWORD = ITEMS.register("frozen_litherite_sword", () ->
            new AbilitySwordItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SWORD = ITEMS.register("smoldering_litherite_sword", () ->
            new AbilitySwordItem(SMOLDERING, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SWORD = ITEMS.register("frostbitten_litherite_sword", () ->
            new AbilitySwordItem(FROSTBITTEN, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SWORD = ITEMS.register("infused_litherite_sword", () ->
            new InfusedLitheriteSwordItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SWORD = ITEMS.register("withering_litherite_sword", () ->
            new AbilitySwordItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_SWORD = ITEMS.register("odysium_sword", () ->
            new AbilitySwordItem(Ability.ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SWORD = ITEMS.register("enhanced_odysium_sword", () ->
            new AbilitySwordItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe", () ->
            new PickaxeItem(LITHERITE, new Item.Properties().attributes(PickaxeItem.createAttributes(LITHERITE, 1, -2.8F))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_PICKAXE = ITEMS.register("burning_litherite_pickaxe", () ->
            new BurningLitheritePickaxeItem(LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_PICKAXE = ITEMS.register("frozen_litherite_pickaxe", () ->
            new AbilityPickaxeItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_PICKAXE = ITEMS.register("smoldering_litherite_pickaxe", () ->
            new BurningLitheritePickaxeItem(SMOLDERING, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_PICKAXE = ITEMS.register("frostbitten_litherite_pickaxe", () ->
            new AbilityPickaxeItem(FROSTBITTEN, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_PICKAXE = ITEMS.register("infused_litherite_pickaxe", () ->
            new InfusedLitheritePickaxeItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_PICKAXE = ITEMS.register("withering_litherite_pickaxe", () ->
            new AbilityPickaxeItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_PICKAXE = ITEMS.register("odysium_pickaxe", () ->
            new AbilityPickaxeItem(Ability.ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_PICKAXE = ITEMS.register("enhanced_odysium_pickaxe", () ->
            new AbilityPickaxeItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_AXE = ITEMS.register("litherite_axe", () ->
            new AxeItem(LITHERITE, new Item.Properties().attributes(AxeItem.createAttributes(LITHERITE, 5, -3))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_AXE = ITEMS.register("burning_litherite_axe", () ->
            new BurningLitheriteAxeItem(LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_AXE = ITEMS.register("frozen_litherite_axe", () ->
            new AbilityAxeItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_AXE = ITEMS.register("smoldering_litherite_axe", () ->
            new BurningLitheriteAxeItem(SMOLDERING, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_AXE = ITEMS.register("frostbitten_litherite_axe", () ->
            new AbilityAxeItem(FROSTBITTEN, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_AXE = ITEMS.register("infused_litherite_axe", () ->
            new InfusedLitheriteAxeItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_AXE = ITEMS.register("withering_litherite_axe", () ->
            new AbilityAxeItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_AXE = ITEMS.register("odysium_axe", () ->
            new AbilityAxeItem(Ability.ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_AXE = ITEMS.register("enhanced_odysium_axe", () ->
            new AbilityAxeItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel", () ->
            new ShovelItem(LITHERITE, new Item.Properties().attributes(ShovelItem.createAttributes(LITHERITE, 1.5F, -3F))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SHOVEL = ITEMS.register("burning_litherite_shovel", () ->
            new BurningLitheriteShovelItem(LITHERITE, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SHOVEL = ITEMS.register("frozen_litherite_shovel", () ->
            new AbilityShovelItem(FROZEN, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SHOVEL = ITEMS.register("smoldering_litherite_shovel", () ->
            new BurningLitheriteShovelItem(SMOLDERING, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SHOVEL = ITEMS.register("frostbitten_litherite_shovel", () ->
            new AbilityShovelItem(FROSTBITTEN, LITHERITE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SHOVEL = ITEMS.register("infused_litherite_shovel", () ->
            new InfusedLitheriteShovelItem(LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SHOVEL = ITEMS.register("withering_litherite_shovel", () ->
            new AbilityShovelItem(WITHERING, LITHERITE, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_SHOVEL = ITEMS.register("odysium_shovel", () ->
            new AbilityShovelItem(Ability.ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SHOVEL = ITEMS.register("enhanced_odysium_shovel", () ->
            new AbilityShovelItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe", () ->
            new HoeItem(LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = ITEMS.register("burning_litherite_hoe", () ->
            new BurningLitheriteHoeItem(LITHERITE, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = ITEMS.register("frozen_litherite_hoe", () ->
            new AbilityHoeItem(FROZEN, LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HOE = ITEMS.register("smoldering_litherite_hoe", () ->
            new BurningLitheriteHoeItem(SMOLDERING, LITHERITE, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(LITHERITE, -4, 0)).rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HOE = ITEMS.register("frostbitten_litherite_hoe", () ->
            new AbilityHoeItem(FROSTBITTEN, LITHERITE, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(LITHERITE, -4, 0)).rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = ITEMS.register("infused_litherite_hoe", () ->
            new InfusedLitheriteHoeItem(LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HOE = ITEMS.register("withering_litherite_hoe", () ->
            new AbilityHoeItem(WITHERING, LITHERITE, new Item.Properties().attributes(HoeItem.createAttributes(LITHERITE, -4, 0))));

    public static final RegistrySupplier<Item> ODYSIUM_HOE = ITEMS.register("odysium_hoe", () ->
            new AbilityHoeItem(Ability.ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(ODYSIUM, -5, 0)).rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HOE = ITEMS.register("enhanced_odysium_hoe", () ->
            new AbilityHoeItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(ODYSIUM, -5, 0)).rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer", () ->
            new HammerItem(LITHERITE, 5, -3.0F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = ITEMS.register("burning_litherite_hammer", () ->
            new BurningLitheriteHammerItem(LITHERITE, 5, -3.0F, 4, new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = ITEMS.register("frozen_litherite_hammer", () ->
            new AbilityHammerItem(FROZEN, LITHERITE, 5, -3F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HAMMER = ITEMS.register("smoldering_litherite_hammer", () ->
            new BurningLitheriteHammerItem(SMOLDERING, LITHERITE, 5, -3F, 4, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HAMMER = ITEMS.register("frostbitten_litherite_hammer", () ->
            new AbilityHammerItem(FROSTBITTEN, LITHERITE, 5, -3F, 4, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = ITEMS.register("infused_litherite_hammer", () ->
            new InfusedLitheriteHammerItem(LITHERITE, 5, -3.0F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HAMMER = ITEMS.register("withering_litherite_hammer", () ->
            new AbilityHammerItem(WITHERING, LITHERITE, 5, -3.0F, 4, new Item.Properties()));

    public static final RegistrySupplier<Item> ODYSIUM_HAMMER = ITEMS.register("odysium_hammer", () ->
            new AbilityHammerItem(Ability.ODYSIUM, ODYSIUM, 5, -3.0F, 4, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HAMMER = ITEMS.register("enhanced_odysium_hammer", () ->
            new AbilityHammerItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, 5, -3.0F, 4, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> WAR_HAMMER = ITEMS.register("war_hammer", () ->
            LitherealExpectPlatform.createWarHammer(ODYSIUM, 1, -3.4F, new Item.Properties().durability(500).fireResistant().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> LITHERITE_BRUSH = ITEMS.register("litherite_brush", () ->
            new LitheriteBrushItem(new Item.Properties().durability(256)));

    public static final RegistrySupplier<Item> LITHERITE_WRENCH = ITEMS.register("litherite_wrench", () ->
            new WrenchItem(new Item.Properties().stacksTo(1).durability(128)));

    public static void register() {

    }
}
