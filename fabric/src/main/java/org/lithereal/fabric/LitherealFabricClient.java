package org.lithereal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.lithereal.LitherealClient;
import org.lithereal.client.gui.screens.inventory.*;
import org.lithereal.block.ModBlocks;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.lithereal.client.renderer.InfusementChamberBlockEntityRenderer;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.fabric.world.block.entity.FabricBlockEntities;
import org.lithereal.fabric.client.gui.screens.inventory.FabricScreenHandlers;
import org.lithereal.entity.ModEntities;

public class LitherealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(FabricBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY, InfusedLitheriteBlockEntityRenderer::new);
        BlockEntityRenderers.register(FabricBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY, InfusementChamberBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(FabricBlocks.FIRE_CRUCIBLE_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_FIRE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.INFINITY_GLASS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LITHER_LANTERN.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LITHEREAL_VAULT.get(), RenderType.cutout());
        EntityRendererRegistry.register(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);

        MenuScreens.register(FabricScreenHandlers.ETHER_BATTERY_SCREEN_HANDLER, EtherBatteryScreen::new);
        MenuScreens.register(FabricScreenHandlers.ETHER_COLLECTOR_SCREEN_HANDLER, EtherCollectorScreen::new);
        MenuScreens.register(FabricScreenHandlers.FIRE_CRUCIBLE_SCREEN_HANDLER, FireCrucibleScreen::new);
        MenuScreens.register(FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER, FreezingStationScreen::new);
        MenuScreens.register(FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER, InfusementChamberScreen::new);

        LitherealClient.init();
    }
}