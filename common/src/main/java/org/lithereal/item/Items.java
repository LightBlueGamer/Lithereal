package org.lithereal.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import org.lithereal.Lithereal;
import org.lithereal.item.custom.ArmorMaterials;
import org.lithereal.item.custom.Hammer;
import org.lithereal.item.custom.tiers.Tiers;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> LITHERITE_CRYSTAL = ITEMS.register("litherite_crystal", () ->
            new Item(new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword", () ->
            new SwordItem(Tiers.LITHERITE, 3, -2.4f, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe", () ->
            new PickaxeItem(Tiers.LITHERITE, 1, -2.8f, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer", () ->
            new Hammer(Tiers.LITHERITE, 5, -3f, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB).durability(15750)));

    public static final RegistrySupplier<Item> LITHERITE_AXE = ITEMS.register("litherite_axe", () ->
            new AxeItem(Tiers.LITHERITE, 5, -3f, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel", () ->
            new ShovelItem(Tiers.LITHERITE, 1.5f, -3f, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe", () ->
            new HoeItem(Tiers.LITHERITE, -3, 0f, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_HELMET = ITEMS.register("litherite_helmet", () ->
            new ArmorItem(ArmorMaterials.LITHERITE, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate", () ->
            new ArmorItem(ArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings", () ->
            new ArmorItem(ArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots", () ->
            new ArmorItem(ArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));

    public static void register() {
        ITEMS.register();
    }
}
