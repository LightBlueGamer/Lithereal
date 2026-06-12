package org.lithereal.client.gui.screens.inventory;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
//? fabric {
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
//?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.*;
//? neoforge {
/*import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
*///?}

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Lithereal.MOD_ID, Registries.MENU);

    //? fabric {
    public static final RegistrySupplier<MenuType<ElectricCrucibleMenu>> ELECTRIC_CRUCIBLE_MENU = MENU_TYPES.register("electric_crucible_menu", () -> new ExtendedMenuType<>(ElectricCrucibleMenu::new, BlockPos.STREAM_CODEC));
    //?}
    //? neoforge {
    /*public static final RegistrySupplier<MenuType<ElectricCrucibleMenu>> ELECTRIC_CRUCIBLE_MENU = MENU_TYPES.register("electric_crucible_menu", () -> IMenuTypeExtension.create(ElectricCrucibleMenu::new));
    *///?}

    //? fabric {
    public static final RegistrySupplier<MenuType<FireCrucibleMenu>> FIRE_CRUCIBLE_MENU = MENU_TYPES.register("fire_crucible_menu", () -> new ExtendedMenuType<>(FireCrucibleMenu::new, BlockPos.STREAM_CODEC));
    //?}
    //? neoforge {
    /*public static final RegistrySupplier<MenuType<FireCrucibleMenu>> FIRE_CRUCIBLE_MENU = MENU_TYPES.register("fire_crucible_menu", () -> IMenuTypeExtension.create(FireCrucibleMenu::new));
     *///?}

    //? fabric {
    public static final RegistrySupplier<MenuType<FreezingStationMenu>> FREEZING_STATION_MENU = MENU_TYPES.register("freezing_station_menu", () -> new ExtendedMenuType<>(FreezingStationMenu::new, BlockPos.STREAM_CODEC));
    //?}
    //? neoforge {
    /*public static final RegistrySupplier<MenuType<FreezingStationMenu>> FREEZING_STATION_MENU = MENU_TYPES.register("freezing_station_menu", () -> IMenuTypeExtension.create(FreezingStationMenu::new));
     *///?}

    //? fabric {
    public static final RegistrySupplier<MenuType<InfusementChamberMenu>> INFUSEMENT_CHAMBER_MENU = MENU_TYPES.register("infusement_chamber_menu", () -> new ExtendedMenuType<>(InfusementChamberMenu::new, BlockPos.STREAM_CODEC));
    //?}
    //? neoforge {
    /*public static final RegistrySupplier<MenuType<InfusementChamberMenu>> INFUSEMENT_CHAMBER_MENU = MENU_TYPES.register("infusement_chamber_menu", () -> IMenuTypeExtension.create(InfusementChamberMenu::new));
     *///?}

    public static void register() {
        MENU_TYPES.register();
    }
}