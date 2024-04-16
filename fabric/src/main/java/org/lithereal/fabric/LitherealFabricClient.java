package org.lithereal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.lithereal.LitherealClient;
import org.lithereal.block.ModBlocks;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.lithereal.client.renderer.InfusementChamberBlockEntityRenderer;
import org.lithereal.entity.ModEntities;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.entity.FabricBlockEntities;
import org.lithereal.fabric.screen.FabricScreenHandlers;
import org.lithereal.screen.*;

public class LitherealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(FabricBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY, InfusedLitheriteBlockEntityRenderer::new);
        BlockEntityRenderers.register(FabricBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY, InfusementChamberBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_FIRE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FabricBlocks.FIRE_CRUCIBLE_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.INFINITY_GLASS.get(), RenderType.translucent());
        EntityRendererRegistry.register(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);

        MenuScreens.register(FabricScreenHandlers.FIRE_CRUCIBLE_SCREEN_HANDLER, FireCrucibleScreen::new);
        MenuScreens.register(FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER, FreezingStationScreen::new);
        MenuScreens.register(FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER, InfusementChamberScreen::new);
        MenuScreens.register(FabricScreenHandlers.LITHER_COLLECTOR_SCREEN_HANDLER, LitherCollectorScreen::new);
        MenuScreens.register(FabricScreenHandlers.LITHER_BATTERY_SCREEN_HANDLER, LitherBatteryScreen::new);

        LitherealClient.init();
    }
}