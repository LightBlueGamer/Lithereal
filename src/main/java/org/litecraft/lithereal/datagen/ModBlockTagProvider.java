package org.litecraft.lithereal.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Lithereal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        diamondPickTag(ModBlocks.LITHERITE_ORE.get());
        diamondPickTag(ModBlocks.DEEPSLATE_LITHERITE_ORE.get());
        diamondPickTag(ModBlocks.LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.COOLED_LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.HEATED_LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.WITHERING_LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.REGENERATING_LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.GLOWING_LITHERITE_BLOCK.get());

        pickaxeTag(ModBlocks.SCORCHED_NETHERRACK.get());
        pickaxeTag(ModBlocks.SCORCHED_CRIMSON_NYLIUM.get());
        pickaxeTag(ModBlocks.SCORCHED_WARPED_NYLIUM.get());

        diamondPickTag(ModBlocks.FREEZING_STATION.get());
        diamondPickTag(ModBlocks.FIRE_CRUCIBLE.get());
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
