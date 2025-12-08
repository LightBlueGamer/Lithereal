package org.lithereal;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.lithereal.block.ModPhantomBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.KeyMapping;
import org.lithereal.client.particle.EtherealSoulProvider;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.client.particle.StandardBiomeProvider;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.client.renderer.InfusementChamberBlockEntityModel;
import org.lithereal.client.renderer.ModBoatRenderer;
import org.lithereal.core.component.ModComponents;
import org.lithereal.item.*;
import org.lithereal.item.obscured.ObscuredItem;
import org.lithereal.util.ModBlockColors;
import org.lithereal.util.ModItemColors;
import org.lithereal.block.ModBlocks;
import org.lithereal.item.compat.CompatInit;
import org.lithereal.item.infused.InfusedItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static dev.architectury.platform.Platform.isModLoaded;

public class LitherealClient {
    public static void init() {
        EntityModelLayerRegistry.register(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
        EntityModelLayerRegistry.register(InfusementChamberBlockEntityModel.LAYER_LOCATION, InfusementChamberBlockEntityModel::createBodyLayer);
        EntityModelLayerRegistry.register(ModBoatRenderer.PHANTOM_OAK_BOAT_LAYER, BoatModel::createBodyModel);
        EntityModelLayerRegistry.register(ModBoatRenderer.PHANTOM_OAK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        BlockEntityRendererRegistry.register(ModBlockEntities.ETHEREAL_CORE_PORTAL.get(), TheEndPortalRenderer::new);
        BlockEntityRendererRegistry.register((BlockEntityType<SignBlockEntity>) ModBlockEntities.SIGN.get(), SignRenderer::new);
        BlockEntityRendererRegistry.register((BlockEntityType<SignBlockEntity>) ModBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.LITHER_FIRE_FLAME.get(), FlameParticle.Provider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.SOUL.get(), EtherealSoulProvider::new);
        LitherealExpectPlatform.registerParticleProvider(ModParticles.CRYSTAL_SPARKLE.get(), StandardBiomeProvider::new);
        registerKeyBindings();
        registerColorHandlers();
        registerItemsToTab();
    }

    private static void registerKeyBindings() {
        KeyMappingRegistry.register(KeyMapping.FREEZE_KEY);
        KeyMappingRegistry.register(KeyMapping.SCORCH_KEY);
    }

    private static void registerColorHandlers() {
        ItemColor itemColor = ModItemColors.INFUSED_LITHERITE_COLOR_HANDLER::apply;
        BlockColor blockColor = ModBlockColors.INFUSED_LITHERITE_BLOCK_COLOR;

        ColorHandlerRegistry.registerItemColors(itemColor, LitherealExpectPlatform.getInfusedLitheriteBlock().asItem(), ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_SWORD.get(), ModToolItems.INFUSED_LITHERITE_SHOVEL.get(), ModToolItems.INFUSED_LITHERITE_PICKAXE.get(), ModToolItems.INFUSED_LITHERITE_AXE.get(), ModToolItems.INFUSED_LITHERITE_HOE.get(), ModToolItems.INFUSED_LITHERITE_HAMMER.get(), ModArmorItems.INFUSED_LITHERITE_HELMET.get(), ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(), ModArmorItems.INFUSED_LITHERITE_BOOTS.get());
        if (isModLoaded("combatify"))
            CompatInit.setColoursForCombatify(itemColor);
        ColorHandlerRegistry.registerBlockColors(blockColor, LitherealExpectPlatform.getInfusedLitheriteBlock());
        ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos) : 8573157, ModBlocks.ETHEREAL_GRASS_BLOCK);
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> 8573157, ModBlocks.ETHEREAL_GRASS_BLOCK);
        RenderTypeRegistry.register(RenderType.cutoutMipped(), ModBlocks.ETHEREAL_GRASS_BLOCK.get(), ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get(), ModPhantomBlocks.PHANTOM_ICE_FLOWER.get(), ModPhantomBlocks.PHANTOM_ROSE.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.INFINITY_GLASS.get(), ModBlocks.LITHERITE_CRYSTAL_BLOCK.get());
    }

    private static void registerItemsToTab() {
        List<ItemStack> litherite = new ArrayList<>();
        List<ItemStack> otherI = new ArrayList<>();
        List<ItemStack> otherB = new ArrayList<>(Arrays.asList(
                LitherealExpectPlatform.getFireCrucibleBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getFreezingStationBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getInfusementChamberBlock().asItem().getDefaultInstance()));

        for (Holder<Potion> holder : BuiltInRegistries.POTION.holders().toList()) {
            List<ItemLike> itemLikes = Arrays.asList(ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(),
                    ModToolItems.INFUSED_LITHERITE_SWORD.get(),
                    ModToolItems.INFUSED_LITHERITE_SHOVEL.get(),
                    ModToolItems.INFUSED_LITHERITE_PICKAXE.get(),
                    ModToolItems.INFUSED_LITHERITE_AXE.get(),
                    ModToolItems.INFUSED_LITHERITE_HOE.get(),
                    ModToolItems.INFUSED_LITHERITE_HAMMER.get(),
                    ModArmorItems.INFUSED_LITHERITE_HELMET.get(),
                    ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get(),
                    ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get(),
                    ModArmorItems.INFUSED_LITHERITE_BOOTS.get(),
                    LitherealExpectPlatform.getInfusedLitheriteBlock());
            for (ItemLike itemLike : itemLikes) {
                ItemStack current = new ItemStack(itemLike);
                current.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
                litherite.add(current);
            }
            if (isModLoaded("combatify"))
                litherite = CompatInit.populateInfusedForCombatify(litherite, holder);
        }

        litherite.add(LitherealExpectPlatform.getLitheriteItem().getDefaultInstance());

        for (RegistrySupplier<Item> itemRegistrySupplier : ModItems.ITEMS) {
            if (itemRegistrySupplier.is(ModBlocks.ETHEREAL_CORE_PORTAL_LOC)) continue;
            ItemStack item = new ItemStack(itemRegistrySupplier.get());
            if (item.getItem() instanceof ObscuredItem) item.set(ModComponents.REVEALED.get(), true);

            if (!(item.getItem() instanceof InfusedItem) && !litherite.contains(item) && isEquipment(BuiltInRegistries.ITEM.getKey(item.getItem()).getPath())) {
                litherite.add(item);
            } else if (!(item.getItem() instanceof InfusedItem)) otherI.add(item);
        }

        litherite.sort(Comparator.comparing(itemStack -> {
            String descriptionId = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath();

            if (descriptionId.startsWith("litherite") || descriptionId.startsWith("deepslate_litherite") || descriptionId.startsWith("etherstone_litherite")) return "1";
            else if (descriptionId.startsWith("burning_litherite")) return "2";
            else if (descriptionId.startsWith("frozen_litherite")) return "3";
            else if (descriptionId.startsWith("withering_litherite")) return "4";
            else if (descriptionId.startsWith("infused_litherite")) return "5";
            else if (descriptionId.startsWith("charged_litherite")) return "6";
            else if (descriptionId.startsWith("odysium")) return "7";
            else if (descriptionId.startsWith("phantom")) return "8";

            return descriptionId;
        }));
        litherite.addAll(otherI);
        litherite.addAll(otherB);
        litherite = litherite.stream().filter(stack -> !stack.isEmpty()).toList();

        CreativeTabRegistry.appendBuiltinStack(ModCreativeTabs.LITHEREAL_TAB.get(), litherite.toArray(new ItemStack[0]));

    }
    public static boolean isEquipment(String id) {
        return id.startsWith("litherite") || id.startsWith("deepslate_litherite") || id.startsWith("etherstone_litherite") || id.startsWith("burning_litherite") || id.startsWith("frozen_litherite") || id.startsWith("withering_litherite") || id.startsWith("infused_litherite") || id.startsWith("charged_litherite") || id.startsWith("odysium") || id.startsWith("phantom");
    }
}
