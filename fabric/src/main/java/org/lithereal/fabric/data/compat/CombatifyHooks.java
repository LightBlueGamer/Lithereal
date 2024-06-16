package org.lithereal.fabric.data.compat;

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
import org.lithereal.fabric.world.item.HammerItemWithType;
import org.lithereal.fabric.world.item.ModExtendedTier;
import org.lithereal.fabric.world.item.WarHammerItemWithType;
import org.lithereal.fabric.world.item.ability.AbilityHammerItemWithType;
import org.lithereal.fabric.world.item.ability.FabricAbilityKnife;
import org.lithereal.fabric.world.item.ability.FabricAbilityLongSword;
import org.lithereal.fabric.world.item.burning.BurningLitheriteHammerItemWithType;
import org.lithereal.fabric.world.item.infused.FabricInfusedKnife;
import org.lithereal.fabric.world.item.infused.FabricInfusedLongSword;
import org.lithereal.fabric.world.item.infused.InfusedLitheriteHammerItemWithType;
import org.lithereal.world.item.HammerItem;
import org.lithereal.world.item.ModTier;
import org.lithereal.world.item.WarHammerItem;
import org.lithereal.world.item.ability.Ability;
import org.lithereal.world.item.ability.AbilityHammerItem;
import org.lithereal.world.item.burning.BurningLitheriteHammerItem;
import org.lithereal.world.item.infused.InfusedLitheriteHammerItem;

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

    public static HammerItem createHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        return new HammerItemWithType(tier, damage, speed, properties);
    }

    public static AbilityHammerItem createAbilityHammerWithType(Ability ability, Tier tier, int damage, float speed, Item.Properties properties) {
        return new AbilityHammerItemWithType(ability, tier, damage, speed, properties);
    }

    public static BurningLitheriteHammerItem createBurningHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        return new BurningLitheriteHammerItemWithType(tier, damage, speed, properties);
    }

    public static InfusedLitheriteHammerItem createInfusedHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        return new InfusedLitheriteHammerItemWithType(tier, damage, speed, properties);
    }

    public static WarHammerItem createWarHammerWithType(Tier tier, int damage, float speed, Item.Properties properties) {
        return new WarHammerItemWithType(tier, damage, speed, properties);
    }
    public static ModExtendedTier generateExtendedTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        return new ModExtendedTier(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
    }
    public static ModTier registerTier(String name, ModExtendedTier tier) {
        Combatify.defineDefaultTier(name, tier);
        return tier;
    }
}
