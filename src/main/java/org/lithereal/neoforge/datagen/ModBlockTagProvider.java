package org.lithereal.neoforge.datagen;

//? neoforge {
import com.google.common.collect.Maps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.lithereal.Lithereal;
import org.lithereal.data.datagen.ItemLikeDataProvider;
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
        ItemLikeDataProvider.ALL_DATA_PROVIDERS.forEach(blockDataProvider -> blockDataProvider.tagData()
                .blockTags()
                .forEach(blockTagKey -> blockTags.computeIfAbsent(blockTagKey, this::tag)
                        .add((Block) blockDataProvider.target().get())));
        blockTags.computeIfAbsent(ModTags.INCORRECT_FOR_ODYSIUM_TOOL, this::tag).addTags(ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, this::tag).addTags(ModTags.NEEDS_ODYSIUM_TOOL, ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, this::tag).addTags(ModTags.NEEDS_ODYSIUM_TOOL, ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.INCORRECT_FOR_IRON_TOOL, this::tag).addTags(ModTags.NEEDS_ODYSIUM_TOOL, ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.INCORRECT_FOR_GOLD_TOOL, this::tag).addTags(ModTags.NEEDS_ODYSIUM_TOOL, ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.INCORRECT_FOR_COPPER_TOOL, this::tag).addTags(ModTags.NEEDS_ODYSIUM_TOOL, ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.INCORRECT_FOR_STONE_TOOL, this::tag).addTags(ModTags.NEEDS_ODYSIUM_TOOL, ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.INCORRECT_FOR_WOODEN_TOOL, this::tag).addTags(ModTags.NEEDS_ODYSIUM_TOOL, ModTags.NEEDS_APEX_TOOL);
        blockTags.computeIfAbsent(BlockTags.MINEABLE_WITH_PICKAXE, this::tag).addTags(ModTags.ETHERSTONE, ModTags.PAILITE, ModTags.LUMINITE, ModTags.VERDONE);
        blockTags.computeIfAbsent(ModTags.LITHER_CHARGE_REACTS_WITH, this::tag).addTags(BlockTags.BASE_STONE_OVERWORLD, Tags.Blocks.END_STONES, Tags.Blocks.COBBLESTONES, ModTags.BASE_STONE_ETHEREAL_CORE);
        blockTags.computeIfAbsent(ModTags.ETHEREAL_CORE_CARVER_REPLACEABLES, this::tag).add(Blocks.WATER).addTags(ModTags.BASE_STONE_ETHEREAL_CORE);
        blockTags.computeIfAbsent(ModTags.ETHERSTONE_ORE_REPLACEABLES, this::tag).addTags(ModTags.BASE_STONE_ETHEREAL_CORE);
        blockTags.computeIfAbsent(BlockTags.LOGS,  this::tag).addTags(ModTags.PHANTOM_OAK_LOGS_B, ModTags.FORTSHROOM_STEMS_B, ModTags.MALISHROOM_STEMS_B);
    }
}
//?}