package org.lithereal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
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
import org.lithereal.fabric.client.armor.LitheriteArmorRenderer;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.fabric.client.gui.screens.inventory.FabricScreenHandlers;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModArmorItems;

public class LitherealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
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

        ArmorRenderer.register(new LitheriteArmorRenderer(), ModArmorItems.LITHERITE_HELMET.get(), ModArmorItems.LITHERITE_CHESTPLATE.get(), ModArmorItems.LITHERITE_LEGGINGS.get(), ModArmorItems.LITHERITE_BOOTS.get(),
                ModArmorItems.BURNING_LITHERITE_HELMET.get(), ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get(), ModArmorItems.BURNING_LITHERITE_LEGGINGS.get(), ModArmorItems.BURNING_LITHERITE_BOOTS.get(),
                ModArmorItems.FROZEN_LITHERITE_HELMET.get(), ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get(), ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get(), ModArmorItems.FROZEN_LITHERITE_BOOTS.get(),
                ModArmorItems.SMOLDERING_LITHERITE_HELMET.get(), ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE.get(), ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS.get(), ModArmorItems.SMOLDERING_LITHERITE_BOOTS.get(),
                ModArmorItems.FROSTBITTEN_LITHERITE_HELMET.get(), ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE.get(), ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS.get(), ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS.get(),
                ModArmorItems.INFUSED_LITHERITE_HELMET.get(), ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(), ModArmorItems.INFUSED_LITHERITE_BOOTS.get(),
                ModArmorItems.WITHERING_LITHERITE_HELMET.get(), ModArmorItems.WITHERING_LITHERITE_CHESTPLATE.get(), ModArmorItems.WITHERING_LITHERITE_LEGGINGS.get(), ModArmorItems.WITHERING_LITHERITE_BOOTS.get(),
                ModArmorItems.ODYSIUM_HELMET.get(), ModArmorItems.ODYSIUM_CHESTPLATE.get(), ModArmorItems.ODYSIUM_LEGGINGS.get(), ModArmorItems.ODYSIUM_BOOTS.get(),
                ModArmorItems.ENHANCED_ODYSIUM_HELMET.get(), ModArmorItems.ENHANCED_ODYSIUM_CHESTPLATE.get(), ModArmorItems.ENHANCED_ODYSIUM_LEGGINGS.get(), ModArmorItems.ENHANCED_ODYSIUM_BOOTS.get());

        LitherealClient.init();
    }
}