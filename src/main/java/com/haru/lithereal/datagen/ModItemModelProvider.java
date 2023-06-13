package com.haru.lithereal.datagen;

import com.haru.lithereal.Lithereal;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import com.haru.lithereal.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.LITHERITE_CRYSTAL);
        simpleItem(ModItems.HEATED_LITHERITE_CRYSTAL);
        simpleItem(ModItems.COOLED_LITHERITE_CRYSTAL);

        simpleItem(ModItems.LITHERITE_HELMET);
        simpleItem(ModItems.HEATED_LITHERITE_HELMET);
        simpleItem(ModItems.COOLED_LITHERITE_HELMET);

        simpleItem(ModItems.LITHERITE_CHESTPLATE);
        simpleItem(ModItems.HEATED_LITHERITE_CHESTPLATE);
        simpleItem(ModItems.COOLED_LITHERITE_CHESTPLATE);

        simpleItem(ModItems.LITHERITE_LEGGINGS);
        simpleItem(ModItems.HEATED_LITHERITE_LEGGINGS);
        simpleItem(ModItems.COOLED_LITHERITE_LEGGINGS);

        simpleItem(ModItems.LITHERITE_BOOTS);
        simpleItem(ModItems.HEATED_LITHERITE_BOOTS);
        simpleItem(ModItems.COOLED_LITHERITE_BOOTS);

        handheldItem(ModItems.LITHERITE_SWORD);
        handheldItem(ModItems.HEATED_LITHERITE_SWORD);
        handheldItem(ModItems.COOLED_LITHERITE_SWORD);

        handheldItem(ModItems.LITHERITE_PICKAXE);
        handheldItem(ModItems.HEATED_LITHERITE_PICKAXE);
        handheldItem(ModItems.COOLED_LITHERITE_PICKAXE);

        handheldItem(ModItems.LITHERITE_HAMMER);
        handheldItem(ModItems.BURNING_LITHERITE_HAMMER);
        handheldItem(ModItems.FROZEN_LITHERITE_HAMMER);

        handheldItem(ModItems.LITHERITE_AXE);
        handheldItem(ModItems.HEATED_LITHERITE_AXE);
        handheldItem(ModItems.COOLED_LITHERITE_AXE);

        handheldItem(ModItems.LITHERITE_HOE);
        handheldItem(ModItems.HEATED_LITHERITE_HOE);
        handheldItem(ModItems.COOLED_LITHERITE_HOE);

        handheldItem(ModItems.LITHERITE_SHOVEL);
        handheldItem(ModItems.HEATED_LITHERITE_SHOVEL);
        handheldItem(ModItems.COOLED_LITHERITE_SHOVEL);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + item.getId().getPath()));
    }
}