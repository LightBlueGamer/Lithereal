package org.lithereal.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.Lithereal;
import org.lithereal.block.custom.BlueFireBlock;
import org.lithereal.block.custom.BurningLitheriteBlock;
import org.lithereal.block.custom.FrozenLitheriteBlock;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> LITHERITE_ORE = registerBlock("litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of( )
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    public static final RegistrySupplier<Block> DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    public static final RegistrySupplier<Block> LITHERITE_BLOCK = registerBlock("litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> BURNING_LITHERITE_BLOCK = registerHeatedBlock("burning_litherite_block",
            () -> new BurningLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> FORZEN_LITHERITE_BLOCK = registerHeatedBlock("frozen_litherite_block",
            () -> new FrozenLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> BLUE_FIRE = registerBlockOnly("blue_fire",
            () -> new BlueFireBlock(BlockBehaviour.Properties.copy(Blocks.FIRE), 15));

    public static final RegistrySupplier<Block> SCORCHED_CRIMSON_NYLIUM = registerBlock("scorched_crimson_nylium",
            () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK)));

    public static final RegistrySupplier<Block> SCORCHED_NETHERRACK = registerBlock("scorched_netherrack",
            () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK)));

    public static final RegistrySupplier<Block> SCORCHED_WARPED_NYLIUM = registerBlock("scorched_warped_nylium",
            () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK)));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));
    }

    private static <T extends Block> RegistrySupplier<T> registerHeatedBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerHeatedBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerHeatedBlockItem(String name, RegistrySupplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().fireResistant().arch$tab(ModCreativeTabs.LITHEREAL_TAB)));
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockOnly(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    public static void register() {
        BLOCKS.register();
    }
}
