package org.lithereal.neoforge.datagen;

//? neoforge {
import com.mojang.math.Quadrant;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.NonNull;
import org.lithereal.Lithereal;
import org.lithereal.block.*;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.item.*;
import org.lithereal.util.ModBlockFamilies;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static net.minecraft.client.data.models.ItemModelGenerators.BLANK_LAYER;
import static net.minecraft.client.data.models.model.ModelTemplates.create;
import static net.minecraft.client.data.models.model.ModelTemplates.createItem;

public class ModModelProvider extends ModelProvider {
    public static final ModelTemplate TWO_LAYERED_HANDHELD_ITEM = createItem("handheld",TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HANDHELD_WAR_HAMMER_ITEM = createItem("lithereal:handheld_war_hammer", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH = createItem("brush", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH_BRUSHING_0 = createItem("brush_brushing_0", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH_BRUSHING_1 = createItem("brush_brushing_1", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH_BRUSHING_2 = createItem("brush_brushing_2", TextureSlot.LAYER0);
    public static final ModelTemplate GLASS_ENCLOSED_TORCH = create("lithereal:template_glass_enclosed_torch", TextureSlot.TORCH);
    public static final ModelTemplate GLASS_ENCLOSED_WALL_TORCH = create("lithereal:template_glass_enclosed_torch_wall", TextureSlot.TORCH);
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
        fireCrucibleBlock(ModBlocks.FIRE_CRUCIBLE.get(), blockModels);
        electricCrucibleBlock(ModBlocks.ELECTRIC_CRUCIBLE.get(), blockModels);
        existingBlockWithItem(ModBlocks.FREEZING_STATION, blockModels);
        infusementChamberBlock(ModBlocks.INFUSEMENT_CHAMBER.get(), blockModels);
        createFarmland(ModBlocks.ETHEREAL_FARMLAND.get(), ModBlocks.ETHEREAL_DIRT.get(), blockModels);
        blockWithItem(ModBlocks.ETHEREAL_DIRT, blockModels);
        grassLikeBlock(ModBlocks.ETHEREAL_GRASS_BLOCK, ModBlocks.ETHEREAL_DIRT, new Constant(8573157), blockModels);
        blockWithItem(ModBlocks.SCORCHED_NETHERRACK, blockModels);
        nyliumBlock(ModBlocks.SCORCHED_CRIMSON_NYLIUM.get(), Blocks.CRIMSON_NYLIUM, ModBlocks.SCORCHED_NETHERRACK.get(), blockModels);
        nyliumBlock(ModBlocks.SCORCHED_WARPED_NYLIUM.get(), Blocks.WARPED_NYLIUM, ModBlocks.SCORCHED_NETHERRACK.get(), blockModels);
        blockWithItem(ModBlocks.COARSE_ETHEREAL_DIRT, blockModels);
        blockWithItem(ModBlocks.PHANTOM_GRAVEL, blockModels);
        blockWithItem(ModBlocks.CREATIVE_ETHER_SOURCE, blockModels);
        blockWithItem(ModBlocks.PASSIVE_ETHER_ABSORBER, blockModels);
        blockWithItem(ModBlocks.PURE_ETHER_SOURCE, blockModels);
        blockWithItem(ModBlocks.INFINITY_GLASS, blockModels);
        blockWithItem(ModBlocks.LITHERITE_CRYSTAL_BLOCK, blockModels);
        blockWithItem(ModBlocks.ETHEREAL_CRYSTAL_BLOCK, blockModels);
        corneredBlock(ModBlocks.PURE_ETHEREAL_CRYSTAL_BLOCK.get(), blockModels);
        createGlassEnclosedTorch(ModBlocks.LITHER_TORCH.get(), ModBlocks.LITHER_WALL_TORCH.get(), blockModels);
        blockModels.createLantern(ModBlocks.LITHER_LANTERN.get());
        createFire(ModBlocks.BLUE_FIRE.get(), blockModels);
        existingBlockWithFacing(ModBlocks.IMPURE_ETHEREAL_CRYSTAL.get(), blockModels);
        riftBlock(ModBlocks.ETHEREAL_RIFT.get(), blockModels);
        riftLikePortalBlock(ModBlocks.ETHEREAL_CORE_PORTAL.get(), ModBlocks.ETHEREAL_RIFT.get(), blockModels);
        createVault(ModBlocks.LITHEREAL_VAULT.get(), blockModels, false);

        blockWithItem(ModStorageBlocks.LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.BURNING_LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.FROZEN_LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.WITHERING_LITHERITE_BLOCK, blockModels);
        blockWithTintedItem(ModStorageBlocks.INFUSED_LITHERITE_BLOCK, new Potion(), blockModels);
        blockWithItem(ModStorageBlocks.CHARGED_LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.ODYSIUM_BLOCK, blockModels);
        blockWithItem(ModPhantomBlocks.PHANTOM_DIAMOND_BLOCK, blockModels);

        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PLANKS.get(), blockModels);
        blockModels.createPlantWithDefaultItem(ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), ModTreeBlocks.POTTED_PHANTOM_OAK_SAPLING.get(), PlantType.NOT_TINTED);
        logBlock(ModTreeBlocks.PHANTOM_OAK_LOG.get(),  ModTreeBlocks.PHANTOM_OAK_WOOD.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.PHANTOM_OAK_SHELF.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
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
        blockModels.createShelf(ModTreeBlocks.MALISHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
        itemForBlockModel(ModTreeBlocks.MALISHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.MALISHROOM_PRESSURE_PLATE.get(), blockModels);

        blockWithItem(ModTreeBlocks.FORTSHROOM_BLOCK, blockModels);
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PLANKS.get(), blockModels);
        logBlock(ModTreeBlocks.FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HYPHAE.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_HYPHAE.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HANGING_SIGN.get(), ModTreeBlocks.FORTSHROOM_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.FORTSHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PRESSURE_PLATE.get(), blockModels);

        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.MALISHROOM.get(), ModVegetationBlocks.POTTED_MALISHROOM.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.FORTSHROOM.get(), ModVegetationBlocks.POTTED_FORTSHROOM.get(), PlantType.NOT_TINTED);

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
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ROSE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get(), ModPhantomBlocks.POTTED_PHANTOM_ICE_FLOWER.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get(), PlantType.NOT_TINTED);
    }

    public void registerItemModels(ItemModelGenerators itemModels) {
        basicItem(ModItems.BOSS_ESSENCE.get(), itemModels);
        basicItem(ModItems.AWAKENED_BOSS_ESSENCE.get(), itemModels);
        basicItem(ModItems.PURE_BOSS_ESSENCE.get(), itemModels);
        basicItem(ModItems.LITHER_CHARGE.get(), itemModels);
        basicItem(ModItems.LITHEREAL_KEY.get(), itemModels);
        basicItem(ModItems.MOLTEN_LITHERITE_BUCKET.get(), itemModels);
        basicItem(ModItems.MUSIC_DISC_SPARKLE.get(), itemModels);
        handheldItem(ModItems.MYSTERIOUS_ROD.get(), itemModels);
        basicItem(ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), itemModels);
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

        basicItem(ModSpawnEggs.PHANTOM_ZOMBIE_SPAWN_EGG.get(), itemModels);
        basicItem(ModSpawnEggs.PHANTOM_DROWNED_SPAWN_EGG.get(), itemModels);
    }

    public void blockWithItem(Supplier<? extends Block> block, BlockModelGenerators blockModels) {
        blockModels.createTrivialCube(block.get());
        blockModels.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(block.get()));
    }

    public void existingBlockWithItem(Supplier<? extends Block> block, BlockModelGenerators blockModels) {
        blockModels.createNonTemplateModelBlock(block.get());
        blockModels.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(block.get()));
    }

    public void blockWithTintedItem(Supplier<? extends Block> block, ItemTintSource tintSource, BlockModelGenerators blockModels) {
        blockModels.createNonTemplateModelBlock(block.get());
        blockModels.registerSimpleTintedItemModel(block.get(), ModelLocationUtils.getModelLocation(block.get()), tintSource);
    }

    public void nyliumBlock(Block nylium, Block topDonor, Block netherrack, BlockModelGenerators blockModels) {
        TextureMapping mapping = (new TextureMapping()).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(netherrack)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(topDonor)).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(nylium, "_side"));
        blockModels.blockStateOutput.accept(createSimpleBlock(nylium, plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(nylium, mapping, blockModels.modelOutput))));
        blockModels.registerSimpleItemModel(nylium, ModelLocationUtils.getModelLocation(nylium));
    }

    public void fireCrucibleBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant litModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_lit"));
        MultiVariant blueLitModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_blue_lit"));
        MultiVariant litFireModel = variants(plainModel(ModelLocationUtils.getModelLocation(block, "_lit_fire0")),
                plainModel(ModelLocationUtils.getModelLocation(block, "_lit_fire1")));
        MultiVariant litBlueFireModel = variants(plainModel(ModelLocationUtils.getModelLocation(block, "_lit_blue_fire0")),
                plainModel(ModelLocationUtils.getModelLocation(block, "_lit_blue_fire1")));
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.UNLIT), model)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.LIT), litModel)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.BLUE_LIT), blueLitModel)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.LIT), litFireModel)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.BLUE_LIT), litBlueFireModel));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public void electricCrucibleBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant onModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_on"));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(FireCrucibleBlock.HEAT_STATE)
                .select(FireCrucibleBlockEntity.HeatState.UNLIT, model)
                .select(FireCrucibleBlockEntity.HeatState.LIT, onModel)
                .select(FireCrucibleBlockEntity.HeatState.BLUE_LIT, onModel)));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public void infusementChamberBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant filledModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_filled"));
        MultiVariant overlayModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_overlay"));
        MultiVariant filledOverlayModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_filled_overlay"));
        plainVariant(TexturedModel.ORIENTABLE_ONLY_TOP.updateTexture(textureMapping -> {
            textureMapping.put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_empty"));
            textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_rest"));
            textureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_vent"));
        }).createWithSuffix(block, "_inventory", blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, InfusementChamberBlock.PRIMARY_FILLED, InfusementChamberBlock.SECONDARY_FILLED)
                .generate((direction, primaryFilled, secondaryFilled) -> {
                    MultiVariant usedModel = model;
                    if (primaryFilled && secondaryFilled) usedModel = filledOverlayModel;
                    else if (primaryFilled) usedModel = overlayModel;
                    else if (secondaryFilled) usedModel = filledModel;
                    return switch (direction) {
                        case NORTH -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R180));
                        case WEST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R90));
                        case EAST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R270));
                        default -> usedModel;
                    };
                })));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block, "_inventory"));
    }

    public void corneredBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(TexturedModel.CUBE.create(block, blockModels.modelOutput));
        MultiVariant cornerModel = plainVariant(TexturedModel.COLUMN.updateTexture(textureMapping -> {
            textureMapping.put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_corner"));
            textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block));
        }).createWithSuffix(block, "_corner", blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(PureEtherealCrystalBlock.CORNER, BlockStateProperties.HORIZONTAL_FACING)
                .generate((isCorner, direction) -> {
                    MultiVariant usedModel = isCorner ? cornerModel : model;
                    return switch (direction) {
                        case NORTH -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R180));
                        case WEST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R90));
                        case EAST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R270));
                        default -> usedModel;
                    };
                })));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    private void existingBlockWithFacing(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.FACING)
                .select(Direction.UP, model)
                .select(Direction.DOWN, model.with(VariantMutator.X_ROT.withValue(Quadrant.R180)))
                .select(Direction.EAST, model.with(VariantMutator.X_ROT.withValue(Quadrant.R90).then(VariantMutator.Y_ROT.withValue(Quadrant.R90))))
                .select(Direction.WEST, model.with(VariantMutator.X_ROT.withValue(Quadrant.R270).then(VariantMutator.Y_ROT.withValue(Quadrant.R90))))
                .select(Direction.NORTH, model.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                .select(Direction.SOUTH, model.with(VariantMutator.X_ROT.withValue(Quadrant.R270)))));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    private void riftBlock(Block rift, BlockModelGenerators blockModels) {
        Identifier verticalModel = ModelLocationUtils.getModelLocation(rift, "_vertical");
        MultiVariant riftHorizontal = plainVariant(ModelLocationUtils.getModelLocation(rift, "_horizontal"));
        MultiVariant riftVertical = plainVariant(verticalModel);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(rift).with(PropertyDispatch.initial(BlockStateProperties.AXIS)
                .select(Direction.Axis.X, riftVertical.with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                .select(Direction.Axis.Y, riftHorizontal)
                .select(Direction.Axis.Z, riftVertical)));
        blockModels.registerSimpleItemModel(rift, verticalModel);
    }

    private void riftLikePortalBlock(Block portal, Block rift, BlockModelGenerators blockModels) {
        Identifier horizontalModel = ModelLocationUtils.getModelLocation(rift, "_horizontal");
        MultiVariant riftHorizontal = plainVariant(horizontalModel);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(portal, riftHorizontal));
        blockModels.registerSimpleItemModel(portal, horizontalModel);
    }

    private void logBlock(Block log, Block wood, BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.woodProvider(log).log(log).wood(wood);
    }

    private void createFarmland(Block farmland, Block dirt, BlockModelGenerators blockModels) {
        TextureMapping dryTextures = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(dirt)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(farmland));
        TextureMapping moistTextures = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(dirt)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(farmland, "_moist"));
        MultiVariant dryModel = plainVariant(ModelTemplates.FARMLAND.create(farmland, dryTextures, blockModels.modelOutput));
        MultiVariant moistModel = plainVariant(ModelTemplates.FARMLAND.create(ModelLocationUtils.getModelLocation(farmland, "_moist"), moistTextures, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(farmland).with(createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, moistModel, dryModel)));
        blockModels.registerSimpleItemModel(farmland, ModelLocationUtils.getModelLocation(farmland));
    }

    public void createFire(Block fire, BlockModelGenerators blockModels) {
        ConditionBuilder noSides = condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false).term(BlockStateProperties.UP, false);
        MultiVariant floorFireModels = blockModels.createFloorFireModels(fire);
        MultiVariant sideFireModels = blockModels.createSideFireModels(fire);
        MultiVariant topFireModels = blockModels.createTopFireModels(fire);
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(fire).with(noSides, floorFireModels).with(or(condition().term(BlockStateProperties.NORTH, true), noSides), sideFireModels).with(or(condition().term(BlockStateProperties.EAST, true), noSides), sideFireModels.with(Y_ROT_90)).with(or(condition().term(BlockStateProperties.SOUTH, true), noSides), sideFireModels.with(Y_ROT_180)).with(or(condition().term(BlockStateProperties.WEST, true), noSides), sideFireModels.with(Y_ROT_270)).with(condition().term(BlockStateProperties.UP, true), topFireModels));
    }

    public void createVault(Block vault, BlockModelGenerators blockModels, boolean isOminous) {
        TextureMapping inactiveTextures = TextureMapping.vault(vault, "_front_off", "_side_off", "_top", "_bottom");
        TextureMapping activeTextures = TextureMapping.vault(vault, "_front_on", "_side_on", "_top", "_bottom");
        TextureMapping unlockingTextures = TextureMapping.vault(vault, "_front_ejecting", "_side_on", "_top", "_bottom");
        TextureMapping ejectingRewardTextures = TextureMapping.vault(vault, "_front_ejecting", "_side_on", "_top_ejecting", "_bottom");
        Identifier inactiveModel = ModelTemplates.VAULT.create(vault, inactiveTextures, blockModels.modelOutput);
        MultiVariant inactive = plainVariant(inactiveModel);
        MultiVariant active = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_active", activeTextures, blockModels.modelOutput));
        MultiVariant unlocking = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_unlocking", unlockingTextures, blockModels.modelOutput));
        MultiVariant ejectingReward = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_ejecting_reward", ejectingRewardTextures, blockModels.modelOutput));
        blockModels.registerSimpleItemModel(vault, inactiveModel);
        if (isOminous) {
            TextureMapping inactiveTexturesOminous = TextureMapping.vault(vault, "_front_off_ominous", "_side_off_ominous", "_top_ominous", "_bottom_ominous");
            TextureMapping activeTexturesOminous = TextureMapping.vault(vault, "_front_on_ominous", "_side_on_ominous", "_top_ominous", "_bottom_ominous");
            TextureMapping unlockingTexturesOminous = TextureMapping.vault(vault, "_front_ejecting_ominous", "_side_on_ominous", "_top_ominous", "_bottom_ominous");
            TextureMapping ejectingRewardTexturesOminous = TextureMapping.vault(vault, "_front_ejecting_ominous", "_side_on_ominous", "_top_ejecting_ominous", "_bottom_ominous");
            MultiVariant inactiveOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_ominous", inactiveTexturesOminous, blockModels.modelOutput));
            MultiVariant activeOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_active_ominous", activeTexturesOminous, blockModels.modelOutput));
            MultiVariant unlockingOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_unlocking_ominous", unlockingTexturesOminous, blockModels.modelOutput));
            MultiVariant ejectingRewardOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_ejecting_reward_ominous", ejectingRewardTexturesOminous, blockModels.modelOutput));
            blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(vault).with(PropertyDispatch.initial(VaultBlock.STATE, VaultBlock.OMINOUS).generate((state, ominous) -> {
                MultiVariant variant;
                switch (state) {
                    case INACTIVE -> variant = ominous ? inactiveOminous : inactive;
                    case ACTIVE -> variant = ominous ? activeOminous : active;
                    case UNLOCKING -> variant = ominous ? unlockingOminous : unlocking;
                    case EJECTING -> variant = ominous ? ejectingRewardOminous : ejectingReward;
                    default -> throw new MatchException(null, null);
                }

                return variant;
            })).with(ROTATION_HORIZONTAL_FACING));
        }
        else {
            blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(vault).with(PropertyDispatch.initial(VaultBlock.STATE).generate((state) -> {
                MultiVariant variant;
                switch (state) {
                    case INACTIVE -> variant = inactive;
                    case ACTIVE -> variant = active;
                    case UNLOCKING -> variant = unlocking;
                    case EJECTING -> variant = ejectingReward;
                    default -> throw new MatchException(null, null);
                }

                return variant;
            })).with(ROTATION_HORIZONTAL_FACING));
        }
    }

    public void grassLikeBlock(Supplier<? extends Block> grass, Supplier<? extends Block> dirt, ItemTintSource tintSource, BlockModelGenerators blockModels) {
        Material bottomTexture = TextureMapping.getBlockTexture(dirt.get());
        TextureMapping snowyMapping = (new TextureMapping()).put(TextureSlot.BOTTOM, bottomTexture).copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE).put(TextureSlot.TOP, TextureMapping.getBlockTexture(grass.get(), "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(grass.get(), "_snow"));
        MultiVariant snowyGrass = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(grass.get(), "_snow", snowyMapping, blockModels.modelOutput));
        Identifier plainGrassModel = ModelLocationUtils.getModelLocation(grass.get());
        blockModels.createGrassLikeBlock(grass.get(), createRotatedVariants(plainModel(plainGrassModel)), snowyGrass);
        blockModels.registerSimpleTintedItemModel(grass.get(), plainGrassModel, tintSource);
    }

    public void createGlassEnclosedTorch(Block ground, Block wall, BlockModelGenerators blockModels) {
        TextureMapping textures = TextureMapping.torch(ground);
        blockModels.blockStateOutput.accept(createSimpleBlock(ground, plainVariant(GLASS_ENCLOSED_TORCH.create(ground, textures, blockModels.modelOutput))));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(wall, plainVariant(GLASS_ENCLOSED_WALL_TORCH.create(wall, textures, blockModels.modelOutput))).with(ROTATION_TORCH));
        blockModels.registerSimpleFlatItemModel(ground.asItem());
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
        createFlatItemModelWithNameSuffix(item, "_brushing_0", BRUSH_BRUSHING_0, itemModels);
        createFlatItemModelWithNameSuffix(item, "_brushing_1", BRUSH_BRUSHING_1, itemModels);
        createFlatItemModelWithNameSuffix(item, "_brushing_2", BRUSH_BRUSHING_2, itemModels);
        itemModels.generateBrush(item);
    }

    public Identifier createFlatItemModelWithNameSuffix(Item item, String suffix, ModelTemplate template, ItemModelGenerators itemModels) {
        return template.create(ModelLocationUtils.getModelLocation(item, suffix), TextureMapping.layer0(item), itemModels.modelOutput);
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