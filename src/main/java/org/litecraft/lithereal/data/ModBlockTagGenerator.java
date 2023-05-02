package org.litecraft.lithereal.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.litecraft.lithereal.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        diamondPickTag(ModBlocks.LITHERITE_ORE);
        diamondPickTag(ModBlocks.DEEPSLATE_LITHERITE_ORE);
        diamondPickTag(ModBlocks.LITHERITE_BLOCK);
        diamondPickTag(ModBlocks.FROZEN_LITHERITE_BLOCK);
        diamondPickTag(ModBlocks.BURNING_LITHERITE_BLOCK);
    }

    private void diamondPickTag(Block block) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
    }
}
