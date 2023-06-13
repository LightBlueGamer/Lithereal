package com.haru.lithereal.block;

import com.haru.lithereal.Lithereal;
import com.haru.lithereal.block.custom.BlueFireBlock;
import com.haru.lithereal.block.custom.BurningLitheriteBlock;
import com.haru.lithereal.block.custom.FireCrucibleBlock;
import com.haru.lithereal.block.custom.FreezingStationBlock;
import com.haru.lithereal.item.ModCreativeModeTabs;
import com.haru.lithereal.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Lithereal.MOD_ID);

    public static final RegistryObject<Block> FREEZING_STATION = registerBlock("freezing_station",
            () -> new FreezingStationBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> FIRE_CRUCIBLE = registerBlock("fire_crucible",
            () -> new FireCrucibleBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> LITHERITE_BLOCK = registerBlock("litherite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> COOLED_LITHERITE_BLOCK = registerBlock("cooled_litherite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HEATED_LITHERITE_BLOCK = registerHeatedBlock("heated_litherite_block",
            () -> new BurningLitheriteBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LITHERITE_ORE = registerBlock("litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    public static final RegistryObject<Block> DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    public static final RegistryObject<Block> BLUE_FIRE = registerBlockOnly("blue_fire",
            () -> new BlueFireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_FIRE), 15));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockOnly(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    }

    private static <T extends Block> RegistryObject<T> registerHeatedBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerHeatedBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerHeatedBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().fireResistant().tab(ModCreativeModeTabs.LITHEREAL_TAB)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}