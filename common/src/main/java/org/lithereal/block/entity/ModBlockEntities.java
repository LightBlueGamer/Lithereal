package org.lithereal.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModStorageBlocks;
import org.lithereal.block.ModTreeBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK_ENTITY_TYPE);


    public static final RegistrySupplier<BlockEntityType<InfusedLitheriteBlockEntity>> INFUSED_LITHERITE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("infused_litherite_block", () ->
                    BlockEntityType.Builder.of(InfusedLitheriteBlockEntity::new,
                            ModStorageBlocks.INFUSED_LITHERITE_BLOCK.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<LitherealVaultBlockEntity>> LITHEREAL_VAULT =
            BLOCK_ENTITIES.register("lithereal_vault", () ->
                    BlockEntityType.Builder.of(LitherealVaultBlockEntity::new,
                            ModBlocks.LITHEREAL_VAULT.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<InfiniteEtherSourceBlockEntity>> INFINITE_ETHER_GENERATOR =
            BLOCK_ENTITIES.register("infinite_ether_generator", () ->
                    BlockEntityType.Builder.of(InfiniteEtherSourceBlockEntity::new,
                            ModBlocks.CREATIVE_ETHER_SOURCE.get(),
                            ModBlocks.PASSIVE_ETHER_ABSORBER.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<EtherealCorePortalBlockEntity>> ETHEREAL_CORE_PORTAL =
            BLOCK_ENTITIES.register("ethereal_core_portal", () ->
                    BlockEntityType.Builder.of(EtherealCorePortalBlockEntity::new,
                            ModBlocks.ETHEREAL_CORE_PORTAL.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<EtherealRiftBlockEntity>> ETHEREAL_RIFT =
            BLOCK_ENTITIES.register("ethereal_rift", () ->
                    BlockEntityType.Builder.of(EtherealRiftBlockEntity::new,
                            ModBlocks.ETHEREAL_RIFT.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<? extends SignBlockEntity>> SIGN =
            BLOCK_ENTITIES.register(
                    "sign",
                    () -> BlockEntityType.Builder.of(
                            CustomSignBlockEntity::new,
                            ModTreeBlocks.PHANTOM_OAK_SIGN.get(),
                            ModTreeBlocks.PHANTOM_OAK_WALL_SIGN.get()
                    ).build(null));

    public static final RegistrySupplier<BlockEntityType<? extends SignBlockEntity>> HANGING_SIGN =
            BLOCK_ENTITIES.register(
                    "hanging_sign",
                    () -> BlockEntityType.Builder.of(
                            CustomHangingSignBlockEntity::new,
                            ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(),
                            ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get()
                    ).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
