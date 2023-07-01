package org.litecraft.lithereal.item;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
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
import org.litecraft.lithereal.item.custom.infused.*;
import org.litecraft.lithereal.item.custom.withering.WitheringLitheriteArmorItem;
import org.litecraft.lithereal.item.custom.withering.WitheringLitheriteSword;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Lithereal.MOD_ID);

    public static final RegistryObject<Item> LITHERITE_CRYSTAL = ITEMS.register("litherite_crystal",
            () -> new LitheriteItem(new Item.Properties()));

    public static final ForgeTier LITHERITE = new ForgeTier(4, 1750, 11.0F, 4.0F, 20, Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(Items.DIAMOND));
    
    public static final RegistryObject<Item> LITHERITE_SWORD = ITEMS.register("litherite_sword",
            () -> new SwordItem(LITHERITE, 3, -2.4f,
                    new Item.Properties()));

    public static final RegistryObject<Item> LITHERITE_PICKAXE = ITEMS.register("litherite_pickaxe",
            () -> new PickaxeItem(LITHERITE, 1, -2.8f,
                    new Item.Properties()));

    public static final RegistryObject<Item> LITHERITE_HAMMER = ITEMS.register("litherite_hammer",
            () -> new LitheriteHammerItem(LITHERITE, 5, -3f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> LITHERITE_AXE = ITEMS.register("litherite_axe",
            () -> new AxeItem(LITHERITE, 5, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> LITHERITE_SHOVEL = ITEMS.register("litherite_shovel",
            () -> new ShovelItem(LITHERITE, 1.5f, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> LITHERITE_HOE = ITEMS.register("litherite_hoe",
            () -> new HoeItem(LITHERITE, -3, 0.0f,
                    new Item.Properties()));

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
            () -> new BurningLitheriteSword(LITHERITE, 3, -2.4f,
                    new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_PICKAXE = ITEMS.register("heated_litherite_pickaxe",
            () -> new BurningLitheritePickaxeItem(LITHERITE, 1, -2.8f,
                    new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_HAMMER = ITEMS.register("heated_litherite_hammer",
            () -> new BurningLitheriteHammerItem(LITHERITE, 5, -3f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> HEATED_LITHERITE_AXE = ITEMS.register("heated_litherite_axe",
            () -> new BurningLitheriteAxeItem(LITHERITE, 5, -3f,
                    new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_SHOVEL = ITEMS.register("heated_litherite_shovel",
            () -> new BurningLitheriteShovelItem(LITHERITE, 1.5f, -3f,
                    new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> HEATED_LITHERITE_HOE = ITEMS.register("heated_litherite_hoe",
            () -> new BurningLitheriteHoeItem(LITHERITE, -3, 0.0f,
                    new Item.Properties().fireResistant()));

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
            () -> new CooledLitheriteSword(LITHERITE, 3, -2.4f,
                    new Item.Properties()));

    public static final RegistryObject<Item> COOLED_LITHERITE_PICKAXE = ITEMS.register("cooled_litherite_pickaxe",
            () -> new PickaxeItem(LITHERITE, 1, -2.8f,
                    new Item.Properties()));

    public static final RegistryObject<Item> COOLED_LITHERITE_HAMMER = ITEMS.register("cooled_litherite_hammer",
            () -> new LitheriteHammerItem(LITHERITE, 5, -3f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> COOLED_LITHERITE_AXE = ITEMS.register("cooled_litherite_axe",
            () -> new AxeItem(LITHERITE, 5, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> COOLED_LITHERITE_SHOVEL = ITEMS.register("cooled_litherite_shovel",
            () -> new ShovelItem(LITHERITE, 1.5f, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> COOLED_LITHERITE_HOE = ITEMS.register("cooled_litherite_hoe",
            () -> new HoeItem(LITHERITE, -3, 0.0f,
                    new Item.Properties()));

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
            () -> new WitheringLitheriteSword(LITHERITE, 3, -2.4f,
                    new Item.Properties()));

    public static final RegistryObject<Item> WITHERING_LITHERITE_PICKAXE = ITEMS.register("withering_litherite_pickaxe",
            () -> new PickaxeItem(LITHERITE, 1, -2.8f,
                    new Item.Properties()));

    public static final RegistryObject<Item> WITHERING_LITHERITE_HAMMER = ITEMS.register("withering_litherite_hammer",
            () -> new LitheriteHammerItem(LITHERITE, 5, -3f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> WITHERING_LITHERITE_AXE = ITEMS.register("withering_litherite_axe",
            () -> new AxeItem(LITHERITE, 5, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> WITHERING_LITHERITE_SHOVEL = ITEMS.register("withering_litherite_shovel",
            () -> new ShovelItem(LITHERITE, 1.5f, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> WITHERING_LITHERITE_HOE = ITEMS.register("withering_litherite_hoe",
            () -> new HoeItem(LITHERITE, -3, 0.0f,
                    new Item.Properties()));

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


    public static final RegistryObject<Item> INFUSED_LITHERITE_CRYSTAL = ITEMS.register("infused_litherite_crystal",
            () -> new InfusedLitheriteItem(new Item.Properties()));

    public static final RegistryObject<Item> INFUSED_LITHERITE_SWORD = ITEMS.register("infused_litherite_sword",
            () -> new InfusedLitheriteSword(LITHERITE, 3, -2.4f,
                    new Item.Properties()));

    public static final RegistryObject<Item> INFUSED_LITHERITE_PICKAXE = ITEMS.register("infused_litherite_pickaxe",
            () -> new InfusedLitheritePickaxe(LITHERITE, 1, -2.8f,
                    new Item.Properties()));

    public static final RegistryObject<Item> INFUSED_LITHERITE_HAMMER = ITEMS.register("infused_litherite_hammer",
            () -> new InfusedLitheriteHammer(LITHERITE, 5, -3f,
                    new Item.Properties().durability(15750)));

    public static final RegistryObject<Item> INFUSED_LITHERITE_AXE = ITEMS.register("infused_litherite_axe",
            () -> new InfusedLitheriteAxe(LITHERITE, 5, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> INFUSED_LITHERITE_SHOVEL = ITEMS.register("infused_litherite_shovel",
            () -> new InfusedLitheriteShovel(LITHERITE, 1.5f, -3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> INFUSED_LITHERITE_HOE = ITEMS.register("infused_litherite_hoe",
            () -> new InfusedLitheriteHoe(LITHERITE, -3, 0.0f,
                    new Item.Properties()));

    public static final RegistryObject<Item> INFUSED_LITHERITE_HELMET = ITEMS.register("infused_litherite_helmet",
            () -> new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(500)));

    public static final RegistryObject<Item> INFUSED_LITHERITE_CHESTPLATE = ITEMS.register("infused_litherite_chestplate",
            () -> new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(750)));

    public static final RegistryObject<Item> INFUSED_LITHERITE_LEGGINGS = ITEMS.register("infused_litherite_leggings",
            () -> new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(700)));

    public static final RegistryObject<Item> INFUSED_LITHERITE_BOOTS = ITEMS.register("infused_litherite_boots",
            () -> new InfusedLitheriteArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(550)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}