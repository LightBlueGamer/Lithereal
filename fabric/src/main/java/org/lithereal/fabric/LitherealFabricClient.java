package org.lithereal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.lithereal.fabric.block.entity.FabricBlockEntities;
import org.lithereal.fabric.screen.FabricScreenHandlers;
import org.lithereal.screen.FireCrucibleScreen;
import org.lithereal.screen.FreezingStationScreen;
import org.lithereal.screen.InfusementChamberScreen;

public class LitherealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(FabricBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY, InfusedLitheriteBlockEntityRenderer::new);

        MenuScreens.register(FabricScreenHandlers.FIRE_CRUCIBLE_SCREEN_HANDLER, FireCrucibleScreen::new);
        MenuScreens.register(FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER, FreezingStationScreen::new);
        MenuScreens.register(FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER, InfusementChamberScreen::new);
    }
}
