package org.litecraft.lithereal.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.LITHERITE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.BURNING_LITHERITE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.FROZEN_LITHERITE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.LITHERITE_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DEEPSLATE_LITHERITE_ORE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.LITHERITE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_CRYSTAL, Models.GENERATED);

        itemModelGenerator.register(ModItems.LITHERITE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_HELMET, Models.GENERATED);

        itemModelGenerator.register(ModItems.LITHERITE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_CHESTPLATE, Models.GENERATED);

        itemModelGenerator.register(ModItems.LITHERITE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_LEGGINGS, Models.GENERATED);

        itemModelGenerator.register(ModItems.LITHERITE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_BOOTS, Models.GENERATED);

        itemModelGenerator.register(ModItems.LITHERITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_SWORD, Models.HANDHELD);

        itemModelGenerator.register(ModItems.LITHERITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_PICKAXE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.LITHERITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_SHOVEL, Models.HANDHELD);

        itemModelGenerator.register(ModItems.LITHERITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_AXE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.LITHERITE_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BURNING_LITHERITE_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FROZEN_LITHERITE_HOE, Models.HANDHELD);
    }
}