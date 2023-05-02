package org.litecraft.lithereal.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.item.custom.*;

public class ModItems {
    public static final Item LITHERITE_CRYSTAL = registerItem("litherite_crystal",
            new Item(new FabricItemSettings()));
    public static final Item BURNING_LITHERITE_CRYSTAL = registerItem("heated_litherite_crystal",
            new Item(new FabricItemSettings()));
    public static final Item FROZEN_LITHERITE_CRYSTAL = registerItem("cooled_litherite_crystal",
            new Item(new FabricItemSettings()));

    public static final Item LITHERITE_SHOVEL = registerItem("litherite_shovel",
            new ShovelItem(ToolMaterials.DIAMOND, 2.5f, -1f, new FabricItemSettings().maxDamage(1750)));
    public static final Item BURNING_LITHERITE_SHOVEL = registerItem("heated_litherite_shovel",
            new ShovelItem(ToolMaterials.DIAMOND, 2.5f, -1f, new FabricItemSettings().fireproof().maxDamage(1750)));
    public static final Item FROZEN_LITHERITE_SHOVEL = registerItem("cooled_litherite_shovel",
            new ShovelItem(ToolMaterials.DIAMOND, 2.5f, -1f, new FabricItemSettings().maxDamage(1750)));

    public static final Item LITHERITE_AXE = registerItem("litherite_axe",
            new AxeItem(ToolMaterials.DIAMOND, 6f, -0.6f, new FabricItemSettings().maxDamage(1750)));
    public static final Item BURNING_LITHERITE_AXE = registerItem("heated_litherite_axe",
            new AxeItem(ToolMaterials.DIAMOND, 6f, -0.6f, new FabricItemSettings().fireproof().maxDamage(1750)));
    public static final Item FROZEN_LITHERITE_AXE = registerItem("cooled_litherite_axe",
            new AxeItem(ToolMaterials.DIAMOND, 6f, -0.6f, new FabricItemSettings().maxDamage(1750)));

    public static final Item LITHERITE_PICKAXE = registerItem("litherite_pickaxe",
            new PickaxeItem(ToolMaterials.DIAMOND, 2, -1.2f, new FabricItemSettings().maxDamage(1750)));
    public static final Item BURNING_LITHERITE_PICKAXE = registerItem("heated_litherite_pickaxe",
            new PickaxeItem(ToolMaterials.DIAMOND, 2, -1.2f, new FabricItemSettings().fireproof().maxDamage(1750)));
    public static final Item FROZEN_LITHERITE_PICKAXE = registerItem("cooled_litherite_pickaxe",
            new PickaxeItem(ToolMaterials.DIAMOND, 2, -1.2f, new FabricItemSettings().maxDamage(1750)));

    public static final Item LITHERITE_HOE = registerItem("litherite_hoe",
            new HoeItem(ToolMaterials.DIAMOND, -2, 0.8f, new FabricItemSettings().maxDamage(1750)));
    public static final Item BURNING_LITHERITE_HOE = registerItem("heated_litherite_hoe",
            new HoeItem(ToolMaterials.DIAMOND, -2, 0.8f, new FabricItemSettings().fireproof().maxDamage(1750)));
    public static final Item FROZEN_LITHERITE_HOE = registerItem("cooled_litherite_hoe",
            new HoeItem(ToolMaterials.DIAMOND, -2, 0.8f, new FabricItemSettings().maxDamage(1750)));

    public static final Item LITHERITE_SWORD = registerItem("litherite_sword",
            new SwordItem(ToolMaterials.DIAMOND, 4, -1.6f, new FabricItemSettings().maxDamage(1750)));
    public static final Item BURNING_LITHERITE_SWORD = registerItem("heated_litherite_sword",
            new HeatedLitheriteSword(ToolMaterials.DIAMOND, 4, -1.6f, new FabricItemSettings().fireproof().maxDamage(1750)));
    public static final Item FROZEN_LITHERITE_SWORD = registerItem("cooled_litherite_sword",
            new CooledLitheriteSword(ToolMaterials.DIAMOND, 4, -1.6f, new FabricItemSettings().maxDamage(1750)));

    public static final Item LITHERITE_HELMET = registerItem("litherite_helmet",
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.HELMET, new FabricItemSettings().maxDamage(500)));
    public static final Item BURNING_LITHERITE_HELMET = registerItem("heated_litherite_helmet",
            new HeatedLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.HELMET, new FabricItemSettings().fireproof().maxDamage(500)));
    public static final Item FROZEN_LITHERITE_HELMET = registerItem("cooled_litherite_helmet",
            new CooledLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.HELMET, new FabricItemSettings().maxDamage(500)));

    public static final Item LITHERITE_CHESTPLATE = registerItem("litherite_chestplate",
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxDamage(750)));
    public static final Item BURNING_LITHERITE_CHESTPLATE = registerItem("heated_litherite_chestplate",
            new HeatedLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().fireproof().maxDamage(750)));
    public static final Item FROZEN_LITHERITE_CHESTPLATE = registerItem("cooled_litherite_chestplate",
            new CooledLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxDamage(750)));

    public static final Item LITHERITE_LEGGINGS = registerItem("litherite_leggings",
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxDamage(700)));
    public static final Item BURNING_LITHERITE_LEGGINGS = registerItem("heated_litherite_leggings",
            new HeatedLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings().fireproof().maxDamage(700)));
    public static final Item FROZEN_LITHERITE_LEGGINGS = registerItem("cooled_litherite_leggings",
            new CooledLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxDamage(700)));

    public static final Item LITHERITE_BOOTS = registerItem("litherite_boots",
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS, new FabricItemSettings().maxDamage(550)));
    public static final Item BURNING_LITHERITE_BOOTS = registerItem("heated_litherite_boots",
            new HeatedLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.BOOTS, new FabricItemSettings().fireproof().maxDamage(550)));
    public static final Item FROZEN_LITHERITE_BOOTS = registerItem("cooled_litherite_boots",
            new CooledLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.BOOTS, new FabricItemSettings().maxDamage(550)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Lithereal.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {
        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_CRYSTAL);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_CRYSTAL);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_CRYSTAL);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_SWORD);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_SWORD);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_SWORD);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_AXE);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_AXE);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_AXE);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_PICKAXE);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_PICKAXE);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_PICKAXE);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_HOE);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_HOE);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_HOE);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_SHOVEL);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_SHOVEL);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_SHOVEL);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_HELMET);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_HELMET);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_HELMET);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_CHESTPLATE);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_CHESTPLATE);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_CHESTPLATE);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_LEGGINGS);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_LEGGINGS);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_LEGGINGS);

        addToItemGroup(ModItemGroup.LITHEREAL, LITHERITE_BOOTS);
        addToItemGroup(ModItemGroup.LITHEREAL, BURNING_LITHERITE_BOOTS);
        addToItemGroup(ModItemGroup.LITHEREAL, FROZEN_LITHERITE_BOOTS);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }
    public static void registerModItems() {
        Lithereal.LOGGER.info("Registering Mod Items for " + Lithereal.MOD_ID);

        addItemsToItemGroup();
    }
}