package org.lithereal.neoforge;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.lithereal.Lithereal;
import org.lithereal.block.ModVegetationBlocks;
import org.lithereal.entity.ModEntities;
import org.lithereal.entity.phantom.PhantomDrowned;
import org.lithereal.entity.phantom.PhantomZombie;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.mob_effect.potion.ModPotions;
import org.lithereal.neoforge.data.worldgen.LitherealOverworldRegion;
import terrablender.api.Regions;

public class Events {
    public static class ModBusEvents {
        public static void register(IEventBus bus) {
            bus.register(ModBusEvents.class);
        }
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                Regions.register(new LitherealOverworldRegion(2));
            });
        }
    }
    public static class ForgeBusEvents {
        public static void register() {
            NeoForge.EVENT_BUS.register(ForgeBusEvents.class);
        }
        @SubscribeEvent
        public static void onBrewingRecipes(RegisterBrewingRecipesEvent event) {
            PotionBrewing.Builder builder = event.getBuilder();
            builder.addStartMix(ModVegetationBlocks.MALISHROOM.get().asItem(), Lithereal.asHolder(ModPotions.UNLUCK));
            builder.addMix(Lithereal.asHolder(ModPotions.UNLUCK), Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_UNLUCK));
            builder.addStartMix(ModVegetationBlocks.FORTSHROOM.get().asItem(), Potions.LUCK);
            builder.addMix(Potions.LUCK, Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_LUCK));
            builder.addMix(Potions.LUCK, Items.GLOWSTONE_DUST, Lithereal.asHolder(ModPotions.STRONG_LUCK));
            builder.addStartMix(ModRawMaterialItems.SATURNITE_CRYSTAL.get(), Lithereal.asHolder(ModPotions.STURDINESS));
            builder.addMix(Lithereal.asHolder(ModPotions.STURDINESS), Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_STURDINESS));
            builder.addMix(Lithereal.asHolder(ModPotions.STURDINESS), Items.GLOWSTONE_DUST, Lithereal.asHolder(ModPotions.STRONG_STURDINESS));
        }
        @SubscribeEvent
        public static void onDefaultAttributesRegistry(EntityAttributeCreationEvent event) {
            event.put(ModEntities.PHANTOM_ZOMBIE.get(), PhantomZombie.createAttributes().build());
            event.put(ModEntities.PHANTOM_DROWNED.get(), PhantomDrowned.createAttributes().build());
        }
    }
}
