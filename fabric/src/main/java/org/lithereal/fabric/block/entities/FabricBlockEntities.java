package org.lithereal.fabric.block.entities;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.fabric.block.FabricBlocks;

public class FabricBlockEntities {
    public static final BlockEntityType<InfusedLitheriteBlockEntity> INFUSED_LITHERITE_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Lithereal.MOD_ID, "infused_litherite_block_entity"),
                    FabricBlockEntityTypeBuilder.create(InfusedLitheriteBlockEntity::new,
                            FabricBlocks.INFUSED_LITHERITE_BLOCK).build());

    public static void registerBlockEntities() {

    }
}
