package org.lithereal.forge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.forge.block.ForgeBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
    }

    private void diamondPickTag(Block block) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(block);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(block);
    }

    private void pickaxeTag(Block block) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(block);
    }
}
