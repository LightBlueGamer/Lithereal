package org.lithereal.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import org.lithereal.Lithereal;
import org.lithereal.entity.phantom.PhantomDrowned;
import org.lithereal.entity.phantom.PhantomZombie;
import org.lithereal.entity.projectile.ThrownLitherCharge;
import org.lithereal.item.ModItems;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Lithereal.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<ThrownLitherCharge>> LITHER_CHARGE =
            registerEntity("lither_charge", EntityType.Builder.<ThrownLitherCharge>of(ThrownLitherCharge::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

    public static final RegistrySupplier<EntityType<Boat>> PHANTOM_OAK_BOAT =
            registerEntity("phantom_oak_boat", EntityType.Builder.of(boatFactory(ModItems.PHANTOM_OAK_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));

    public static final RegistrySupplier<EntityType<ChestBoat>> PHANTOM_OAK_CHEST_BOAT =
            registerEntity("phantom_oak_chest_boat", EntityType.Builder.of(chestBoatFactory(ModItems.PHANTOM_OAK_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));

    public static final RegistrySupplier<EntityType<Boat>> FORTSHROOM_BOAT =
            registerEntity("fortshroom_boat", EntityType.Builder.of(boatFactory(ModItems.FORTSHROOM_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));

    public static final RegistrySupplier<EntityType<ChestBoat>> FORTSHROOM_CHEST_BOAT =
            registerEntity("fortshroom_chest_boat", EntityType.Builder.of(chestBoatFactory(ModItems.FORTSHROOM_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));

    public static final RegistrySupplier<EntityType<Boat>> MALISHROOM_BOAT =
            registerEntity("malishroom_boat", EntityType.Builder.of(boatFactory(ModItems.MALISHROOM_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));

    public static final RegistrySupplier<EntityType<ChestBoat>> MALISHROOM_CHEST_BOAT =
            registerEntity("malishroom_chest_boat", EntityType.Builder.of(chestBoatFactory(ModItems.MALISHROOM_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));

    public static final RegistrySupplier<? extends EntityType<RiftSpawner>> RIFT_SPAWNER =
            registerEntity("rift_spawner", EntityType.Builder.of(RiftSpawner::new, MobCategory.MISC)
                    .sized(1f, 1f).eyeHeight(0.75F).clientTrackingRange(16));

    public static final RegistrySupplier<EntityType<PhantomZombie>> PHANTOM_ZOMBIE =
            registerEntity(
                    "phantom_zombie",
                    EntityType.Builder.<PhantomZombie>of(PhantomZombie::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .passengerAttachments(2.0125F)
                            .ridingOffset(-0.7F)
                            .clientTrackingRange(8)
            );

    public static final RegistrySupplier<EntityType<PhantomDrowned>> PHANTOM_DROWNED =
            registerEntity(
                    "phantom_drowned",
                    EntityType.Builder.<PhantomDrowned>of(PhantomDrowned::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .passengerAttachments(2.0125F)
                            .ridingOffset(-0.7F)
                            .clientTrackingRange(8)
            );

    private static EntityType.EntityFactory<Boat> boatFactory(final Supplier<Item> boatItem) {
        return (entityType, level) -> new Boat(entityType, level, boatItem);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(final Supplier<Item> dropItem) {
        return (entityType, level) -> new ChestBoat(entityType, level, dropItem);
    }
    
    public static <T extends Entity> RegistrySupplier<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> resourceKey = Lithereal.key(Registries.ENTITY_TYPE, name);
        return ENTITIES.register(name, () -> builder.build(resourceKey));
    }

    public static void register() {
        ENTITIES.register();
    }
}