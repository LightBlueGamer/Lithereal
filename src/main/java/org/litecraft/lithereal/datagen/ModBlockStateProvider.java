package org.litecraft.lithereal.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Lithereal.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.LITHERITE_BLOCK);
        blockWithItem(ModBlocks.LITHERITE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_LITHERITE_ORE);
        blockWithItem(ModBlocks.COOLED_LITHERITE_BLOCK);
        blockWithItem(ModBlocks.HEATED_LITHERITE_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}