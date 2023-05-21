package org.litecraft.lithereal.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Lithereal.MOD_ID);

    public static final RegistryObject<BlockEntityType<FreezingStationBlockEntity>> FREEZING_STATION =
            BLOCK_ENTITIES.register("freezing_station", () ->
                    BlockEntityType.Builder.of(FreezingStationBlockEntity::new,
                            ModBlocks.FREEZING_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<FireCrucibleBlockEntity>> FIRE_CRUCIBLE =
            BLOCK_ENTITIES.register("fire_crucible", () ->
                    BlockEntityType.Builder.of(FireCrucibleBlockEntity::new,
                            ModBlocks.FIRE_CRUCIBLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<BurningLitheriteBlockEntity>> BURNING_LITHERITE_BLOCK =
            BLOCK_ENTITIES.register("burning_litherite_block", () ->
                    BlockEntityType.Builder.of(BurningLitheriteBlockEntity::new,
                            ModBlocks.HEATED_LITHERITE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
