package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static org.lithereal.block.ModBlocks.registerBlock;

public class ModStorageBlocks {
    public static final RegistrySupplier<Block> ODYSIUM_BLOCK = registerBlock("odysium_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(8f).requiresCorrectToolForDrops().explosionResistance(50).sound(SoundType.METAL)));

    public static final RegistrySupplier<Block> LITHERITE_BLOCK = registerBlock("litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)));

    public static final RegistrySupplier<Block> BURNING_LITHERITE_BLOCK = registerBlock("burning_litherite_block",
            () -> new BurningLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> FROZEN_LITHERITE_BLOCK = registerBlock("frozen_litherite_block",
            () -> new FrozenLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> WITHERING_LITHERITE_BLOCK = registerBlock("withering_litherite_block",
            () -> new WitheringLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> CHARGED_LITHERITE_BLOCK = registerBlock("charged_litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static void register() {

    }
}
