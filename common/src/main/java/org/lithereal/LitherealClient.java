package org.lithereal;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.lithereal.client.KeyMapping;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.client.renderer.InfusementChamberBlockEntityModel;
import org.lithereal.util.ModBlockColors;
import org.lithereal.util.ModItemColors;
import org.lithereal.block.ModBlocks;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
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
        LitherealExpectPlatform.registerParticleProvider(ModParticles.BLUE_FIRE_FLAME.get(), FlameParticle.Provider::new);
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

        ColorHandlerRegistry.registerItemColors(itemColor, LitherealExpectPlatform.getInfusedLitheriteBlock().asItem(), ModItems.INFUSED_LITHERITE_INGOT.get(), ModItems.INFUSED_LITHERITE_SWORD.get(), ModItems.INFUSED_LITHERITE_SHOVEL.get(), ModItems.INFUSED_LITHERITE_PICKAXE.get(), ModItems.INFUSED_LITHERITE_AXE.get(), ModItems.INFUSED_LITHERITE_HOE.get(), ModItems.INFUSED_LITHERITE_HAMMER.get(), ModItems.INFUSED_LITHERITE_HELMET.get(), ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModItems.INFUSED_LITHERITE_LEGGINGS.get(), ModItems.INFUSED_LITHERITE_BOOTS.get());
        if (isModLoaded("combatify"))
            CompatInit.setColoursForCombatify(itemColor);
        ColorHandlerRegistry.registerBlockColors(blockColor, LitherealExpectPlatform.getInfusedLitheriteBlock());
    }

    private static void registerItemsToTab() {
        List<ItemStack> litherite = new ArrayList<>();
        List<ItemStack> otherI = new ArrayList<>();
        List<ItemStack> otherB = new ArrayList<>(Arrays.asList(
                LitherealExpectPlatform.getFireCrucibleBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getFreezingStationBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getInfusementChamberBlock().asItem().getDefaultInstance()));

        for (Potion potion : BuiltInRegistries.POTION.stream().toList()) {
            Holder<Potion> holder = BuiltInRegistries.POTION.wrapAsHolder(potion);
            List<ItemLike> itemLikes = Arrays.asList(LitherealExpectPlatform.getInfusedLitheriteBlock(),
                    ModItems.INFUSED_LITHERITE_INGOT.get(),
                    ModItems.INFUSED_LITHERITE_SWORD.get(),
                    ModItems.INFUSED_LITHERITE_SHOVEL.get(),
                    ModItems.INFUSED_LITHERITE_PICKAXE.get(),
                    ModItems.INFUSED_LITHERITE_AXE.get(),
                    ModItems.INFUSED_LITHERITE_HOE.get(),
                    ModItems.INFUSED_LITHERITE_HAMMER.get(),
                    ModItems.INFUSED_LITHERITE_HELMET.get(),
                    ModItems.INFUSED_LITHERITE_CHESTPLATE.get(),
                    ModItems.INFUSED_LITHERITE_LEGGINGS.get(),
                    ModItems.INFUSED_LITHERITE_BOOTS.get());
            for (ItemLike itemLike : itemLikes){
                ItemStack current = new ItemStack(itemLike);
                current.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
                litherite.add(current);
            }
            if (isModLoaded("combatify"))
                litherite = CompatInit.populateInfusedForCombatify(litherite, holder);
        }

        for (RegistrySupplier<Block> blockRegistryObject : ModBlocks.BLOCKS) {
            ItemStack blockItem = new ItemStack(blockRegistryObject.get());

            if (!(blockItem.getItem() instanceof InfusedItem) && !litherite.contains(blockItem) && isEquipment(BuiltInRegistries.ITEM.getKey(blockItem.getItem()).getPath())) {
                litherite.add(blockItem);
            } else otherB.add(blockItem);
        }

        litherite.add(LitherealExpectPlatform.getLitheriteItem().getDefaultInstance());

        for (RegistrySupplier<Item> itemRegistrySupplier : ModItems.ITEMS) {
            ItemStack item = new ItemStack(itemRegistrySupplier.get());

            if (!(item.getItem() instanceof InfusedItem) && !litherite.contains(item) && isEquipment(BuiltInRegistries.ITEM.getKey(item.getItem()).getPath())) {
                litherite.add(item);
            } else otherI.add(item);
        }

        litherite.sort(Comparator.comparing(itemStack -> {
            String descriptionId = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath();

            if (descriptionId.startsWith("litherite") || descriptionId.startsWith("deepslate_litherite")) return "1";
            else if (descriptionId.startsWith("burning_litherite")) return "2";
            else if (descriptionId.startsWith("frozen_litherite")) return "3";
            else if (descriptionId.startsWith("withering_litherite")) return "4";
            else if (descriptionId.startsWith("infused_litherite")) return "5";
            else if (descriptionId.startsWith("charged_litherite")) return "6";
            else if (descriptionId.startsWith("odysium")) return "7";

            return descriptionId;
        }));
        litherite.addAll(otherI);
        litherite.addAll(otherB);
        litherite = litherite.stream().filter(stack -> !stack.isEmpty()).toList();

        CreativeTabRegistry.appendBuiltinStack(ModCreativeTabs.LITHEREAL_TAB.get(), litherite.toArray(new ItemStack[0]));

    }
    public static boolean isEquipment(String id) {
        return id.startsWith("litherite") || id.startsWith("deepslate_litherite") || id.startsWith("burning_litherite") || id.startsWith("frozen_litherite") || id.startsWith("withering_litherite") || id.startsWith("infused_litherite") || id.startsWith("charged_litherite") || id.startsWith("odysium");
    }
}