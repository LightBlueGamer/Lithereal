package org.lithereal.neoforge.data.compat;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ability.Ability;
import org.lithereal.neoforge.world.item.ModExtendedTier;

import java.util.function.Supplier;

public class CombatifyHooks {
    public static void disableShield(Player player, float damage) {
        throw new AssertionError();
//        ((PlayerExtensions) player).ctsShieldDisable(damage, MethodHandler.getBlockingItem(player).getItem());
    }
    public static void knockback(LivingEntity entity, float strength, float x, float z) {
        throw new AssertionError();
//        MethodHandler.knockback(entity, strength, x, z);
    }
    public static double getCurrentAttackReach(Player player, float baseTime) {
        throw new AssertionError();
//        return MethodHandler.getCurrentAttackReach(player, baseTime);
    }
    public static Item generateLongsword(Tier tier, Item.Properties properties) {
        throw new AssertionError();
//        return new LongSwordItem(tier, properties);
    }
    public static Item generateKnife(Tier tier, Item.Properties properties) {
        throw new AssertionError();
//        return new KnifeItem(tier, properties);
    }
    public static Item generateAbilityLongsword(Ability ability, Tier tier, Item.Properties properties) {
        throw new AssertionError();
//        return new ForgeAbilityLongSword(ability, tier, properties);
    }
    public static Item generateAbilityKnife(Ability ability, Tier tier, Item.Properties properties) {
        throw new AssertionError();
//        return new ForgeAbilityKnife(ability, tier, properties);
    }
    public static Item generateInfusedLongsword(Tier tier, Item.Properties properties) {
        throw new AssertionError();
//        return new ForgeInfusedLongSword(tier, properties);
    }
    public static Item generateInfusedKnife(Tier tier, Item.Properties properties) {
        throw new AssertionError();
//        return new ForgeInfusedKnife(tier, properties);
    }
    public static ModExtendedTier generateExtendedTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        return new ModExtendedTier(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
    }
}
