package org.lithereal.neoforge.datagen;

//? neoforge {
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import org.jspecify.annotations.NonNull;
import org.lithereal.Lithereal;
import org.lithereal.block.*;
import org.lithereal.item.ModArmorItems;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.item.ModToolItems;
import org.lithereal.util.ModBlockFamilies;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.ItemModelGenerators.BLANK_LAYER;
import static net.minecraft.client.data.models.model.ModelTemplates.createItem;

public class ModModelProvider extends ModelProvider {
    public static final ModelTemplate TWO_LAYERED_HANDHELD_ITEM = createItem("handheld",TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HANDHELD_WAR_HAMMER_ITEM = createItem("lithereal:handheld_war_hammer", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH = createItem("brush", TextureSlot.LAYER0);
    public ModModelProvider(PackOutput output) {
        super(output, Lithereal.MOD_ID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }

    public void registerBlockModels(BlockModelGenerators blockModels) {
        ModBlockFamilies.MOD_BLOCK_FAMILIES.stream().filter(BlockFamily::shouldGenerateModel).forEach(blockFamily -> blockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily));
        blockWithItem(ModBlocks.COARSE_ETHEREAL_DIRT, blockModels);
        blockWithItem(ModBlocks.PHANTOM_GRAVEL, blockModels);
        blockWithItem(ModBlocks.CREATIVE_ETHER_SOURCE, blockModels);
        blockWithItem(ModBlocks.PASSIVE_ETHER_ABSORBER, blockModels);
        blockWithItem(ModBlocks.PURE_ETHER_SOURCE, blockModels);
        blockWithItem(ModBlocks.ETHEREAL_CRYSTAL_BLOCK, blockModels);

        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PLANKS.get(), blockModels);
        blockModels.createPlantWithDefaultItem(ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), ModTreeBlocks.POTTED_PHANTOM_OAK_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        logBlock(ModTreeBlocks.PHANTOM_OAK_LOG.get(),  ModTreeBlocks.PHANTOM_OAK_WOOD.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get());
        blockModels.createTrivialBlock(ModTreeBlocks.PHANTOM_OAK_LEAVES.get(), TexturedModel.LEAVES);
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_LEAVES.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get(), blockModels);

        blockWithItem(ModTreeBlocks.MALISHROOM_BLOCK, blockModels);
        blockWithItem(ModTreeBlocks.RED_MALISHROOM_BLOCK, blockModels);
        itemForBlockModel(ModTreeBlocks.MALISHROOM_PLANKS.get(), blockModels);
        logBlock(ModTreeBlocks.MALISHROOM_STEM.get(), ModTreeBlocks.MALISHROOM_HYPHAE.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get(), ModTreeBlocks.STRIPPED_MALISHROOM_HYPHAE.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get(), ModTreeBlocks.MALISHROOM_HANGING_SIGN.get(), ModTreeBlocks.MALISHROOM_WALL_HANGING_SIGN.get());
        itemForBlockModel(ModTreeBlocks.MALISHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.MALISHROOM_PRESSURE_PLATE.get(), blockModels);

        blockWithItem(ModTreeBlocks.FORTSHROOM_BLOCK, blockModels);
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PLANKS.get(), blockModels);
        logBlock(ModTreeBlocks.FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HYPHAE.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_HYPHAE.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HANGING_SIGN.get(), ModTreeBlocks.FORTSHROOM_WALL_HANGING_SIGN.get());
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PRESSURE_PLATE.get(), blockModels);

        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.MALISHROOM.get(), ModVegetationBlocks.POTTED_MALISHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.FORTSHROOM.get(), ModVegetationBlocks.POTTED_FORTSHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        itemForBlockModel(ModStoneBlocks.ETHERSTONE.get(), blockModels);
        blockModels.family(ModStoneBlocks.ETHERSTONE.get()).generateFor(new BlockFamily.Builder(ModStoneBlocks.ETHERSTONE.get())
                .wall(ModStoneBlocks.ETHERSTONE_WALL.get())
                .stairs(ModStoneBlocks.ETHERSTONE_STAIRS.get())
                .slab(ModStoneBlocks.ETHERSTONE_SLAB.get())
                .polished(ModStoneBlocks.POLISHED_ETHERSTONE.get())
                .getFamily());
        blockModels.createAxisAlignedPillarBlock(ModStoneBlocks.CHISELED_ETHERSTONE.get(), TexturedModel.COLUMN);
        itemForBlockModel(ModStoneBlocks.CHISELED_ETHERSTONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_ETHERSTONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_BRICKS.get(), blockModels);

        itemForBlockModel(ModStoneBlocks.PAILITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.PAILITE_PRESSURE_PLATE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_PRESSURE_PLATE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get(), blockModels);
        blockModels.family(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get()).generateFor(new BlockFamily.Builder(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get())
                .wall(ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get())
                .stairs(ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get())
                .slab(ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get())
                .button(ModStoneBlocks.POLISHED_PAILITE_BRICK_BUTTON.get())
                .pressurePlate(ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get())
                .getFamily());
        blockModels.createAxisAlignedPillarBlock(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), TexturedModel.COLUMN);
        itemForBlockModel(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), blockModels);

        itemForBlockModel(ModStoneBlocks.VERDONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_VERDONE.get(), blockModels);

        itemForBlockModel(ModStoneBlocks.LUMINITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_LUMINITE.get(), blockModels);

        blockWithItem(ModOreBlocks.LITHERITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_LITHERITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_LITHERITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.NERITH_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_NERITH_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_NERITH_ORE, blockModels);
        blockWithItem(ModOreBlocks.LUMINIUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_LUMINIUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_LUMINIUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.CYRUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_CYRUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_CYRUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.COPALITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_COPALITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_COPALITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.AURELITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_AURELITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_AURELITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.SATURNITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.PAILITE_SATURNITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.HELLIONITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.PAILITE_HELLIONITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ELUNITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.CHRYON_ORE, blockModels);
        blockWithItem(ModOreBlocks.ALLIAN_ORE, blockModels);
        blockWithItem(ModOreBlocks.PAILITE_NETHERITE_ORE, blockModels);

        blockWithItem(ModPhantomBlocks.PHANTOM_DIAMOND_ORE, blockModels);
        blockWithItem(ModPhantomBlocks.PHANTOM_QUARTZ_ORE, blockModels);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ROSE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get(), ModPhantomBlocks.POTTED_PHANTOM_ICE_FLOWER.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get(), BlockModelGenerators.PlantType.NOT_TINTED);
    }

    private void logBlock(Block log, Block wood, BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.woodProvider(log).log(log).wood(wood);
    }

    public void registerItemModels(ItemModelGenerators itemModels) {
        basicItem(ModItems.PHANTOM_OAK_BOAT.get(), itemModels);
        basicItem(ModItems.PHANTOM_OAK_CHEST_BOAT.get(), itemModels);
        basicItem(ModItems.FORTSHROOM_BOAT.get(), itemModels);
        basicItem(ModItems.FORTSHROOM_CHEST_BOAT.get(), itemModels);
        basicItem(ModItems.MALISHROOM_BOAT.get(), itemModels);
        basicItem(ModItems.MALISHROOM_CHEST_BOAT.get(), itemModels);

        basicItem(ModRawMaterialItems.LITHERITE_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.PHANTOM_DIAMOND.get(), itemModels);
        basicItem(ModRawMaterialItems.ODYSIUM_INGOT.get(), itemModels);
        basicItem(ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), itemModels);
        basicPotionItemNoOverlay(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), itemModels);
        basicItem(ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.UNIFIER.get(), itemModels);
        basicItem(ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get(), itemModels);
        basicItem(ModRawMaterialItems.NERITH_INGOT.get(), itemModels);
        basicItem(ModRawMaterialItems.ALLIAN_INGOT.get(), itemModels);
        basicItem(ModRawMaterialItems.AURELITE_DUST.get(), itemModels);
        basicItem(ModRawMaterialItems.AURELITE_INGOT.get(), itemModels);
        basicItem(ModRawMaterialItems.CHRYON_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.COPALITE_DUST.get(), itemModels);
        basicItem(ModRawMaterialItems.COPALITE_INGOT.get(), itemModels);
        basicItem(ModRawMaterialItems.CYRUM_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.ELUNITE_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.HELLIONITE_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.LUMINIUM_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.NETHERITE_NUGGET.get(), itemModels);
        basicItem(ModRawMaterialItems.NETHERITE_FRAGMENT.get(), itemModels);
        basicItem(ModRawMaterialItems.RAW_ALLIUM.get(), itemModels);
        basicItem(ModRawMaterialItems.RAW_NERITH.get(), itemModels);
        basicItem(ModRawMaterialItems.SATURNITE_CRYSTAL.get(), itemModels);
        basicItem(ModRawMaterialItems.SOLIUMITE_INGOT.get(), itemModels);
        basicItem(ModRawMaterialItems.ELCRUM_INGOT.get(), itemModels);

        handheldItem(ModToolItems.LITHERITE_SWORD.get(), itemModels);
        handheldItem(ModToolItems.LITHERITE_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.LITHERITE_AXE.get(), itemModels);
        handheldItem(ModToolItems.LITHERITE_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.LITHERITE_HOE.get(), itemModels);
        handheldItem(ModToolItems.LITHERITE_HAMMER.get(), itemModels);
        handheldItem(ModToolItems.LITHERITE_WRENCH.get(), itemModels);
        brushItem(ModToolItems.LITHERITE_BRUSH.get(), itemModels);
        handheldItem(ModToolItems.BURNING_LITHERITE_SWORD.get(), itemModels);
        handheldItem(ModToolItems.BURNING_LITHERITE_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.BURNING_LITHERITE_AXE.get(), itemModels);
        handheldItem(ModToolItems.BURNING_LITHERITE_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.BURNING_LITHERITE_HOE.get(), itemModels);
        handheldItem(ModToolItems.BURNING_LITHERITE_HAMMER.get(), itemModels);
        handheldItem(ModToolItems.FROZEN_LITHERITE_SWORD.get(), itemModels);
        handheldItem(ModToolItems.FROZEN_LITHERITE_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.FROZEN_LITHERITE_AXE.get(), itemModels);
        handheldItem(ModToolItems.FROZEN_LITHERITE_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.FROZEN_LITHERITE_HOE.get(), itemModels);
        handheldItem(ModToolItems.FROZEN_LITHERITE_HAMMER.get(), itemModels);
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_SWORD.get(), itemModels);
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_AXE.get(), itemModels);
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_HOE.get(), itemModels);
        handheldItem(ModToolItems.SMOLDERING_LITHERITE_HAMMER.get(), itemModels);
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_SWORD.get(), itemModels);
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_AXE.get(), itemModels);
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_HOE.get(), itemModels);
        handheldItem(ModToolItems.FROSTBITTEN_LITHERITE_HAMMER.get(), itemModels);
        handheldPotionItem(ModToolItems.INFUSED_LITHERITE_SWORD.get(), itemModels);
        handheldPotionItem(ModToolItems.INFUSED_LITHERITE_PICKAXE.get(), itemModels);
        handheldPotionItem(ModToolItems.INFUSED_LITHERITE_AXE.get(), itemModels);
        handheldPotionItem(ModToolItems.INFUSED_LITHERITE_SHOVEL.get(), itemModels);
        handheldPotionItem(ModToolItems.INFUSED_LITHERITE_HOE.get(), itemModels);
        handheldPotionItem(ModToolItems.INFUSED_LITHERITE_HAMMER.get(), itemModels);
        handheldItem(ModToolItems.WITHERING_LITHERITE_SWORD.get(), itemModels);
        handheldItem(ModToolItems.WITHERING_LITHERITE_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.WITHERING_LITHERITE_AXE.get(), itemModels);
        handheldItem(ModToolItems.WITHERING_LITHERITE_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.WITHERING_LITHERITE_HOE.get(), itemModels);
        handheldItem(ModToolItems.WITHERING_LITHERITE_HAMMER.get(), itemModels);
        handheldItem(ModToolItems.ODYSIUM_SWORD.get(), itemModels);
        handheldItem(ModToolItems.ODYSIUM_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.ODYSIUM_AXE.get(), itemModels);
        handheldItem(ModToolItems.ODYSIUM_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.ODYSIUM_HOE.get(), itemModels);
        handheldItem(ModToolItems.ODYSIUM_HAMMER.get(), itemModels);
        bowItem(ModToolItems.ODYSIUM_BOW.get(), itemModels);
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_SWORD.get(), itemModels);
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_PICKAXE.get(), itemModels);
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_AXE.get(), itemModels);
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_SHOVEL.get(), itemModels);
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_HOE.get(), itemModels);
        handheldItem(ModToolItems.ENHANCED_ODYSIUM_HAMMER.get(), itemModels);
        bowItem(ModToolItems.ENHANCED_ODYSIUM_BOW.get(), itemModels);

        itemModels.generateFlatItem(ModToolItems.WAR_HAMMER.get(), HANDHELD_WAR_HAMMER_ITEM);

        basicItem(ModArmorItems.LITHERITE_HELMET.get(), itemModels);
        basicItem(ModArmorItems.LITHERITE_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.LITHERITE_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.LITHERITE_BOOTS.get(), itemModels);
        basicItem(ModArmorItems.BURNING_LITHERITE_HELMET.get(), itemModels);
        basicItem(ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.BURNING_LITHERITE_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.BURNING_LITHERITE_BOOTS.get(), itemModels);
        basicItem(ModArmorItems.FROZEN_LITHERITE_HELMET.get(), itemModels);
        basicItem(ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.FROZEN_LITHERITE_BOOTS.get(), itemModels);
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_HELMET.get(), itemModels);
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.SMOLDERING_LITHERITE_BOOTS.get(), itemModels);
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_HELMET.get(), itemModels);
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS.get(), itemModels);
        basicPotionItemNoOverlay(ModArmorItems.INFUSED_LITHERITE_HELMET.get(), itemModels);
        basicPotionItemNoOverlay(ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(), itemModels);
        basicPotionItemNoOverlay(ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(), itemModels);
        basicPotionItemNoOverlay(ModArmorItems.INFUSED_LITHERITE_BOOTS.get(), itemModels);
        basicItem(ModArmorItems.WITHERING_LITHERITE_HELMET.get(), itemModels);
        basicItem(ModArmorItems.WITHERING_LITHERITE_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.WITHERING_LITHERITE_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.WITHERING_LITHERITE_BOOTS.get(), itemModels);
        basicItem(ModArmorItems.ODYSIUM_HELMET.get(), itemModels);
        basicItem(ModArmorItems.ODYSIUM_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.ODYSIUM_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.ODYSIUM_BOOTS.get(), itemModels);
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_HELMET.get(), itemModels);
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_CHESTPLATE.get(), itemModels);
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_LEGGINGS.get(), itemModels);
        basicItem(ModArmorItems.ENHANCED_ODYSIUM_BOOTS.get(), itemModels);
    }

    public void blockWithItem(Supplier<Block> block, BlockModelGenerators blockModels) {
        blockModels.createTrivialCube(block.get());
        blockModels.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(block.get()));
    }

    public void itemForBlockModel(Block block, BlockModelGenerators blockModels) {
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public void basicItem(Item item, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    public void basicPotionItemNoOverlay(Item item, ItemModelGenerators itemModels) {
        generateItemWithTint(item, new Potion(), itemModels);
    }

    public void generateItemWithTint(Item item, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        Identifier model = itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, overlayTint));
    }

    public void basicPotionItem(Item item, ItemModelGenerators itemModels) {
        itemModels.generateItemWithTintedOverlay(item, new Potion());
    }

    public void handheldPotionItem(Item item, ItemModelGenerators itemModels) {
        generateHandheldItemWithTintedOverlay(item, new Potion(), itemModels);
    }

    public void generateHandheldItemWithTintedOverlay(Item item, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        this.generateHandheldItemWithTintedOverlay(item, "_overlay", overlayTint, itemModels);
    }

    public void generateHandheldItemWithTintedOverlay(Item item, String overlaySuffix, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        Identifier model = this.generateLayeredHandheldItem(item, TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item, overlaySuffix), itemModels);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, BLANK_LAYER, overlayTint));
    }

    public Identifier generateLayeredHandheldItem(Item target, Material layer0, Material layer1, ItemModelGenerators itemModels) {
        return TWO_LAYERED_HANDHELD_ITEM.create(target, TextureMapping.layered(layer0, layer1), itemModels.modelOutput);
    }

    public void handheldItem(Item item, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public void brushItem(Item item, ItemModelGenerators itemModels) {
        itemModels.createFlatItemModel(item, BRUSH);
        itemModels.generateBrush(item);
    }

    public void bowItem(Item bowItem, ItemModelGenerators itemModels) {
        itemModels.createFlatItemModel(bowItem, ModelTemplates.BOW);
        itemModels.generateBow(bowItem);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
//        return Streams.of(ModBlocks.BLOCKS);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.empty();
//        return Streams.of(ModItems.ITEMS);
    }
}
//?}