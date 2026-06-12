package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.*;
import org.lithereal.item.ability.*;
import org.lithereal.item.burning.*;
import org.lithereal.item.infused.*;

import static org.lithereal.item.ModItems.*;
import static org.lithereal.item.ModItems.ODYSIUM;
import static org.lithereal.item.ability.Ability.*;

public class ModToolItems {
    public static final RegistrySupplier<Item> LITHERITE_SWORD = registerItem("litherite_sword", properties ->
            new Item(properties.sword(LITHERITE, 3, -2.4f)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SWORD = registerItem("burning_litherite_sword", properties ->
            new AbilitySwordItem(BURNING, LITHERITE, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SWORD = registerItem("frozen_litherite_sword", properties ->
            new AbilitySwordItem(FROZEN, LITHERITE, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SWORD = registerItem("smoldering_litherite_sword", properties ->
            new AbilitySwordItem(SMOLDERING, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SWORD = registerItem("frostbitten_litherite_sword", properties ->
            new AbilitySwordItem(FROSTBITTEN, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SWORD = registerItem("infused_litherite_sword", properties ->
            new InfusedLitheriteSwordItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SWORD = registerItem("withering_litherite_sword", properties ->
            new AbilitySwordItem(WITHERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> ODYSIUM_SWORD = registerItem("odysium_sword", properties ->
            new AbilitySwordItem(Ability.ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SWORD = registerItem("enhanced_odysium_sword", properties ->
            new AbilitySwordItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = registerItem("litherite_pickaxe", properties ->
            new Item(properties.pickaxe(LITHERITE, 1, -2.8F)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_PICKAXE = registerItem("burning_litherite_pickaxe", properties ->
            new BurningLitheritePickaxeItem(LITHERITE, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_PICKAXE = registerItem("frozen_litherite_pickaxe", properties ->
            new AbilityPickaxeItem(FROZEN, LITHERITE, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_PICKAXE = registerItem("smoldering_litherite_pickaxe", properties ->
            new BurningLitheritePickaxeItem(SMOLDERING, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_PICKAXE = registerItem("frostbitten_litherite_pickaxe", properties ->
            new AbilityPickaxeItem(FROSTBITTEN, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_PICKAXE = registerItem("infused_litherite_pickaxe", properties ->
            new InfusedLitheritePickaxeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_PICKAXE = registerItem("withering_litherite_pickaxe", properties ->
            new AbilityPickaxeItem(WITHERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> ODYSIUM_PICKAXE = registerItem("odysium_pickaxe", properties ->
            new AbilityPickaxeItem(Ability.ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_PICKAXE = registerItem("enhanced_odysium_pickaxe", properties ->
            new AbilityPickaxeItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_AXE = registerItem("litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_AXE = registerItem("burning_litherite_axe", properties ->
            new BurningLitheriteAxeItem(LITHERITE, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_AXE = registerItem("frozen_litherite_axe", properties ->
            new AbilityAxeItem(FROZEN, LITHERITE, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_AXE = registerItem("smoldering_litherite_axe", properties ->
            new BurningLitheriteAxeItem(SMOLDERING, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_AXE = registerItem("frostbitten_litherite_axe", properties ->
            new AbilityAxeItem(FROSTBITTEN, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_AXE = registerItem("infused_litherite_axe", properties ->
            new InfusedLitheriteAxeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_AXE = registerItem("withering_litherite_axe", properties ->
            new AbilityAxeItem(WITHERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> ODYSIUM_AXE = registerItem("odysium_axe", properties ->
            new AbilityAxeItem(Ability.ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_AXE = registerItem("enhanced_odysium_axe", properties ->
            new AbilityAxeItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = registerItem("litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5F, -3F, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SHOVEL = registerItem("burning_litherite_shovel", properties ->
            new BurningLitheriteShovelItem(LITHERITE, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SHOVEL = registerItem("frozen_litherite_shovel", properties ->
            new AbilityShovelItem(FROZEN, LITHERITE, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SHOVEL = registerItem("smoldering_litherite_shovel", properties ->
            new BurningLitheriteShovelItem(SMOLDERING, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SHOVEL = registerItem("frostbitten_litherite_shovel", properties ->
            new AbilityShovelItem(FROSTBITTEN, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SHOVEL = registerItem("infused_litherite_shovel", properties ->
            new InfusedLitheriteShovelItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SHOVEL = registerItem("withering_litherite_shovel", properties ->
            new AbilityShovelItem(WITHERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> ODYSIUM_SHOVEL = registerItem("odysium_shovel", properties ->
            new AbilityShovelItem(Ability.ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SHOVEL = registerItem("enhanced_odysium_shovel", properties ->
            new AbilityShovelItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_HOE = registerItem("litherite_hoe", properties ->
            new HoeItem(LITHERITE, -4, 0, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = registerItem("burning_litherite_hoe", properties ->
            new BurningLitheriteHoeItem(LITHERITE, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = registerItem("frozen_litherite_hoe", properties ->
            new AbilityHoeItem(FROZEN, LITHERITE, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HOE = registerItem("smoldering_litherite_hoe", properties ->
            new BurningLitheriteHoeItem(SMOLDERING, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HOE = registerItem("frostbitten_litherite_hoe", properties ->
            new AbilityHoeItem(FROSTBITTEN, LITHERITE, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = registerItem("infused_litherite_hoe", properties ->
            new InfusedLitheriteHoeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HOE = registerItem("withering_litherite_hoe", properties ->
            new AbilityHoeItem(WITHERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> ODYSIUM_HOE = registerItem("odysium_hoe", properties ->
            new AbilityHoeItem(Ability.ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HOE = registerItem("enhanced_odysium_hoe", properties ->
            new AbilityHoeItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = registerItem("litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3.0F, 3, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = registerItem("burning_litherite_hammer", properties ->
            new BurningLitheriteHammerItem(LITHERITE, 5, -3.0F, 3, properties.fireResistant()));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = registerItem("frozen_litherite_hammer", properties ->
            new AbilityHammerItem(FROZEN, LITHERITE, 5, -3F, 3, properties));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HAMMER = registerItem("smoldering_litherite_hammer", properties ->
            new BurningLitheriteHammerItem(SMOLDERING, LITHERITE, 5, -3F, 3, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HAMMER = registerItem("frostbitten_litherite_hammer", properties ->
            new AbilityHammerItem(FROSTBITTEN, LITHERITE, 5, -3F, 3, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = registerItem("infused_litherite_hammer", properties ->
            new InfusedLitheriteHammerItem(LITHERITE, 5, -3.0F, 3, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HAMMER = registerItem("withering_litherite_hammer", properties ->
            new AbilityHammerItem(WITHERING, LITHERITE, 5, -3.0F, 3, properties));

    public static final RegistrySupplier<Item> ODYSIUM_HAMMER = registerItem("odysium_hammer", properties ->
            new AbilityHammerItem(Ability.ODYSIUM, ODYSIUM, 5, -3.0F, 5, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HAMMER = registerItem("enhanced_odysium_hammer", properties ->
            new AbilityHammerItem(Ability.ENHANCED_ODYSIUM, ODYSIUM, 5, -3.0F, 5, properties.fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> ODYSIUM_BOW = registerItem("odysium_bow", properties ->
            new ModBowItem(ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON), 2, 0.2F, 1));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_BOW = registerItem("enhanced_odysium_bow", properties ->
            new ModBowItem(ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON), 3, 0.1F, 1.5F));

    public static final RegistrySupplier<Item> WAR_HAMMER = registerItem("war_hammer", properties ->
            new WarHammerItem(ODYSIUM, 1, -3.4F, properties.durability(500).fireResistant().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> LITHERITE_BRUSH = registerItem("litherite_brush", properties ->
            new LitheriteBrushItem(properties.durability(256)));

    public static final RegistrySupplier<Item> LITHERITE_WRENCH = registerItem("litherite_wrench", properties ->
            new WrenchItem(properties.stacksTo(1).durability(128)));

    public static void register() {

    }
}
