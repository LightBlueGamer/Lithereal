package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import org.lithereal.entity.ModEntities;

import static org.lithereal.item.ModItems.registerItem;

public class ModSpawnEggs {
    public static final RegistrySupplier<Item> PHANTOM_ZOMBIE_SPAWN_EGG = registerItem("phantom_zombie_spawn_egg", properties ->
            new SpawnEggItem(properties.spawnEgg(ModEntities.PHANTOM_ZOMBIE.get())));
    public static final RegistrySupplier<Item> PHANTOM_DROWNED_SPAWN_EGG = registerItem("phantom_drowned_spawn_egg", properties ->
            new SpawnEggItem(properties.spawnEgg(ModEntities.PHANTOM_DROWNED.get())));
    public static final RegistrySupplier<Item> POCKET_RIFT = registerItem("pocket_rift", properties ->
            new SpawnEggItem(properties.spawnEgg(ModEntities.RIFT_SPAWNER.get())));

    public static void register() {

    }
}
