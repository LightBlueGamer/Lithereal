package org.lithereal.forge.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lithereal.Lithereal;

public class ForgeMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lithereal.MOD_ID);

    public static final RegistryObject<MenuType<ForgeFireCrucibleMenu>> FIRE_CRUCIBLE_MENU =
            registerMenuType(ForgeFireCrucibleMenu::new, "fire_crucible_menu");

    public static final RegistryObject<MenuType<ForgeFreezingStationMenu>> FREEZING_STATION_MENU =
            registerMenuType(ForgeFreezingStationMenu::new, "freezing_station_menu");

    public static final RegistryObject<MenuType<ForgeInfusementChamberMenu>> INFUSEMENT_CHAMBER_MENU =
            registerMenuType(ForgeInfusementChamberMenu::new, "infusement_chamber_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus) ;
    }
}
