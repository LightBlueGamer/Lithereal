package org.lithereal;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.lithereal.block.*;
import org.lithereal.block.entity.*;
import org.lithereal.client.gui.screens.inventory.*;
import org.lithereal.item.WarHammerItem;

import java.nio.file.Path;
import java.util.function.Supplier;

public class LitherealExpectPlatform {
    @ExpectPlatform
    public static WoodType registerWoodType(WoodType woodType) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static RotatedPillarBlock strippableLog(Supplier<Block> stripped, BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<ElectricCrucibleBlockEntity> getElectricCrucibleBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ElectricCrucibleBlock getElectricCrucibleBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<ElectricCrucibleMenu> getElectricCrucibleMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<FireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static FireCrucibleBlock getFireCrucibleBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<FireCrucibleMenu> getFireCrucibleMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<FreezingStationBlockEntity> getFreezingStationBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static FreezingStationBlock getFreezingStationBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<FreezingStationMenu> getFreezingStationMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<InfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static InfusementChamberBlock getInfusementChamberBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<InfusementChamberMenu> getInfusementChamberMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getLitheriteItem() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<EtherCollectorBlockEntity> getEtherCollectorBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<EtherCollectorBlockEntity> getEtherCollectorBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EtherCollectorBlock getEtherCollectorBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<EtherCollectorMenu> getEtherCollectorMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<EtherBatteryBlockEntity> getEtherBatteryBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<EtherBatteryBlockEntity> getEtherBatteryBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EtherBatteryBlock getEtherBatteryBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<EtherBatteryMenu> getEtherBatteryMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<ChargingStationBlockEntity> getChargingStationBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<ChargingStationBlockEntity> getChargingStationBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ChargingStationBlock getChargingStationBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<ChargingStationMenu> getChargingStationMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WarHammerItem createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SimpleParticleType createSimpleParticleType(boolean alwaysSpawn) {
        throw new AssertionError();
    }

    @ExpectPlatform
    @Environment(EnvType.CLIENT)
    public static <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> type, ParticleProviderRegistry.DeferredParticleProvider<T> particleProvider) {
        throw new AssertionError();
    }
}