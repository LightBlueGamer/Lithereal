package org.litecraft.lithereal.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItemGroup;
import org.litecraft.lithereal.item.ModItems;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.LITHERITE_CRYSTAL, "Litherite Crystal");
        translationBuilder.add(ModItems.BURNING_LITHERITE_CRYSTAL, "Burning Litherite Crystal");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_CRYSTAL, "Frozen Litherite Crystal");

        translationBuilder.add(ModItems.LITHERITE_SWORD, "Litherite Crystal Sword");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_SWORD, "Frozen Litherite Crystal Sword");
        translationBuilder.add(ModItems.BURNING_LITHERITE_SWORD, "Burning Litherite Crystal Sword");

        translationBuilder.add(ModItems.LITHERITE_HOE, "Litherite Crystal Hoe");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_HOE, "Frozen Litherite Crystal Hoe");
        translationBuilder.add(ModItems.BURNING_LITHERITE_HOE, "Burning Litherite Crystal Hoe");

        translationBuilder.add(ModItems.LITHERITE_PICKAXE, "Litherite Crystal Pickaxe");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_PICKAXE, "Frozen Litherite Crystal Pickaxe");
        translationBuilder.add(ModItems.BURNING_LITHERITE_PICKAXE, "Burning Litherite Crystal Pickaxe");

        translationBuilder.add(ModItems.LITHERITE_AXE, "Litherite Crystal Axe");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_AXE, "Frozen Litherite Crystal Axe");
        translationBuilder.add(ModItems.BURNING_LITHERITE_AXE, "Burning Litherite Crystal Axe");

        translationBuilder.add(ModItems.LITHERITE_SHOVEL, "Litherite Crystal Shovel");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_SHOVEL, "Frozen Litherite Crystal Shovel");
        translationBuilder.add(ModItems.BURNING_LITHERITE_SHOVEL, "Burning Litherite Crystal Shovel");

        translationBuilder.add(ModItems.LITHERITE_HELMET, "Litherite Crystal Helmet");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_HELMET, "Frozen Litherite Crystal Helmet");
        translationBuilder.add(ModItems.BURNING_LITHERITE_HELMET, "Burning Litherite Crystal Helmet");

        translationBuilder.add(ModItems.LITHERITE_CHESTPLATE, "Litherite Crystal Chestplate");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_CHESTPLATE, "Frozen Litherite Crystal Chestplate");
        translationBuilder.add(ModItems.BURNING_LITHERITE_CHESTPLATE, "Burning Litherite Crystal Chestplate");

        translationBuilder.add(ModItems.LITHERITE_LEGGINGS, "Litherite Crystal Leggings");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_LEGGINGS, "Frozen Litherite Crystal Leggings");
        translationBuilder.add(ModItems.BURNING_LITHERITE_LEGGINGS, "Burning Litherite Crystal Leggings");

        translationBuilder.add(ModItems.LITHERITE_BOOTS, "Litherite Crystal Boots");
        translationBuilder.add(ModItems.FROZEN_LITHERITE_BOOTS, "Frozen Litherite Crystal Boots");
        translationBuilder.add(ModItems.BURNING_LITHERITE_BOOTS, "Burning Litherite Crystal Boots");

        translationBuilder.add(ModBlocks.LITHERITE_BLOCK, "Litherite Block");
        translationBuilder.add(ModBlocks.LITHERITE_ORE, "Litherite Ore");
        translationBuilder.add(ModBlocks.DEEPSLATE_LITHERITE_ORE, "Deepslate Litherite Ore");
        translationBuilder.add(ModBlocks.FROZEN_LITHERITE_BLOCK, "Frozen Litherite Block");
        translationBuilder.add(ModBlocks.BURNING_LITHERITE_BLOCK, "Burning Litherite Block");

        translationBuilder.add(ModItemGroup.LITHEREAL, "Lithereal");
    }
}
