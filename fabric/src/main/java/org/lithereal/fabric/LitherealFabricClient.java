package org.lithereal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import org.lithereal.LitherealClient;
import org.lithereal.block.ModBlocks;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.lithereal.client.renderer.InfusementChamberBlockEntityRenderer;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.entity.FabricBlockEntities;
import org.lithereal.fabric.screen.FabricScreenHandlers;
import org.lithereal.screen.FireCrucibleScreen;
import org.lithereal.screen.FreezingStationScreen;
import org.lithereal.screen.InfusementChamberScreen;
import org.lithereal.screen.LitherCollectorScreen;

public class LitherealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(FabricBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY, InfusedLitheriteBlockEntityRenderer::new);
        BlockEntityRenderers.register(FabricBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY, InfusementChamberBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_FIRE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FabricBlocks.FIRE_CRUCIBLE_BLOCK, RenderType.cutout());

        MenuScreens.register(FabricScreenHandlers.FIRE_CRUCIBLE_SCREEN_HANDLER, FireCrucibleScreen::new);
        MenuScreens.register(FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER, FreezingStationScreen::new);
        MenuScreens.register(FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER, InfusementChamberScreen::new);
        MenuScreens.register(FabricScreenHandlers.LITHER_COLLECTOR_SCREEN_HANDLER, LitherCollectorScreen::new);
        LitherealClient.init();
    }
}