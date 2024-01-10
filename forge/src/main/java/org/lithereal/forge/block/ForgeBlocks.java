package org.lithereal.forge.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.forge.block.custom.ForgeFireCrucibleBlock;
import org.lithereal.forge.block.custom.ForgeFreezingStationBlock;
import org.lithereal.forge.block.custom.ForgeInfusementChamberBlock;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.forge.item.custom.infused.ForgeInfusedLitheriteBlockItem;

import java.util.function.Supplier;

public class ForgeBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Lithereal.MOD_ID);

    public static final RegistryObject<Block> INFUSED_LITHERITE_BLOCK = registerColouredBlock("infused_litherite_block",
            () -> new InfusedLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> FIRE_CRUCIBLE = registerBlock("fire_crucible",
            () -> new ForgeFireCrucibleBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> FREEZING_STATION = registerBlock("freezing_station",
            () -> new ForgeFreezingStationBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> INFUSEMENT_CHAMBER = registerBlock("infusement_chamber",
            () -> new ForgeInfusementChamberBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerColouredBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerColouredBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerColouredBlockItem(String name, RegistryObject<T> block) {
        return ForgeItems.ITEMS.register(name, () -> new ForgeInfusedLitheriteBlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ForgeItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
