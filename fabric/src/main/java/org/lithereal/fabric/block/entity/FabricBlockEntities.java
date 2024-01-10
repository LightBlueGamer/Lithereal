package org.lithereal.fabric.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.fabric.block.FabricBlocks;

@SuppressWarnings("EmptyMethod")
public class FabricBlockEntities {
    public static final BlockEntityType<InfusedLitheriteBlockEntity> INFUSED_LITHERITE_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Lithereal.MOD_ID, "infused_litherite_block_entity"),
                    FabricBlockEntityTypeBuilder.create(InfusedLitheriteBlockEntity::new,
                            FabricBlocks.INFUSED_LITHERITE_BLOCK).build());

    public static final BlockEntityType<FabricFireCrucibleBlockEntity> FIRE_CRUCIBLE_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Lithereal.MOD_ID, "fire_crucible_block_entity"),
                    FabricBlockEntityTypeBuilder.create(FabricFireCrucibleBlockEntity::new,
                            FabricBlocks.FIRE_CRUCIBLE_BLOCK).build());

    public static final BlockEntityType<FabricFreezingStationBlockEntity> FREEZING_STATION_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Lithereal.MOD_ID, "freezing_station_block_entity"),
                    FabricBlockEntityTypeBuilder.create(FabricFreezingStationBlockEntity::new,
                            FabricBlocks.FREEZING_STATION_BLOCK).build());

    public static final BlockEntityType<FabricInfusementChamberBlockEntity> INFUSEMENT_CHAMBER_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Lithereal.MOD_ID, "infusement_chamber_block_entity"),
                    FabricBlockEntityTypeBuilder.create(FabricInfusementChamberBlockEntity::new,
                            FabricBlocks.INFUSEMENT_CHAMBER_BLOCK).build());

    @SuppressWarnings("EmptyMethod")
    public static void registerBlockEntities() {

    }
}
