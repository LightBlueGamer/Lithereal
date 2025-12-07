package org.lithereal.neoforge;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLPaths;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.*;
import org.lithereal.block.entity.EtherCollectorBlockEntity;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.item.WarHammerItem;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.neoforge.world.block.ForgeInfusementChamberBlock;
import org.lithereal.neoforge.world.block.ForgeStrippableLogBlock;
import org.lithereal.neoforge.world.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.world.block.entity.ForgeFireCrucibleBlockEntity;
import org.lithereal.neoforge.world.block.entity.ForgeFreezingStationBlockEntity;
import org.lithereal.neoforge.world.block.entity.ForgeInfusementChamberBlockEntity;
import org.lithereal.neoforge.world.item.ForgeItems;
import org.lithereal.neoforge.world.item.ForgeWarHammerItem;
import org.lithereal.neoforge.client.gui.screens.inventory.ForgeMenuTypes;
import org.lithereal.client.gui.screens.inventory.EtherCollectorMenu;
import org.lithereal.client.gui.screens.inventory.FireCrucibleMenu;
import org.lithereal.client.gui.screens.inventory.FreezingStationMenu;
import org.lithereal.client.gui.screens.inventory.InfusementChamberMenu;
import org.lithereal.neoforge.world.item.infused.ForgeInfusedLitheriteArmorItem;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Supplier;

public class LitherealExpectPlatformImpl {
    public static RotatedPillarBlock strippableLog(Supplier<Block> stripped, BlockBehaviour.Properties properties) {
        return new ForgeStrippableLogBlock(stripped.get(), properties);
    }
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static InfusedLitheriteArmorItem createInfusedLitheriteArmorItem(Holder<ArmorMaterial> armorMaterial, ArmorItem.Type type, int durability, Item.Properties properties) {
        return new ForgeInfusedLitheriteArmorItem(armorMaterial, type, durability, properties);
    }

    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        return ForgeBlocks.INFUSED_LITHERITE_BLOCK.get();
    }

    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        return ForgeBlockEntities.INFUSED_LITHERITE_BLOCK.get();
    }

    public static BlockEntityType<ForgeFireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        return ForgeBlockEntities.FIRE_CRUCIBLE.get();
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
        return ForgeBlocks.FIRE_CRUCIBLE.get();
    }

    public static MenuType<FireCrucibleMenu> getFireCrucibleMenu() {
        return ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get();
    }

    public static BlockEntityType<ForgeFreezingStationBlockEntity> getFreezingStationBlockEntity() {
        return ForgeBlockEntities.FREEZING_STATION.get();
    }

    public static MenuType<FreezingStationMenu> getFreezingStationMenu() {
        return ForgeMenuTypes.FREEZING_STATION_MENU.get();
    }

    public static FreezingStationBlock getFreezingStationBlock() {
        return ForgeBlocks.FREEZING_STATION.get();
    }

    public static BlockEntityType<ForgeInfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        return ForgeBlockEntities.INFUSEMENT_CHAMBER.get();
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
        return ForgeBlocks.INFUSEMENT_CHAMBER.get();
    }

    public static Function<BlockBehaviour.Properties, InfusementChamberBlock> getInfusementChamberBlockFactory() {
        return ForgeInfusementChamberBlock::new;
    }

    public static MenuType<InfusementChamberMenu> getInfusementChamberMenu() {
        return ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get();
    }

    public static Item getLitheriteItem() {
        return ForgeItems.LITHERITE_CRYSTAL.get();
    }

    public static BlockEntityType<EtherCollectorBlockEntity> getEtherCollectorBlockEntity() {
        return null;
    }

    public static BlockEntityTicker<EtherCollectorBlockEntity> getEtherCollectorBlockEntityTicker() {
        return null;
    }

    public static EtherCollectorBlock getEtherCollectorBlock() {
        return null;
    }

    public static MenuType<EtherCollectorMenu> getEtherCollectorMenu() {
        return null;
    }

    public static WarHammerItem createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        return new ForgeWarHammerItem(tier, damage, speed, properties);
    }

    public static SimpleParticleType createSimpleParticleType(boolean alwaysSpawn) {
        return new SimpleParticleType(alwaysSpawn);
    }

    @OnlyIn(Dist.CLIENT)
    public static <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> type, ParticleProviderRegistry.DeferredParticleProvider<T> particleProvider) {
        // Ignore
    }
}
