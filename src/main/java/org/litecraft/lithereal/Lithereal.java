package org.litecraft.lithereal;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.block.entity.ModBlockEntities;
import org.litecraft.lithereal.item.ModCreativeModeTabs;
import org.litecraft.lithereal.item.ModItems;
import org.litecraft.lithereal.recipe.ModRecipes;
import org.litecraft.lithereal.screen.FireCrucibleScreen;
import org.litecraft.lithereal.screen.FreezingStationScreen;
import org.litecraft.lithereal.screen.InfusementChamberScreen;
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

        ModCreativeModeTabs.register(modEventBus);

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
            MenuScreens.register(ModMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ModEventSubscriber {
        @SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
            if (!event.getLevel().isClientSide()) {
                Entity entity = event.getEntity();
                if (entity instanceof ItemEntity itemEntity) {
                    ItemStack itemStack = itemEntity.getItem();
                    if (itemStack.getItem() == ModItems.GLOWING_LITHERITE_CRYSTAL.get()) {
                        itemEntity.setGlowingTag(true);
                    }
                }
            }
        }
    }


}