package org.lithereal;

import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.core.component.ModComponents;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.entity.phantom.PhantomDrowned;
import org.lithereal.mob_effect.ModMobEffects;
import org.lithereal.mob_effect.potion.ModPotions;
import org.lithereal.networking.ClientboundRetributionDeathPacket;
import org.lithereal.tags.ModTags;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.world.feature.ModFeatures;
import org.lithereal.world.feature.tree.decorator.ModDecorators;
import org.lithereal.world.feature.tree.trunkplacer.ModTrunkPlacers;
import org.lithereal.world.structure.ModStructureProcessorTypes;

import java.util.logging.Logger;

public class Lithereal {
    public static final String MOD_ID = "lithereal";
    public static final ResourceKey<LootTable> LITHEREAL_CHAMBERS_REWARD = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "chests/lithereal_chambers/reward"));

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static void init() {
        ModParticles.register();
        ModBlocks.register();
        ModCreativeTabs.register();
        ModArmorMaterials.register();
        ModComponents.register();
        ModMobEffects.register();
        ModPotions.register();
        ModTags.init();
        ModItems.register();
        ModBlockEntities.register();
        ModRecipes.register();
        ModEntities.register();
        ModStructureProcessorTypes.register();
        ModFeatures.register();
        ModDecorators.register();
        ModTrunkPlacers.register();

        SpawnPlacementsRegistry.register(ModEntities.PHANTOM_ZOMBIE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Monster::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(ModEntities.PHANTOM_DROWNED, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING, PhantomDrowned::checkPhantomDrownedSpawnRules);

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ClientboundRetributionDeathPacket.TYPE, ClientboundRetributionDeathPacket.STREAM_CODEC, (value, context) -> {
            Level level = context.getPlayer().level();
            Vec3 pos = value.position();
            for (int cnt = 0; cnt < 250; cnt++) {
                level.addParticle(ModParticles.RETRIBUTION_LIGHT_BURST.get(), pos.x, pos.y, pos.z, 0, 0, 0);
            }
        });

        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static <T> Holder<T> asHolder(RegistrySupplier<T> registrySupplier) {
        return registrySupplier.getRegistrar().getHolder(registrySupplier.getId());
    }
}