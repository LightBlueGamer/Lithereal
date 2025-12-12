package org.lithereal.neoforge.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.lithereal.Lithereal;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.item.ModArmorItems;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModToolItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModTreeBlocks.PHANTOM_OAK_DOOR.get().asItem());

        basicItem(ModItems.PHANTOM_OAK_BOAT.get());
        basicItem(ModItems.PHANTOM_OAK_CHEST_BOAT.get());
        basicItem(ModItems.PHANTOM_OAK_SIGN.get());
        basicItem(ModItems.PHANTOM_OAK_HANGING_SIGN.get());

        handheldItem(ModToolItems.LITHERITE_SWORD.get());
        handheldItem(ModToolItems.LITHERITE_PICKAXE.get());
        handheldItem(ModToolItems.LITHERITE_AXE.get());
        handheldItem(ModToolItems.LITHERITE_SHOVEL.get());
        handheldItem(ModToolItems.LITHERITE_HOE.get());
        handheldItem(ModToolItems.LITHERITE_HAMMER.get());
        handheldItem(ModToolItems.BURNING_LITHERITE_SWORD.get());
        handheldItem(ModToolItems.BURNING_LITHERITE_PICKAXE.get());
        handheldItem(ModToolItems.BURNING_LITHERITE_AXE.get());
        handheldItem(ModToolItems.BURNING_LITHERITE_SHOVEL.get());
        handheldItem(ModToolItems.BURNING_LITHERITE_HOE.get());
        handheldItem(ModToolItems.BURNING_LITHERITE_HAMMER.get());
        handheldItem(ModToolItems.FROZEN_LITHERITE_SWORD.get());
        handheldItem(ModToolItems.FROZEN_LITHERITE_PICKAXE.get());
        handheldItem(ModToolItems.FROZEN_LITHERITE_AXE.get());
        handheldItem(ModToolItems.FROZEN_LITHERITE_SHOVEL.get());
        handheldItem(ModToolItems.FROZEN_LITHERITE_HOE.get());
        handheldItem(ModToolItems.FROZEN_LITHERITE_HAMMER.get());
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_SWORD.get());
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_PICKAXE.get());
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_AXE.get());
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_SHOVEL.get());
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_HOE.get());
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_HAMMER.get());
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_SWORD.get());
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_PICKAXE.get());
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_AXE.get());
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_SHOVEL.get());
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_HOE.get());
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_HAMMER.get());
        handheldItem(ModToolItems.WITHERING_LITHERITE_SWORD.get());
        handheldItem(ModToolItems.WITHERING_LITHERITE_PICKAXE.get());
        handheldItem(ModToolItems.WITHERING_LITHERITE_AXE.get());
        handheldItem(ModToolItems.WITHERING_LITHERITE_SHOVEL.get());
        handheldItem(ModToolItems.WITHERING_LITHERITE_HOE.get());
        handheldItem(ModToolItems.WITHERING_LITHERITE_HAMMER.get());
        handheldItem(ModToolItems.ODYSIUM_SWORD.get());
        handheldItem(ModToolItems.ODYSIUM_PICKAXE.get());
        handheldItem(ModToolItems.ODYSIUM_AXE.get());
        handheldItem(ModToolItems.ODYSIUM_SHOVEL.get());
        handheldItem(ModToolItems.ODYSIUM_HOE.get());
        handheldItem(ModToolItems.ODYSIUM_HAMMER.get());

        basicItem(ModArmorItems.LITHERITE_HELMET.get());
        basicItem(ModArmorItems.LITHERITE_CHESTPLATE.get());
        basicItem(ModArmorItems.LITHERITE_LEGGINGS.get());
        basicItem(ModArmorItems.LITHERITE_BOOTS.get());
        basicItem(ModArmorItems.BURNING_LITHERITE_HELMET.get());
        basicItem(ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get());
        basicItem(ModArmorItems.BURNING_LITHERITE_LEGGINGS.get());
        basicItem(ModArmorItems.BURNING_LITHERITE_BOOTS.get());
        basicItem(ModArmorItems.FROZEN_LITHERITE_HELMET.get());
        basicItem(ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get());
        basicItem(ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get());
        basicItem(ModArmorItems.FROZEN_LITHERITE_BOOTS.get());
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_HELMET.get());
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE.get());
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS.get());
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_BOOTS.get());
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_HELMET.get());
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE.get());
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS.get());
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS.get());
        basicItem(ModArmorItems.WITHERING_LITHERITE_HELMET.get());
        basicItem(ModArmorItems.WITHERING_LITHERITE_CHESTPLATE.get());
        basicItem(ModArmorItems.WITHERING_LITHERITE_LEGGINGS.get());
        basicItem(ModArmorItems.WITHERING_LITHERITE_BOOTS.get());
        basicItem(ModArmorItems.ODYSIUM_HELMET.get());
        basicItem(ModArmorItems.ODYSIUM_CHESTPLATE.get());
        basicItem(ModArmorItems.ODYSIUM_LEGGINGS.get());
        basicItem(ModArmorItems.ODYSIUM_BOOTS.get());
    }
}