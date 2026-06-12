package org.lithereal.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
//? fabric {
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
//?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModStorageBlocks;
import org.lithereal.block.ModTreeBlocks;

import java.util.function.BiFunction;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK_ENTITY_TYPE);


    public static final RegistrySupplier<BlockEntityType<InfusedLitheriteBlockEntity>> INFUSED_LITHERITE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("infused_litherite_block", () ->
                    createBlockEntityType(InfusedLitheriteBlockEntity::new,
                            ModStorageBlocks.INFUSED_LITHERITE_BLOCK.get()));

    public static final RegistrySupplier<BlockEntityType<ElectricCrucibleBlockEntity>> ELECTRIC_CRUCIBLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("electric_crucible_block", () ->
                    createBlockEntityType(ElectricCrucibleBlockEntity::new,
                            ModBlocks.ELECTRIC_CRUCIBLE.get()));

    public static final RegistrySupplier<BlockEntityType<FireCrucibleBlockEntity>> FIRE_CRUCIBLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fire_crucible_block", () ->
                    createBlockEntityType(FireCrucibleBlockEntity::new,
                            ModBlocks.FIRE_CRUCIBLE.get()));

    public static final RegistrySupplier<BlockEntityType<FreezingStationBlockEntity>> FREEZING_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("freezing_station_block", () ->
                    createBlockEntityType(FreezingStationBlockEntity::new,
                            ModBlocks.FREEZING_STATION.get()));

    public static final RegistrySupplier<BlockEntityType<InfusementChamberBlockEntity>> INFUSEMENT_CHAMBER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("infusement_chamber_block", () ->
                    createBlockEntityType(InfusementChamberBlockEntity::new,
                            ModBlocks.INFUSEMENT_CHAMBER.get()));

    public static final RegistrySupplier<BlockEntityType<LitherealVaultBlockEntity>> LITHEREAL_VAULT =
            BLOCK_ENTITIES.register("lithereal_vault", () ->
                    createBlockEntityType(LitherealVaultBlockEntity::new,
                            ModBlocks.LITHEREAL_VAULT.get()));

    public static final RegistrySupplier<BlockEntityType<InfiniteEtherSourceBlockEntity>> INFINITE_ETHER_GENERATOR =
            BLOCK_ENTITIES.register("infinite_ether_generator", () ->
                    createBlockEntityType(InfiniteEtherSourceBlockEntity::new,
                            ModBlocks.CREATIVE_ETHER_SOURCE.get(),
                            ModBlocks.PASSIVE_ETHER_ABSORBER.get()));

    public static final RegistrySupplier<BlockEntityType<EtherealCorePortalBlockEntity>> ETHEREAL_CORE_PORTAL =
            BLOCK_ENTITIES.register("ethereal_core_portal", () ->
                    createBlockEntityType(EtherealCorePortalBlockEntity::new,
                            ModBlocks.ETHEREAL_CORE_PORTAL.get()));

    public static final RegistrySupplier<BlockEntityType<EtherealRiftBlockEntity>> ETHEREAL_RIFT =
            BLOCK_ENTITIES.register("ethereal_rift", () ->
                    createBlockEntityType(EtherealRiftBlockEntity::new,
                            ModBlocks.ETHEREAL_RIFT.get()));

    public static final RegistrySupplier<BlockEntityType<? extends SignBlockEntity>> SIGN =
            BLOCK_ENTITIES.register(
                    "sign",
                    () -> createBlockEntityType(
                            CustomSignBlockEntity::new,
                            ModTreeBlocks.PHANTOM_OAK_SIGN.get(),
                            ModTreeBlocks.PHANTOM_OAK_WALL_SIGN.get(),
                            ModTreeBlocks.FORTSHROOM_SIGN.get(),
                            ModTreeBlocks.FORTSHROOM_WALL_SIGN.get(),
                            ModTreeBlocks.MALISHROOM_SIGN.get(),
                            ModTreeBlocks.MALISHROOM_WALL_SIGN.get()
                    ));

    public static final RegistrySupplier<BlockEntityType<? extends SignBlockEntity>> HANGING_SIGN =
            BLOCK_ENTITIES.register(
                    "hanging_sign",
                    () -> createBlockEntityType(
                            CustomHangingSignBlockEntity::new,
                            ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(),
                            ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get(),
                            ModTreeBlocks.FORTSHROOM_HANGING_SIGN.get(),
                            ModTreeBlocks.FORTSHROOM_WALL_HANGING_SIGN.get(),
                            ModTreeBlocks.MALISHROOM_HANGING_SIGN.get(),
                            ModTreeBlocks.MALISHROOM_WALL_HANGING_SIGN.get()
                    ));

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(final BiFunction<BlockPos, BlockState, T> factory, final Block... validBlocks) {
        //? fabric {
        return FabricBlockEntityTypeBuilder.create(factory::apply, validBlocks).build();
        //?}
        //? neoforge {
        /*return new BlockEntityType<>(factory, Set.of(validBlocks));
        *///?}
    }

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
