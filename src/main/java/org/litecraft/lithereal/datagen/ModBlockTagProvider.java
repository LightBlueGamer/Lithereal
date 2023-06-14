package org.litecraft.lithereal.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Lithereal.MOD_ID, existingFileHelper);
    }

    protected void addTags(TagKey<Block> p_256380_) {
        diamondPickTag(ModBlocks.LITHERITE_ORE.get());
        diamondPickTag(ModBlocks.DEEPSLATE_LITHERITE_ORE.get());
        diamondPickTag(ModBlocks.LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.COOLED_LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.HEATED_LITHERITE_BLOCK.get());
        diamondPickTag(ModBlocks.WITHERING_LITHERITE_BLOCK.get());

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
