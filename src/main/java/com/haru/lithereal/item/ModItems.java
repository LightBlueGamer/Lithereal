package com.haru.lithereal.item;

import com.haru.lithereal.Lithereal;
import com.haru.lithereal.item.custom.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Lithereal.MOD_ID);

    public static final RegistryObject<Item> LITHERITE_CRYSTAL = ITEMS.register("litherite_crystal",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_CRYSTAL = ITEMS.register("heated_litherite_crystal",
            () -> new Item(new Item.Properties().fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> COOLED_LITHERITE_CRYSTAL = ITEMS.register("cooled_litherite_crystal",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> COOLED_LITHERITE_SWORD = ITEMS.register("cooled_litherite_sword",
            () -> new CooledLitheriteSword(Tiers.DIAMOND, 4, -1.6f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_SWORD = ITEMS.register("heated_litherite_sword",
            () -> new BurningLitheriteSword(Tiers.DIAMOND, 4, -1.6f, new Item.Properties().durability(1750).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword",
            () -> new SwordItem(Tiers.DIAMOND, 4, -1.6f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> COOLED_LITHERITE_PICKAXE = ITEMS.register("cooled_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_PICKAXE = ITEMS.register("heated_litherite_pickaxe",
            () -> new BurningLitheritePickaxeItem(Tiers.DIAMOND, 2, -1.2f, new Item.Properties().durability(1750).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> FROZEN_LITHERITE_HAMMER = ITEMS.register("cooled_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f, new Item.Properties().durability(15750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> BURNING_LITHERITE_HAMMER = ITEMS.register("heated_litherite_hammer",
            () -> new BurningLitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f, new Item.Properties().durability(15750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f, new Item.Properties().durability(15750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> COOLED_LITHERITE_AXE = ITEMS.register("cooled_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_AXE = ITEMS.register("heated_litherite_axe",
            () -> new BurningLitheriteAxeItem(Tiers.DIAMOND, 6, -0.6f, new Item.Properties().durability(1750).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_AXE = ITEMS.register("litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> COOLED_LITHERITE_SHOVEL = ITEMS.register("cooled_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_SHOVEL = ITEMS.register("heated_litherite_shovel",
            () -> new BurningLitheriteShovelItem(Tiers.DIAMOND, 2.5f, -1f, new Item.Properties().durability(1750).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> COOLED_LITHERITE_HOE = ITEMS.register("cooled_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_HOE = ITEMS.register("heated_litherite_hoe",
            () -> new BurningLitheriteHoeItem(Tiers.DIAMOND, -2, 0.8f, new Item.Properties().durability(1750).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f, new Item.Properties().durability(1750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> LITHERITE_HELMET = ITEMS.register("litherite_helmet",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, EquipmentSlot.HEAD, new Item.Properties().durability(500).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, EquipmentSlot.CHEST, new Item.Properties().durability(750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, EquipmentSlot.LEGS, new Item.Properties().durability(700).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, EquipmentSlot.FEET, new Item.Properties().durability(550).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> HEATED_LITHERITE_HELMET = ITEMS.register("heated_litherite_helmet",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, EquipmentSlot.HEAD, new Item.Properties().durability(500).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_CHESTPLATE = ITEMS.register("heated_litherite_chestplate",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, EquipmentSlot.CHEST, new Item.Properties().durability(750).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_LEGGINGS = ITEMS.register("heated_litherite_leggings",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, EquipmentSlot.LEGS, new Item.Properties().durability(700).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> HEATED_LITHERITE_BOOTS = ITEMS.register("heated_litherite_boots",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, EquipmentSlot.FEET, new Item.Properties().durability(550).fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static final RegistryObject<Item> COOLED_LITHERITE_HELMET = ITEMS.register("cooled_litherite_helmet",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, EquipmentSlot.HEAD, new Item.Properties().durability(500).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> COOLED_LITHERITE_CHESTPLATE = ITEMS.register("cooled_litherite_chestplate",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, EquipmentSlot.CHEST, new Item.Properties().durability(750).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> COOLED_LITHERITE_LEGGINGS = ITEMS.register("cooled_litherite_leggings",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, EquipmentSlot.LEGS, new Item.Properties().durability(700).tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    public static final RegistryObject<Item> COOLED_LITHERITE_BOOTS = ITEMS.register("cooled_litherite_boots",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, EquipmentSlot.FEET, new Item.Properties().durability(550).tab(ModCreativeModeTabs.LITHEREAL_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}