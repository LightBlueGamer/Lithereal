package com.haru.lithereal.datagen;

import com.haru.lithereal.Lithereal;
import com.haru.lithereal.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;


public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Lithereal.MOD_ID, existingFileHelper);
    }

    protected void addTags(TagKey<Block> p_256380_) {
        this.diamondPickTag(ModBlocks.LITHERITE_ORE.get());
        this.diamondPickTag(ModBlocks.DEEPSLATE_LITHERITE_ORE.get());
        this.diamondPickTag(ModBlocks.LITHERITE_BLOCK.get());
        this.diamondPickTag(ModBlocks.COOLED_LITHERITE_BLOCK.get());
        this.diamondPickTag(ModBlocks.HEATED_LITHERITE_BLOCK.get());

        this.diamondPickTag(ModBlocks.FREEZING_STATION.get());
        this.diamondPickTag(ModBlocks.FIRE_CRUCIBLE.get());
    }



    private void diamondPickTag(Block block) {
        this.addTags(BlockTags.MINEABLE_WITH_PICKAXE);
        this.addTags(BlockTags.NEEDS_DIAMOND_TOOL);
    }
}
