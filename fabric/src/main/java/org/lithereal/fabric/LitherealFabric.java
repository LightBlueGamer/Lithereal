package org.lithereal.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.fabric.world.block.entity.FabricBlockEntities;
import org.lithereal.fabric.data.compat.ModWeaponType;
import org.lithereal.fabric.world.item.FabricItems;
import org.lithereal.fabric.client.gui.screens.inventory.FabricScreenHandlers;
import org.lithereal.fabric.data.worldgen.FabricWorldGeneration;
import org.lithereal.item.ModItems;

import java.util.Set;

public class LitherealFabric implements ModInitializer {

    public static final ResourceKey<LootTable> WITHER_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "entities/wither"));
    public static final ResourceKey<LootTable> BASTION_TREASURE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "chests/bastion_treasure"));

    private static final Set<ResourceKey<LootTable>> TARGET_LOOT_TABLE_KEYS = Set.of(
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "chests/woodland_mansion")),
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "chests/buried_treasure")),
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "chests/desert_pyramid")),
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "chests/igloo_chest")),
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "chests/jungle_temple")),
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "chests/village/village_mason"))
    );

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("combatify"))
            ModWeaponType.init();
        FabricItems.registerModItems();
        FabricBlocks.registerModBlocks();
        FabricBlockEntities.registerBlockEntities();
        FabricScreenHandlers.registerScreenHandlers();
        FabricWorldGeneration.generateModWorldGen();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(source.isBuiltin() && WITHER_LOOT_TABLE_KEY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ModItems.BOSS_ESSENCE.get()));
                tableBuilder.pool(poolBuilder.build());
            }
        });

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(source.isBuiltin() && BASTION_TREASURE_LOOT_TABLE_KEY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get()));
                tableBuilder.pool(poolBuilder.build());
            }
        });

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && TARGET_LOOT_TABLE_KEYS.contains(key)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(createEtherstoneEntry(75, 0, 2))
                        .add(createEtherstoneEntry(32, 1, 4))
                        .add(createEtherstoneEntry(18, 3, 7))
                        .add(createEtherstoneEntry(5, 5, 12));
                tableBuilder.pool(poolBuilder.build());
            }
        });

        Lithereal.init();
    }

    private static LootItem.Builder createEtherstoneEntry(int weight, int min, int max) {
        return LootItem.lootTableItem(ModBlocks.ETHERSTONE.get())
                .setWeight(weight)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }
}
