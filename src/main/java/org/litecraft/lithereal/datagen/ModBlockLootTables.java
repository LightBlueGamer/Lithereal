package org.litecraft.lithereal.datagen;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        ItemStack item = new ItemStack(ModItems.LITHERITE_CRYSTAL.get());

        dropSelf(ModBlocks.LITHERITE_BLOCK.get());
        dropSelf(ModBlocks.COOLED_LITHERITE_BLOCK.get());
        dropSelf(ModBlocks.HEATED_LITHERITE_BLOCK.get());
        dropSelf(ModBlocks.WITHERING_LITHERITE_BLOCK.get());
        dropSelf(ModBlocks.INFUSED_LITHERITE_BLOCK.get());

        dropSelf(ModBlocks.FREEZING_STATION.get());
        dropSelf(ModBlocks.FIRE_CRUCIBLE.get());
        dropSelf(ModBlocks.INFUSEMENT_CHAMBER_CASING.get());
        dropSelf(ModBlocks.INFUSEMENT_CHAMBER_CORE.get());
        dropSelf(ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER.get());

        dropSelf(ModBlocks.SCORCHED_NETHERRACK.get());

        add(ModBlocks.LITHERITE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.LITHERITE_ORE.get(), item.getItem()));
        add(ModBlocks.DEEPSLATE_LITHERITE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.DEEPSLATE_LITHERITE_ORE.get(), item.getItem()));

        add(ModBlocks.BLUE_FIRE.get(),
                (block) -> createOreDrop(ModBlocks.BLUE_FIRE.get(), Items.AIR));

        add(ModBlocks.INFUSEMENT_CHAMBER.get(),
                (block -> createOreDrop(ModBlocks.INFUSEMENT_CHAMBER.get(), ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER.get().asItem())));

        this.add(ModBlocks.SCORCHED_CRIMSON_NYLIUM.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                LootItem.lootTableItem(ModBlocks.SCORCHED_CRIMSON_NYLIUM.get()).when(HAS_SILK_TOUCH)
                        .otherwise(LootItem.lootTableItem(ModBlocks.SCORCHED_NETHERRACK.get())))));

        this.add(ModBlocks.SCORCHED_WARPED_NYLIUM.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                LootItem.lootTableItem(ModBlocks.SCORCHED_WARPED_NYLIUM.get()).when(HAS_SILK_TOUCH)
                        .otherwise(LootItem.lootTableItem(ModBlocks.SCORCHED_NETHERRACK.get())))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}