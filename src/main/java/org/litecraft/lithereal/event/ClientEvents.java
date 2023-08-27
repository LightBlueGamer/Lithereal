package org.litecraft.lithereal.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.litecraft.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.litecraft.lithereal.util.KeyBinding;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Lithereal.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {

        }
    }

    @Mod.EventBusSubscriber(modid = Lithereal.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void modelLayerLocationInit(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
        }
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SCORCH_KEY);
            event.register(KeyBinding.FREEZE_KEY);
        }
        @SubscribeEvent
        public static void entityRendererInit(EntityRenderersEvent.RegisterRenderers event) {
            InfusedLitheriteBlockEntityRenderer.register(event);
        }
    }
}