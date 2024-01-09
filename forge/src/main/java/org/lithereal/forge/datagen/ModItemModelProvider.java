package org.lithereal.forge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModBlocks;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.MOLTEN_LITHERITE_BUCKET.get());
        simpleItem(ModItems.CHARGED_LITHERITE_CRYSTAL.get());
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