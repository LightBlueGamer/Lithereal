package org.lithereal.fabric;

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
import org.jetbrains.annotations.NotNull;
import org.lithereal.LitherealExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;
import org.lithereal.block.custom.*;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.custom.FabricInfusementChamberBlock;
import org.lithereal.fabric.block.entity.*;
import org.lithereal.fabric.compat.CombatifyHooks;
import org.lithereal.fabric.item.FabricItems;
import org.lithereal.fabric.screen.*;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ModTier;
import org.lithereal.item.custom.WarHammer;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LitherealExpectPlatformImpl {
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
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

    public static BlockEntityTicker<FabricFireCrucibleBlockEntity> getFireCrucibleBlockEntityTicker() {
        return FabricFireCrucibleBlockEntity::tick;
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

    public static BlockEntityTicker<FabricFreezingStationBlockEntity> getFreezingStationBlockEntityTicker() {
        return FabricFreezingStationBlockEntity::tick;
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

    public static BlockEntityTicker<FabricInfusementChamberBlockEntity> getInfusementChamberBlockEntityTicker() {
        return FabricInfusementChamberBlockEntity::tick;
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

    public static BlockEntityType<FabricLitherCollectorBlockEntity> getLitherCollectorBlockEntity() {
        return FabricBlockEntities.LITHER_COLLECTOR_BLOCK_ENTITY;
    }

    public static BlockEntityTicker<FabricLitherCollectorBlockEntity> getLitherCollectorBlockEntityTicker() {
        return FabricLitherCollectorBlockEntity::tick;
    }

    public static LitherCollectorBlock getLitherCollectorBlock() {
        return (LitherCollectorBlock) FabricBlocks.LITHER_COLLECTOR_BLOCK;
    }

    public static MenuType<FabricLitherCollectorMenu> getLitherCollectorMenu() {
        return FabricScreenHandlers.LITHER_COLLECTOR_SCREEN_HANDLER;
    }

    public static BlockEntityType<FabricLitherBatteryBlockEntity> getLitherBatteryBlockEntity() {
        return FabricBlockEntities.LITHER_BATTERY_BLOCK_ENTITY;
    }

    public static BlockEntityTicker<FabricLitherBatteryBlockEntity> getLitherBatteryBlockEntityTicker() {
        return FabricLitherBatteryBlockEntity::tick;
    }

    public static MenuType<FabricLitherBatteryMenu> getLitherBatteryMenu() {
        return FabricScreenHandlers.LITHER_BATTERY_SCREEN_HANDLER;
    }

    public static LitherBatteryBlock getLitherBatteryBlock() {
        return (LitherBatteryBlock) FabricBlocks.LITHER_BATTERY_BLOCK;
    }

    public static void applyKnockbackToNearbyEntities(Player player, LivingEntity target, float strength) {
        boolean combatify = FabricLoader.getInstance().isModLoaded("combatify");
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

                if (affectedEntities >= 4) {
                    break;
                }
            }
        }
    }
    public static WarHammer createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        return new WarHammer(tier, damage, speed, properties);
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
        return CombatifyHooks.registerTier(name, CombatifyHooks.generateExtendedTier(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect));
    }
}
