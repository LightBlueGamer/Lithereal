package org.lithereal.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.Nullable;

public class ModBowItem extends BowItem {
    private final Tier tier;
    private final float speedFactor;
    private final float fatigueFactor;
    private final float damageFactor;

    public ModBowItem(Tier tier, Properties properties, float speedFactor, float fatigueFactor, float damageFactor) {
		super(properties.durability(tier.getUses()));
        this.tier = tier;
        this.speedFactor = speedFactor;
        this.fatigueFactor = fatigueFactor;
        this.damageFactor = damageFactor;
    }

    @Override
    protected void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float f, float g, float h, @Nullable LivingEntity livingEntity2) {
        super.shootProjectile(livingEntity, projectile, i, f * speedFactor, g * fatigueFactor, h, livingEntity2);
        if (projectile instanceof AbstractArrow abstractArrow) abstractArrow.setBaseDamage(abstractArrow.getBaseDamage() * damageFactor);
    }

    @Override
    public int getDefaultProjectileRange() {
        return (int) (super.getDefaultProjectileRange() * speedFactor);
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return this.tier.getRepairIngredient().test(itemStack2) || super.isValidRepairItem(itemStack, itemStack2);
    }
}
