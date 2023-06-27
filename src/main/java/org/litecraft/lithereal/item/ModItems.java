package org.litecraft.lithereal.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.item.custom.LitheriteHammerItem;
import org.litecraft.lithereal.item.custom.LitheriteItem;
import org.litecraft.lithereal.item.custom.ModArmorMaterials;
import org.litecraft.lithereal.item.custom.WrenchItem;
import org.litecraft.lithereal.item.custom.burning.*;
import org.litecraft.lithereal.item.custom.freezing.CooledLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.freezing.CooledLitheriteSword;
import org.litecraft.lithereal.item.custom.invisible.InvisibleLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.invisible.InvisibleLitheriteSword;
import org.litecraft.lithereal.item.custom.leaping.LeapingLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.leaping.LeapingLitheriteSword;
import org.litecraft.lithereal.item.custom.lucky.LuckyLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.lucky.LuckyLitheriteSword;
import org.litecraft.lithereal.item.custom.nightvision.NightVisionLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.nightvision.NightVisionLitheriteSword;
import org.litecraft.lithereal.item.custom.poison.PoisonLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.poison.PoisonLitheriteSword;
import org.litecraft.lithereal.item.custom.potion.PotionLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.potion.PotionLitheriteSword;
import org.litecraft.lithereal.item.custom.regenerating.RegeneratingLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.regenerating.RegeneratingLitheriteSword;
import org.litecraft.lithereal.item.custom.slow.SlowLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.slow.SlowLitheriteSword;
import org.litecraft.lithereal.item.custom.slowfalling.SlowFallingLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.slowfalling.SlowFallingLitheriteSword;
import org.litecraft.lithereal.item.custom.strong.StrongLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.strong.StrongLitheriteSword;
import org.litecraft.lithereal.item.custom.swift.SwiftLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.swift.SwiftLitheriteSword;
import org.litecraft.lithereal.item.custom.waterbreathing.WaterBreathingLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.waterbreathing.WaterBreathingLitheriteSword;
import org.litecraft.lithereal.item.custom.weak.WeakLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.weak.WeakLitheriteSword;
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


    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_CRYSTAL = ITEMS.register("night_vision_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_SWORD = ITEMS.register("night_vision_litherite_sword",
            () -> new NightVisionLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_PICKAXE = ITEMS.register("night_vision_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_HAMMER = ITEMS.register("night_vision_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_AXE = ITEMS.register("night_vision_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_SHOVEL = ITEMS.register("night_vision_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_HOE = ITEMS.register("night_vision_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_HELMET = ITEMS.register("night_vision_litherite_helmet",
            () -> new NightVisionLitheriteArmorItem(ModArmorMaterials.NIGHT_VISION_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_CHESTPLATE = ITEMS.register("night_vision_litherite_chestplate",
            () -> new NightVisionLitheriteArmorItem(ModArmorMaterials.NIGHT_VISION_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_LEGGINGS = ITEMS.register("night_vision_litherite_leggings",
            () -> new NightVisionLitheriteArmorItem(ModArmorMaterials.NIGHT_VISION_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> NIGHT_VISION_LITHERITE_BOOTS = ITEMS.register("night_vision_litherite_boots",
            () -> new NightVisionLitheriteArmorItem(ModArmorMaterials.NIGHT_VISION_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> LEAPING_LITHERITE_CRYSTAL = ITEMS.register("leaping_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEAPING_LITHERITE_SWORD = ITEMS.register("leaping_litherite_sword",
            () -> new LeapingLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_PICKAXE = ITEMS.register("leaping_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_HAMMER = ITEMS.register("leaping_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_AXE = ITEMS.register("leaping_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_SHOVEL = ITEMS.register("leaping_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_HOE = ITEMS.register("leaping_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_HELMET = ITEMS.register("leaping_litherite_helmet",
            () -> new LeapingLitheriteArmorItem(ModArmorMaterials.LEAPING_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_CHESTPLATE = ITEMS.register("leaping_litherite_chestplate",
            () -> new LeapingLitheriteArmorItem(ModArmorMaterials.LEAPING_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_LEGGINGS = ITEMS.register("leaping_litherite_leggings",
            () -> new LeapingLitheriteArmorItem(ModArmorMaterials.LEAPING_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> LEAPING_LITHERITE_BOOTS = ITEMS.register("leaping_litherite_boots",
            () -> new LeapingLitheriteArmorItem(ModArmorMaterials.LEAPING_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> SWIFT_LITHERITE_CRYSTAL = ITEMS.register("swift_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SWIFT_LITHERITE_SWORD = ITEMS.register("swift_litherite_sword",
            () -> new SwiftLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_PICKAXE = ITEMS.register("swift_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_HAMMER = ITEMS.register("swift_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_AXE = ITEMS.register("swift_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_SHOVEL = ITEMS.register("swift_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_HOE = ITEMS.register("swift_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_HELMET = ITEMS.register("swift_litherite_helmet",
            () -> new SwiftLitheriteArmorItem(ModArmorMaterials.SWIFT_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_CHESTPLATE = ITEMS.register("swift_litherite_chestplate",
            () -> new SwiftLitheriteArmorItem(ModArmorMaterials.SWIFT_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_LEGGINGS = ITEMS.register("swift_litherite_leggings",
            () -> new SwiftLitheriteArmorItem(ModArmorMaterials.SWIFT_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> SWIFT_LITHERITE_BOOTS = ITEMS.register("swift_litherite_boots",
            () -> new SwiftLitheriteArmorItem(ModArmorMaterials.SWIFT_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> SLOW_LITHERITE_CRYSTAL = ITEMS.register("slow_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SLOW_LITHERITE_SWORD = ITEMS.register("slow_litherite_sword",
            () -> new SlowLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_LITHERITE_PICKAXE = ITEMS.register("slow_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_LITHERITE_HAMMER = ITEMS.register("slow_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> SLOW_LITHERITE_AXE = ITEMS.register("slow_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_LITHERITE_SHOVEL = ITEMS.register("slow_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_LITHERITE_HOE = ITEMS.register("slow_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_LITHERITE_HELMET = ITEMS.register("slow_litherite_helmet",
            () -> new SlowLitheriteArmorItem(ModArmorMaterials.SLOW_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> SLOW_LITHERITE_CHESTPLATE = ITEMS.register("slow_litherite_chestplate",
            () -> new SlowLitheriteArmorItem(ModArmorMaterials.SLOW_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> SLOW_LITHERITE_LEGGINGS = ITEMS.register("slow_litherite_leggings",
            () -> new SlowLitheriteArmorItem(ModArmorMaterials.SLOW_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> SLOW_LITHERITE_BOOTS = ITEMS.register("slow_litherite_boots",
            () -> new SlowLitheriteArmorItem(ModArmorMaterials.SLOW_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_CRYSTAL = ITEMS.register("water_breathing_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_SWORD = ITEMS.register("water_breathing_litherite_sword",
            () -> new WaterBreathingLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_PICKAXE = ITEMS.register("water_breathing_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_HAMMER = ITEMS.register("water_breathing_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_AXE = ITEMS.register("water_breathing_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_SHOVEL = ITEMS.register("water_breathing_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_HOE = ITEMS.register("water_breathing_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_HELMET = ITEMS.register("water_breathing_litherite_helmet",
            () -> new WaterBreathingLitheriteArmorItem(ModArmorMaterials.WATER_BREATHING_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_CHESTPLATE = ITEMS.register("water_breathing_litherite_chestplate",
            () -> new WaterBreathingLitheriteArmorItem(ModArmorMaterials.WATER_BREATHING_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_LEGGINGS = ITEMS.register("water_breathing_litherite_leggings",
            () -> new WaterBreathingLitheriteArmorItem(ModArmorMaterials.WATER_BREATHING_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> WATER_BREATHING_LITHERITE_BOOTS = ITEMS.register("water_breathing_litherite_boots",
            () -> new WaterBreathingLitheriteArmorItem(ModArmorMaterials.WATER_BREATHING_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> POISON_LITHERITE_CRYSTAL = ITEMS.register("poison_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POISON_LITHERITE_SWORD = ITEMS.register("poison_litherite_sword",
            () -> new PoisonLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POISON_LITHERITE_PICKAXE = ITEMS.register("poison_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POISON_LITHERITE_HAMMER = ITEMS.register("poison_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> POISON_LITHERITE_AXE = ITEMS.register("poison_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POISON_LITHERITE_SHOVEL = ITEMS.register("poison_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POISON_LITHERITE_HOE = ITEMS.register("poison_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POISON_LITHERITE_HELMET = ITEMS.register("poison_litherite_helmet",
            () -> new PoisonLitheriteArmorItem(ModArmorMaterials.POISON_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> POISON_LITHERITE_CHESTPLATE = ITEMS.register("poison_litherite_chestplate",
            () -> new PoisonLitheriteArmorItem(ModArmorMaterials.POISON_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> POISON_LITHERITE_LEGGINGS = ITEMS.register("poison_litherite_leggings",
            () -> new PoisonLitheriteArmorItem(ModArmorMaterials.POISON_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> POISON_LITHERITE_BOOTS = ITEMS.register("poison_litherite_boots",
            () -> new PoisonLitheriteArmorItem(ModArmorMaterials.POISON_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> STRONG_LITHERITE_CRYSTAL = ITEMS.register("strong_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STRONG_LITHERITE_SWORD = ITEMS.register("strong_litherite_sword",
            () -> new StrongLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> STRONG_LITHERITE_PICKAXE = ITEMS.register("strong_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> STRONG_LITHERITE_HAMMER = ITEMS.register("strong_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> STRONG_LITHERITE_AXE = ITEMS.register("strong_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> STRONG_LITHERITE_SHOVEL = ITEMS.register("strong_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> STRONG_LITHERITE_HOE = ITEMS.register("strong_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> STRONG_LITHERITE_HELMET = ITEMS.register("strong_litherite_helmet",
            () -> new StrongLitheriteArmorItem(ModArmorMaterials.STRONG_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> STRONG_LITHERITE_CHESTPLATE = ITEMS.register("strong_litherite_chestplate",
            () -> new StrongLitheriteArmorItem(ModArmorMaterials.STRONG_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> STRONG_LITHERITE_LEGGINGS = ITEMS.register("strong_litherite_leggings",
            () -> new StrongLitheriteArmorItem(ModArmorMaterials.STRONG_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> STRONG_LITHERITE_BOOTS = ITEMS.register("strong_litherite_boots",
            () -> new StrongLitheriteArmorItem(ModArmorMaterials.STRONG_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> WEAK_LITHERITE_CRYSTAL = ITEMS.register("weak_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WEAK_LITHERITE_SWORD = ITEMS.register("weak_litherite_sword",
            () -> new WeakLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WEAK_LITHERITE_PICKAXE = ITEMS.register("weak_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WEAK_LITHERITE_HAMMER = ITEMS.register("weak_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> WEAK_LITHERITE_AXE = ITEMS.register("weak_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WEAK_LITHERITE_SHOVEL = ITEMS.register("weak_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WEAK_LITHERITE_HOE = ITEMS.register("weak_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> WEAK_LITHERITE_HELMET = ITEMS.register("weak_litherite_helmet",
            () -> new WeakLitheriteArmorItem(ModArmorMaterials.WEAK_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> WEAK_LITHERITE_CHESTPLATE = ITEMS.register("weak_litherite_chestplate",
            () -> new WeakLitheriteArmorItem(ModArmorMaterials.WEAK_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> WEAK_LITHERITE_LEGGINGS = ITEMS.register("weak_litherite_leggings",
            () -> new WeakLitheriteArmorItem(ModArmorMaterials.WEAK_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> WEAK_LITHERITE_BOOTS = ITEMS.register("weak_litherite_boots",
            () -> new WeakLitheriteArmorItem(ModArmorMaterials.WEAK_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> LUCKY_LITHERITE_CRYSTAL = ITEMS.register("lucky_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LUCKY_LITHERITE_SWORD = ITEMS.register("lucky_litherite_sword",
            () -> new LuckyLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_PICKAXE = ITEMS.register("lucky_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_HAMMER = ITEMS.register("lucky_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_AXE = ITEMS.register("lucky_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_SHOVEL = ITEMS.register("lucky_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_HOE = ITEMS.register("lucky_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_HELMET = ITEMS.register("lucky_litherite_helmet",
            () -> new LuckyLitheriteArmorItem(ModArmorMaterials.LUCKY_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_CHESTPLATE = ITEMS.register("lucky_litherite_chestplate",
            () -> new LuckyLitheriteArmorItem(ModArmorMaterials.LUCKY_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_LEGGINGS = ITEMS.register("lucky_litherite_leggings",
            () -> new LuckyLitheriteArmorItem(ModArmorMaterials.LUCKY_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> LUCKY_LITHERITE_BOOTS = ITEMS.register("lucky_litherite_boots",
            () -> new LuckyLitheriteArmorItem(ModArmorMaterials.LUCKY_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_CRYSTAL = ITEMS.register("slow_falling_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_SWORD = ITEMS.register("slow_falling_litherite_sword",
            () -> new SlowFallingLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_PICKAXE = ITEMS.register("slow_falling_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_HAMMER = ITEMS.register("slow_falling_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_AXE = ITEMS.register("slow_falling_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_SHOVEL = ITEMS.register("slow_falling_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_HOE = ITEMS.register("slow_falling_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_HELMET = ITEMS.register("slow_falling_litherite_helmet",
            () -> new SlowFallingLitheriteArmorItem(ModArmorMaterials.SLOW_FALLING_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_CHESTPLATE = ITEMS.register("slow_falling_litherite_chestplate",
            () -> new SlowFallingLitheriteArmorItem(ModArmorMaterials.SLOW_FALLING_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_LEGGINGS = ITEMS.register("slow_falling_litherite_leggings",
            () -> new SlowFallingLitheriteArmorItem(ModArmorMaterials.SLOW_FALLING_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> SLOW_FALLING_LITHERITE_BOOTS = ITEMS.register("slow_falling_litherite_boots",
            () -> new SlowFallingLitheriteArmorItem(ModArmorMaterials.SLOW_FALLING_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));


    public static final RegistryObject<Item> POTION_LITHERITE_CRYSTAL = ITEMS.register("potion_litherite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POTION_LITHERITE_SWORD = ITEMS.register("potion_litherite_sword",
            () -> new PotionLitheriteSword(Tiers.DIAMOND, 4, -1.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POTION_LITHERITE_PICKAXE = ITEMS.register("potion_litherite_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 2, -1.2f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POTION_LITHERITE_HAMMER = ITEMS.register("potion_litherite_hammer",
            () -> new LitheriteHammerItem(Tiers.DIAMOND, 4, -2.4f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> POTION_LITHERITE_AXE = ITEMS.register("potion_litherite_axe",
            () -> new AxeItem(Tiers.DIAMOND, 6, -0.6f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POTION_LITHERITE_SHOVEL = ITEMS.register("potion_litherite_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 2.5f, -1f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POTION_LITHERITE_HOE = ITEMS.register("potion_litherite_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -2, 0.8f,
                    new Item.Properties().durability(1750)));

    public static final RegistryObject<Item> POTION_LITHERITE_HELMET = ITEMS.register("potion_litherite_helmet",
            () -> new PotionLitheriteArmorItem(ModArmorMaterials.POTION_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> POTION_LITHERITE_CHESTPLATE = ITEMS.register("potion_litherite_chestplate",
            () -> new PotionLitheriteArmorItem(ModArmorMaterials.POTION_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> POTION_LITHERITE_LEGGINGS = ITEMS.register("potion_litherite_leggings",
            () -> new PotionLitheriteArmorItem(ModArmorMaterials.POTION_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> POTION_LITHERITE_BOOTS = ITEMS.register("potion_litherite_boots",
            () -> new PotionLitheriteArmorItem(ModArmorMaterials.POTION_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}