package com.haru.lithereal;

import com.haru.lithereal.block.ModBlocks;
import com.haru.lithereal.block.entity.ModBlockEntities;
import com.haru.lithereal.item.ModItems;
import com.haru.lithereal.recipe.ModRecipes;
import com.haru.lithereal.screen.FireCrucibleScreen;
import com.haru.lithereal.screen.FreezingStationScreen;
import com.haru.lithereal.screen.ModMenuTypes;
import com.haru.lithereal.worldgen.ModConfiguredFeatures;
import com.haru.lithereal.worldgen.ModPlacedFeatures;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Lithereal.MOD_ID)
public class Lithereal {
    public static final String MOD_ID = "lithereal";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Lithereal() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
            MenuScreens.register(ModMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
        }
    }


}