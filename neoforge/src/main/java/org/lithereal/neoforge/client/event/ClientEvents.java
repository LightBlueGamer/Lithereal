package org.lithereal.neoforge.client.event;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import org.lithereal.Lithereal;
import org.lithereal.LitherealClient;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.EtherealCoreSpecialEffects;
import org.lithereal.client.gui.screens.inventory.ElectricCrucibleScreen;
import org.lithereal.client.particle.EtherealSoulProvider;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.client.particle.PortalParticleProvider;
import org.lithereal.client.particle.StandardBiomeProvider;
import org.lithereal.client.renderer.*;
import org.lithereal.client.renderer.zombie.BetterZombieModel;
import org.lithereal.client.renderer.zombie.PhantomDrownedRenderer;
import org.lithereal.client.renderer.zombie.PhantomZombieRenderer;
import org.lithereal.entity.ModEntities;
import org.lithereal.neoforge.world.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.client.gui.screens.inventory.ForgeMenuTypes;
import org.lithereal.client.gui.screens.inventory.FireCrucibleScreen;
import org.lithereal.client.gui.screens.inventory.FreezingStationScreen;
import org.lithereal.client.gui.screens.inventory.InfusementChamberScreen;

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
        public static void onDimensionSpecialEffectsRegister(RegisterDimensionSpecialEffectsEvent event) {
            event.register(Lithereal.id("ethereal_core"), new EtherealCoreSpecialEffects());
        }
        @SubscribeEvent
        public static void onParticleProviderRegister(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.LITHER_FIRE_FLAME.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.SOUL.get(), EtherealSoulProvider::new);
            event.registerSpriteSet(ModParticles.CRYSTAL_SPARKLE.get(), StandardBiomeProvider::new);
            event.registerSpriteSet(ModParticles.PORTAL_SPARKLE.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.PORTAL_EMISSION.get(), PortalParticleProvider::new);
        }
        @SubscribeEvent
        public static void onRegisterMenu(RegisterMenuScreensEvent event) {
            event.register(ForgeMenuTypes.ELECTRIC_CRUCIBLE_MENU.get(), ElectricCrucibleScreen::new);
            event.register(ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
            event.register(ForgeMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
            event.register(ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);
        }
        @SubscribeEvent
        public static void modelLayerLocationInit(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
            event.registerLayerDefinition(InfusementChamberBlockEntityModel.LAYER_LOCATION, InfusementChamberBlockEntityModel::createBodyLayer);
            event.registerLayerDefinition(BetterZombieModel.ZOMBIE, () -> BetterZombieModel.createBodyLayer(CubeDeformation.NONE));
            event.registerLayerDefinition(BetterZombieModel.ZOMBIE_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(1.0F)), 64, 32));
            event.registerLayerDefinition(BetterZombieModel.ZOMBIE_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.5F)), 64, 32));
            event.registerLayerDefinition(LitherealArmorModel.OUTER_ARMOR, () -> LayerDefinition.create(LitherealArmorModel.createBodyLayer(new CubeDeformation(1.0F)), 64, 32));
            event.registerLayerDefinition(LitherealArmorModel.INNER_ARMOR, () -> LayerDefinition.create(LitherealArmorModel.createBodyLayer(new CubeDeformation(0.5F)), 64, 32));
            event.registerLayerDefinition(ModBoatRenderer.PHANTOM_OAK_BOAT_LAYER, BoatModel::createBodyModel);
            event.registerLayerDefinition(ModBoatRenderer.PHANTOM_OAK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        }
        @SubscribeEvent
        public static void entityRendererInit(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSED_LITHERITE_BLOCK.get(), InfusedLitheriteBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSEMENT_CHAMBER.get(), InfusementChamberBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
            event.registerEntityRenderer(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(ModEntities.PHANTOM_ZOMBIE.get(), PhantomZombieRenderer::new);
            event.registerEntityRenderer(ModEntities.PHANTOM_DROWNED.get(), PhantomDrownedRenderer::new);
            event.registerEntityRenderer(ModEntities.MOD_BOAT.get(), pContext -> new ModBoatRenderer<>(pContext, false));
            event.registerEntityRenderer(ModEntities.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer<>(pContext, true));
            event.registerEntityRenderer(ModEntities.RIFT_SPAWNER.get(), NoopRenderer::new);
        }
    }
    public static class ClientForgeBusEvents {
        public static void register() {
//            NeoForge.EVENT_BUS.register(ClientForgeBusEvents.class);
        }
    }
}
