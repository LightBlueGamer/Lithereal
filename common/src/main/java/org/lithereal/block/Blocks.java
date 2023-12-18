package org.lithereal.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.Lithereal;
import org.lithereal.item.CreativeTabs;
import org.lithereal.item.Items;

import java.util.function.Supplier;

public class Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> LITHERITE_ORE = registerBlock("litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    public static final RegistrySupplier<Block> DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return Items.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().arch$tab(CreativeTabs.LITHEREAL_TAB)));
    }

    public static void register() {
        BLOCKS.register();
    }
}
