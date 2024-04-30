package org.lithereal.forge.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.lithereal.Lithereal;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }

    private ItemModelBuilder simplePotionItem(Item item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                        new ResourceLocation(Lithereal.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item).getPath() + "_head"))
                .texture("layer1",
                        new ResourceLocation(Lithereal.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item).getPath() + "_base"));
    }

    private ItemModelBuilder handheldPotionItem(Item item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item).getPath() + "_head"))
                .texture("layer1",
                new ResourceLocation(Lithereal.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item).getPath() + "_base"));
    }
}