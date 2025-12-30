package org.lithereal;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.*;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.KeyMapping;
import org.lithereal.client.particle.*;
import org.lithereal.client.renderer.LitherealArmorModel;
import org.lithereal.client.renderer.ModBoatRenderer;
import org.lithereal.client.renderer.zombie.BetterZombieModel;
import org.lithereal.item.*;
import org.lithereal.util.ModBlockColors;
import org.lithereal.util.ModItemColors;
import org.lithereal.item.compat.CompatInit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.architectury.platform.Platform.isModLoaded;

public class LitherealClient {
    public static void init() {
        EntityModelLayerRegistry.register(BetterZombieModel.ZOMBIE, () -> BetterZombieModel.createBodyLayer(CubeDeformation.NONE));
        EntityModelLayerRegistry.register(BetterZombieModel.ZOMBIE_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(1.0F)), 64, 32));
        EntityModelLayerRegistry.register(BetterZombieModel.ZOMBIE_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.5F)), 64, 32));
        EntityModelLayerRegistry.register(LitherealArmorModel.OUTER_ARMOR, () -> LayerDefinition.create(LitherealArmorModel.createBodyLayer(new CubeDeformation(1.0F), 0), 64, 32));
        EntityModelLayerRegistry.register(LitherealArmorModel.INNER_ARMOR, () -> LayerDefinition.create(LitherealArmorModel.createBodyLayer(new CubeDeformation(0.5F), 0), 64, 32));
        EntityModelLayerRegistry.register(ModBoatRenderer.PHANTOM_OAK_BOAT_LAYER, BoatModel::createBodyModel);
        EntityModelLayerRegistry.register(ModBoatRenderer.PHANTOM_OAK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        BlockEntityRendererRegistry.register((BlockEntityType<SignBlockEntity>) ModBlockEntities.SIGN.get(), SignRenderer::new);
        BlockEntityRendererRegistry.register((BlockEntityType<SignBlockEntity>) ModBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.LITHER_FIRE_FLAME.get(), FlameParticle.Provider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.SOUL.get(), EtherealSoulProvider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.RETRIBUTION_HOLY_BEAM.get(), BeamParticle.RetributionBeamProvider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.RETRIBUTION_LIGHT_BURST.get(), BeamParticle.RetributionBurstProvider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.CRYSTAL_SPARKLE.get(), StandardBiomeProvider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.PORTAL_SPARKLE.get(), FlameParticle.Provider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.PORTAL_EMISSION.get(), PortalParticleProvider::new);
        registerKeyBindings();
        registerColorHandlers();
        registerItemsToBuildingBlocksTab();
        registerItemsToNaturalTab();
        registerItemsToMaterialsTab();
        registerItemsToToolsTab();
        registerItemsToCombatTab();
        registerBowProperties(ModToolItems.ODYSIUM_BOW.get());
        registerBowProperties(ModToolItems.ENHANCED_ODYSIUM_BOW.get());
    }

    private static void registerBowProperties(ItemLike bow) {
        ItemPropertiesRegistry.register(bow, ResourceLocation.withDefaultNamespace("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0F : (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemPropertiesRegistry.register(bow,
                ResourceLocation.withDefaultNamespace("pulling"),
                (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
    }

    private static void registerKeyBindings() {
        KeyMappingRegistry.register(KeyMapping.FREEZE_KEY);
        KeyMappingRegistry.register(KeyMapping.SCORCH_KEY);
    }

    private static void registerColorHandlers() {
        ItemColor itemColor = ModItemColors.INFUSED_LITHERITE_COLOR_HANDLER::apply;
        BlockColor blockColor = ModBlockColors.INFUSED_LITHERITE_BLOCK_COLOR;

        ColorHandlerRegistry.registerItemColors(itemColor, ModStorageBlocks.INFUSED_LITHERITE_BLOCK.get().asItem(), ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_SWORD.get(), ModToolItems.INFUSED_LITHERITE_SHOVEL.get(), ModToolItems.INFUSED_LITHERITE_PICKAXE.get(), ModToolItems.INFUSED_LITHERITE_AXE.get(), ModToolItems.INFUSED_LITHERITE_HOE.get(), ModToolItems.INFUSED_LITHERITE_HAMMER.get(), ModArmorItems.INFUSED_LITHERITE_HELMET.get(), ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(), ModArmorItems.INFUSED_LITHERITE_BOOTS.get());
        if (isModLoaded("combatify"))
            CompatInit.setColoursForCombatify(itemColor);
        ColorHandlerRegistry.registerBlockColors(blockColor, ModStorageBlocks.INFUSED_LITHERITE_BLOCK.get());
        ColorHandlerRegistry.registerBlockColors((BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) -> {
            BlockEntity entity = world.getBlockEntity(pos);
            int color = 0;
            if (entity instanceof InfusementChamberBlockEntity infusementChamberBlockEntity)
                color = infusementChamberBlockEntity.getStoredPotion().getColor();

            return tintIndex == 0 ? color : -1;
        }, LitherealExpectPlatform.getInfusementChamberBlock());
        ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos) : 8573157, ModBlocks.ETHEREAL_GRASS_BLOCK);
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> 8573157, ModBlocks.ETHEREAL_GRASS_BLOCK);
        RenderTypeRegistry.register(RenderType.cutoutMipped(), ModBlocks.ETHEREAL_GRASS_BLOCK.get(),
                ModTreeBlocks.PHANTOM_OAK_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.LITHER_WALL_TORCH.get(),
                ModBlocks.LITHER_TORCH.get(),
                ModBlocks.LITHER_LANTERN.get(),
                ModBlocks.LITHEREAL_VAULT.get(),
                ModTreeBlocks.PHANTOM_OAK_DOOR.get(),
                ModTreeBlocks.PHANTOM_OAK_TRAPDOOR.get(),
                ModTreeBlocks.PHANTOM_OAK_SAPLING.get(),
                ModTreeBlocks.POTTED_PHANTOM_OAK_SAPLING.get(),
                ModVegetationBlocks.MALISHROOM.get(),
                ModVegetationBlocks.POTTED_MALISHROOM.get(),
                ModVegetationBlocks.FORTSHROOM.get(),
                ModVegetationBlocks.POTTED_FORTSHROOM.get(),
                ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get(),
                ModPhantomBlocks.PHANTOM_ICE_FLOWER.get(),
                ModPhantomBlocks.PHANTOM_ROSE.get(),
                ModPhantomBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get(),
                ModPhantomBlocks.POTTED_PHANTOM_ICE_FLOWER.get(),
                ModPhantomBlocks.POTTED_PHANTOM_ROSE.get(),
                LitherealExpectPlatform.getInfusementChamberBlock());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.INFINITY_GLASS.get(), ModBlocks.PURE_ETHER_SOURCE.get(), ModBlocks.ETHEREAL_CRYSTAL_BLOCK.get(), ModBlocks.LITHERITE_CRYSTAL_BLOCK.get(), ModBlocks.ETHEREAL_CORE_PORTAL.get(), ModBlocks.ETHEREAL_RIFT.get());
    }

    private static void registerItemsToBuildingBlocksTab() {
        CreativeTabRegistry.append(ModCreativeTabs.BUILDING_BLOCKS_TAB, ModItems.LITHER_TORCH.get(),
                ModBlocks.LITHER_LANTERN.get(),
                ModBlocks.CREATIVE_ETHER_SOURCE.get(),
                ModBlocks.PASSIVE_ETHER_ABSORBER.get(),
                ModBlocks.INFINITY_GLASS.get(),
                LitherealExpectPlatform.getElectricCrucibleBlock(),
                LitherealExpectPlatform.getFireCrucibleBlock(),
                LitherealExpectPlatform.getFreezingStationBlock(),
                LitherealExpectPlatform.getInfusementChamberBlock(),
                ModStoneBlocks.ETHERSTONE.get(),
                ModStoneBlocks.ETHERSTONE_SLAB.get(),
                ModStoneBlocks.ETHERSTONE_STAIRS.get(),
                ModStoneBlocks.ETHERSTONE_WALL.get(),
                ModStoneBlocks.POLISHED_ETHERSTONE.get(),
                ModStoneBlocks.POLISHED_ETHERSTONE_SLAB.get(),
                ModStoneBlocks.POLISHED_ETHERSTONE_STAIRS.get(),
                ModStoneBlocks.POLISHED_ETHERSTONE_WALL.get(),
                ModStoneBlocks.CHISELED_ETHERSTONE.get(),
                ModStoneBlocks.ETHERSTONE_BRICKS.get(),
                ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get(),
                ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get(),
                ModStoneBlocks.ETHERSTONE_BRICK_WALL.get(),
                ModStoneBlocks.PAILITE.get(),
                ModStoneBlocks.PAILITE_SLAB.get(),
                ModStoneBlocks.PAILITE_STAIRS.get(),
                ModStoneBlocks.PAILITE_WALL.get(),
                ModStoneBlocks.PAILITE_BUTTON.get(),
                ModStoneBlocks.PAILITE_PRESSURE_PLATE.get(),
                ModStoneBlocks.POLISHED_PAILITE.get(),
                ModStoneBlocks.POLISHED_PAILITE_SLAB.get(),
                ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(),
                ModStoneBlocks.POLISHED_PAILITE_WALL.get(),
                ModStoneBlocks.POLISHED_PAILITE_BUTTON.get(),
                ModStoneBlocks.POLISHED_PAILITE_PRESSURE_PLATE.get(),
                ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(),
                ModStoneBlocks.POLISHED_PAILITE_BRICKS.get(),
                ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get(),
                ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get(),
                ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get(),
                ModStoneBlocks.POLISHED_PAILITE_BRICK_BUTTON.get(),
                ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get(),
                ModStoneBlocks.LUMINITE.get(),
                ModStoneBlocks.LUMINITE_SLAB.get(),
                ModStoneBlocks.LUMINITE_STAIRS.get(),
                ModStoneBlocks.LUMINITE_WALL.get(),
                ModStoneBlocks.POLISHED_LUMINITE.get(),
                ModStoneBlocks.POLISHED_LUMINITE_SLAB.get(),
                ModStoneBlocks.POLISHED_LUMINITE_STAIRS.get(),
                ModStoneBlocks.POLISHED_LUMINITE_WALL.get(),
                ModStoneBlocks.VERDONE.get(),
                ModStoneBlocks.VERDONE_SLAB.get(),
                ModStoneBlocks.VERDONE_STAIRS.get(),
                ModStoneBlocks.VERDONE_WALL.get(),
                ModStoneBlocks.POLISHED_VERDONE.get(),
                ModStoneBlocks.POLISHED_VERDONE_SLAB.get(),
                ModStoneBlocks.POLISHED_VERDONE_STAIRS.get(),
                ModStoneBlocks.POLISHED_VERDONE_WALL.get(),
                ModTreeBlocks.PHANTOM_OAK_LOG.get(),
                ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(),
                ModTreeBlocks.PHANTOM_OAK_WOOD.get(),
                ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(),
                ModTreeBlocks.PHANTOM_OAK_PLANKS.get(),
                ModTreeBlocks.PHANTOM_OAK_SLAB.get(),
                ModTreeBlocks.PHANTOM_OAK_STAIRS.get(),
                ModTreeBlocks.PHANTOM_OAK_FENCE.get(),
                ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get(),
                ModTreeBlocks.PHANTOM_OAK_BUTTON.get(),
                ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get(),
                ModTreeBlocks.PHANTOM_OAK_DOOR.get(),
                ModTreeBlocks.PHANTOM_OAK_TRAPDOOR.get(),
                ModItems.PHANTOM_OAK_SIGN.get(),
                ModItems.PHANTOM_OAK_HANGING_SIGN.get());
    }

    private static void registerItemsToNaturalTab() {
        CreativeTabRegistry.append(ModCreativeTabs.NATURAL_BLOCKS_TAB, ModOreBlocks.LITHERITE_ORE,
                ModOreBlocks.DEEPSLATE_LITHERITE_ORE,
                ModOreBlocks.ETHERSTONE_LITHERITE_ORE,
                ModBlocks.LITHERITE_CRYSTAL_BLOCK,
                ModOreBlocks.NERITH_ORE,
                ModOreBlocks.DEEPSLATE_NERITH_ORE,
                ModOreBlocks.ETHERSTONE_NERITH_ORE,
                ModOreBlocks.LUMINIUM_ORE,
                ModOreBlocks.DEEPSLATE_LUMINIUM_ORE,
                ModOreBlocks.ETHERSTONE_LUMINIUM_ORE,
                ModOreBlocks.CYRUM_ORE,
                ModOreBlocks.DEEPSLATE_CYRUM_ORE,
                ModOreBlocks.ETHERSTONE_CYRUM_ORE,
                ModOreBlocks.COPALITE_ORE,
                ModOreBlocks.DEEPSLATE_COPALITE_ORE,
                ModOreBlocks.ETHERSTONE_COPALITE_ORE,
                ModOreBlocks.AURELITE_ORE,
                ModOreBlocks.DEEPSLATE_AURELITE_ORE,
                ModOreBlocks.ETHERSTONE_AURELITE_ORE,
                ModOreBlocks.SATURNITE_ORE,
                ModOreBlocks.HELLIONITE_ORE,
                ModOreBlocks.ELUNITE_ORE,
                ModOreBlocks.CHRYON_ORE,
                ModOreBlocks.ALLIAN_ORE,
                ModPhantomBlocks.PHANTOM_DIAMOND_ORE,
                ModBlocks.ETHEREAL_CORE_PORTAL,
                ModBlocks.ETHEREAL_DIRT,
                ModBlocks.ETHEREAL_GRASS_BLOCK,
                ModTreeBlocks.PHANTOM_OAK_LEAVES,
                ModTreeBlocks.PHANTOM_OAK_LOG,
                ModTreeBlocks.PHANTOM_OAK_SAPLING,
                ModVegetationBlocks.FORTSHROOM,
                ModVegetationBlocks.MALISHROOM,
                ModPhantomBlocks.PHANTOM_ROSE,
                ModPhantomBlocks.PHANTOM_ICE_FLOWER,
                ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE,
                ModStoneBlocks.ETHERSTONE,
                ModStoneBlocks.PAILITE,
                ModStoneBlocks.LUMINITE,
                ModStoneBlocks.VERDONE,
                ModBlocks.ETHEREAL_RIFT,
                ModBlocks.ETHEREAL_CRYSTAL_BLOCK,
                ModBlocks.PURE_ETHER_SOURCE,
                ModBlocks.IMPURE_ETHEREAL_CRYSTAL,
                ModBlocks.PURE_ETHEREAL_CRYSTAL_BLOCK,
                ModBlocks.LITHEREAL_VAULT,
                ModBlocks.SCORCHED_NETHERRACK,
                ModBlocks.SCORCHED_CRIMSON_NYLIUM,
                ModBlocks.SCORCHED_WARPED_NYLIUM);
    }

    private static void registerItemsToMaterialsTab() {
        List<ItemStack> litherite = new ArrayList<>();

        for (Holder<Potion> holder : BuiltInRegistries.POTION.holders().toList()) {
            List<ItemLike> itemLikes = Arrays.asList(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(),
                    ModStorageBlocks.INFUSED_LITHERITE_BLOCK.get());
            for (ItemLike itemLike : itemLikes) {
                ItemStack current = new ItemStack(itemLike);
                current.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
                litherite.add(current);
            }
        }
        CreativeTabRegistry.append(ModCreativeTabs.MATERIALS_TAB, ModItems.MOLTEN_LITHERITE_BUCKET.get(),
                LitherealExpectPlatform.getLitheriteItem(),
                ModStorageBlocks.LITHERITE_BLOCK.get(),
                ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(),
                ModStorageBlocks.BURNING_LITHERITE_BLOCK.get(),
                ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(),
                ModStorageBlocks.FROZEN_LITHERITE_BLOCK.get(),
                ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(),
                ModStorageBlocks.WITHERING_LITHERITE_BLOCK.get());
        CreativeTabRegistry.appendStack(ModCreativeTabs.MATERIALS_TAB, litherite.toArray(new ItemStack[0]));
        CreativeTabRegistry.append(ModCreativeTabs.MATERIALS_TAB, ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get(),
                ModStorageBlocks.CHARGED_LITHERITE_BLOCK.get(),
                ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.MYSTERIOUS_ROD.get(),
                ModRawMaterialItems.ODYSIUM_INGOT.get(),
                ModStorageBlocks.ODYSIUM_BLOCK.get(),
                ModRawMaterialItems.PHANTOM_DIAMOND.get(),
                ModPhantomBlocks.PHANTOM_DIAMOND_BLOCK.get(),
                ModRawMaterialItems.NETHERITE_NUGGET.get(),
                ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get(),
                ModRawMaterialItems.RAW_ALLIUM.get(),
                ModRawMaterialItems.ALLIAN_INGOT.get(),
                ModRawMaterialItems.RAW_NERITH.get(),
                ModRawMaterialItems.NERITH_INGOT.get(),
                ModRawMaterialItems.AURELITE_DUST.get(),
                ModRawMaterialItems.AURELITE_INGOT.get(),
                ModRawMaterialItems.COPALITE_DUST.get(),
                ModRawMaterialItems.COPALITE_INGOT.get(),
                ModRawMaterialItems.CHRYON_CRYSTAL.get(),
                ModRawMaterialItems.CYRUM_CRYSTAL.get(),
                ModRawMaterialItems.ELUNITE_CRYSTAL.get(),
                ModRawMaterialItems.ELCRUM_INGOT.get(),
                ModRawMaterialItems.HELLIONITE_CRYSTAL.get(),
                ModRawMaterialItems.LUMINIUM_CRYSTAL.get(),
                ModRawMaterialItems.SOLIUMITE_INGOT.get(),
                ModRawMaterialItems.SATURNITE_CRYSTAL.get(),
                ModItems.BOSS_ESSENCE.get(),
                ModItems.AWAKENED_BOSS_ESSENCE.get(),
                ModItems.PURE_BOSS_ESSENCE.get(),
                ModRawMaterialItems.UNIFIER.get());
    }

    private static void registerItemsToToolsTab() {
        List<ItemStack> litherite = new ArrayList<>();
        for (Holder<Potion> holder : BuiltInRegistries.POTION.holders().toList()) {
            List<ItemLike> itemLikes = Arrays.asList(ModToolItems.INFUSED_LITHERITE_SHOVEL.get(),
                    ModToolItems.INFUSED_LITHERITE_PICKAXE.get(),
                    ModToolItems.INFUSED_LITHERITE_AXE.get(),
                    ModToolItems.INFUSED_LITHERITE_HOE.get(),
                    ModToolItems.INFUSED_LITHERITE_HAMMER.get());
            for (ItemLike itemLike : itemLikes) {
                ItemStack current = new ItemStack(itemLike);
                current.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
                litherite.add(current);
            }
        }
        CreativeTabRegistry.append(ModCreativeTabs.TOOLS_TAB, ModToolItems.LITHERITE_SHOVEL,
                ModToolItems.LITHERITE_PICKAXE,
                ModToolItems.LITHERITE_AXE,
                ModToolItems.LITHERITE_HOE,
                ModToolItems.LITHERITE_HAMMER,
                ModToolItems.LITHERITE_BRUSH,
                ModToolItems.LITHERITE_WRENCH,
                ModToolItems.BURNING_LITHERITE_SHOVEL,
                ModToolItems.BURNING_LITHERITE_PICKAXE,
                ModToolItems.BURNING_LITHERITE_AXE,
                ModToolItems.BURNING_LITHERITE_HOE,
                ModToolItems.BURNING_LITHERITE_HAMMER,
                ModToolItems.SMOLDERING_LITHERITE_SHOVEL,
                ModToolItems.SMOLDERING_LITHERITE_PICKAXE,
                ModToolItems.SMOLDERING_LITHERITE_AXE,
                ModToolItems.SMOLDERING_LITHERITE_HOE,
                ModToolItems.SMOLDERING_LITHERITE_HAMMER,
                ModToolItems.FROZEN_LITHERITE_SHOVEL,
                ModToolItems.FROZEN_LITHERITE_PICKAXE,
                ModToolItems.FROZEN_LITHERITE_AXE,
                ModToolItems.FROZEN_LITHERITE_HOE,
                ModToolItems.FROZEN_LITHERITE_HAMMER,
                ModToolItems.FROSTBITTEN_LITHERITE_SHOVEL,
                ModToolItems.FROSTBITTEN_LITHERITE_PICKAXE,
                ModToolItems.FROSTBITTEN_LITHERITE_AXE,
                ModToolItems.FROSTBITTEN_LITHERITE_HOE,
                ModToolItems.FROSTBITTEN_LITHERITE_HAMMER,
                ModToolItems.WITHERING_LITHERITE_SHOVEL,
                ModToolItems.WITHERING_LITHERITE_PICKAXE,
                ModToolItems.WITHERING_LITHERITE_AXE,
                ModToolItems.WITHERING_LITHERITE_HOE,
                ModToolItems.WITHERING_LITHERITE_HAMMER);
        CreativeTabRegistry.appendStack(ModCreativeTabs.TOOLS_TAB, litherite.toArray(new ItemStack[0]));
        CreativeTabRegistry.append(ModCreativeTabs.TOOLS_TAB, ModToolItems.ODYSIUM_SHOVEL,
                ModToolItems.ODYSIUM_PICKAXE,
                ModToolItems.ODYSIUM_AXE,
                ModToolItems.ODYSIUM_HOE,
                ModToolItems.ODYSIUM_HAMMER,
                ModToolItems.ENHANCED_ODYSIUM_SHOVEL,
                ModToolItems.ENHANCED_ODYSIUM_PICKAXE,
                ModToolItems.ENHANCED_ODYSIUM_AXE,
                ModToolItems.ENHANCED_ODYSIUM_HOE,
                ModToolItems.ENHANCED_ODYSIUM_HAMMER,
                ModItems.MUSIC_DISC_SPARKLE,
                ModItems.LITHEREAL_KEY,
                ModItems.PHANTOM_OAK_BOAT,
                ModItems.PHANTOM_OAK_CHEST_BOAT);
    }

    private static void registerItemsToCombatTab() {
        List<ItemStack> litherite = new ArrayList<>();
        for (Holder<Potion> holder : BuiltInRegistries.POTION.holders().toList()) {
            List<ItemLike> itemLikes = Arrays.asList(ModToolItems.INFUSED_LITHERITE_SWORD.get(),
                    ModToolItems.INFUSED_LITHERITE_AXE.get(),
                    ModArmorItems.INFUSED_LITHERITE_HELMET.get(),
                    ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(),
                    ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(),
                    ModArmorItems.INFUSED_LITHERITE_BOOTS.get());
            for (ItemLike itemLike : itemLikes) {
                ItemStack current = new ItemStack(itemLike);
                current.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
                litherite.add(current);
            }
            if (isModLoaded("combatify"))
                litherite = CompatInit.populateInfusedForCombatify(litherite, holder);
        }
        CreativeTabRegistry.append(ModCreativeTabs.COMBAT_TAB, ModToolItems.LITHERITE_SWORD,
                ModToolItems.LITHERITE_AXE,
                ModArmorItems.LITHERITE_HELMET,
                ModArmorItems.LITHERITE_CHESTPLATE,
                ModArmorItems.LITHERITE_LEGGINGS,
                ModArmorItems.LITHERITE_BOOTS,
                ModToolItems.BURNING_LITHERITE_SWORD,
                ModToolItems.BURNING_LITHERITE_AXE,
                ModArmorItems.BURNING_LITHERITE_HELMET,
                ModArmorItems.BURNING_LITHERITE_CHESTPLATE,
                ModArmorItems.BURNING_LITHERITE_LEGGINGS,
                ModArmorItems.BURNING_LITHERITE_BOOTS,
                ModToolItems.SMOLDERING_LITHERITE_SWORD,
                ModToolItems.SMOLDERING_LITHERITE_AXE,
                ModArmorItems.SMOLDERING_LITHERITE_HELMET,
                ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE,
                ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS,
                ModArmorItems.SMOLDERING_LITHERITE_BOOTS,
                ModToolItems.FROZEN_LITHERITE_SWORD,
                ModToolItems.FROZEN_LITHERITE_AXE,
                ModArmorItems.FROZEN_LITHERITE_HELMET,
                ModArmorItems.FROZEN_LITHERITE_CHESTPLATE,
                ModArmorItems.FROZEN_LITHERITE_LEGGINGS,
                ModArmorItems.FROZEN_LITHERITE_BOOTS,
                ModToolItems.FROSTBITTEN_LITHERITE_SWORD,
                ModToolItems.FROSTBITTEN_LITHERITE_AXE,
                ModArmorItems.FROSTBITTEN_LITHERITE_HELMET,
                ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE,
                ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS,
                ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS,
                ModToolItems.WITHERING_LITHERITE_SWORD,
                ModToolItems.WITHERING_LITHERITE_AXE,
                ModArmorItems.WITHERING_LITHERITE_HELMET,
                ModArmorItems.WITHERING_LITHERITE_CHESTPLATE,
                ModArmorItems.WITHERING_LITHERITE_LEGGINGS,
                ModArmorItems.WITHERING_LITHERITE_BOOTS);
        CreativeTabRegistry.appendStack(ModCreativeTabs.COMBAT_TAB, litherite.toArray(new ItemStack[0]));
        CreativeTabRegistry.append(ModCreativeTabs.COMBAT_TAB, ModToolItems.ODYSIUM_SWORD,
                ModToolItems.ODYSIUM_AXE,
                ModToolItems.ODYSIUM_BOW,
                ModArmorItems.ODYSIUM_HELMET,
                ModArmorItems.ODYSIUM_CHESTPLATE,
                ModArmorItems.ODYSIUM_LEGGINGS,
                ModArmorItems.ODYSIUM_BOOTS,
                ModToolItems.ENHANCED_ODYSIUM_SWORD,
                ModToolItems.ENHANCED_ODYSIUM_AXE,
                ModToolItems.ENHANCED_ODYSIUM_BOW,
                ModArmorItems.ENHANCED_ODYSIUM_HELMET,
                ModArmorItems.ENHANCED_ODYSIUM_CHESTPLATE,
                ModArmorItems.ENHANCED_ODYSIUM_LEGGINGS,
                ModArmorItems.ENHANCED_ODYSIUM_BOOTS,
                ModToolItems.WAR_HAMMER,
                ModItems.LITHER_CHARGE);
        if (isModLoaded("combatify"))
            CompatInit.populateCombatTabForCombatify();
    }
}
