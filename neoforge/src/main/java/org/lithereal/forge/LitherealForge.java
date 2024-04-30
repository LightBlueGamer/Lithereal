package org.lithereal.forge;

import dev.architectury.platform.hooks.EventBusesHooks;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lithereal.Lithereal;
import org.lithereal.LitherealClient;
import org.lithereal.forge.block.ForgeBlocks;
import org.lithereal.forge.block.entity.ForgeBlockEntities;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.forge.screen.ForgeMenuTypes;
import org.lithereal.screen.FireCrucibleScreen;
import org.lithereal.screen.FreezingStationScreen;
import org.lithereal.screen.InfusementChamberScreen;

@Mod(Lithereal.MOD_ID)
public class LitherealForge {
    public LitherealForge(IEventBus modEventBus) {
        ForgeBlocks.register(modEventBus);
        ForgeBlockEntities.register(modEventBus);
        ForgeItems.register(modEventBus);
        ForgeMenuTypes.register(modEventBus);

        Lithereal.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(modid = Lithereal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LitherealClient.init();
            MenuScreens.register(ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
            MenuScreens.register(ForgeMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
            MenuScreens.register(ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);
        }
    }
}
