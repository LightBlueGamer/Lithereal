package org.lithereal.fabric.world.block.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.fabric.world.block.FabricBlocks;

@SuppressWarnings("EmptyMethod")
public class FabricBlockEntities {
    public static final BlockEntityType<InfusedLitheriteBlockEntity> INFUSED_LITHERITE_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "infused_litherite_block_entity"),
                    BlockEntityType.Builder.of(InfusedLitheriteBlockEntity::new,
                            FabricBlocks.INFUSED_LITHERITE_BLOCK).build());

    public static final BlockEntityType<FabricElectricCrucibleBlockEntity> ELECTRIC_CRUCIBLE_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "electric_crucible_block_entity"),
                    BlockEntityType.Builder.of(FabricElectricCrucibleBlockEntity::new,
                            FabricBlocks.ELECTRIC_CRUCIBLE_BLOCK).build());

    public static final BlockEntityType<FabricFireCrucibleBlockEntity> FIRE_CRUCIBLE_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "fire_crucible_block_entity"),
                    BlockEntityType.Builder.of(FabricFireCrucibleBlockEntity::new,
                            FabricBlocks.FIRE_CRUCIBLE_BLOCK).build());

    public static final BlockEntityType<FabricFreezingStationBlockEntity> FREEZING_STATION_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "freezing_station_block_entity"),
                    BlockEntityType.Builder.of(FabricFreezingStationBlockEntity::new,
                            FabricBlocks.FREEZING_STATION_BLOCK).build());

    public static final BlockEntityType<FabricInfusementChamberBlockEntity> INFUSEMENT_CHAMBER_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "infusement_chamber_block_entity"),
                    BlockEntityType.Builder.of(FabricInfusementChamberBlockEntity::new,
                            FabricBlocks.INFUSEMENT_CHAMBER_BLOCK).build());

    @SuppressWarnings("EmptyMethod")
    public static void registerBlockEntities() {

    }
}
