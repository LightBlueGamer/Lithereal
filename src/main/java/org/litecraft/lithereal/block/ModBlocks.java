package org.litecraft.lithereal.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.custom.*;
import org.litecraft.lithereal.item.ModItems;
import org.litecraft.lithereal.item.custom.infused.InfusedLitheriteBlockItem;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Lithereal.MOD_ID);

    public static final RegistryObject<Block> FREEZING_STATION = registerBlock("freezing_station",
            () -> new FreezingStationBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> FIRE_CRUCIBLE = registerBlock("fire_crucible",
            () -> new FireCrucibleBlock(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> INFUSEMENT_CHAMBER = registerBlockOnly("infusement_chamber",
            () -> new InfusementChamberBlock(BlockBehaviour.Properties.of()
                    .strength(7f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> INFUSEMENT_CHAMBER_CONTROLLER = registerBlock("infusement_chamber_controller",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(7f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LITHERITE_BLOCK = registerBlock("litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> COOLED_LITHERITE_BLOCK = registerBlock("cooled_litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HEATED_LITHERITE_BLOCK = registerHeatedBlock("heated_litherite_block",
            () -> new BurningLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> WITHERING_LITHERITE_BLOCK = registerBlock("withering_litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> INFUSED_LITHERITE_BLOCK = registerColouredBlock("infused_litherite_block",
            () -> new InfusedLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LITHERITE_ORE = registerBlock("litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    public static final RegistryObject<Block> DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));

    public static final RegistryObject<Block> BLUE_FIRE = registerBlockOnly("blue_fire",
            () -> new BlueFireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_FIRE), 15));

    public static final RegistryObject<Block> SCORCHED_CRIMSON_NYLIUM = registerBlock("scorched_crimson_nylium",
            () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK)));

    public static final RegistryObject<Block> SCORCHED_NETHERRACK = registerBlock("scorched_netherrack",
            () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK)));

    public static final RegistryObject<Block> SCORCHED_WARPED_NYLIUM = registerBlock("scorched_warped_nylium",
            () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK)));

    public static final RegistryObject<Block> INFUSEMENT_CHAMBER_CASING = registerBlock("infusement_chamber_casing",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> INFUSEMENT_CHAMBER_CORE = registerBlock("infusement_chamber_core",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<T> registerColouredBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerColouredBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockOnly(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<Item> registerColouredBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new InfusedLitheriteBlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerHeatedBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerHeatedBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerHeatedBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().fireResistant()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}