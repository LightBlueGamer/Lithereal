package org.lithereal.neoforge;

//? neoforge {
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.lithereal.Lithereal;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.block.ModVegetationBlocks;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.entity.ModEntities;
import org.lithereal.entity.phantom.PhantomDrowned;
import org.lithereal.entity.phantom.PhantomZombie;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.mob_effect.potion.ModPotions;
import org.lithereal.world.LitherealOverworldRegion;
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
        @SubscribeEvent
        public static void onDefaultAttributesRegistry(EntityAttributeCreationEvent event) {
            event.put(ModEntities.PHANTOM_ZOMBIE.get(), PhantomZombie.createAttributes().build());
            event.put(ModEntities.PHANTOM_DROWNED.get(), PhantomDrowned.createAttributes().build());
        }
        @SubscribeEvent
        public static void onBlockEntityTypeAddBlocks(BlockEntityTypeAddBlocksEvent event) {
            event.modify(BlockEntityType.SHELF, ModTreeBlocks.PHANTOM_OAK_SHELF.get(), ModTreeBlocks.FORTSHROOM_SHELF.get(), ModTreeBlocks.MALISHROOM_SHELF.get());
        }
    }
    public static class ForgeBusEvents {
        public static void register() {
            NeoForge.EVENT_BUS.register(ForgeBusEvents.class);
        }
        @SubscribeEvent // on the game event bus
        public static void datapackSync(OnDatapackSyncEvent event) {
            // Specify what recipe types to sync to the client
            event.sendRecipes(ModRecipes.BURNING_TYPE.get(), ModRecipes.FREEZING_TYPE.get(), ModRecipes.INFUSING_TYPE.get(), RecipeType.SMELTING);
        }
        @SubscribeEvent
        public static void onBrewingRecipes(RegisterBrewingRecipesEvent event) {
            PotionBrewing.Builder builder = event.getBuilder();
            builder.addMix(Potions.FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE, Lithereal.asHolder(ModPotions.FREEZE_RESISTANCE));
            builder.addMix(Potions.LONG_FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE, Lithereal.asHolder(ModPotions.LONG_FREEZE_RESISTANCE));
            builder.addMix(Lithereal.asHolder(ModPotions.FREEZE_RESISTANCE), Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_FREEZE_RESISTANCE));
            builder.addStartMix(ModVegetationBlocks.MALISHROOM.get().asItem(), Lithereal.asHolder(ModPotions.UNLUCK));
            builder.addMix(Lithereal.asHolder(ModPotions.UNLUCK), Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_UNLUCK));
            builder.addStartMix(ModVegetationBlocks.FORTSHROOM.get().asItem(), Potions.LUCK);
            builder.addMix(Potions.LUCK, Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_LUCK));
            builder.addMix(Potions.LUCK, Items.GLOWSTONE_DUST, Lithereal.asHolder(ModPotions.STRONG_LUCK));
            builder.addStartMix(ModRawMaterialItems.SATURNITE_CRYSTAL.get(), Lithereal.asHolder(ModPotions.STURDINESS));
            builder.addMix(Lithereal.asHolder(ModPotions.STURDINESS), Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_STURDINESS));
            builder.addMix(Lithereal.asHolder(ModPotions.STURDINESS), Items.GLOWSTONE_DUST, Lithereal.asHolder(ModPotions.STRONG_STURDINESS));
            builder.addMix(Lithereal.asHolder(ModPotions.STURDINESS), Items.FERMENTED_SPIDER_EYE, Lithereal.asHolder(ModPotions.FRAILNESS));
            builder.addMix(Lithereal.asHolder(ModPotions.LONG_STURDINESS), Items.FERMENTED_SPIDER_EYE, Lithereal.asHolder(ModPotions.LONG_FRAILNESS));
            builder.addMix(Lithereal.asHolder(ModPotions.FRAILNESS), Items.REDSTONE, Lithereal.asHolder(ModPotions.LONG_FRAILNESS));
        }
    }
}
//?}