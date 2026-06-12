package org.lithereal.fabric;

//? fabric {
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.lithereal.LitherealClient;
import org.lithereal.client.KeyMapping;
import org.lithereal.client.gui.screens.inventory.*;
import org.lithereal.client.particle.*;
import org.lithereal.client.renderer.zombie.PhantomDrownedRenderer;
import org.lithereal.client.renderer.zombie.PhantomZombieRenderer;
import org.lithereal.fabric.client.armor.LitheriteArmorRenderer;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModArmorItems;

import java.util.List;

public class LitherealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHANTOM_ZOMBIE.get(), PhantomZombieRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHANTOM_DROWNED.get(), PhantomDrownedRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHANTOM_OAK_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.PHANTOM_OAK_BOAT_LAYER));
        EntityRendererRegistry.register(ModEntities.PHANTOM_OAK_CHEST_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.PHANTOM_OAK_CHEST_BOAT_LAYER));
        EntityRendererRegistry.register(ModEntities.FORTSHROOM_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.FORTSHROOM_BOAT_LAYER));
        EntityRendererRegistry.register(ModEntities.FORTSHROOM_CHEST_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.FORTSHROOM_CHEST_BOAT_LAYER));
        EntityRendererRegistry.register(ModEntities.MALISHROOM_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.MALISHROOM_BOAT_LAYER));
        EntityRendererRegistry.register(ModEntities.MALISHROOM_CHEST_BOAT.get(), pContext -> new BoatRenderer(pContext, LitherealClient.MALISHROOM_CHEST_BOAT_LAYER));
        EntityRendererRegistry.register(ModEntities.RIFT_SPAWNER.get(), NoopRenderer::new);

        MenuScreens.register(ModMenuTypes.ELECTRIC_CRUCIBLE_MENU.get(), ElectricCrucibleScreen::new);
        MenuScreens.register(ModMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
        MenuScreens.register(ModMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
        MenuScreens.register(ModMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);

        ArmorRenderer.register(LitheriteArmorRenderer::new, ModArmorItems.LITHERITE_HELMET.get(), ModArmorItems.LITHERITE_CHESTPLATE.get(), ModArmorItems.LITHERITE_LEGGINGS.get(), ModArmorItems.LITHERITE_BOOTS.get(),
                ModArmorItems.BURNING_LITHERITE_HELMET.get(), ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get(), ModArmorItems.BURNING_LITHERITE_LEGGINGS.get(), ModArmorItems.BURNING_LITHERITE_BOOTS.get(),
                ModArmorItems.FROZEN_LITHERITE_HELMET.get(), ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get(), ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get(), ModArmorItems.FROZEN_LITHERITE_BOOTS.get(),
                ModArmorItems.SMOLDERING_LITHERITE_HELMET.get(), ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE.get(), ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS.get(), ModArmorItems.SMOLDERING_LITHERITE_BOOTS.get(),
                ModArmorItems.FROSTBITTEN_LITHERITE_HELMET.get(), ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE.get(), ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS.get(), ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS.get(),
                ModArmorItems.INFUSED_LITHERITE_HELMET.get(), ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(), ModArmorItems.INFUSED_LITHERITE_BOOTS.get(),
                ModArmorItems.WITHERING_LITHERITE_HELMET.get(), ModArmorItems.WITHERING_LITHERITE_CHESTPLATE.get(), ModArmorItems.WITHERING_LITHERITE_LEGGINGS.get(), ModArmorItems.WITHERING_LITHERITE_BOOTS.get(),
                ModArmorItems.ODYSIUM_HELMET.get(), ModArmorItems.ODYSIUM_CHESTPLATE.get(), ModArmorItems.ODYSIUM_LEGGINGS.get(), ModArmorItems.ODYSIUM_BOOTS.get(),
                ModArmorItems.ENHANCED_ODYSIUM_HELMET.get(), ModArmorItems.ENHANCED_ODYSIUM_CHESTPLATE.get(), ModArmorItems.ENHANCED_ODYSIUM_LEGGINGS.get(), ModArmorItems.ENHANCED_ODYSIUM_BOOTS.get());

        registerParticleProvider(ModParticles.PHANTOM_OAK_LEAVES.get(), FallingLeavesParticle.PaleOakProvider::new);
        registerParticleProvider(ModParticles.LITHER_FIRE_FLAME.get(), FlameParticle.Provider::new);
        registerParticleProvider(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
        registerParticleProvider(ModParticles.SOUL.get(), EtherealSoulProvider::new);
        registerParticleProvider(ModParticles.RETRIBUTION_HOLY_BEAM.get(), BeamParticle.RetributionBeamProvider::new);
        registerParticleProvider(ModParticles.RETRIBUTION_LIGHT_BURST.get(), BeamParticle.RetributionBurstProvider::new);
        registerParticleProvider(ModParticles.CRYSTAL_SPARKLE.get(), StandardBiomeProvider::new);
        registerParticleProvider(ModParticles.PORTAL_SPARKLE.get(), FlameParticle.Provider::new);
        registerParticleProvider(ModParticles.PORTAL_EMISSION.get(), PortalParticleProvider::new);
        LitherealClient.init();
        ClientLifecycleEvents.CLIENT_STARTED.register(LitherealClient::onClientStarted);

        KeyMappingHelper.registerKeyMapping(KeyMapping.FREEZE_KEY);
        KeyMappingHelper.registerKeyMapping(KeyMapping.SCORCH_KEY);
    }
    @Environment(EnvType.CLIENT)
    public static <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> type, ParticleProviderRegistry.DeferredParticleProvider<T> particleProvider) {
        net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry.getInstance().register(type, (provider) -> particleProvider.create(new ParticleProviderRegistry.ExtendedSpriteSet() {
            @Override
            public TextureAtlas getAtlas() {
                return provider.getAtlas();
            }

            @Override
            public List<TextureAtlasSprite> getSprites() {
                return provider.getSprites();
            }

            @Override
            public @NotNull TextureAtlasSprite get(int i, int j) {
                return provider.get(i, j);
            }

            @Override
            public @NotNull TextureAtlasSprite get(RandomSource randomSource) {
                return provider.get(randomSource);
            }

            @Override
            public TextureAtlasSprite first() {
                return provider.first();
            }
        }));
    }
}
//?}