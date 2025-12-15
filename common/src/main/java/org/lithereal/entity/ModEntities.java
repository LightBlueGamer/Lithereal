package org.lithereal.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.lithereal.Lithereal;
import org.lithereal.entity.phantom.PhantomDrowned;
import org.lithereal.entity.phantom.PhantomZombie;
import org.lithereal.entity.projectile.ThrownLitherCharge;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Lithereal.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<ThrownLitherCharge>> LITHER_CHARGE =
            ENTITIES.register("lither_charge", () -> EntityType.Builder.<ThrownLitherCharge>of(ThrownLitherCharge::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("lither_charge"));

    public static final RegistrySupplier<EntityType<ModBoat>> MOD_BOAT =
            ENTITIES.register("mod_boat", () -> EntityType.Builder.<ModBoat>of(ModBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build("mod_boat"));

    public static final RegistrySupplier<EntityType<ModChestBoat>> MOD_CHEST_BOAT =
            ENTITIES.register("mod_chest_boat", () -> EntityType.Builder.<ModChestBoat>of(ModChestBoat::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).eyeHeight(0.5625F).clientTrackingRange(10).build("mod_chest_boat"));

    public static final RegistrySupplier<? extends EntityType<RiftSpawner>> RIFT_SPAWNER =
            ENTITIES.register("rift_spawner", () -> EntityType.Builder.of(RiftSpawner::new, MobCategory.MISC)
                    .sized(1f, 1f).eyeHeight(0.75F).clientTrackingRange(16).build("rift_spawner"));

    public static final RegistrySupplier<EntityType<PhantomZombie>> PHANTOM_ZOMBIE =
            ENTITIES.register(
                    "phantom_zombie",
                    () -> EntityType.Builder.<PhantomZombie>of(PhantomZombie::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .passengerAttachments(2.0125F)
                            .ridingOffset(-0.7F)
                            .clientTrackingRange(8)
                            .build("phantom_zombie")
            );

    public static final RegistrySupplier<EntityType<PhantomDrowned>> PHANTOM_DROWNED =
            ENTITIES.register(
                    "phantom_drowned",
                    () -> EntityType.Builder.<PhantomDrowned>of(PhantomDrowned::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .passengerAttachments(2.0125F)
                            .ridingOffset(-0.7F)
                            .clientTrackingRange(8)
                            .build("phantom_drowned")
            );

    public static void register() {
        ENTITIES.register();
    }
}