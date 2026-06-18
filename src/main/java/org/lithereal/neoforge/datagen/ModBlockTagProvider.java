package org.lithereal.neoforge.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.lithereal.Lithereal;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.tags.ModTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Lithereal.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Map<TagKey<Block>, TagAppender<Block, Block>> blockTags = Maps.newHashMap();
        blockTags.computeIfAbsent(ModTags.PHANTOM_OAK_LOGS_B, this::tag).add(ModTreeBlocks.PHANTOM_OAK_LOG.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.PHANTOM_OAK_WOOD.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get());
        blockTags.computeIfAbsent(ModTags.FORTSHROOM_STEMS_B, this::tag).add(ModTreeBlocks.FORTSHROOM_STEM.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HYPHAE.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_HYPHAE.get());
        blockTags.computeIfAbsent(ModTags.MALISHROOM_STEMS_B, this::tag).add(ModTreeBlocks.MALISHROOM_STEM.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get(), ModTreeBlocks.MALISHROOM_HYPHAE.get(), ModTreeBlocks.STRIPPED_MALISHROOM_HYPHAE.get());
    }
}
