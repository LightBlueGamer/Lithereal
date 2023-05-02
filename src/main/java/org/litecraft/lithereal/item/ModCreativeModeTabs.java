package org.litecraft.lithereal.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.litecraft.lithereal.Lithereal;

@Mod.EventBusSubscriber(modid = Lithereal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab LITHEREAL_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        LITHEREAL_TAB = event.registerCreativeModeTab(new ResourceLocation(Lithereal.MOD_ID, "lithereal_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.LITHERITE_CRYSTAL.get()))
                        .title(Component.translatable("creativemodetab.lithereal_tab")));
    }
}