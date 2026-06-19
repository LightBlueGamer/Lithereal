package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.item.Potion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.block.datagen.BlockDataProvider;
import org.lithereal.block.datagen.BlockDataTemplate;
import org.lithereal.block.datagen.BlockDataTemplates;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.item.infused.InfusedBlockItem;
import org.lithereal.tags.ModTags;

import static org.lithereal.block.ModBlocks.registerBlock;

public class ModStorageBlocks {
    public static final RegistrySupplier<Block> ODYSIUM_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_DIAMOND_TOOL).consume(registerBlock("odysium_block",
            resourceKey -> new Block(BlockBehaviour.Properties.of().setId(resourceKey)
                    .strength(8f).requiresCorrectToolForDrops().explosionResistance(50).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant()), ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Block> LITHERITE_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_DIAMOND_TOOL).consume(registerBlock("litherite_block",
            resourceKey -> new Block(BlockBehaviour.Properties.of().setId(resourceKey)
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL))), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Block> BURNING_LITHERITE_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_DIAMOND_TOOL, BlockTags.INFINIBURN_OVERWORLD, ModTags.BLUE_FIRE_BASE_BLOCKS).consume(registerBlock("burning_litherite_block",
            resourceKey -> new BurningLitheriteBlock(BlockBehaviour.Properties.of().setId(resourceKey)
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant()), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Block> FROZEN_LITHERITE_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_DIAMOND_TOOL).consume(registerBlock("frozen_litherite_block",
            resourceKey -> new FrozenLitheriteBlock(BlockBehaviour.Properties.of().setId(resourceKey)
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL))), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Block> WITHERING_LITHERITE_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_DIAMOND_TOOL).consume(registerBlock("withering_litherite_block",
            resourceKey -> new WitheringLitheriteBlock(BlockBehaviour.Properties.of().setId(resourceKey)
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL))), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Block> INFUSED_LITHERITE_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_DIAMOND_TOOL)
            .copyWithModelOverride(BlockDataTemplate.BASIC_BLOCK_WITH_TINTED_ITEM.apply(new Potion())).consume(registerBlock("infused_litherite_block",
                    resourceKey -> new InfusedLitheriteBlock(BlockBehaviour.Properties.of().setId(resourceKey)
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)),
            (finalBlock, properties) -> new InfusedBlockItem(finalBlock, properties.fireResistant())), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Block> CHARGED_LITHERITE_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_DIAMOND_TOOL).consume(registerBlock("charged_litherite_block",
                    resourceKey -> new Block(BlockBehaviour.Properties.of().setId(resourceKey)
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL))), ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, BlockTags.NEEDS_IRON_TOOL, BlockDataProvider.cBlockTag("storage_blocks/diamond")).consume(registerBlock("phantom_diamond_block", resourceKey ->
                    new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DIAMOND_BLOCK).setId(resourceKey))), ModRawMaterialItems.PHANTOM_DIAMOND);

    public static void register() {

    }
}
