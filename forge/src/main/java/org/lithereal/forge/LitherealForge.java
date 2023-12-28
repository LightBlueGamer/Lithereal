package org.lithereal.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lithereal.Lithereal;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lithereal.forge.block.ForgeBlocks;
import org.lithereal.forge.block.entities.ForgeBlockEntities;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.forge.screen.ForgeMenuTypes;
import org.lithereal.screen.FireCrucibleScreen;
import org.lithereal.screen.FreezingStationScreen;
import org.lithereal.screen.InfusementChamberScreen;

@Mod(Lithereal.MOD_ID)
public class LitherealForge {
    public LitherealForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Lithereal.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

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
            MenuScreens.register(ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
            MenuScreens.register(ForgeMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
            MenuScreens.register(ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);
        }
    }
}
