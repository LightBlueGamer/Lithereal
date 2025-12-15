package org.lithereal.neoforge.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.stream.Streams;
import org.jetbrains.annotations.NotNull;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModVegetationBlocks;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModRawMaterialItems;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModEntityLootTableProvider extends EntityLootSubProvider {
    private final List<EntityType<?>> cachedGeneratedEntityTypes = new ArrayList<>();
    protected ModEntityLootTableProvider(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        this.add(
                ModEntities.PHANTOM_DROWNED.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(ModRawMaterialItems.CHRYON_CRYSTAL.get()))
                                        .add(LootItem.lootTableItem(ModVegetationBlocks.MALISHROOM.get()))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.11F, 0.02F))
                        )
        );
        this.add(
                ModEntities.PHANTOM_ZOMBIE.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(ModRawMaterialItems.ELUNITE_CRYSTAL.get()))
                                        .add(LootItem.lootTableItem(ModVegetationBlocks.FORTSHROOM.get()))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.11F, 0.02F))
                        )
        );
    }

    @Override
    protected void add(EntityType<?> arg, ResourceKey<LootTable> arg2, LootTable.Builder arg3) {
        super.add(arg, arg2, arg3);
        cachedGeneratedEntityTypes.add(arg);
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        if (cachedGeneratedEntityTypes != null) return cachedGeneratedEntityTypes.stream();
        return Streams.of(ModEntities.ENTITIES).map(Holder::value);
    }
}