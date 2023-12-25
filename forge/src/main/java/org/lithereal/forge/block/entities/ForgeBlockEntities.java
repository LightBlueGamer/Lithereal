package org.lithereal.forge.block.entities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.forge.block.ForgeBlocks;

public class ForgeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Lithereal.MOD_ID);
    public static final RegistryObject<BlockEntityType<InfusedLitheriteBlockEntity>> INFUSED_LITHERITE_BLOCK =
            BLOCK_ENTITIES.register("infused_litherite_block", () ->
                    BlockEntityType.Builder.of(InfusedLitheriteBlockEntity::new,
                            ForgeBlocks.INFUSED_LITHERITE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FireCrucibleBlockEntity>> FIRE_CRUCIBLE =
            BLOCK_ENTITIES.register("fire_crucible", () ->
                    BlockEntityType.Builder.of(FireCrucibleBlockEntity::new,
                            ForgeBlocks.FIRE_CRUCIBLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
