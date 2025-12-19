package org.lithereal.neoforge.client.event;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.LitherealClient;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.EtherealCoreSpecialEffects;
import org.lithereal.client.gui.screens.inventory.ElectricCrucibleScreen;
import org.lithereal.client.particle.*;
import org.lithereal.client.renderer.*;
import org.lithereal.client.renderer.zombie.BetterZombieModel;
import org.lithereal.client.renderer.zombie.PhantomDrownedRenderer;
import org.lithereal.client.renderer.zombie.PhantomZombieRenderer;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModArmorItems;
import org.lithereal.neoforge.world.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.client.gui.screens.inventory.ForgeMenuTypes;
import org.lithereal.client.gui.screens.inventory.FireCrucibleScreen;
import org.lithereal.client.gui.screens.inventory.FreezingStationScreen;
import org.lithereal.client.gui.screens.inventory.InfusementChamberScreen;

public class ClientEvents {
    public static class ClientModBusEvents {
        public static void register(IEventBus bus) {
            bus.register(ClientModBusEvents.class);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LitherealClient.init();
        }
        @SubscribeEvent
        public static void onDimensionSpecialEffectsRegister(RegisterDimensionSpecialEffectsEvent event) {
            event.register(Lithereal.id("ethereal_core"), new EtherealCoreSpecialEffects());
        }
        @SubscribeEvent
        public static void onParticleProviderRegister(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.LITHER_FIRE_FLAME.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.SOUL.get(), EtherealSoulProvider::new);
            event.registerSpriteSet(ModParticles.RETRIBUTION_HOLY_BEAM.get(), BeamParticle.RetributionBeamProvider::new);
            event.registerSpriteSet(ModParticles.RETRIBUTION_LIGHT_BURST.get(), BeamParticle.RetributionBurstProvider::new);
            event.registerSpriteSet(ModParticles.CRYSTAL_SPARKLE.get(), StandardBiomeProvider::new);
            event.registerSpriteSet(ModParticles.PORTAL_SPARKLE.get(), FlameParticle.Provider::new);
            event.registerSpriteSet(ModParticles.PORTAL_EMISSION.get(), PortalParticleProvider::new);
        }
        @SubscribeEvent
        public static void onRegisterMenu(RegisterMenuScreensEvent event) {
            event.register(ForgeMenuTypes.ELECTRIC_CRUCIBLE_MENU.get(), ElectricCrucibleScreen::new);
            event.register(ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get(), FireCrucibleScreen::new);
            event.register(ForgeMenuTypes.FREEZING_STATION_MENU.get(), FreezingStationScreen::new);
            event.register(ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get(), InfusementChamberScreen::new);
        }
        @SubscribeEvent
        public static void modelLayerLocationInit(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
            event.registerLayerDefinition(InfusementChamberBlockEntityModel.LAYER_LOCATION, InfusementChamberBlockEntityModel::createBodyLayer);
            event.registerLayerDefinition(BetterZombieModel.ZOMBIE, () -> BetterZombieModel.createBodyLayer(CubeDeformation.NONE));
            event.registerLayerDefinition(BetterZombieModel.ZOMBIE_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(1.0F)), 64, 32));
            event.registerLayerDefinition(BetterZombieModel.ZOMBIE_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.5F)), 64, 32));
            event.registerLayerDefinition(LitherealArmorModel.OUTER_ARMOR, () -> LayerDefinition.create(LitherealArmorModel.createBodyLayer(new CubeDeformation(1.0F), 0), 64, 32));
            event.registerLayerDefinition(LitherealArmorModel.INNER_ARMOR, () -> LayerDefinition.create(LitherealArmorModel.createBodyLayer(new CubeDeformation(0.5F), 0), 64, 32));
            event.registerLayerDefinition(ModBoatRenderer.PHANTOM_OAK_BOAT_LAYER, BoatModel::createBodyModel);
            event.registerLayerDefinition(ModBoatRenderer.PHANTOM_OAK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        }
        @SubscribeEvent
        public static void modelLayersInit(EntityRenderersEvent.AddLayers event) {
            LitherealArmorModel.OUTER = new LitherealArmorModel(event.getEntityModels().bakeLayer(LitherealArmorModel.OUTER_ARMOR));
            LitherealArmorModel.INNER = new LitherealArmorModel(event.getEntityModels().bakeLayer(LitherealArmorModel.INNER_ARMOR));
        }
        @SubscribeEvent
        public static void entityRendererInit(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSED_LITHERITE_BLOCK.get(), InfusedLitheriteBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ForgeBlockEntities.INFUSEMENT_CHAMBER.get(), InfusementChamberBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
            event.registerEntityRenderer(ModEntities.LITHER_CHARGE.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(ModEntities.PHANTOM_ZOMBIE.get(), PhantomZombieRenderer::new);
            event.registerEntityRenderer(ModEntities.PHANTOM_DROWNED.get(), PhantomDrownedRenderer::new);
            event.registerEntityRenderer(ModEntities.MOD_BOAT.get(), pContext -> new ModBoatRenderer<>(pContext, false));
            event.registerEntityRenderer(ModEntities.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer<>(pContext, true));
            event.registerEntityRenderer(ModEntities.RIFT_SPAWNER.get(), NoopRenderer::new);
        }
        @SubscribeEvent
        public static void registerClientExtensionsEvent(RegisterClientExtensionsEvent event) {
            event.registerItem(new IClientItemExtensions() {
                @Override
                public int getArmorLayerTintColor(@NotNull ItemStack stack, @NotNull LivingEntity entity, ArmorMaterial.@NotNull Layer layer, int layerIdx, int fallbackColor) {
                    return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
                }
                @Override
                public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    return equipmentSlot == EquipmentSlot.LEGS ? LitherealArmorModel.INNER : LitherealArmorModel.OUTER;
                }
            }, ModArmorItems.INFUSED_LITHERITE_HELMET, ModArmorItems.INFUSED_LITHERITE_CHESTPLATE, ModArmorItems.INFUSED_LITHERITE_LEGGINGS, ModArmorItems.INFUSED_LITHERITE_BOOTS);
            event.registerItem(new IClientItemExtensions() {
                @Override
                public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    return equipmentSlot == EquipmentSlot.LEGS ? LitherealArmorModel.INNER : LitherealArmorModel.OUTER;
                }
            }, ModArmorItems.LITHERITE_HELMET, ModArmorItems.LITHERITE_CHESTPLATE, ModArmorItems.LITHERITE_LEGGINGS, ModArmorItems.LITHERITE_BOOTS,
                    ModArmorItems.BURNING_LITHERITE_HELMET, ModArmorItems.BURNING_LITHERITE_CHESTPLATE, ModArmorItems.BURNING_LITHERITE_LEGGINGS, ModArmorItems.BURNING_LITHERITE_BOOTS,
                    ModArmorItems.FROZEN_LITHERITE_HELMET, ModArmorItems.FROZEN_LITHERITE_CHESTPLATE, ModArmorItems.FROZEN_LITHERITE_LEGGINGS, ModArmorItems.FROZEN_LITHERITE_BOOTS,
                    ModArmorItems.SMOLDERING_LITHERITE_HELMET, ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE, ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS, ModArmorItems.SMOLDERING_LITHERITE_BOOTS,
                    ModArmorItems.FROSTBITTEN_LITHERITE_HELMET, ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE, ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS, ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS,
                    ModArmorItems.WITHERING_LITHERITE_HELMET, ModArmorItems.WITHERING_LITHERITE_CHESTPLATE, ModArmorItems.WITHERING_LITHERITE_LEGGINGS, ModArmorItems.WITHERING_LITHERITE_BOOTS,
                    ModArmorItems.ODYSIUM_HELMET, ModArmorItems.ODYSIUM_CHESTPLATE, ModArmorItems.ODYSIUM_LEGGINGS, ModArmorItems.ODYSIUM_BOOTS,
                    ModArmorItems.ENHANCED_ODYSIUM_HELMET, ModArmorItems.ENHANCED_ODYSIUM_CHESTPLATE, ModArmorItems.ENHANCED_ODYSIUM_LEGGINGS, ModArmorItems.ENHANCED_ODYSIUM_BOOTS);
        }
    }
    public static class ClientForgeBusEvents {
        public static void register() {
//            NeoForge.EVENT_BUS.register(ClientForgeBusEvents.class);
        }
    }
}
