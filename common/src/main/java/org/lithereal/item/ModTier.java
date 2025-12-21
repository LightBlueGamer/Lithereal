package org.lithereal.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.lithereal.data.compat.DefaultedHooks;
import org.lithereal.item.compat.CompatInit;

import java.util.function.Supplier;

import static dev.architectury.platform.Platform.isModLoaded;

public record ModTier(int uses, float speed, float attackDamageBonus, int enchantmentValue,
                      @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) implements Tier {

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
        return "Tier[uses=" + this.uses + ", speed=" + this.speed + ", attackDamageBonus=" + this.attackDamageBonus + ", enchantmentValue=" + this.enchantmentValue + ", repairIngredient=" + this.repairIngredient + "]";
    }

    public static ModTier create(String name, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        if (isModLoaded("combatify"))
            attackDamageBonus -= CompatInit.getTierDamageNerf();
        ModTier tier = new ModTier(uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
        if (isModLoaded("defaulted"))
            return DefaultedHooks.registerTier(name, tier);
        return tier;
    }
}