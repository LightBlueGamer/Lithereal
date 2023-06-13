package org.litecraft.lithereal;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;
import org.litecraft.lithereal.block.entity.ModBlockEntities;
import org.litecraft.lithereal.item.ModCreativeModeTabs;
import org.litecraft.lithereal.recipe.ModRecipes;
import org.litecraft.lithereal.screen.FireCrucibleScreen;
import org.litecraft.lithereal.screen.FreezingStationScreen;
import org.litecraft.lithereal.screen.ModMenuTypes;
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

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == ModCreativeModeTabs.LITHEREAL_TAB) {
            event.accept(ModBlocks.LITHERITE_BLOCK);
            event.accept(ModItems.LITHERITE_CRYSTAL);
            event.accept(ModItems.LITHERITE_SWORD);
            event.accept(ModItems.LITHERITE_SHOVEL);
            event.accept(ModItems.LITHERITE_PICKAXE);
            event.accept(ModItems.LITHERITE_AXE);
            event.accept(ModItems.LITHERITE_HOE);
            event.accept(ModItems.LITHERITE_HAMMER);
            event.accept(ModItems.LITHERITE_HELMET);
            event.accept(ModItems.LITHERITE_CHESTPLATE);
            event.accept(ModItems.LITHERITE_LEGGINGS);
            event.accept(ModItems.LITHERITE_BOOTS);

            event.accept(ModBlocks.HEATED_LITHERITE_BLOCK);
            event.accept(ModItems.HEATED_LITHERITE_CRYSTAL);
            event.accept(ModItems.HEATED_LITHERITE_SWORD);
            event.accept(ModItems.HEATED_LITHERITE_SHOVEL);
            event.accept(ModItems.HEATED_LITHERITE_PICKAXE);
            event.accept(ModItems.HEATED_LITHERITE_AXE);
            event.accept(ModItems.HEATED_LITHERITE_HOE);
            event.accept(ModItems.BURNING_LITHERITE_HAMMER);
            event.accept(ModItems.HEATED_LITHERITE_HELMET);
            event.accept(ModItems.HEATED_LITHERITE_CHESTPLATE);
            event.accept(ModItems.HEATED_LITHERITE_LEGGINGS);
            event.accept(ModItems.HEATED_LITHERITE_BOOTS);

            event.accept(ModBlocks.COOLED_LITHERITE_BLOCK);
            event.accept(ModItems.COOLED_LITHERITE_CRYSTAL);
            event.accept(ModItems.COOLED_LITHERITE_SWORD);
            event.accept(ModItems.COOLED_LITHERITE_SHOVEL);
            event.accept(ModItems.COOLED_LITHERITE_PICKAXE);
            event.accept(ModItems.COOLED_LITHERITE_AXE);
            event.accept(ModItems.COOLED_LITHERITE_HOE);
            event.accept(ModItems.FROZEN_LITHERITE_HAMMER);
            event.accept(ModItems.COOLED_LITHERITE_HELMET);
            event.accept(ModItems.COOLED_LITHERITE_CHESTPLATE);
            event.accept(ModItems.COOLED_LITHERITE_LEGGINGS);
            event.accept(ModItems.COOLED_LITHERITE_BOOTS);

            event.accept(ModBlocks.WITHERING_LITHERITE_BLOCK);
            event.accept(ModItems.WITHERING_LITHERITE_CRYSTAL);
            event.accept(ModItems.WITHERING_LITHERITE_SWORD);
            event.accept(ModItems.WITHERING_LITHERITE_SHOVEL);
            event.accept(ModItems.WITHERING_LITHERITE_PICKAXE);
            event.accept(ModItems.WITHERING_LITHERITE_AXE);
            event.accept(ModItems.WITHERING_LITHERITE_HOE);
            event.accept(ModItems.WITHERING_LITHERITE_HAMMER);
            event.accept(ModItems.WITHERING_LITHERITE_HELMET);
            event.accept(ModItems.WITHERING_LITHERITE_CHESTPLATE);
            event.accept(ModItems.WITHERING_LITHERITE_LEGGINGS);
            event.accept(ModItems.WITHERING_LITHERITE_BOOTS);

            event.accept(ModBlocks.LITHERITE_ORE);
            event.accept(ModBlocks.DEEPSLATE_LITHERITE_ORE);

            event.accept(ModBlocks.FREEZING_STATION);
            event.accept(ModBlocks.FIRE_CRUCIBLE);
        }
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