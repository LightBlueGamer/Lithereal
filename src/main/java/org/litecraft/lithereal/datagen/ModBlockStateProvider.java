package org.litecraft.lithereal.datagen;

import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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
        blockWithItem(ModBlocks.WITHERING_LITHERITE_BLOCK);
        blockWithItem(ModBlocks.INFUSED_LITHERITE_BLOCK);

        topSideBottomModelWithItem(ModBlocks.FREEZING_STATION);
        topSideBottomModelWithItem(ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER);
        topSideBottomModelWithItem(ModBlocks.INFUSEMENT_CHAMBER);

        cubeAll(ModBlocks.INFUSEMENT_CHAMBER_CASING.get());
        cubeAll(ModBlocks.INFUSEMENT_CHAMBER_CORE.get());
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void topSideBottomModelWithItem(RegistryObject<Block> blockRegistryObject) {
        String blockId = blockRegistryObject.getId().getPath();
        ModelFile blockModelParent = models().getExistingFile(new ResourceLocation("minecraft:block/cube_bottom_top"));

        ModelFile blockModel = models().getBuilder(blockId)
                .texture("top", new ResourceLocation(Lithereal.MOD_ID, "block/"+blockId+"_top"))
                .texture("side", new ResourceLocation(Lithereal.MOD_ID, "block/"+blockId+"_side"))
                .texture("bottom", new ResourceLocation(Lithereal.MOD_ID, "block/"+blockId+"_bottom"))
                .parent(blockModelParent);

        simpleBlockWithItem(blockRegistryObject.get(), blockModel);
    }
}