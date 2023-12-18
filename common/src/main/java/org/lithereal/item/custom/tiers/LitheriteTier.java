package org.lithereal.item.custom.tiers;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.item.Items;

public class LitheriteTier implements Tier {
    @Override
    public int getUses() {
        return 1750;
    }

    @Override
    public float getSpeed() {
        return 11;
    }

    @Override
    public float getAttackDamageBonus() {
        return 4;
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.LITHERITE_CRYSTAL.get());
    }
}