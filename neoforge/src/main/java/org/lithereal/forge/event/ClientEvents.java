package org.lithereal.forge.event;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lithereal.Lithereal;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.lithereal.client.renderer.InfusementChamberBlockEntityModel;
import org.lithereal.client.renderer.InfusementChamberBlockEntityRenderer;
import org.lithereal.entity.ModEntities;
import org.lithereal.forge.block.entity.ForgeBlockEntities;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Lithereal.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void modelLayerLocationInit(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
            event.registerLayerDefinition(InfusementChamberBlockEntityModel.LAYER_LOCATION, InfusementChamberBlockEntityModel::createBodyLayer);
        }
        @SubscribeEvent
        public static void entityRendererInit(EntityRenderersEvent.RegisterRenderers event) {
            registerBlockEntityRenderer(event);
            event.registerEntityRenderer(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);
        }
    }

    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSED_LITHERITE_BLOCK.get(), InfusedLitheriteBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSEMENT_CHAMBER.get(), InfusementChamberBlockEntityRenderer::new);
    }
}
