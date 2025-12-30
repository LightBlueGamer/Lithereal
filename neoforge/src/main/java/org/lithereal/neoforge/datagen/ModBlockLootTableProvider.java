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
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.neoforge.world.item.ForgeItems;

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
        dropSelf(ModBlocks.CREATIVE_ETHER_SOURCE.get());
        dropSelf(ModBlocks.PASSIVE_ETHER_ABSORBER.get());
        dropSelf(ModBlocks.PURE_ETHER_SOURCE.get());
        dropSelf(ForgeBlocks.ELECTRIC_CRUCIBLE.get());
        createMultipleOreDrops(ModBlocks.IMPURE_ETHEREAL_CRYSTAL.get(), ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get(), 3, 5);
        createMultipleOreDropsNoFortune(ModBlocks.ETHEREAL_CRYSTAL_BLOCK.get(), ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get(), 7, 9);

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

        dropSelf(ModStoneBlocks.ETHERSTONE.get());
        dropSelf(ModStoneBlocks.ETHERSTONE_STAIRS.get());
        dropSelf(ModStoneBlocks.ETHERSTONE_WALL.get());
        add(ModStoneBlocks.ETHERSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.CHISELED_ETHERSTONE.get());
        dropSelf(ModStoneBlocks.POLISHED_ETHERSTONE.get());
        dropSelf(ModStoneBlocks.POLISHED_ETHERSTONE_STAIRS.get());
        dropSelf(ModStoneBlocks.POLISHED_ETHERSTONE_WALL.get());
        add(ModStoneBlocks.POLISHED_ETHERSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.ETHERSTONE_BRICKS.get());
        dropSelf(ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get());
        dropSelf(ModStoneBlocks.ETHERSTONE_BRICK_WALL.get());
        add(ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.PAILITE.get());
        dropSelf(ModStoneBlocks.PAILITE_STAIRS.get());
        dropSelf(ModStoneBlocks.PAILITE_WALL.get());
        add(ModStoneBlocks.PAILITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.PAILITE_BUTTON.get());
        dropSelf(ModStoneBlocks.PAILITE_PRESSURE_PLATE.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_WALL.get());
        add(ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_BUTTON.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_PRESSURE_PLATE.get());
        dropSelf(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get());
        add(ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_BRICK_BUTTON.get());
        dropSelf(ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get());
        dropSelf(ModStoneBlocks.LUMINITE.get());
        dropSelf(ModStoneBlocks.LUMINITE_STAIRS.get());
        dropSelf(ModStoneBlocks.LUMINITE_WALL.get());
        add(ModStoneBlocks.LUMINITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.POLISHED_LUMINITE.get());
        dropSelf(ModStoneBlocks.POLISHED_LUMINITE_STAIRS.get());
        dropSelf(ModStoneBlocks.POLISHED_LUMINITE_WALL.get());
        add(ModStoneBlocks.POLISHED_LUMINITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.VERDONE.get());
        dropSelf(ModStoneBlocks.VERDONE_STAIRS.get());
        dropSelf(ModStoneBlocks.VERDONE_WALL.get());
        add(ModStoneBlocks.VERDONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModStoneBlocks.POLISHED_VERDONE.get());
        dropSelf(ModStoneBlocks.POLISHED_VERDONE_STAIRS.get());
        dropSelf(ModStoneBlocks.POLISHED_VERDONE_WALL.get());
        add(ModStoneBlocks.POLISHED_VERDONE_SLAB.get(), this::createSlabItemTable);

        createMultipleOreDrops(ModOreBlocks.ALLIAN_ORE.get(), ModRawMaterialItems.RAW_ALLIUM.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.AURELITE_ORE.get(), ModRawMaterialItems.AURELITE_DUST.get(), 3, 4);
        createMultipleOreDrops(ModOreBlocks.DEEPSLATE_AURELITE_ORE.get(), ModRawMaterialItems.AURELITE_DUST.get(), 3, 4);
        createMultipleOreDrops(ModOreBlocks.ETHERSTONE_AURELITE_ORE.get(), ModRawMaterialItems.AURELITE_DUST.get(), 3, 4);
        createMultipleOreDrops(ModOreBlocks.CHRYON_ORE.get(), ModRawMaterialItems.CHRYON_CRYSTAL.get(), 2, 3);
        createMultipleOreDrops(ModOreBlocks.COPALITE_ORE.get(), ModRawMaterialItems.COPALITE_DUST.get(), 4, 5);
        createMultipleOreDrops(ModOreBlocks.DEEPSLATE_COPALITE_ORE.get(), ModRawMaterialItems.COPALITE_DUST.get(), 4, 5);
        createMultipleOreDrops(ModOreBlocks.ETHERSTONE_COPALITE_ORE.get(), ModRawMaterialItems.COPALITE_DUST.get(), 4, 5);
        createMultipleOreDrops(ModOreBlocks.CYRUM_ORE.get(), ModRawMaterialItems.CYRUM_CRYSTAL.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.DEEPSLATE_CYRUM_ORE.get(), ModRawMaterialItems.CYRUM_CRYSTAL.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.ETHERSTONE_CYRUM_ORE.get(), ModRawMaterialItems.CYRUM_CRYSTAL.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.ELUNITE_ORE.get(), ModRawMaterialItems.ELUNITE_CRYSTAL.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.HELLIONITE_ORE.get(), ModRawMaterialItems.HELLIONITE_CRYSTAL.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.LITHERITE_ORE.get(), ForgeItems.LITHERITE_CRYSTAL.get(), 1, 2);
        createMultipleOreDrops(ModOreBlocks.DEEPSLATE_LITHERITE_ORE.get(), ForgeItems.LITHERITE_CRYSTAL.get(), 1, 2);
        createMultipleOreDrops(ModOreBlocks.ETHERSTONE_LITHERITE_ORE.get(), ForgeItems.LITHERITE_CRYSTAL.get(), 1, 2);
        createMultipleOreDrops(ModOreBlocks.LUMINIUM_ORE.get(), ModRawMaterialItems.LUMINIUM_CRYSTAL.get(), 1, 2);
        createMultipleOreDrops(ModOreBlocks.DEEPSLATE_LUMINIUM_ORE.get(), ModRawMaterialItems.LUMINIUM_CRYSTAL.get(), 1, 2);
        createMultipleOreDrops(ModOreBlocks.ETHERSTONE_LUMINIUM_ORE.get(), ModRawMaterialItems.LUMINIUM_CRYSTAL.get(), 1, 2);
        createMultipleOreDrops(ModOreBlocks.NERITH_ORE.get(), ModRawMaterialItems.RAW_NERITH.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.DEEPSLATE_NERITH_ORE.get(), ModRawMaterialItems.RAW_NERITH.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.ETHERSTONE_NERITH_ORE.get(), ModRawMaterialItems.RAW_NERITH.get(), 1, 1);
        createMultipleOreDrops(ModOreBlocks.SATURNITE_ORE.get(), ModRawMaterialItems.SATURNITE_CRYSTAL.get(), 1, 2);

        createMultipleOreDrops(ModPhantomBlocks.PHANTOM_DIAMOND_ORE.get(), ModRawMaterialItems.PHANTOM_DIAMOND.get(), 1, 1);
        dropSelf(ModPhantomBlocks.PHANTOM_ROSE.get());
        dropSelf(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get());
        dropSelf(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get());
        dropPottedContents(ModPhantomBlocks.POTTED_PHANTOM_ROSE.get());
        dropPottedContents(ModPhantomBlocks.POTTED_PHANTOM_ICE_FLOWER.get());
        dropPottedContents(ModPhantomBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get());
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        super.add(block, builder);
        cachedGeneratedBlocks.add(block);
    }

    protected LootTable.Builder createMultipleOreDropsNoFortune(Block pBlock, Item item, float minDrops, float maxDrops) {
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))));
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