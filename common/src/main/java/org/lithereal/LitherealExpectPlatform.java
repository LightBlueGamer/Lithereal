package org.lithereal;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.lithereal.block.custom.*;
import org.lithereal.block.entity.*;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.Hammer;
import org.lithereal.item.custom.ModTier;
import org.lithereal.item.custom.WarHammer;
import org.lithereal.item.custom.ability.AbilityHammer;
import org.lithereal.item.custom.burning.BurningLitheriteHammer;
import org.lithereal.item.custom.infused.InfusedLitheriteHammer;
import org.lithereal.screen.*;

import java.nio.file.Path;
import java.util.function.Supplier;

public class LitherealExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
    @ExpectPlatform
    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<FireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static FireCrucibleBlock getFireCrucibleBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<FireCrucibleMenu> getFireCrucibleMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<FreezingStationBlockEntity> getFreezingStationBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static FreezingStationBlock getFreezingStationBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<FreezingStationMenu> getFreezingStationMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<InfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<InfusementChamberBlockEntity> getInfusementChamberBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static InfusementChamberBlock getInfusementChamberBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<InfusementChamberMenu> getInfusementChamberMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getLitheriteItem() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<LitherCollectorBlockEntity> getLitherCollectorBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<LitherCollectorBlockEntity> getLitherCollectorBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static LitherCollectorBlock getLitherCollectorBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<LitherCollectorMenu> getLitherCollectorMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<LitherBatteryBlockEntity> getLitherBatteryBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<LitherBatteryBlockEntity> getLitherBatteryBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static LitherBatteryBlock getLitherBatteryBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<LitherBatteryMenu> getLitherBatteryMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<ChargingStationBlockEntity> getChargingStationBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<ChargingStationBlockEntity> getChargingStationBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ChargingStationBlock getChargingStationBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<ChargingStationMenu> getChargingStationMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void applyKnockbackToNearbyEntities(Player player, LivingEntity target, float strength) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Hammer createHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static AbilityHammer createAbilityHammerWithType(Ability ability, Tier tier, int damage, float speed, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BurningLitheriteHammer createBurningHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static InfusedLitheriteHammer createInfusedHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WarHammer createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item createLongsword(Tier tier, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item createKnife(Tier tier, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item createAbilityLongsword(Ability ability, Tier tier, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item createAbilityKnife(Ability ability, Tier tier, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item createInfusedLongsword(Tier tier, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item createInfusedKnife(Tier tier, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ModTier createCombatifyTier(String name, int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TagKey<Block> getTier5Incorrect() {
        // Await Conventional Tags
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TagKey<Block> getTier6Incorrect() {
        // Await Conventional Tags
        throw new AssertionError();
    }
}