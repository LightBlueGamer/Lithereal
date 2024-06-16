package org.lithereal.fabric.world.item;

import net.atlas.combatify.extensions.ExtendedTier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.lithereal.world.item.ModTier;

import java.util.function.Supplier;

public class ModExtendedTier extends ModTier implements ExtendedTier {
    public ModExtendedTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient, TagKey<Block> incorrect) {
        super(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient, incorrect);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public String baseTierName() {
        return "netherite";
    }
}
