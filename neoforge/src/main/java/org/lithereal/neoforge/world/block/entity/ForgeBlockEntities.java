package org.lithereal.neoforge.world.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;

public class ForgeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Lithereal.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfusedLitheriteBlockEntity>> INFUSED_LITHERITE_BLOCK =
            BLOCK_ENTITIES.register("infused_litherite_block", () ->
                    BlockEntityType.Builder.of(InfusedLitheriteBlockEntity::new,
                            ForgeBlocks.INFUSED_LITHERITE_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeFireCrucibleBlockEntity>> FIRE_CRUCIBLE =
            BLOCK_ENTITIES.register("fire_crucible", () ->
                    BlockEntityType.Builder.of(ForgeFireCrucibleBlockEntity::new,
                            ForgeBlocks.FIRE_CRUCIBLE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeFreezingStationBlockEntity>> FREEZING_STATION =
            BLOCK_ENTITIES.register("freezing_station", () ->
                    BlockEntityType.Builder.of(ForgeFreezingStationBlockEntity::new,
                            ForgeBlocks.FREEZING_STATION.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeInfusementChamberBlockEntity>> INFUSEMENT_CHAMBER =
            BLOCK_ENTITIES.register("infusement_chamber", () ->
                    BlockEntityType.Builder.of(ForgeInfusementChamberBlockEntity::new,
                            ForgeBlocks.INFUSEMENT_CHAMBER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
