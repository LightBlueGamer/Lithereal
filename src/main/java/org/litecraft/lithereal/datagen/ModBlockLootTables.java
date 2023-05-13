package org.litecraft.lithereal.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.LITHERITE_BLOCK.get());
        dropSelf(ModBlocks.COOLED_LITHERITE_BLOCK.get());
        dropSelf(ModBlocks.HEATED_LITHERITE_BLOCK.get());

        dropSelf(ModBlocks.FREEZING_STATION.get());
        dropSelf(ModBlocks.FIRE_CRUCIBLE.get());

        add(ModBlocks.LITHERITE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.LITHERITE_ORE.get(), ModItems.LITHERITE_CRYSTAL.get()));
        add(ModBlocks.DEEPSLATE_LITHERITE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.DEEPSLATE_LITHERITE_ORE.get(), ModItems.LITHERITE_CRYSTAL.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}