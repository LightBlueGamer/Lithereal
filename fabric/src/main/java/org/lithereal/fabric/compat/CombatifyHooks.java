package org.lithereal.fabric.compat;

import net.atlas.combatify.Combatify;
import net.atlas.combatify.extensions.PlayerExtensions;
import net.atlas.combatify.item.KnifeItem;
import net.atlas.combatify.item.LongSwordItem;
import net.atlas.combatify.util.MethodHandler;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.lithereal.fabric.item.custom.ModExtendedTier;
import org.lithereal.fabric.item.custom.ability.FabricAbilityKnife;
import org.lithereal.fabric.item.custom.ability.FabricAbilityLongSword;
import org.lithereal.fabric.item.custom.infused.FabricInfusedKnife;
import org.lithereal.fabric.item.custom.infused.FabricInfusedLongSword;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ModTier;

import java.util.function.Supplier;

public class CombatifyHooks {
    public static void disableShield(Player player, float damage) {
        ((PlayerExtensions) player).ctsShieldDisable(damage, MethodHandler.getBlockingItem(player).getItem());
    }
    public static void knockback(LivingEntity entity, float strength, float x, float z) {
        MethodHandler.knockback(entity, strength, x, z);
    }
    public static double getCurrentAttackReach(Player player, float baseTime) {
        return MethodHandler.getCurrentAttackReach(player, baseTime);
    }
    public static Item generateLongsword(Tier tier, Item.Properties properties) {
        return new LongSwordItem(tier, properties);
    }
    public static Item generateKnife(Tier tier, Item.Properties properties) {
        return new KnifeItem(tier, properties);
    }
    public static Item generateAbilityLongsword(Ability ability, Tier tier, Item.Properties properties) {
        return new FabricAbilityLongSword(ability, tier, properties);
    }
    public static Item generateAbilityKnife(Ability ability, Tier tier, Item.Properties properties) {
        return new FabricAbilityKnife(ability, tier, properties);
    }
    public static Item generateInfusedLongsword(Tier tier, Item.Properties properties) {
        return new FabricInfusedLongSword(tier, properties);
    }
    public static Item generateInfusedKnife(Tier tier, Item.Properties properties) {
        return new FabricInfusedKnife(tier, properties);
    }
    public static ModExtendedTier generateExtendedTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        return new ModExtendedTier(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
    }
    public static ModTier registerTier(String name, ModExtendedTier tier) {
        Combatify.ITEMS.tiers.put(name, tier);
        return tier;
    }
}
