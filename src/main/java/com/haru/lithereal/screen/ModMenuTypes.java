package com.haru.lithereal.screen;

import com.haru.lithereal.Lithereal;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lithereal.MOD_ID);

    public static final RegistryObject<MenuType<FreezingStationMenu>> FREEZING_STATION_MENU =
            registerMenuType(FreezingStationMenu::new, "freezing_station_menu");

    public static final RegistryObject<MenuType<FireCrucibleMenu>> FIRE_CRUCIBLE_MENU =
            registerMenuType(FireCrucibleMenu::new, "fire_crucible_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus) ;
    }
}
