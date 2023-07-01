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

import java.util.ArrayList;

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
        ArrayList<String> descriptionIDs = new ArrayList<>();
        for(Potion potion : ForgeRegistries.POTIONS) {
            ItemStack stack = key.asItem().getDefaultInstance();
            PotionUtils.setPotion(stack, potion);
            String descID = key.asItem().getDescriptionId(stack);
            if(!descriptionIDs.contains(descID)) {
                String strippedPotionKnowledge = descID.replaceAll(key.getDescriptionId() + ".effect.", "");
                String nameForID = name;
                switch (strippedPotionKnowledge) {
                    case "awkward", "mundane", "thick" -> nameForID = "Infused " + name;
                    case "empty" -> nameForID = "Uncraftable Infused " + name;
                    case "water" -> nameForID = "Splashing " + name;
                    case "night_vision" -> nameForID = "Night Vision " + name;
                    case "invisibility" -> nameForID = "Invisibility " + name;
                    case "leaping" -> nameForID = "Jump Boost " + name;
                    case "fire_resistance" -> nameForID = "Fire Resistance " + name;
                    case "swiftness" -> nameForID = "Swift " + name;
                    case "slowness" -> nameForID = "Slowed " + name;
                    case "water_breathing" -> nameForID = "Water Breathing " + name;
                    case "healing" -> nameForID = "Healing " + name;
                    case "harming" -> nameForID = "Harming " + name;
                    case "poison" -> nameForID = "Poisoned " + name;
                    case "regeneration" -> nameForID = "Regenerating " + name;
                    case "strength" -> nameForID = "Empowered " + name;
                    case "weakness" -> nameForID = "Weakening " + name;
                    case "levitation" -> nameForID = "Levitating " + name;
                    case "luck" -> nameForID = "Lucky " + name;
                    case "turtle_master" -> nameForID = "Turtle Master's " + name;
                    case "slow_falling" -> nameForID = "Slow Falling " + name;
                }
                add(key.asItem().getDescriptionId(stack), nameForID);
                descriptionIDs.add(descID);
            }
        }
    }

    public void addPotion(Item key, String name) {
        add(key, "Infused " + name);
        ArrayList<String> descriptionIDs = new ArrayList<>();
        for(Potion potion : ForgeRegistries.POTIONS) {
            ItemStack stack = key.getDefaultInstance();
            PotionUtils.setPotion(stack, potion);
            String descID = key.asItem().getDescriptionId(stack);
            if(!descriptionIDs.contains(descID)) {
                String strippedPotionKnowledge = descID.replaceAll(key.getDescriptionId() + ".effect.", "");
                String nameForID = name;
                switch (strippedPotionKnowledge) {
                    case "awkward", "mundane", "thick" -> nameForID = "Infused " + name;
                    case "empty" -> nameForID = "Uncraftable Infused " + name;
                    case "water" -> nameForID = "Splashing " + name;
                    case "night_vision" -> nameForID = "Night Vision " + name;
                    case "invisibility" -> nameForID = "Invisibility " + name;
                    case "leaping" -> nameForID = "Jump Boost " + name;
                    case "fire_resistance" -> nameForID = "Fire Resistance " + name;
                    case "swiftness" -> nameForID = "Swift " + name;
                    case "slowness" -> nameForID = "Slowed " + name;
                    case "water_breathing" -> nameForID = "Water Breathing " + name;
                    case "healing" -> nameForID = "Healing " + name;
                    case "harming" -> nameForID = "Harming " + name;
                    case "poison" -> nameForID = "Poisoned " + name;
                    case "regeneration" -> nameForID = "Regenerating " + name;
                    case "strength" -> nameForID = "Empowered " + name;
                    case "weakness" -> nameForID = "Weakening " + name;
                    case "levitation" -> nameForID = "Levitating " + name;
                    case "luck" -> nameForID = "Lucky " + name;
                    case "turtle_master" -> nameForID = "Turtle Master's " + name;
                    case "slow_falling" -> nameForID = "Slow Falling " + name;
                }
                add(key.asItem().getDescriptionId(stack), nameForID);
                descriptionIDs.add(descID);
            }
        }
    }
}
