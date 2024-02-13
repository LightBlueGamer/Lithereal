package org.lithereal.fabric.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import org.lithereal.Lithereal;

public class FabricScreenHandlers {

    public static final MenuType<FabricFireCrucibleMenu> FIRE_CRUCIBLE_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Lithereal.MOD_ID, "fire_crucible"),
                    new ExtendedScreenHandlerType<>(FabricFireCrucibleMenu::new));

    public static final MenuType<FabricFreezingStationMenu> FREEZING_STATION_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Lithereal.MOD_ID, "freezing_station"),
                    new ExtendedScreenHandlerType<>(FabricFreezingStationMenu::new));

    public static final MenuType<FabricInfusementChamberMenu> INFUSEMENT_CHAMBER_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Lithereal.MOD_ID, "infusement_chamber"),
                    new ExtendedScreenHandlerType<>(FabricInfusementChamberMenu::new));

    public static final MenuType<FabricLitherCollectorMenu> LITHER_COLLECTOR_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Lithereal.MOD_ID, "lither_collector"),
                    new ExtendedScreenHandlerType<>(FabricLitherCollectorMenu::new));

    public static final MenuType<FabricLitherBatteryMenu> LITHER_BATTERY_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Lithereal.MOD_ID, "lither_battery"),
                    new ExtendedScreenHandlerType<>(FabricLitherBatteryMenu::new));

    public static void registerScreenHandlers() {
    }
}