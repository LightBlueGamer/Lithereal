package org.lithereal.neoforge.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.lithereal.Lithereal;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.item.ModItems;

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
    }
}