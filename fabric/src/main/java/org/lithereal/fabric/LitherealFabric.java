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
import org.lithereal.Lithereal;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.fabric.world.block.entity.FabricBlockEntities;
import org.lithereal.fabric.data.compat.ModWeaponType;
import org.lithereal.fabric.world.item.FabricItems;
import org.lithereal.fabric.client.gui.screens.inventory.FabricScreenHandlers;
import org.lithereal.fabric.data.worldgen.FabricWorldGeneration;
import org.lithereal.item.ModItems;

public class LitherealFabric implements ModInitializer {

    public static final ResourceKey<LootTable> WITHER_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "entities/wither"));

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

        Lithereal.init();
    }


}
