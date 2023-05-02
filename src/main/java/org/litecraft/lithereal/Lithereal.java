package org.litecraft.lithereal;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModCreativeModeTabs;
import org.litecraft.lithereal.item.ModItems;
import org.slf4j.Logger;

import java.util.Iterator;

@Mod(Lithereal.MOD_ID)
public class Lithereal {
    public static final String MOD_ID = "lithereal";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Lithereal() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == ModCreativeModeTabs.LITHEREAL_TAB) {
            event.accept(ModItems.LITHERITE_CRYSTAL);
            event.accept(ModItems.HEATED_LITHERITE_CRYSTAL);
            event.accept(ModItems.COOLED_LITHERITE_CRYSTAL);
            event.accept(ModItems.COOLED_LITHERITE_SWORD);
            event.accept(ModItems.HEATED_LITHERITE_SWORD);
            event.accept(ModItems.LITHERITE_SWORD);
            event.accept(ModItems.COOLED_LITHERITE_PICKAXE);
            event.accept(ModItems.HEATED_LITHERITE_PICKAXE);
            event.accept(ModItems.LITHERITE_PICKAXE);
            event.accept(ModItems.COOLED_LITHERITE_AXE);
            event.accept(ModItems.HEATED_LITHERITE_AXE);
            event.accept(ModItems.LITHERITE_AXE);
            event.accept(ModItems.COOLED_LITHERITE_SHOVEL);
            event.accept(ModItems.HEATED_LITHERITE_SHOVEL);
            event.accept(ModItems.LITHERITE_SHOVEL);
            event.accept(ModItems.COOLED_LITHERITE_HOE);
            event.accept(ModItems.HEATED_LITHERITE_HOE);
            event.accept(ModItems.LITHERITE_HOE);
            event.accept(ModItems.LITHERITE_HELMET);
            event.accept(ModItems.LITHERITE_CHESTPLATE);
            event.accept(ModItems.LITHERITE_LEGGINGS);
            event.accept(ModItems.LITHERITE_BOOTS);
            event.accept(ModItems.COOLED_LITHERITE_HELMET);
            event.accept(ModItems.COOLED_LITHERITE_CHESTPLATE);
            event.accept(ModItems.COOLED_LITHERITE_LEGGINGS);
            event.accept(ModItems.COOLED_LITHERITE_BOOTS);
            event.accept(ModItems.HEATED_LITHERITE_HELMET);
            event.accept(ModItems.HEATED_LITHERITE_CHESTPLATE);
            event.accept(ModItems.HEATED_LITHERITE_LEGGINGS);
            event.accept(ModItems.HEATED_LITHERITE_BOOTS);

            event.accept(ModBlocks.LITHERITE_ORE);
            event.accept(ModBlocks.DEEPSLATE_LITHERITE_ORE);
            event.accept(ModBlocks.LITHERITE_BLOCK);
            event.accept(ModBlocks.COOLED_LITHERITE_BLOCK);
            event.accept(ModBlocks.HEATED_LITHERITE_BLOCK);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}