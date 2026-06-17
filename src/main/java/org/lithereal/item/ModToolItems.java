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
            new Item(BURNING.createSwordComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SWORD = registerItem("frozen_litherite_sword", properties ->
            new Item(FROZEN.createSwordComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SWORD = registerItem("smoldering_litherite_sword", properties ->
            new Item(SMOLDERING.createSwordComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SWORD = registerItem("frostbitten_litherite_sword", properties ->
            new Item(FROSTBITTEN.createSwordComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SWORD = registerItem("infused_litherite_sword", properties ->
            new InfusedSwordItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SWORD = registerItem("withering_litherite_sword", properties ->
            new Item(WITHERING.createSwordComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> ODYSIUM_SWORD = registerItem("odysium_sword", properties ->
            new Item(Ability.ODYSIUM.createSwordComponent(ODYSIUM, properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SWORD = registerItem("enhanced_odysium_sword", properties ->
            new Item(Ability.ENHANCED_ODYSIUM.createSwordComponent(ODYSIUM, properties)));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = registerItem("litherite_pickaxe", properties ->
            new Item(properties.pickaxe(LITHERITE, 1, -2.8F)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_PICKAXE = registerItem("burning_litherite_pickaxe", properties ->
            new BurningPickaxeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_PICKAXE = registerItem("frozen_litherite_pickaxe", properties ->
            new Item(FROZEN.createPickaxeComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_PICKAXE = registerItem("smoldering_litherite_pickaxe", properties ->
            new BurningPickaxeItem(SMOLDERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_PICKAXE = registerItem("frostbitten_litherite_pickaxe", properties ->
            new Item(FROSTBITTEN.createPickaxeComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_PICKAXE = registerItem("infused_litherite_pickaxe", properties ->
            new InfusedPickaxeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_PICKAXE = registerItem("withering_litherite_pickaxe", properties ->
            new Item(WITHERING.createPickaxeComponent(LITHERITE, properties)));

    public static final RegistrySupplier<Item> ODYSIUM_PICKAXE = registerItem("odysium_pickaxe", properties ->
            new Item(Ability.ODYSIUM.createPickaxeComponent(ODYSIUM, properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_PICKAXE = registerItem("enhanced_odysium_pickaxe", properties ->
            new Item(ENHANCED_ODYSIUM.createPickaxeComponent(ODYSIUM, properties)));

    public static final RegistrySupplier<Item> LITHERITE_AXE = registerItem("litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_AXE = registerItem("burning_litherite_axe", properties ->
            new BurningAxeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_AXE = registerItem("frozen_litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, FROZEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_AXE = registerItem("smoldering_litherite_axe", properties ->
            new BurningAxeItem(SMOLDERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_AXE = registerItem("frostbitten_litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, FROSTBITTEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_AXE = registerItem("infused_litherite_axe", properties ->
            new InfusedAxeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_AXE = registerItem("withering_litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, WITHERING.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_AXE = registerItem("odysium_axe", properties ->
            new AxeItem(ODYSIUM, 5, -3, Ability.ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_AXE = registerItem("enhanced_odysium_axe", properties ->
            new AxeItem(ODYSIUM, 5, -3, Ability.ENHANCED_ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = registerItem("litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5F, -3F, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SHOVEL = registerItem("burning_litherite_shovel", properties ->
            new BurningShovelItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SHOVEL = registerItem("frozen_litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5f, -3f, FROZEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SHOVEL = registerItem("smoldering_litherite_shovel", properties ->
            new BurningShovelItem(SMOLDERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SHOVEL = registerItem("frostbitten_litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5f, -3f, FROSTBITTEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SHOVEL = registerItem("infused_litherite_shovel", properties ->
            new InfusedShovelItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SHOVEL = registerItem("withering_litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5f, -3f, WITHERING.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_SHOVEL = registerItem("odysium_shovel", properties ->
            new ShovelItem(ODYSIUM, 1.5f, -3f, Ability.ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SHOVEL = registerItem("enhanced_odysium_shovel", properties ->
            new ShovelItem(ODYSIUM, 1.5f, -3f, ENHANCED_ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_HOE = registerItem("litherite_hoe", properties ->
            new HoeItem(LITHERITE, -3, 0, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = registerItem("burning_litherite_hoe", properties ->
            new BurningHoeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = registerItem("frozen_litherite_hoe", properties ->
            new HoeItem(LITHERITE, -3, 0, FROZEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HOE = registerItem("smoldering_litherite_hoe", properties ->
            new BurningHoeItem(SMOLDERING, LITHERITE, properties));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HOE = registerItem("frostbitten_litherite_hoe", properties ->
            new HoeItem(LITHERITE, -3, 0, FROSTBITTEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = registerItem("infused_litherite_hoe", properties ->
            new InfusedHoeItem(LITHERITE, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HOE = registerItem("withering_litherite_hoe", properties ->
            new HoeItem(LITHERITE, -3, 0, WITHERING.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_HOE = registerItem("odysium_hoe", properties ->
            new HoeItem(ODYSIUM, -5, 0, Ability.ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HOE = registerItem("enhanced_odysium_hoe", properties ->
            new HoeItem(ODYSIUM, -5, 0, Ability.ENHANCED_ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = registerItem("litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3.0F, 3, properties));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = registerItem("burning_litherite_hammer", properties ->
            new BurningHammerItem(LITHERITE, 5, -3.0F, 3, properties));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = registerItem("frozen_litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3F, 3, FROZEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HAMMER = registerItem("smoldering_litherite_hammer", properties ->
            new BurningHammerItem(SMOLDERING, LITHERITE, 5, -3F, 3, properties));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HAMMER = registerItem("frostbitten_litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3F, 3, FROSTBITTEN.createToolComponent(properties)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = registerItem("infused_litherite_hammer", properties ->
            new InfusedHammerItem(LITHERITE, 5, -3.0F, 3, properties));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HAMMER = registerItem("withering_litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3.0F, 3, WITHERING.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_HAMMER = registerItem("odysium_hammer", properties ->
            new HammerItem(ODYSIUM, 5, -3.0F, 5, Ability.ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HAMMER = registerItem("enhanced_odysium_hammer", properties ->
            new HammerItem(ODYSIUM, 5, -3.0F, 5, Ability.ENHANCED_ODYSIUM.createToolComponent(properties)));

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

    public static final RegistrySupplier<Item> LITHERITE_SPEAR = registerItem("litherite_spear", properties ->
            new Item(properties.spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SPEAR = registerItem("burning_litherite_spear", properties ->
            new Item(Ability.BURNING.createToolComponent(properties).spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SPEAR = registerItem("frozen_litherite_spear", properties ->
            new Item(FROZEN.createToolComponent(properties).spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SPEAR = registerItem("frostbitten_litherite_spear", properties ->
            new Item(FROSTBITTEN.createToolComponent(properties).spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SPEAR = registerItem("smoldering_litherite_spear", properties ->
            new Item(SMOLDERING.createToolComponent(properties).spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SPEAR = registerItem("infused_litherite_spear", properties ->
            new InfusedSpearItem(properties.spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SPEAR = registerItem("withering_litherite_spear", properties ->
            new Item(WITHERING.createToolComponent(properties).spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final RegistrySupplier<Item> ODYSIUM_SPEAR = registerItem("odysium_spear", properties ->
            new Item(Ability.ODYSIUM.createToolComponent(properties).spear(ODYSIUM,
                    1.25F, 1.45F, 0.35F, 2.0F, 8.0F, 5F, 4.6F, 7.5F, 4.1F)));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SPEAR = registerItem("enhanced_odysium_spear", properties ->
            new Item(ENHANCED_ODYSIUM.createToolComponent(properties).spear(ODYSIUM,
                    1.25F, 1.45F, 0.35F, 2.0F, 8.0F, 5F, 4.6F, 7.5F, 4.1F)));

    public static void register() {

    }
}
