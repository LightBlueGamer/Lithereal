package org.litecraft.lithereal.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;

@Mod.EventBusSubscriber(modid = Lithereal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Lithereal.MOD_ID);

    public static RegistryObject<CreativeModeTab> LITHEREAL_TAB = CREATIVE_MODE_TABS.register("lithereal_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LITHERITE_CRYSTAL.get()))
                    .title(Component.translatable("creativemodetab.lithereal_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.LITHERITE_CRYSTAL.get());
                        output.accept(ModItems.HEATED_LITHERITE_CRYSTAL.get());
                        output.accept(ModItems.COOLED_LITHERITE_CRYSTAL.get());
                        output.accept(ModItems.COOLED_LITHERITE_SWORD.get());
                        output.accept(ModItems.HEATED_LITHERITE_SWORD.get());
                        output.accept(ModItems.LITHERITE_SWORD.get());
                        output.accept(ModItems.COOLED_LITHERITE_PICKAXE.get());
                        output.accept(ModItems.HEATED_LITHERITE_PICKAXE.get());
                        output.accept(ModItems.LITHERITE_PICKAXE.get());
                        output.accept(ModItems.FROZEN_LITHERITE_HAMMER.get());
                        output.accept(ModItems.BURNING_LITHERITE_HAMMER.get());
                        output.accept(ModItems.LITHERITE_HAMMER.get());
                        output.accept(ModItems.COOLED_LITHERITE_AXE.get());
                        output.accept(ModItems.HEATED_LITHERITE_AXE.get());
                        output.accept(ModItems.LITHERITE_AXE.get());
                        output.accept(ModItems.COOLED_LITHERITE_SHOVEL.get());
                        output.accept(ModItems.HEATED_LITHERITE_SHOVEL.get());
                        output.accept(ModItems.LITHERITE_SHOVEL.get());
                        output.accept(ModItems.COOLED_LITHERITE_HOE.get());
                        output.accept(ModItems.HEATED_LITHERITE_HOE.get());
                        output.accept(ModItems.LITHERITE_HOE.get());
                        output.accept(ModItems.LITHERITE_HELMET.get());
                        output.accept(ModItems.LITHERITE_CHESTPLATE.get());
                        output.accept(ModItems.LITHERITE_LEGGINGS.get());
                        output.accept(ModItems.LITHERITE_BOOTS.get());
                        output.accept(ModItems.COOLED_LITHERITE_HELMET.get());
                        output.accept(ModItems.COOLED_LITHERITE_CHESTPLATE.get());
                        output.accept(ModItems.COOLED_LITHERITE_LEGGINGS.get());
                        output.accept(ModItems.COOLED_LITHERITE_BOOTS.get());
                        output.accept(ModItems.HEATED_LITHERITE_HELMET.get());
                        output.accept(ModItems.HEATED_LITHERITE_CHESTPLATE.get());
                        output.accept(ModItems.HEATED_LITHERITE_LEGGINGS.get());
                        output.accept(ModItems.HEATED_LITHERITE_BOOTS.get());

                        output.accept(ModBlocks.LITHERITE_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_LITHERITE_ORE.get());
                        output.accept(ModBlocks.LITHERITE_BLOCK.get());
                        output.accept(ModBlocks.COOLED_LITHERITE_BLOCK.get());
                        output.accept(ModBlocks.HEATED_LITHERITE_BLOCK.get());
                        output.accept(ModBlocks.FREEZING_STATION.get());
                        output.accept(ModBlocks.FIRE_CRUCIBLE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}