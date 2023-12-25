package org.lithereal.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.Lithereal;
import org.lithereal.item.custom.ModArmorMaterials;
import org.lithereal.item.custom.Hammer;
import org.lithereal.item.custom.ModTier;
import org.lithereal.item.custom.burning.*;
import org.lithereal.item.custom.frozen.*;
import org.lithereal.item.custom.infused.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> LITHERITE_CRYSTAL = ITEMS.register("litherite_crystal", () ->
            new Item(new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CRYSTAL = ITEMS.register("burning_litherite_crystal", () ->
            new Item(new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CRYSTAL = ITEMS.register("frozen_litherite_crystal", () ->
            new Item(new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CRYSTAL = ITEMS.register("infused_litherite_crystal", () ->
            new InfusedLitheriteItem(new Item.Properties()));

    public static final Tier LITHERITE = new ModTier(4, 1750, 11.0F, 4.0F, 20, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.LITHERITE_CRYSTAL.get()));
    public static final RegistrySupplier<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword", () ->
            new SwordItem(LITHERITE, 3, -2.4f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SWORD = ITEMS.register("burning_litherite_sword", () ->
            new BurningLitheriteSword(LITHERITE, 3, -2.4f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SWORD = ITEMS.register("frozen_litherite_sword", () ->
            new FrozenLitheriteSword(LITHERITE, 3, -2.4f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SWORD = ITEMS.register("infused_litherite_sword", () ->
            new InfusedLitheriteSword(LITHERITE, 3, -2.4f, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe", () ->
            new PickaxeItem(LITHERITE, 1, -2.8f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_PICKAXE = ITEMS.register("burning_litherite_pickaxe", () ->
            new BurningLitheritePickaxe(LITHERITE, 1, -2.8f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_PICKAXE = ITEMS.register("frozen_litherite_pickaxe", () ->
            new FrozenLitheritePickaxe(LITHERITE, 1, -2.8f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_PICKAXE = ITEMS.register("infused_litherite_pickaxe", () ->
            new InfusedLitheritePickaxe(LITHERITE, 1, -2.8f, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer", () ->
            new Hammer(LITHERITE, 5, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB).durability(8750)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = ITEMS.register("burning_litherite_hammer", () ->
            new BurningLitheriteHammer(LITHERITE, 5, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB).durability(8750)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = ITEMS.register("frozen_litherite_hammer", () ->
            new FrozenLitheriteHammer(LITHERITE, 5, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB).durability(8750)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = ITEMS.register("infused_litherite_hammer", () ->
            new InfusedLitheriteHammer(LITHERITE, 5, -3f, new Item.Properties().durability(8750)));

    public static final RegistrySupplier<Item> LITHERITE_AXE = ITEMS.register("litherite_axe", () ->
            new AxeItem(LITHERITE, 5, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_AXE = ITEMS.register("burning_litherite_axe", () ->
            new BurningLitheriteAxe(LITHERITE, 5, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_AXE = ITEMS.register("frozen_litherite_axe", () ->
            new FrozenLitheriteAxe(LITHERITE, 5, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_AXE = ITEMS.register("infused_litherite_axe", () ->
            new InfusedLitheriteAxe(LITHERITE, 5, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel", () ->
            new ShovelItem(LITHERITE, 1.5f, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SHOVEL = ITEMS.register("burning_litherite_shovel", () ->
            new BurningLitheriteShovel(LITHERITE, 1.5f, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SHOVEL = ITEMS.register("frozen_litherite_shovel", () ->
            new FrozenLitheriteShovel(LITHERITE, 1.5f, -3f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SHOVEL = ITEMS.register("infused_litherite_shovel", () ->
            new InfusedLitheriteShovel(LITHERITE, 1.5f, -3f, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe", () ->
            new HoeItem(LITHERITE, -3, 0f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = ITEMS.register("burning_litherite_hoe", () ->
            new BurningLitheriteHoe(LITHERITE, -3, 0f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = ITEMS.register("frozen_litherite_hoe", () ->
            new FrozenLitheriteHoe(LITHERITE, -3, 0f, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = ITEMS.register("infused_litherite_hoe", () ->
            new InfusedLitheriteHoe(LITHERITE, -3, 0f, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_HELMET = ITEMS.register("litherite_helmet", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HELMET = ITEMS.register("burning_litherite_helmet", () ->
            new BurningLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HELMET = ITEMS.register("frozen_litherite_helmet", () ->
            new FrozenLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HELMET = ITEMS.register("infused_litherite_helmet", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = ITEMS.register("burning_litherite_chestplate", () ->
            new BurningLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = ITEMS.register("frozen_litherite_chestplate", () ->
            new FrozenLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = ITEMS.register("infused_litherite_chestplate", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = ITEMS.register("burning_litherite_leggings", () ->
            new BurningLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = ITEMS.register("frozen_litherite_leggings", () ->
            new FrozenLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = ITEMS.register("infused_litherite_leggings", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots", () ->
            new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = ITEMS.register("burning_litherite_boots", () ->
            new BurningLitheriteArmor(ModArmorMaterials.BURNING_LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = ITEMS.register("frozen_litherite_boots", () ->
            new FrozenLitheriteArmor(ModArmorMaterials.FROZEN_LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = ITEMS.register("infused_litherite_boots", () ->
            new InfusedLitheriteArmor(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register() {
        ITEMS.register();
    }
}
