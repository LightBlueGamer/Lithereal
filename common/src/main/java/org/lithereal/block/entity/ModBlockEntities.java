package org.lithereal.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<LitherealVaultBlockEntity>> LITHEREAL_VAULT =
            BLOCK_ENTITIES.register("lithereal_vault", () ->
                    BlockEntityType.Builder.of(LitherealVaultBlockEntity::new,
                            ModBlocks.LITHEREAL_VAULT.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<EtherealCorePortalBlockEntity>> ETHEREAL_CORE_PORTAL =
            BLOCK_ENTITIES.register("ethereal_core_portal", () ->
                    BlockEntityType.Builder.of(EtherealCorePortalBlockEntity::new,
                            ModBlocks.ETHEREAL_CORE_PORTAL.get()).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
