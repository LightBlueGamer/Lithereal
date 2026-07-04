package org.lithereal.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import org.lithereal.core.component.ModComponents;
import org.lithereal.core.component.MultiMiner;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.datagen.ItemDataProvider;
import org.lithereal.tags.ModTags;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class ModEquipment {
    public static final SpearData LITHERITE_SPEAR = new SpearData(1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F);
    public static final SpearData ODYSIUM_SPEAR = new SpearData(1.25F, 1.45F, 0.35F, 2.0F, 8.0F, 5F, 4.6F, 7.5F, 4.1F);
    public static final Map<EquipmentSet.Type, UnaryOperator<Item.Properties>> DEFAULT = ImmutableMap.<EquipmentSet.Type, UnaryOperator<Item.Properties>>builder().build();
    public static final Map<EquipmentSet.Type, UnaryOperator<Item.Properties>> SPECIAL_EQUIPMENT = Util.make(() -> {
        ImmutableMap.Builder<EquipmentSet.Type, UnaryOperator<Item.Properties>> builder = ImmutableMap.builder();
        builder.put(EquipmentSet.Type.SHOVEL, properties -> properties.delayedComponent(ModComponents.MULTI_MINER.get(), MultiMiner::specialShovel));
        builder.put(EquipmentSet.Type.HOE, properties -> properties.delayedComponent(ModComponents.MULTI_MINER.get(), MultiMiner::specialHoe));
        return builder.build();
    });
    public static final EquipmentSet WOODEN = EquipmentSet.createVanilla(Util.make(new HashMap<>(), map -> {
                map.put(EquipmentSet.Type.SWORD, () -> Items.WOODEN_SWORD);
                map.put(EquipmentSet.Type.SPEAR, () -> Items.WOODEN_SPEAR);
                map.put(EquipmentSet.Type.AXE, () -> Items.WOODEN_AXE);
                map.put(EquipmentSet.Type.PICKAXE, () -> Items.WOODEN_PICKAXE);
                map.put(EquipmentSet.Type.SHOVEL, () -> Items.WOODEN_SHOVEL);
                map.put(EquipmentSet.Type.HOE, () -> Items.WOODEN_HOE);
            }), DEFAULT, ItemTags.WOODEN_TOOL_MATERIALS, "wooden", 0)
            .populateCustomToolsAndWeaponsWithStandardRecipe(null, ToolMaterial.WOOD, ItemDataProvider.defaultedItemTag("wooden_tools"));
    public static final EquipmentSet STONE = EquipmentSet.createVanilla(Util.make(new HashMap<>(), map -> {
                map.put(EquipmentSet.Type.SWORD, () -> Items.STONE_SWORD);
                map.put(EquipmentSet.Type.SPEAR, () -> Items.STONE_SPEAR);
                map.put(EquipmentSet.Type.AXE, () -> Items.STONE_AXE);
                map.put(EquipmentSet.Type.PICKAXE, () -> Items.STONE_PICKAXE);
                map.put(EquipmentSet.Type.SHOVEL, () -> Items.STONE_SHOVEL);
                map.put(EquipmentSet.Type.HOE, () -> Items.STONE_HOE);
            }), DEFAULT, ItemTags.STONE_TOOL_MATERIALS, "stone", 1)
            .populateCustomToolsAndWeaponsWithStandardRecipe(null, ToolMaterial.STONE, ItemDataProvider.defaultedItemTag("stone_tools"));
    public static final EquipmentSet COPPER = EquipmentSet.createVanilla(Util.make(new HashMap<>(), map -> {
                map.put(EquipmentSet.Type.HELMET, () -> Items.COPPER_HELMET);
                map.put(EquipmentSet.Type.CHESTPLATE, () -> Items.COPPER_CHESTPLATE);
                map.put(EquipmentSet.Type.LEGGINGS, () -> Items.COPPER_LEGGINGS);
                map.put(EquipmentSet.Type.BOOTS, () -> Items.COPPER_BOOTS);
                map.put(EquipmentSet.Type.HORSE_ARMOR, () -> Items.COPPER_HORSE_ARMOR);
                map.put(EquipmentSet.Type.NAUTILUS_ARMOR, () -> Items.COPPER_NAUTILUS_ARMOR);
                map.put(EquipmentSet.Type.SWORD, () -> Items.COPPER_SWORD);
                map.put(EquipmentSet.Type.SPEAR, () -> Items.COPPER_SPEAR);
                map.put(EquipmentSet.Type.AXE, () -> Items.COPPER_AXE);
                map.put(EquipmentSet.Type.PICKAXE, () -> Items.COPPER_PICKAXE);
                map.put(EquipmentSet.Type.SHOVEL, () -> Items.COPPER_SHOVEL);
                map.put(EquipmentSet.Type.HOE, () -> Items.COPPER_HOE);
            }), DEFAULT, ItemTags.COPPER_TOOL_MATERIALS, "copper", 1)
            .populateCustomToolsAndWeaponsWithStandardRecipe(null, ToolMaterial.COPPER, ItemDataProvider.defaultedItemTag("copper_tools"));
    public static final EquipmentSet IRON = EquipmentSet.createVanilla(Util.make(new HashMap<>(), map -> {
                map.put(EquipmentSet.Type.HELMET, () -> Items.IRON_HELMET);
                map.put(EquipmentSet.Type.CHESTPLATE, () -> Items.IRON_CHESTPLATE);
                map.put(EquipmentSet.Type.LEGGINGS, () -> Items.IRON_LEGGINGS);
                map.put(EquipmentSet.Type.BOOTS, () -> Items.IRON_BOOTS);
                map.put(EquipmentSet.Type.HORSE_ARMOR, () -> Items.IRON_HORSE_ARMOR);
                map.put(EquipmentSet.Type.NAUTILUS_ARMOR, () -> Items.IRON_NAUTILUS_ARMOR);
                map.put(EquipmentSet.Type.SWORD, () -> Items.IRON_SWORD);
                map.put(EquipmentSet.Type.SPEAR, () -> Items.IRON_SPEAR);
                map.put(EquipmentSet.Type.AXE, () -> Items.IRON_AXE);
                map.put(EquipmentSet.Type.PICKAXE, () -> Items.IRON_PICKAXE);
                map.put(EquipmentSet.Type.SHOVEL, () -> Items.IRON_SHOVEL);
                map.put(EquipmentSet.Type.HOE, () -> Items.IRON_HOE);
            }), DEFAULT, ItemTags.IRON_TOOL_MATERIALS, "iron", 2)
            .populateCustomToolsAndWeaponsWithStandardRecipe(null, ToolMaterial.IRON, ItemDataProvider.defaultedItemTag("iron_tools"));
    public static final EquipmentSet GOLDEN = EquipmentSet.createVanilla(Util.make(new HashMap<>(), map -> {
                map.put(EquipmentSet.Type.HELMET, () -> Items.GOLDEN_HELMET);
                map.put(EquipmentSet.Type.CHESTPLATE, () -> Items.GOLDEN_CHESTPLATE);
                map.put(EquipmentSet.Type.LEGGINGS, () -> Items.GOLDEN_LEGGINGS);
                map.put(EquipmentSet.Type.BOOTS, () -> Items.GOLDEN_BOOTS);
                map.put(EquipmentSet.Type.HORSE_ARMOR, () -> Items.GOLDEN_HORSE_ARMOR);
                map.put(EquipmentSet.Type.NAUTILUS_ARMOR, () -> Items.GOLDEN_NAUTILUS_ARMOR);
                map.put(EquipmentSet.Type.SWORD, () -> Items.GOLDEN_SWORD);
                map.put(EquipmentSet.Type.SPEAR, () -> Items.GOLDEN_SPEAR);
                map.put(EquipmentSet.Type.AXE, () -> Items.GOLDEN_AXE);
                map.put(EquipmentSet.Type.PICKAXE, () -> Items.GOLDEN_PICKAXE);
                map.put(EquipmentSet.Type.SHOVEL, () -> Items.GOLDEN_SHOVEL);
                map.put(EquipmentSet.Type.HOE, () -> Items.GOLDEN_HOE);
            }), DEFAULT, ItemTags.GOLD_TOOL_MATERIALS, "golden", 0)
            .populateCustomToolsAndWeaponsWithStandardRecipe(null, ToolMaterial.GOLD, ItemDataProvider.defaultedItemTag("golden_tools"));
    public static final EquipmentSet DIAMOND = EquipmentSet.createVanilla(Util.make(new HashMap<>(), map -> {
        map.put(EquipmentSet.Type.HELMET, () -> Items.DIAMOND_HELMET);
        map.put(EquipmentSet.Type.CHESTPLATE, () -> Items.DIAMOND_CHESTPLATE);
        map.put(EquipmentSet.Type.LEGGINGS, () -> Items.DIAMOND_LEGGINGS);
        map.put(EquipmentSet.Type.BOOTS, () -> Items.DIAMOND_BOOTS);
        map.put(EquipmentSet.Type.HORSE_ARMOR, () -> Items.DIAMOND_HORSE_ARMOR);
        map.put(EquipmentSet.Type.NAUTILUS_ARMOR, () -> Items.DIAMOND_NAUTILUS_ARMOR);
        map.put(EquipmentSet.Type.SWORD, () -> Items.DIAMOND_SWORD);
        map.put(EquipmentSet.Type.SPEAR, () -> Items.DIAMOND_SPEAR);
        map.put(EquipmentSet.Type.AXE, () -> Items.DIAMOND_AXE);
        map.put(EquipmentSet.Type.PICKAXE, () -> Items.DIAMOND_PICKAXE);
        map.put(EquipmentSet.Type.SHOVEL, () -> Items.DIAMOND_SHOVEL);
        map.put(EquipmentSet.Type.HOE, () -> Items.DIAMOND_HOE);
    }), DEFAULT, ItemTags.DIAMOND_TOOL_MATERIALS, "diamond", 3)
            .populateCustomToolsAndWeaponsWithStandardRecipe(null, ToolMaterial.DIAMOND, ItemDataProvider.defaultedItemTag("diamond_tools"));
    public static final EquipmentSet NETHERITE = EquipmentSet.createVanilla(Util.make(new HashMap<>(), map -> {
        map.put(EquipmentSet.Type.HELMET, () -> Items.NETHERITE_HELMET);
        map.put(EquipmentSet.Type.CHESTPLATE, () -> Items.NETHERITE_CHESTPLATE);
        map.put(EquipmentSet.Type.LEGGINGS, () -> Items.NETHERITE_LEGGINGS);
        map.put(EquipmentSet.Type.BOOTS, () -> Items.NETHERITE_BOOTS);
        map.put(EquipmentSet.Type.HORSE_ARMOR, () -> Items.NETHERITE_HORSE_ARMOR);
        map.put(EquipmentSet.Type.NAUTILUS_ARMOR, () -> Items.NETHERITE_NAUTILUS_ARMOR);
        map.put(EquipmentSet.Type.SWORD, () -> Items.NETHERITE_SWORD);
        map.put(EquipmentSet.Type.SPEAR, () -> Items.NETHERITE_SPEAR);
        map.put(EquipmentSet.Type.AXE, () -> Items.NETHERITE_AXE);
        map.put(EquipmentSet.Type.PICKAXE, () -> Items.NETHERITE_PICKAXE);
        map.put(EquipmentSet.Type.SHOVEL, () -> Items.NETHERITE_SHOVEL);
        map.put(EquipmentSet.Type.HOE, () -> Items.NETHERITE_HOE);
    }), DEFAULT, ItemTags.NETHERITE_TOOL_MATERIALS, "netherite", 4)
            .populateCustomToolsAndWeaponsWithSmithingUpgradeRecipe(null, ToolMaterial.NETHERITE, () -> Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, DIAMOND, ItemDataProvider.defaultedItemTag("netherite_tools"));
    public static final EquipmentSet LITHERITE = new EquipmentSet(DEFAULT, ModRawMaterialItems.LITHERITE_CRYSTAL, "litherite", 3, false)
            .populateArmorWithStandardRecipe(null, ModArmorMaterials.LITHERITE, ModTags.LITHERITE_ARMORS)
            .populateToolsAndWeaponsWithStandardRecipe(null, ModItems.LITHERITE, LITHERITE_SPEAR, ModTags.LITHERITE_TOOLS)
            .populateCustomToolsAndWeaponsWithStandardRecipe(null, ModItems.LITHERITE, ModTags.LITHERITE_TOOLS);
    public static final EquipmentSet BURNING_LITHERITE = new EquipmentSet(DEFAULT, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL, "burning_litherite", 3, false)
            .populateArmorWithStandardRecipe(Ability.BURNING, ModArmorMaterials.BURNING_LITHERITE, ModTags.LITHERITE_ARMORS)
            .populateToolsAndWeaponsWithStandardRecipe(Ability.BURNING, ModItems.LITHERITE, LITHERITE_SPEAR, ModTags.LITHERITE_TOOLS)
            .populateCustomToolsAndWeaponsWithStandardRecipe(Ability.BURNING, ModItems.LITHERITE, ModTags.LITHERITE_TOOLS);
    public static final EquipmentSet FROZEN_LITHERITE = new EquipmentSet(DEFAULT, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL, "frozen_litherite", 3, false)
            .populateArmorWithStandardRecipe(Ability.FROZEN, ModArmorMaterials.FROZEN_LITHERITE, ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES)
            .populateToolsAndWeaponsWithStandardRecipe(Ability.FROZEN, ModItems.LITHERITE, LITHERITE_SPEAR, ModTags.LITHERITE_TOOLS)
            .populateCustomToolsAndWeaponsWithStandardRecipe(Ability.FROZEN, ModItems.LITHERITE, ModTags.LITHERITE_TOOLS);
    public static final EquipmentSet SMOLDERING_LITHERITE = new EquipmentSet(DEFAULT, () -> Items.AIR, "smoldering_litherite", 3, false)
            .populateArmorWithThermalUpgradeRecipe(Ability.SMOLDERING, ModArmorMaterials.SMOLDERING_LITHERITE, BURNING_LITHERITE, ModTags.LITHERITE_ARMORS)
            .populateToolsAndWeaponsWithThermalUpgradeRecipe(Ability.SMOLDERING, ModItems.LITHERITE, LITHERITE_SPEAR, BURNING_LITHERITE, ModTags.LITHERITE_TOOLS)
            .populateCustomToolsAndWeaponsWithThermalUpgradeRecipe(Ability.SMOLDERING, ModItems.LITHERITE, BURNING_LITHERITE, ModTags.LITHERITE_TOOLS);
    public static final EquipmentSet FROSTBITTEN_LITHERITE = new EquipmentSet(DEFAULT, () -> Items.AIR, "frostbitten_litherite", 3, false)
            .populateArmorWithThermalUpgradeRecipe(Ability.FROSTBITTEN, ModArmorMaterials.FROSTBITTEN_LITHERITE, FROZEN_LITHERITE, ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES)
            .populateToolsAndWeaponsWithThermalUpgradeRecipe(Ability.FROSTBITTEN, ModItems.LITHERITE, LITHERITE_SPEAR, FROZEN_LITHERITE, ModTags.LITHERITE_TOOLS)
            .populateCustomToolsAndWeaponsWithThermalUpgradeRecipe(Ability.FROSTBITTEN, ModItems.LITHERITE, FROZEN_LITHERITE, ModTags.LITHERITE_TOOLS);
    public static final EquipmentSet INFUSED_LITHERITE = new EquipmentSet(DEFAULT, ModRawMaterialItems.INFUSED_LITHERITE_INGOT, "infused_litherite", 3, true)
            .populateArmorWithStandardRecipe(Ability.INFUSED, ModArmorMaterials.INFUSED_LITHERITE, ModTags.LITHERITE_ARMORS)
            .populateToolsAndWeaponsWithStandardRecipe(Ability.INFUSED, ModItems.LITHERITE, LITHERITE_SPEAR, ModTags.LITHERITE_TOOLS)
            .populateCustomToolsAndWeaponsWithStandardRecipe(Ability.INFUSED, ModItems.LITHERITE, ModTags.LITHERITE_TOOLS);
    public static final EquipmentSet WITHERING_LITHERITE = new EquipmentSet(DEFAULT, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL, "withering_litherite", 3, false)
            .populateArmorWithStandardRecipe(Ability.WITHERING, ModArmorMaterials.WITHERING_LITHERITE, ModTags.LITHERITE_ARMORS)
            .populateToolsAndWeaponsWithStandardRecipe(Ability.WITHERING, ModItems.LITHERITE, LITHERITE_SPEAR, ModTags.LITHERITE_TOOLS)
            .populateCustomToolsAndWeaponsWithStandardRecipe(Ability.WITHERING, ModItems.LITHERITE, ModTags.LITHERITE_TOOLS);
    public static final EquipmentSet ODYSIUM = new EquipmentSet(SPECIAL_EQUIPMENT, ModRawMaterialItems.ODYSIUM_INGOT, "odysium", 5, false)
            .populateArmorWithSmithingUpgradeRecipe(Ability.ODYSIUM, ModArmorMaterials.ODYSIUM, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, NETHERITE, ModTags.ODYSIUM_ARMORS)
            .populateHorseAndNautilusArmorWithSmithingUpgradeRecipe(Ability.ODYSIUM, ModArmorMaterials.ODYSIUM, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, NETHERITE)
            .populateToolsAndWeaponsWithSmithingUpgradeRecipe(Ability.ODYSIUM, ModItems.ODYSIUM, ODYSIUM_SPEAR, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, NETHERITE, ModTags.ODYSIUM_TOOLS)
            .populateCustomToolsAndWeaponsWithSmithingUpgradeRecipe(Ability.ODYSIUM, ModItems.ODYSIUM, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, NETHERITE, ModTags.ODYSIUM_TOOLS);
    public static final EquipmentSet ENHANCED_ODYSIUM = new EquipmentSet(SPECIAL_EQUIPMENT, () -> Items.AIR, "enhanced_odysium", 5, false)
            .populateArmorWithoutRecipe(Ability.ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM, ModTags.ODYSIUM_ARMORS)
            .populateHorseAndNautilusArmor(Ability.ENHANCED_ODYSIUM, ModArmorMaterials.ENHANCED_ODYSIUM)
            .populateToolsAndWeaponsWithoutRecipe(Ability.ENHANCED_ODYSIUM, ModItems.ODYSIUM, ODYSIUM_SPEAR, ModTags.ODYSIUM_TOOLS)
            .populateCustomToolsAndWeaponsWithoutRecipe(Ability.ENHANCED_ODYSIUM, ModItems.ODYSIUM, ModTags.ODYSIUM_TOOLS);

    public static void register() {

    }
}
