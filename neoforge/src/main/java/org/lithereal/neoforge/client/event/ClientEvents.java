package org.lithereal.neoforge.client.event;

import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import org.lithereal.Lithereal;
import org.lithereal.LitherealClient;
import org.lithereal.client.EtherealCoreSpecialEffects;
import org.lithereal.client.particle.EtherealSoulProvider;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.lithereal.client.renderer.InfusementChamberBlockEntityModel;
import org.lithereal.client.renderer.InfusementChamberBlockEntityRenderer;
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
            event.registerSpriteSet(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.SOUL.get(), EtherealSoulProvider::new);
        }
        @SubscribeEvent
        public static void onRegisterMenu(RegisterMenuScreensEvent event) {
            event.register(ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
            event.register(ForgeMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
            event.register(ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);
        }
        @SubscribeEvent
        public static void modelLayerLocationInit(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
            event.registerLayerDefinition(InfusementChamberBlockEntityModel.LAYER_LOCATION, InfusementChamberBlockEntityModel::createBodyLayer);
        }
        @SubscribeEvent
        public static void entityRendererInit(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSED_LITHERITE_BLOCK.get(), InfusedLitheriteBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSEMENT_CHAMBER.get(), InfusementChamberBlockEntityRenderer::new);
            event.registerEntityRenderer(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);
        }
    }
    public static class ClientForgeBusEvents {
        public static void register() {
//            NeoForge.EVENT_BUS.register(ClientForgeBusEvents.class);
        }
    }
}
