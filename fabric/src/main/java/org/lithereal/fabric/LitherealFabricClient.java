package org.lithereal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.lithereal.Lithereal;
import org.lithereal.LitherealClient;
import org.lithereal.client.EtherealCoreSpecialEffects;
import org.lithereal.client.gui.screens.inventory.*;
import org.lithereal.block.ModBlocks;
import org.lithereal.client.renderer.*;
import org.lithereal.client.renderer.zombie.PhantomDrownedRenderer;
import org.lithereal.client.renderer.zombie.PhantomZombieRenderer;
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
        DimensionRenderingRegistry.registerDimensionEffects(Lithereal.id("ethereal_core"), new EtherealCoreSpecialEffects());
        EntityRendererRegistry.register(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHANTOM_ZOMBIE.get(), PhantomZombieRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHANTOM_DROWNED.get(), PhantomDrownedRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOD_BOAT.get(), pContext -> new ModBoatRenderer<>(pContext, false));
        EntityRendererRegistry.register(ModEntities.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer<>(pContext, true));
        EntityRendererRegistry.register(ModEntities.RIFT_SPAWNER.get(), NoopRenderer::new);

        MenuScreens.register(FabricScreenHandlers.ELECTRIC_CRUCIBLE_SCREEN_HANDLER, ElectricCrucibleScreen::new);
        MenuScreens.register(FabricScreenHandlers.FIRE_CRUCIBLE_SCREEN_HANDLER, FireCrucibleScreen::new);
        MenuScreens.register(FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER, FreezingStationScreen::new);
        MenuScreens.register(FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER, InfusementChamberScreen::new);

        LitherealClient.init();
    }
}