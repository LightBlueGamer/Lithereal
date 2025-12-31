package org.lithereal.neoforge.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.lithereal.Lithereal;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.item.ModArmorItems;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.item.ModToolItems;
import org.lithereal.neoforge.world.item.ForgeItems;

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

        basicItem(ForgeItems.LITHERITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.PHANTOM_DIAMOND.get());
        basicItem(ModRawMaterialItems.ODYSIUM_INGOT.get());
        basicItem(ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get());
        basicItem(ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.UNIFIER.get());
        basicItem(ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get());
        basicItem(ModRawMaterialItems.NERITH_INGOT.get());
        basicItem(ModRawMaterialItems.ALLIAN_INGOT.get());
        basicItem(ModRawMaterialItems.AURELITE_DUST.get());
        basicItem(ModRawMaterialItems.AURELITE_INGOT.get());
        basicItem(ModRawMaterialItems.CHRYON_CRYSTAL.get());
        basicItem(ModRawMaterialItems.COPALITE_DUST.get());
        basicItem(ModRawMaterialItems.COPALITE_INGOT.get());
        basicItem(ModRawMaterialItems.CYRUM_CRYSTAL.get());
        basicItem(ModRawMaterialItems.ELUNITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.HELLIONITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.LUMINIUM_CRYSTAL.get());
        basicItem(ModRawMaterialItems.NETHERITE_NUGGET.get());
        basicItem(ModRawMaterialItems.NETHERITE_FRAGMENT.get());
        basicItem(ModRawMaterialItems.RAW_ALLIUM.get());
        basicItem(ModRawMaterialItems.RAW_NERITH.get());
        basicItem(ModRawMaterialItems.SATURNITE_CRYSTAL.get());
        basicItem(ModRawMaterialItems.SOLIUMITE_INGOT.get());
        basicItem(ModRawMaterialItems.ELCRUM_INGOT.get());

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
        bowItem(ModToolItems.ODYSIUM_BOW.get());
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_SWORD.get());
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_PICKAXE.get());
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_AXE.get());
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_SHOVEL.get());
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_HOE.get());
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_HAMMER.get());
        bowItem(ModToolItems.ENHANCED_ODYSIUM_BOW.get());

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
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_HELMET.get());
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_CHESTPLATE.get());
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_LEGGINGS.get());
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_BOOTS.get());
    }

    public void bowItem(Item bowItem) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(bowItem);
        ItemModelBuilder builder = baseBow(location);
        builder.override().model(baseBow(location.withSuffix("_pulling_0")))
                .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1);
        builder.override().model(baseBow(location.withSuffix("_pulling_1")))
                .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.65F);
        builder.override().model(baseBow(location.withSuffix("_pulling_2")))
                .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.9F);
    }
    public ItemModelBuilder baseBow(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("lithereal:item/template_bow"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }
}