package org.lithereal.fabric;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.*;
import org.lithereal.fabric.client.gui.screens.inventory.*;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.fabric.world.block.FabricInfusementChamberBlock;
import org.lithereal.fabric.world.block.entity.*;
import org.lithereal.fabric.world.item.FabricItems;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.item.WarHammerItem;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class LitherealExpectPlatformImpl {
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static InfusedLitheriteArmorItem createInfusedLitheriteArmorItem(Holder<ArmorMaterial> armorMaterial, ArmorItem.Type type, int durability, Item.Properties properties) {
        return new InfusedLitheriteArmorItem(armorMaterial, type, durability, properties);
    }

    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        return (InfusedLitheriteBlock) FabricBlocks.INFUSED_LITHERITE_BLOCK;
    }

    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        return FabricBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY;
    }

    public static BlockEntityType<FabricFireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        return FabricBlockEntities.FIRE_CRUCIBLE_BLOCK_ENTITY;
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
        return (FireCrucibleBlock) FabricBlocks.FIRE_CRUCIBLE_BLOCK;
    }

    public static MenuType<FabricFireCrucibleMenu> getFireCrucibleMenu() {
        return FabricScreenHandlers.FIRE_CRUCIBLE_SCREEN_HANDLER;
    }

    public static BlockEntityType<FabricFreezingStationBlockEntity> getFreezingStationBlockEntity() {
        return FabricBlockEntities.FREEZING_STATION_BLOCK_ENTITY;
    }

    public static MenuType<FabricFreezingStationMenu> getFreezingStationMenu() {
        return FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER;
    }

    public static FreezingStationBlock getFreezingStationBlock() {
        return (FreezingStationBlock) FabricBlocks.FREEZING_STATION_BLOCK;
    }

    public static BlockEntityType<FabricInfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        return FabricBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY;
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
        return (InfusementChamberBlock) FabricBlocks.INFUSEMENT_CHAMBER_BLOCK;
    }

    public static Function<BlockBehaviour.Properties, InfusementChamberBlock> getInfusementChamberBlockFactory() {
        return FabricInfusementChamberBlock::new;
    }

    public static MenuType<FabricInfusementChamberMenu> getInfusementChamberMenu() {
        return FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER;
    }

    public static Item getLitheriteItem() {
        return FabricItems.LITHERITE_CRYSTAL;
    }

    public static WarHammerItem createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        return new WarHammerItem(tier, damage, speed, properties);
    }

    public static SimpleParticleType createSimpleParticleType(boolean alwaysSpawn) {
        return FabricParticleTypes.simple(alwaysSpawn);
    }

    @Environment(EnvType.CLIENT)
    public static <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> type, ParticleProviderRegistry.DeferredParticleProvider<T> particleProvider) {
        ParticleFactoryRegistry.getInstance().register(type, (provider) -> particleProvider.create(new ParticleProviderRegistry.ExtendedSpriteSet() {
            @Override
            public TextureAtlas getAtlas() {
                return provider.getAtlas();
            }

            @Override
            public List<TextureAtlasSprite> getSprites() {
                return provider.getSprites();
            }

            @Override
            public TextureAtlasSprite get(int i, int j) {
                return provider.get(i, j);
            }

            @Override
            public TextureAtlasSprite get(RandomSource randomSource) {
                return provider.get(randomSource);
            }
        }));
    }
}
