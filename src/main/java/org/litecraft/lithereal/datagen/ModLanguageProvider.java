package org.litecraft.lithereal.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Lithereal.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        addBlock(ModBlocks.LITHERITE_BLOCK, "Litherite Block");
        addItem(ModItems.LITHERITE_CRYSTAL, "Litherite Crystal");
        addItem(ModItems.LITHERITE_SWORD, "Litherite Crystal Sword");
        addItem(ModItems.LITHERITE_HOE, "Litherite Crystal Hoe");
        addItem(ModItems.LITHERITE_PICKAXE, "Litherite Crystal Pickaxe");
        addItem(ModItems.LITHERITE_HAMMER, "Litherite Crystal Hammer");
        addItem(ModItems.LITHERITE_AXE, "Litherite Crystal Axe");
        addItem(ModItems.LITHERITE_SHOVEL, "Litherite Crystal Shovel");
        addItem(ModItems.LITHERITE_HELMET, "Litherite Crystal Helmet");
        addItem(ModItems.LITHERITE_CHESTPLATE, "Litherite Crystal Chestplate");
        addItem(ModItems.LITHERITE_LEGGINGS, "Litherite Crystal Leggings");
        addItem(ModItems.LITHERITE_BOOTS, "Litherite Crystal Boots");
        addItem(ModItems.LITHERITE_WRENCH, "Wrench");

        addBlock(ModBlocks.COOLED_LITHERITE_BLOCK, "Frozen Litherite Block");
        addItem(ModItems.COOLED_LITHERITE_CRYSTAL, "Frozen Litherite Crystal");
        addItem(ModItems.COOLED_LITHERITE_SWORD, "Frozen Litherite Crystal Sword");
        addItem(ModItems.COOLED_LITHERITE_HOE, "Frozen Litherite Crystal Hoe");
        addItem(ModItems.COOLED_LITHERITE_PICKAXE, "Frozen Litherite Crystal Pickaxe");
        addItem(ModItems.COOLED_LITHERITE_HAMMER, "Frozen Litherite Crystal Hammer");
        addItem(ModItems.COOLED_LITHERITE_AXE, "Frozen Litherite Crystal Axe");
        addItem(ModItems.COOLED_LITHERITE_SHOVEL, "Frozen Litherite Crystal Shovel");
        addItem(ModItems.COOLED_LITHERITE_HELMET, "Frozen Litherite Crystal Helmet");
        addItem(ModItems.COOLED_LITHERITE_CHESTPLATE, "Frozen Litherite Crystal Chestplate");
        addItem(ModItems.COOLED_LITHERITE_LEGGINGS, "Frozen Litherite Crystal Leggings");
        addItem(ModItems.COOLED_LITHERITE_BOOTS, "Frozen Litherite Crystal Boots");

        addBlock(ModBlocks.HEATED_LITHERITE_BLOCK, "Burning Litherite Block");
        addItem(ModItems.HEATED_LITHERITE_CRYSTAL, "Burning Litherite Crystal");
        addItem(ModItems.HEATED_LITHERITE_SWORD, "Burning Litherite Crystal Sword");
        addItem(ModItems.HEATED_LITHERITE_HOE, "Burning Litherite Crystal Hoe");
        addItem(ModItems.HEATED_LITHERITE_PICKAXE, "Burning Litherite Crystal Pickaxe");
        addItem(ModItems.HEATED_LITHERITE_HAMMER, "Burning Litherite Crystal Hammer");
        addItem(ModItems.HEATED_LITHERITE_AXE, "Burning Litherite Crystal Axe");
        addItem(ModItems.HEATED_LITHERITE_SHOVEL, "Burning Litherite Crystal Shovel");
        addItem(ModItems.HEATED_LITHERITE_HELMET, "Burning Litherite Crystal Helmet");
        addItem(ModItems.HEATED_LITHERITE_CHESTPLATE, "Burning Litherite Crystal Chestplate");
        addItem(ModItems.HEATED_LITHERITE_LEGGINGS, "Burning Litherite Crystal Leggings");
        addItem(ModItems.HEATED_LITHERITE_BOOTS, "Burning Litherite Crystal Boots");

        addBlock(ModBlocks.WITHERING_LITHERITE_BLOCK, "Withering Litherite Block");
        addItem(ModItems.WITHERING_LITHERITE_CRYSTAL, "Withering Litherite Crystal");
        addItem(ModItems.WITHERING_LITHERITE_SWORD, "Withering Litherite Crystal Sword");
        addItem(ModItems.WITHERING_LITHERITE_HOE, "Withering Litherite Crystal Hoe");
        addItem(ModItems.WITHERING_LITHERITE_PICKAXE, "Withering Litherite Crystal Pickaxe");
        addItem(ModItems.WITHERING_LITHERITE_HAMMER, "Withering Litherite Crystal Hammer");
        addItem(ModItems.WITHERING_LITHERITE_AXE, "Withering Litherite Crystal Axe");
        addItem(ModItems.WITHERING_LITHERITE_SHOVEL, "Withering Litherite Crystal Shovel");
        addItem(ModItems.WITHERING_LITHERITE_HELMET, "Withering Litherite Crystal Helmet");
        addItem(ModItems.WITHERING_LITHERITE_CHESTPLATE, "Withering Litherite Crystal Chestplate");
        addItem(ModItems.WITHERING_LITHERITE_LEGGINGS, "Withering Litherite Crystal Leggings");
        addItem(ModItems.WITHERING_LITHERITE_BOOTS, "Withering Litherite Crystal Boots");

        addPotion(ModBlocks.INFUSED_LITHERITE_BLOCK.get(), "Litherite Block");
        addPotion(ModItems.INFUSED_LITHERITE_CRYSTAL.get(), "Litherite Crystal");
        addPotion(ModItems.INFUSED_LITHERITE_SWORD.get(), "Litherite Crystal Sword");
        addPotion(ModItems.INFUSED_LITHERITE_HOE.get(), "Litherite Crystal Hoe");
        addPotion(ModItems.INFUSED_LITHERITE_PICKAXE.get(), "Litherite Crystal Pickaxe");
        addPotion(ModItems.INFUSED_LITHERITE_HAMMER.get(), "Litherite Crystal Hammer");
        addPotion(ModItems.INFUSED_LITHERITE_AXE.get(), "Litherite Crystal Axe");
        addPotion(ModItems.INFUSED_LITHERITE_SHOVEL.get(), "Litherite Crystal Shovel");
        addPotion(ModItems.INFUSED_LITHERITE_HELMET.get(), "Litherite Crystal Helmet");
        addPotion(ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), "Litherite Crystal Chestplate");
        addPotion(ModItems.INFUSED_LITHERITE_LEGGINGS.get(), "Litherite Crystal Leggings");
        addPotion(ModItems.INFUSED_LITHERITE_BOOTS.get(), "Litherite Crystal Boots");

        addBlock(ModBlocks.LITHERITE_ORE, "Litherite Ore");
        addBlock(ModBlocks.DEEPSLATE_LITHERITE_ORE, "Deepslate Litherite Ore");

        addBlock(ModBlocks.SCORCHED_CRIMSON_NYLIUM, "Scorched Crimson Nylium Block");
        addBlock(ModBlocks.SCORCHED_WARPED_NYLIUM, "Scorched Warped Nylium Block");
        addBlock(ModBlocks.SCORCHED_NETHERRACK, "Scorched Netherrack Block");

        addBlock(ModBlocks.FREEZING_STATION, "Freezing Station");
        addBlock(ModBlocks.FIRE_CRUCIBLE, "Fire Crucible");

        addBlock(ModBlocks.INFUSEMENT_CHAMBER_CASING, "Infusement Chamber Casing");
        addBlock(ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER, "Infusement Chamber Controller");
        addBlock(ModBlocks.INFUSEMENT_CHAMBER, "Infusement Chamber");
        addBlock(ModBlocks.INFUSEMENT_CHAMBER_CORE, "Infusement Chamber Core");

        addBlock(ModBlocks.BLUE_FIRE, "Blue Fire");

        add("creativemodetab.lithereal_tab", "Lithereal");

        add("key.lithereal.scorch_ground", "Scorch Ground");
        add("key.lithereal.freeze_water", "Frost Walker");
        add("key.category.lithereal.lithereal", "Lithereal");

        add("message.lithereal.missing_infusement_core", "Missing Infusement Chamber Core.");
        add("message.lithereal.missing_infusement_casing", "Missing Infusement Chamber Casing.");
    }

    public void addPotion(Block key, String name) {
        add(key, "Infused " + name);
        for(Potion potion : ForgeRegistries.POTIONS) {
            ItemStack stack = key.asItem().getDefaultInstance();
            PotionUtils.setPotion(stack, potion);
            ItemStack stack1 = Items.TIPPED_ARROW.getDefaultInstance();
            PotionUtils.setPotion(stack1, potion);
            String string = String.valueOf(Component.translatable(stack1.getItem().getDescriptionId()));
            if (string.contains("Arrow of "))
                string = string.replaceAll("Arrow of ", "");
            if (string.contains("the "))
                string = string.replaceAll("the ", "");
            if (string.contains("Tipped Arrow"))
                string = string.replaceAll("Tipped Arrow", "");
            if(!string.isBlank())
                name = string + " " + name;
            add(key.asItem().getDescriptionId(stack), name);
        }
    }

    public void addPotion(Item key, String name) {
        add(key, "Infused " + name);
        for(Potion potion : ForgeRegistries.POTIONS) {
            ItemStack stack = key.getDefaultInstance();
            PotionUtils.setPotion(stack, potion);
            ItemStack stack1 = Items.TIPPED_ARROW.getDefaultInstance();
            PotionUtils.setPotion(stack1, potion);
            String string = String.valueOf(Component.translatable(stack1.getItem().getDescriptionId()));
            if (string.contains("Arrow of "))
                string = string.replaceAll("Arrow of ", "");
            if (string.contains("the "))
                string = string.replaceAll("the ", "");
            if (string.contains("Tipped Arrow"))
                string = string.replaceAll("Tipped Arrow", "");
            if(!string.isBlank())
                name = string + " " + name;
            add(key.getDescriptionId(stack), name);
        }
    }
}
