package org.lithereal.neoforge.client.event;

//? neoforge {
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.event.lifecycle.ClientStartedEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lithereal.LitherealClient;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.KeyMapping;
import org.lithereal.client.gui.screens.inventory.*;
import org.lithereal.client.model.BetterZombieModel;
import org.lithereal.client.model.LitherealArmorModel;
import org.lithereal.client.particle.*;
import org.lithereal.client.renderer.zombie.PhantomDrownedRenderer;
import org.lithereal.client.renderer.zombie.PhantomZombieRenderer;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModArmorItems;

import java.util.*;

import static net.minecraft.client.model.geom.LayerDefinitions.INNER_ARMOR_DEFORMATION;
import static net.minecraft.client.model.geom.LayerDefinitions.OUTER_ARMOR_DEFORMATION;
import static org.lithereal.LitherealClient.BABY_INNER_ARMOR_DEFORMATION;
import static org.lithereal.LitherealClient.BABY_OUTER_ARMOR_DEFORMATION;

public class ClientEvents {
    public static class ClientModBusEvents {
        public static void register(IEventBus bus) {
            bus.register(ClientModBusEvents.class);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LitherealClient.init();
        }
        @SubscribeEvent
        public static void onParticleProviderRegister(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.LITHER_FIRE_FLAME.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.SOUL.get(), EtherealSoulProvider::new);
            event.registerSpriteSet(ModParticles.RETRIBUTION_HOLY_BEAM.get(), BeamParticle.RetributionBeamProvider::new);
            event.registerSpriteSet(ModParticles.RETRIBUTION_LIGHT_BURST.get(), BeamParticle.RetributionBurstProvider::new);
            event.registerSpriteSet(ModParticles.CRYSTAL_SPARKLE.get(), StandardBiomeProvider::new);
            event.registerSpriteSet(ModParticles.PORTAL_SPARKLE.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.PORTAL_EMISSION.get(), PortalParticleProvider::new);
        }
        @SubscribeEvent
        public static void onRegisterMenu(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.ELECTRIC_CRUCIBLE_MENU.get(), ElectricCrucibleScreen::new);
            event.register(ModMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
            event.register(ModMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
            event.register(ModMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);
        }
        @SubscribeEvent
        public static void modelLayerLocationInit(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BetterZombieModel.ZOMBIE, () -> BetterZombieModel.createBodyLayer(CubeDeformation.NONE));
            event.registerLayerDefinition(BetterZombieModel.BABY_ZOMBIE,  () -> BetterZombieModel.createBabyBodyLayer(CubeDeformation.NONE));
            ArmorModelSet<LayerDefinition> litherealArmorLayerDefinition = LitherealArmorModel.createArmorMeshSet(INNER_ARMOR_DEFORMATION, OUTER_ARMOR_DEFORMATION)
                    .map(mesh -> LayerDefinition.create(mesh, 64, 32));
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET.head(), litherealArmorLayerDefinition::head);
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET.chest(), litherealArmorLayerDefinition::chest);
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET.legs(), litherealArmorLayerDefinition::legs);
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET.feet(), litherealArmorLayerDefinition::feet);
            ArmorModelSet<LayerDefinition> babyLitherealArmorLayerDefinition = LitherealArmorModel.createBabyArmorMeshSet(BABY_INNER_ARMOR_DEFORMATION, BABY_OUTER_ARMOR_DEFORMATION, PartPose.ZERO)
                    .map(mesh -> LayerDefinition.create(mesh, 64, 32));
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET_BABY.head(), babyLitherealArmorLayerDefinition::head);
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET_BABY.chest(), babyLitherealArmorLayerDefinition::chest);
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET_BABY.legs(), babyLitherealArmorLayerDefinition::legs);
            event.registerLayerDefinition(LitherealArmorModel.LITHEREAL_ARMOR_SET_BABY.feet(), babyLitherealArmorLayerDefinition::feet);
            event.registerLayerDefinition(LitherealClient.PHANTOM_OAK_BOAT_LAYER, BoatModel::createBoatModel);
            event.registerLayerDefinition(LitherealClient.PHANTOM_OAK_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
            event.registerLayerDefinition(LitherealClient.FORTSHROOM_BOAT_LAYER, BoatModel::createBoatModel);
            event.registerLayerDefinition(LitherealClient.FORTSHROOM_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
            event.registerLayerDefinition(LitherealClient.MALISHROOM_BOAT_LAYER, BoatModel::createBoatModel);
            event.registerLayerDefinition(LitherealClient.MALISHROOM_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
        }
        @SubscribeEvent
        public static void modelLayersInit(EntityRenderersEvent.AddLayers event) {
            LitherealArmorModel.ARMOR_MODEL_SET = ArmorModelSet.bake(LitherealArmorModel.LITHEREAL_ARMOR_SET, event.getEntityModels(), LitherealArmorModel::new);
            LitherealArmorModel.BABY_ARMOR_MODEL_SET = ArmorModelSet.bake(LitherealArmorModel.LITHEREAL_ARMOR_SET_BABY, event.getEntityModels(), LitherealArmorModel::new);
        }
        @SubscribeEvent
        public static void entityRendererInit(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.SIGN.get(), StandingSignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
            event.registerEntityRenderer(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(ModEntities.PHANTOM_ZOMBIE.get(), PhantomZombieRenderer::new);
            event.registerEntityRenderer(ModEntities.PHANTOM_DROWNED.get(), PhantomDrownedRenderer::new);
            event.registerEntityRenderer(ModEntities.PHANTOM_OAK_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.PHANTOM_OAK_BOAT_LAYER));
            event.registerEntityRenderer(ModEntities.PHANTOM_OAK_CHEST_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.PHANTOM_OAK_CHEST_BOAT_LAYER));
            event.registerEntityRenderer(ModEntities.FORTSHROOM_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.FORTSHROOM_BOAT_LAYER));
            event.registerEntityRenderer(ModEntities.FORTSHROOM_CHEST_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.FORTSHROOM_CHEST_BOAT_LAYER));
            event.registerEntityRenderer(ModEntities.MALISHROOM_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.MALISHROOM_BOAT_LAYER));
            event.registerEntityRenderer(ModEntities.MALISHROOM_CHEST_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.MALISHROOM_CHEST_BOAT_LAYER));
            event.registerEntityRenderer(ModEntities.RIFT_SPAWNER.get(), NoopRenderer::new);
        }
        @SubscribeEvent
        public static void registerClientExtensionsEvent(RegisterClientExtensionsEvent event) {
            event.registerItem(new IClientItemExtensions() {
                @Override
                public int getArmorLayerTintColor(ItemStack stack, EquipmentClientInfo.Layer layer, int layerIdx, int fallbackColor) {
                    return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
                }

                @Override
                public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
                    return (layerType == EquipmentClientInfo.LayerType.HUMANOID_BABY ? LitherealArmorModel.BABY_ARMOR_MODEL_SET : LitherealArmorModel.ARMOR_MODEL_SET).get(itemStack.get(DataComponents.EQUIPPABLE).slot());
                }
            }, ModArmorItems.INFUSED_LITHERITE_HELMET, ModArmorItems.INFUSED_LITHERITE_CHESTPLATE, ModArmorItems.INFUSED_LITHERITE_LEGGINGS, ModArmorItems.INFUSED_LITHERITE_BOOTS);
            event.registerItem(new IClientItemExtensions() {
               @Override
               public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
                   return (layerType == EquipmentClientInfo.LayerType.HUMANOID_BABY ? LitherealArmorModel.BABY_ARMOR_MODEL_SET : LitherealArmorModel.ARMOR_MODEL_SET).get(itemStack.get(DataComponents.EQUIPPABLE).slot());
               }
            }, ModArmorItems.LITHERITE_HELMET, ModArmorItems.LITHERITE_CHESTPLATE, ModArmorItems.LITHERITE_LEGGINGS, ModArmorItems.LITHERITE_BOOTS,
                    ModArmorItems.BURNING_LITHERITE_HELMET, ModArmorItems.BURNING_LITHERITE_CHESTPLATE, ModArmorItems.BURNING_LITHERITE_LEGGINGS, ModArmorItems.BURNING_LITHERITE_BOOTS,
                    ModArmorItems.FROZEN_LITHERITE_HELMET, ModArmorItems.FROZEN_LITHERITE_CHESTPLATE, ModArmorItems.FROZEN_LITHERITE_LEGGINGS, ModArmorItems.FROZEN_LITHERITE_BOOTS,
                    ModArmorItems.SMOLDERING_LITHERITE_HELMET, ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE, ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS, ModArmorItems.SMOLDERING_LITHERITE_BOOTS,
                    ModArmorItems.FROSTBITTEN_LITHERITE_HELMET, ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE, ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS, ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS,
                    ModArmorItems.WITHERING_LITHERITE_HELMET, ModArmorItems.WITHERING_LITHERITE_CHESTPLATE, ModArmorItems.WITHERING_LITHERITE_LEGGINGS, ModArmorItems.WITHERING_LITHERITE_BOOTS,
                    ModArmorItems.ODYSIUM_HELMET, ModArmorItems.ODYSIUM_CHESTPLATE, ModArmorItems.ODYSIUM_LEGGINGS, ModArmorItems.ODYSIUM_BOOTS,
                    ModArmorItems.ENHANCED_ODYSIUM_HELMET, ModArmorItems.ENHANCED_ODYSIUM_CHESTPLATE, ModArmorItems.ENHANCED_ODYSIUM_LEGGINGS, ModArmorItems.ENHANCED_ODYSIUM_BOOTS);
        }

        @SubscribeEvent
        public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
            event.register(KeyMapping.FREEZE_KEY);
            event.register(KeyMapping.SCORCH_KEY);
        }
    }
    public static class ClientForgeBusEvents {
        public static final Map<RecipeType<?>, List<RecipeHolder<?>>> STORED_RECIPES = new HashMap<>();
        public static void register() {
            NeoForge.EVENT_BUS.register(ClientForgeBusEvents.class);
        }
        @SubscribeEvent
        public static void onClientStarted(ClientStartedEvent event) {
            LitherealClient.onClientStarted(event.getClient());
        }

        @SubscribeEvent // on the game event bus only on the physical client
        public static void recipesReceived(RecipesReceivedEvent event) {
            // First remove the previous recipes
            STORED_RECIPES.clear();

            // Then store the recipes you want
            addRecipes(ModRecipes.BURNING_TYPE.get(), event.getRecipeMap().byType(ModRecipes.BURNING_TYPE.get()));
            addRecipes(RecipeType.SMELTING, event.getRecipeMap().byType(RecipeType.SMELTING));
        }

        @SubscribeEvent // on the game event bus only on the physical client
        public static void clientLogOut(ClientPlayerNetworkEvent.LoggingOut event) {
            // Clear the stored recipes on world log out
            STORED_RECIPES.clear();
        }

        public static <I extends RecipeInput, T extends Recipe<I>> void addRecipes(RecipeType<T> recipeType, Collection<RecipeHolder<T>> recipes) {
            STORED_RECIPES.put(recipeType, new ArrayList<>(recipes));
        }

        public static <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeType<T> recipeType) {
            return STORED_RECIPES.get(recipeType).stream().map(recipeHolder -> (RecipeHolder<T>)recipeHolder).toList();
        }
    }
}
//?}