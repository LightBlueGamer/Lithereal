package org.lithereal.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.lithereal.Lithereal;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Lithereal.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<ThrownLitherCharge>> LITHER_CHARGE = ENTITIES.register("lither_charge", () -> EntityType.Builder.<ThrownLitherCharge>of(ThrownLitherCharge::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("lither_charge"));

    public static void register() {
        ENTITIES.register();
    }
}
