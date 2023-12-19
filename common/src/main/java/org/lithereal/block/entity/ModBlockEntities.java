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

    public static final RegistrySupplier<BlockEntityType<BurningLitheriteBlockEntity>> BURNING_LITHERITE_BLOCK =
            BLOCK_ENTITIES.register("burning_litherite_block", () ->
                    BlockEntityType.Builder.of(BurningLitheriteBlockEntity::new,
                            ModBlocks.BURNING_LITHERITE_BLOCK.get()).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
