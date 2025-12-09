package org.lithereal.neoforge.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.stream.Streams;
import org.jetbrains.annotations.NotNull;
import org.lithereal.block.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    private final List<Block> cachedGeneratedBlocks = new ArrayList<>();
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModTreeBlocks.PHANTOM_OAK_PLANKS.get());
        dropPottedContents(ModTreeBlocks.POTTED_PHANTOM_OAK_SAPLING.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_SAPLING.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_LOG.get());
        dropSelf(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_WOOD.get());
        dropSelf(ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_STAIRS.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_SIGN.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_FENCE.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_TRAPDOOR.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get());
        dropSelf(ModTreeBlocks.PHANTOM_OAK_BUTTON.get());
        add(ModTreeBlocks.PHANTOM_OAK_SLAB.get(), this::createSlabItemTable);
        add(ModTreeBlocks.PHANTOM_OAK_DOOR.get(), this::createDoorTable);
        add(ModTreeBlocks.PHANTOM_OAK_LEAVES.get(), block -> createOakLeavesDrops(block, ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropPottedContents(ModVegetationBlocks.POTTED_MALISHROOM.get());
        dropSelf(ModVegetationBlocks.MALISHROOM.get());
        dropPottedContents(ModVegetationBlocks.POTTED_FORTSHROOM.get());
        dropSelf(ModVegetationBlocks.FORTSHROOM.get());

        dropSelf(ModStoneBlocks.PAILITE.get());
        dropSelf(ModStoneBlocks.PAILITE_STAIRS.get());
        dropSelf(ModStoneBlocks.PAILITE_WALL.get());
        add(ModStoneBlocks.PAILITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.POLISHED_PAILITE.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_WALL.get());
        add(ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), this::createSlabItemTable);

        dropSelf(ModPhantomBlocks.PHANTOM_ROSE.get());
        dropSelf(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get());
        dropSelf(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get());
        dropPottedContents(ModPhantomBlocks.POTTED_PHANTOM_ROSE.get());
        dropPottedContents(ModPhantomBlocks.POTTED_PHANTOM_ICE_FLOWER.get());
        dropPottedContents(ModPhantomBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get());
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        cachedGeneratedBlocks.add(block);
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        if (cachedGeneratedBlocks != null) return cachedGeneratedBlocks;
        return Streams.of(ModBlocks.BLOCKS).map(Holder::value)::iterator;
    }
}