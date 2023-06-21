package org.litecraft.lithereal.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.item.custom.*;
import org.litecraft.lithereal.item.custom.burning.*;
import org.litecraft.lithereal.item.custom.freezing.CooledLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.freezing.CooledLitheriteSword;
import org.litecraft.lithereal.item.custom.glowing.GlowingLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.glowing.GlowingLitheriteSword;
import org.litecraft.lithereal.item.custom.invisible.InvisibleLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.invisible.InvisibleLitheriteSword;
import org.litecraft.lithereal.item.custom.regenerating.RegeneratingLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.regenerating.RegeneratingLitheriteSword;
import org.litecraft.lithereal.item.custom.withering.WitheringLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.withering.WitheringLitheriteSword;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Lithereal.MOD_ID);

    public static final RegistryObject<Item> LITHERITE_CRYSTAL = ITEMS.register("litherite_crystal",
            () -> new LitheriteItem(new Item.Properties()));

    public static final RegistryObject<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword",
            () -> new SwordItem(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> LITHERITE_AXE = ITEMS.register("litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LITHERITE_CHESTPLATE = ITEMS.register("litherite_chestplate",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> LITHERITE_HELMET = ITEMS.register("litherite_helmet",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> LITHERITE_LEGGINGS = ITEMS.register("litherite_leggings",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> LITHERITE_BOOTS = ITEMS.register("litherite_boots",
            () -> new ArmorItem(ModArmorMaterials.LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));

    public static final RegistryObject<Item> LITHERITE_WRENCH = ITEMS.register("litherite_wrench",
            () -> new WrenchItem(new Item.Properties().durability(200)));


    public static final RegistryObject<Item> HEATED_LITHERITE_CRYSTAL = ITEMS.register("heated_litherite_crystal",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_SWORD = ITEMS.register("heated_litherite_sword",
            () -> new BurningLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_PICKAXE = ITEMS.register("heated_litherite_pickaxe",
            () -> new BurningLitheritePickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_HAMMER = ITEMS.register("heated_litherite_hammer",
            () -> new BurningLitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> HEATED_LITHERITE_AXE = ITEMS.register("heated_litherite_axe",
            () -> new BurningLitheriteAxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_SHOVEL = ITEMS.register("heated_litherite_shovel",
            () -> new BurningLitheriteShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_HOE = ITEMS.register("heated_litherite_hoe",
            () -> new BurningLitheriteHoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_HELMET = ITEMS.register("heated_litherite_helmet",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_CHESTPLATE = ITEMS.register("heated_litherite_chestplate",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_LEGGINGS = ITEMS.register("heated_litherite_leggings",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700).fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_BOOTS = ITEMS.register("heated_litherite_boots",
            () -> new HeatedLitheriteArmorItem(ModArmorMaterials.HEATED_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550).fireResistant()));


    public static final RegistryObject<Item> COOLED_LITHERITE_CRYSTAL = ITEMS.register("cooled_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COOLED_LITHERITE_SWORD = ITEMS.register("cooled_litherite_sword",
            () -> new CooledLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_PICKAXE = ITEMS.register("cooled_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_HAMMER = ITEMS.register("cooled_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_AXE = ITEMS.register("cooled_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_SHOVEL = ITEMS.register("cooled_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_HOE = ITEMS.register("cooled_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_HELMET = ITEMS.register("cooled_litherite_helmet",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> COOLED_LITHERITE_CHESTPLATE = ITEMS.register("cooled_litherite_chestplate",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_LEGGINGS = ITEMS.register("cooled_litherite_leggings",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> COOLED_LITHERITE_BOOTS = ITEMS.register("cooled_litherite_boots",
            () -> new CooledLitheriteArmorItem(ModArmorMaterials.COOLED_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> WITHERING_LITHERITE_CRYSTAL = ITEMS.register("withering_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WITHERING_LITHERITE_SWORD = ITEMS.register("withering_litherite_sword",
            () -> new WitheringLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_PICKAXE = ITEMS.register("withering_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_HAMMER = ITEMS.register("withering_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_AXE = ITEMS.register("withering_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_SHOVEL = ITEMS.register("withering_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_HOE = ITEMS.register("withering_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_HELMET = ITEMS.register("withering_litherite_helmet",
            () -> new WitheringLitheriteArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_CHESTPLATE = ITEMS.register("withering_litherite_chestplate",
            () -> new WitheringLitheriteArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_LEGGINGS = ITEMS.register("withering_litherite_leggings",
            () -> new WitheringLitheriteArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_BOOTS = ITEMS.register("withering_litherite_boots",
            () -> new WitheringLitheriteArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> REGENERATING_LITHERITE_CRYSTAL = ITEMS.register("regenerating_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_SWORD = ITEMS.register("regenerating_litherite_sword",
            () -> new RegeneratingLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_PICKAXE = ITEMS.register("regenerating_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_HAMMER = ITEMS.register("regenerating_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_AXE = ITEMS.register("regenerating_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_SHOVEL = ITEMS.register("regenerating_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_HOE = ITEMS.register("regenerating_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_HELMET = ITEMS.register("regenerating_litherite_helmet",
            () -> new RegeneratingLitheriteArmorItem(ModArmorMaterials.REGENERATING_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_CHESTPLATE = ITEMS.register("regenerating_litherite_chestplate",
            () -> new RegeneratingLitheriteArmorItem(ModArmorMaterials.REGENERATING_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_LEGGINGS = ITEMS.register("regenerating_litherite_leggings",
            () -> new RegeneratingLitheriteArmorItem(ModArmorMaterials.REGENERATING_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> REGENERATING_LITHERITE_BOOTS = ITEMS.register("regenerating_litherite_boots",
            () -> new RegeneratingLitheriteArmorItem(ModArmorMaterials.REGENERATING_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> GLOWING_LITHERITE_CRYSTAL = ITEMS.register("glowing_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GLOWING_LITHERITE_SWORD = ITEMS.register("glowing_litherite_sword",
            () -> new GlowingLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_PICKAXE = ITEMS.register("glowing_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_HAMMER = ITEMS.register("glowing_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_AXE = ITEMS.register("glowing_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_SHOVEL = ITEMS.register("glowing_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_HOE = ITEMS.register("glowing_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_HELMET = ITEMS.register("glowing_litherite_helmet",
            () -> new GlowingLitheriteArmorItem(ModArmorMaterials.GLOWING_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_CHESTPLATE = ITEMS.register("glowing_litherite_chestplate",
            () -> new GlowingLitheriteArmorItem(ModArmorMaterials.GLOWING_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_LEGGINGS = ITEMS.register("glowing_litherite_leggings",
            () -> new GlowingLitheriteArmorItem(ModArmorMaterials.GLOWING_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> GLOWING_LITHERITE_BOOTS = ITEMS.register("glowing_litherite_boots",
            () -> new GlowingLitheriteArmorItem(ModArmorMaterials.GLOWING_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> INVISIBLE_LITHERITE_CRYSTAL = ITEMS.register("invisible_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_SWORD = ITEMS.register("invisible_litherite_sword",
            () -> new InvisibleLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_PICKAXE = ITEMS.register("invisible_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_HAMMER = ITEMS.register("invisible_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_AXE = ITEMS.register("invisible_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_SHOVEL = ITEMS.register("invisible_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_HOE = ITEMS.register("invisible_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_HELMET = ITEMS.register("invisible_litherite_helmet",
            () -> new InvisibleLitheriteArmorItem(ModArmorMaterials.INVISIBLE_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_CHESTPLATE = ITEMS.register("invisible_litherite_chestplate",
            () -> new InvisibleLitheriteArmorItem(ModArmorMaterials.INVISIBLE_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_LEGGINGS = ITEMS.register("invisible_litherite_leggings",
            () -> new InvisibleLitheriteArmorItem(ModArmorMaterials.INVISIBLE_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> INVISIBLE_LITHERITE_BOOTS = ITEMS.register("invisible_litherite_boots",
            () -> new InvisibleLitheriteArmorItem(ModArmorMaterials.INVISIBLE_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}