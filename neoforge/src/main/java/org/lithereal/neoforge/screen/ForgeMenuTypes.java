package org.lithereal.neoforge.screen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lithereal.Lithereal;
import org.lithereal.screen.FireCrucibleMenu;
import org.lithereal.screen.FreezingStationMenu;
import org.lithereal.screen.InfusementChamberMenu;

public class ForgeMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, Lithereal.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<FireCrucibleMenu>> FIRE_CRUCIBLE_MENU =
            registerMenuType(ForgeFireCrucibleMenu::create, "fire_crucible_menu");

    public static final DeferredHolder<MenuType<?>, MenuType<FreezingStationMenu>> FREEZING_STATION_MENU =
            registerMenuType(ForgeFreezingStationMenu::create, "freezing_station_menu");

    public static final DeferredHolder<MenuType<?>, MenuType<InfusementChamberMenu>> INFUSEMENT_CHAMBER_MENU =
            registerMenuType(ForgeInfusementChamberMenu::create, "infusement_chamber_menu");

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
