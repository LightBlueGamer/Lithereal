package org.lithereal.forge.datagen;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.NonNullList;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        NonNullList<Block> blocks = NonNullList.create();
        return blocks;
//        for (Iterator<RegistrySupplier<Block>> it = ModBlocks.BLOCKS.iterator(); it.hasNext(); ) {
//            RegistrySupplier<Block> block = it.next();
//            blocks.add(block.get());
//        }
//        ForgeBlocks.BLOCKS.getEntries().forEach((RegistryObject<Block> block) -> blocks.add(block.get()));
        //return blocks;
    }
}