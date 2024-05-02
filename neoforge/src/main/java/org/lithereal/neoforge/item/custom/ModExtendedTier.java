package org.lithereal.neoforge.item.custom;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.custom.ModTier;

import java.util.function.Supplier;

public class ModExtendedTier extends ModTier {
    public ModExtendedTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        super(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
    }

    public int getLevel() {
        return level;
    }

    public String baseTierName() {
        return "netherite";
    }
}
