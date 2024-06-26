package org.lithereal.neoforge.world.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lithereal.Lithereal;
import org.lithereal.block.InfusedLitheriteBlock;
import org.lithereal.neoforge.world.item.ForgeItems;
import org.lithereal.neoforge.world.item.infused.ForgeInfusedLitheriteBlockItem;

import java.util.function.Supplier;

public class ForgeBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, Lithereal.MOD_ID);

    public static final DeferredHolder<Block, InfusedLitheriteBlock> INFUSED_LITHERITE_BLOCK = registerColoredBlock("infused_litherite_block",
            () -> new InfusedLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final DeferredHolder<Block, ForgeFireCrucibleBlock> FIRE_CRUCIBLE = registerBlock("fire_crucible",
            () -> new ForgeFireCrucibleBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final DeferredHolder<Block, ForgeFreezingStationBlock> FREEZING_STATION = registerBlock("freezing_station",
            () -> new ForgeFreezingStationBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final DeferredHolder<Block, ForgeInfusementChamberBlock> INFUSEMENT_CHAMBER = registerBlock("infusement_chamber",
            () -> new ForgeInfusementChamberBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    private static <T extends Block> DeferredHolder<Block, T> registerColoredBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerColoredBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredHolder<Item, ForgeInfusedLitheriteBlockItem> registerColoredBlockItem(String name, DeferredHolder<Block, T> block) {
        return ForgeItems.ITEMS.register(name, () -> new ForgeInfusedLitheriteBlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredHolder<Item, BlockItem> registerBlockItem(String name, DeferredHolder<Block, T> block) {
        return ForgeItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
