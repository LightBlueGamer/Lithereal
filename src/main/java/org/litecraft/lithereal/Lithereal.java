package org.litecraft.lithereal;

import com.mojang.logging.LogUtils;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.block.entity.InfusedLitheriteBlockEntity;
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
        @SubscribeEvent
        public static void itemRenderer(RegisterColorHandlersEvent.Item event) {
            event.register((itemStack, layer) -> layer == 0 ? PotionUtils.getColor(itemStack) : -1, ModItems.INFUSED_LITHERITE_CRYSTAL.get(), ModItems.INFUSED_LITHERITE_SWORD.get(), ModItems.INFUSED_LITHERITE_AXE.get(), ModItems.INFUSED_LITHERITE_PICKAXE.get(), ModItems.INFUSED_LITHERITE_SHOVEL.get(), ModItems.INFUSED_LITHERITE_HOE.get(), ModItems.INFUSED_LITHERITE_HAMMER.get(), ModItems.INFUSED_LITHERITE_HELMET.get(), ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModItems.INFUSED_LITHERITE_LEGGINGS.get(), ModItems.INFUSED_LITHERITE_BOOTS.get());
        }
        @SubscribeEvent
        public static void blockRenderer(RegisterColorHandlersEvent.Block event) {
            event.register((blockState, blockAndTintGetter, blockPos, i) -> {
                BlockEntity blockEntity = blockAndTintGetter.getBlockEntity(blockPos);
                if(blockEntity instanceof InfusedLitheriteBlockEntity infused)
                    return PotionUtils.getColor(infused.getStoredPotion());
                return i;
            }, ModBlocks.INFUSED_LITHERITE_BLOCK.get());
        }
    }
}