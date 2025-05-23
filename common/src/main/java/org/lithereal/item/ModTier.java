package org.lithereal.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.lithereal.data.compat.DefaultedHooks;

import java.util.function.Supplier;

import static dev.architectury.platform.Platform.isModLoaded;

public class ModTier implements Tier {
    public final int level;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final @NotNull Supplier<Ingredient> repairIngredient;
    private final TagKey<Block> incorrect;

    public ModTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
        this.incorrect = incorrect;
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrect;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String toString() {
        return "Tier[level=" + this.level + ", uses=" + this.uses + ", speed=" + this.speed + ", attackDamageBonus=" + this.attackDamageBonus + ", enchantmentValue=" + this.enchantmentValue + ", repairIngredient=" + this.repairIngredient + "]";
    }

    public static ModTier create(String name, int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        ModTier tier = new ModTier(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
        if (isModLoaded("defaulted"))
            return DefaultedHooks.registerTier(name, tier);
        return tier;
    }
}