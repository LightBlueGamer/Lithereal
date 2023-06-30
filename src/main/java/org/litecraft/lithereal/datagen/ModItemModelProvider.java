package org.litecraft.lithereal.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.LITHERITE_CRYSTAL);
        simpleItem(ModItems.LITHERITE_HELMET);
        simpleItem(ModItems.LITHERITE_CHESTPLATE);
        simpleItem(ModItems.LITHERITE_LEGGINGS);
        simpleItem(ModItems.LITHERITE_BOOTS);
        handheldItem(ModItems.LITHERITE_SWORD);
        handheldItem(ModItems.LITHERITE_PICKAXE);
        handheldItem(ModItems.LITHERITE_HAMMER);
        handheldItem(ModItems.LITHERITE_AXE);
        handheldItem(ModItems.LITHERITE_HOE);
        handheldItem(ModItems.LITHERITE_SHOVEL);

        simpleItem(ModItems.HEATED_LITHERITE_CRYSTAL);
        simpleItem(ModItems.HEATED_LITHERITE_HELMET);
        simpleItem(ModItems.HEATED_LITHERITE_CHESTPLATE);
        simpleItem(ModItems.HEATED_LITHERITE_LEGGINGS);
        simpleItem(ModItems.HEATED_LITHERITE_BOOTS);
        handheldItem(ModItems.HEATED_LITHERITE_SWORD);
        handheldItem(ModItems.HEATED_LITHERITE_PICKAXE);
        handheldItem(ModItems.HEATED_LITHERITE_HAMMER);
        handheldItem(ModItems.HEATED_LITHERITE_AXE);
        handheldItem(ModItems.HEATED_LITHERITE_HOE);
        handheldItem(ModItems.HEATED_LITHERITE_SHOVEL);

        simpleItem(ModItems.COOLED_LITHERITE_CRYSTAL);
        simpleItem(ModItems.COOLED_LITHERITE_HELMET);
        simpleItem(ModItems.COOLED_LITHERITE_CHESTPLATE);
        simpleItem(ModItems.COOLED_LITHERITE_LEGGINGS);
        simpleItem(ModItems.COOLED_LITHERITE_BOOTS);
        handheldItem(ModItems.COOLED_LITHERITE_SWORD);
        handheldItem(ModItems.COOLED_LITHERITE_PICKAXE);
        handheldItem(ModItems.COOLED_LITHERITE_HAMMER);
        handheldItem(ModItems.COOLED_LITHERITE_AXE);
        handheldItem(ModItems.COOLED_LITHERITE_HOE);
        handheldItem(ModItems.COOLED_LITHERITE_SHOVEL);

        simpleItem(ModItems.WITHERING_LITHERITE_CRYSTAL);
        simpleItem(ModItems.WITHERING_LITHERITE_HELMET);
        simpleItem(ModItems.WITHERING_LITHERITE_CHESTPLATE);
        simpleItem(ModItems.WITHERING_LITHERITE_LEGGINGS);
        simpleItem(ModItems.WITHERING_LITHERITE_BOOTS);
        handheldItem(ModItems.WITHERING_LITHERITE_SWORD);
        handheldItem(ModItems.WITHERING_LITHERITE_PICKAXE);
        handheldItem(ModItems.WITHERING_LITHERITE_HAMMER);
        handheldItem(ModItems.WITHERING_LITHERITE_AXE);
        handheldItem(ModItems.WITHERING_LITHERITE_HOE);
        handheldItem(ModItems.WITHERING_LITHERITE_SHOVEL);

        simpleItem(ModItems.INFUSED_LITHERITE_CRYSTAL);
        simpleItem(ModItems.INFUSED_LITHERITE_HELMET);
        simpleItem(ModItems.INFUSED_LITHERITE_CHESTPLATE);
        simpleItem(ModItems.INFUSED_LITHERITE_LEGGINGS);
        simpleItem(ModItems.INFUSED_LITHERITE_BOOTS);
        handheldPotionItem(ModItems.INFUSED_LITHERITE_SWORD);
        handheldPotionItem(ModItems.INFUSED_LITHERITE_PICKAXE);
        handheldPotionItem(ModItems.INFUSED_LITHERITE_HAMMER);
        handheldPotionItem(ModItems.INFUSED_LITHERITE_AXE);
        handheldPotionItem(ModItems.INFUSED_LITHERITE_HOE);
        handheldPotionItem(ModItems.INFUSED_LITHERITE_SHOVEL);

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

    private ItemModelBuilder simplePotionItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                        new ResourceLocation(Lithereal.MOD_ID,"item/" + item.getId().getPath() + "_head"))
                .texture("layer1",
                        new ResourceLocation(Lithereal.MOD_ID,"item/" + item.getId().getPath() + "_base"));
    }

    private ItemModelBuilder handheldPotionItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + item.getId().getPath() + "_head"))
                .texture("layer1",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + item.getId().getPath() + "_base"));
    }
}