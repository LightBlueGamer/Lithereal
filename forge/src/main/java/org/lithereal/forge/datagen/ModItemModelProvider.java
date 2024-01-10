package org.lithereal.forge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath()));
    }

    private ItemModelBuilder simplePotionItem(Item item) {
        return withExistingParent(LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                        new ResourceLocation(Lithereal.MOD_ID,"item/" + LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath() + "_head"))
                .texture("layer1",
                        new ResourceLocation(Lithereal.MOD_ID,"item/" + LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath() + "_base"));
    }

    private ItemModelBuilder handheldPotionItem(Item item) {
        return withExistingParent(LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath() + "_head"))
                .texture("layer1",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + LitherealExpectPlatform.getResourceLocation(item.getDefaultInstance()).getPath() + "_base"));
    }
}