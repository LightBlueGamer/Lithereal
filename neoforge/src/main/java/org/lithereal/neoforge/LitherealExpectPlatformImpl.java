package org.lithereal.neoforge;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.*;
import org.lithereal.world.block.*;
import org.lithereal.block.entity.EtherCollectorBlockEntity;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.item.HammerItem;
import org.lithereal.item.ModTier;
import org.lithereal.item.WarHammerItem;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityHammerItem;
import org.lithereal.item.burning.BurningLitheriteHammerItem;
import org.lithereal.item.infused.InfusedLitheriteHammerItem;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.neoforge.world.block.ForgeInfusementChamberBlock;
import org.lithereal.neoforge.world.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.world.block.entity.ForgeFireCrucibleBlockEntity;
import org.lithereal.neoforge.world.block.entity.ForgeFreezingStationBlockEntity;
import org.lithereal.neoforge.world.block.entity.ForgeInfusementChamberBlockEntity;
import org.lithereal.neoforge.data.compat.CombatifyHooks;
import org.lithereal.neoforge.world.item.ForgeItems;
import org.lithereal.neoforge.world.item.ForgeWarHammerItem;
import org.lithereal.neoforge.client.gui.screens.inventory.ForgeMenuTypes;
import org.lithereal.client.gui.screens.inventory.EtherCollectorMenu;
import org.lithereal.client.gui.screens.inventory.FireCrucibleMenu;
import org.lithereal.client.gui.screens.inventory.FreezingStationMenu;
import org.lithereal.client.gui.screens.inventory.InfusementChamberMenu;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LitherealExpectPlatformImpl {
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
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

    public static void applyKnockbackToNearbyEntities(Player player, LivingEntity target, float strength) {
        boolean combatify = ModList.get().isLoaded("combatify");
        double radius = !combatify ? 3.0 : CombatifyHooks.getCurrentAttackReach(player, 1);
        float x = Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
        float z = -Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (!combatify)
            target.knockback(strength, x, z);
        else
            CombatifyHooks.knockback(target, strength, x, z);
        List<LivingEntity> nearbyEntities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius));
        nearbyEntities.remove(target);

        int affectedEntities = 0;

        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (!nearbyEntity.isFallFlying() && Math.abs(nearbyEntity.getY() - target.getY()) < 0.1) {
                if (nearbyEntity instanceof Player player1 && player1.isBlocking()) {
                    if (combatify)
                        CombatifyHooks.disableShield(player, 5);
                    else if (!nearbyEntity.isCrouching()) {
                        player1.getCooldowns().addCooldown(player1.getUseItem().getItem(), 100);
                        player1.stopUsingItem();
                        player1.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + player1.level().random.nextFloat() * 0.4F);
                    }
                    player1.level().broadcastEntityEvent(player1, (byte)30);
                }
                if (!combatify)
                    nearbyEntity.knockback(strength, x, z);
                else
                    CombatifyHooks.knockback(nearbyEntity, strength, x, z);
                affectedEntities++;

                if (affectedEntities >= 2) break;
            }
        }
    }

    public static HammerItem createHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        return new HammerItem(tier, damage, speed, properties);
    }

    public static AbilityHammerItem createAbilityHammerWithType(Ability ability, Tier tier, int damage, float speed, Item.Properties properties) {
        return new AbilityHammerItem(ability, tier, damage, speed, properties);
    }

    public static BurningLitheriteHammerItem createBurningHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        return new BurningLitheriteHammerItem(tier, damage, speed, properties);
    }

    public static InfusedLitheriteHammerItem createInfusedHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        return new InfusedLitheriteHammerItem(tier, damage, speed, properties);
    }

    public static WarHammerItem createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        return new ForgeWarHammerItem(tier, damage, speed, properties);
    }

    public static Item createLongsword(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateLongsword(tier, properties);
    }

    public static Item createKnife(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateKnife(tier, properties);
    }

    public static Item createAbilityLongsword(Ability ability, Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateAbilityLongsword(ability, tier, properties);
    }

    public static Item createAbilityKnife(Ability ability, Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateAbilityKnife(ability, tier, properties);
    }

    public static Item createInfusedLongsword(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateInfusedLongsword(tier, properties);
    }

    public static Item createInfusedKnife(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateInfusedKnife(tier, properties);
    }

    public static ModTier createCombatifyTier(String name, int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        return CombatifyHooks.generateExtendedTier(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
    }

    public static SimpleParticleType createSimpleParticleType(boolean alwaysSpawn) {
        return new SimpleParticleType(alwaysSpawn);
    }

    @OnlyIn(Dist.CLIENT)
    public static <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> type, ParticleProviderRegistry.DeferredParticleProvider<T> particleProvider) {
        // Ignore
    }
}
