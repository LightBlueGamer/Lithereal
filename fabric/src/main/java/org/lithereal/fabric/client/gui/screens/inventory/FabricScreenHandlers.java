package org.lithereal.fabric.client.gui.screens.inventory;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;

public class FabricScreenHandlers {
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockPos> BLOCK_POS = new StreamCodec<>() {
        @Override
        public void encode(RegistryFriendlyByteBuf object, BlockPos pos) {
            object.writeBlockPos(pos);
        }

        @Override
        public @NotNull BlockPos decode(RegistryFriendlyByteBuf object) {
            return object.readBlockPos();
        }
    };

    public static final MenuType<FabricFireCrucibleMenu> FIRE_CRUCIBLE_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "fire_crucible"),
                    new ExtendedScreenHandlerType<>(FabricFireCrucibleMenu::new, BLOCK_POS));

    public static final MenuType<FabricFreezingStationMenu> FREEZING_STATION_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "freezing_station"),
                    new ExtendedScreenHandlerType<>(FabricFreezingStationMenu::new, BLOCK_POS));

    public static final MenuType<FabricInfusementChamberMenu> INFUSEMENT_CHAMBER_SCREEN_HANDLER =
            Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "infusement_chamber"),
                    new ExtendedScreenHandlerType<>(FabricInfusementChamberMenu::new, BLOCK_POS));

    public static void registerScreenHandlers() {
    }
}