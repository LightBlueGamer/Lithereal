package org.lithereal.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import org.jetbrains.annotations.Nullable;

public class ModBowItem extends BowItem {
    private final float powerFactor;
    private final float fatigueFactor;

    public ModBowItem(Properties properties, float powerFactor, float fatigueFactor) {
		super(properties);
        this.powerFactor = powerFactor;
        this.fatigueFactor = fatigueFactor;
    }

    @Override
    protected void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float f, float g, float h, @Nullable LivingEntity livingEntity2) {
        super.shootProjectile(livingEntity, projectile, i, f * powerFactor, g * fatigueFactor, h, livingEntity2);
    }

    @Override
    public int getDefaultProjectileRange() {
        return (int) (super.getDefaultProjectileRange() * powerFactor);
    }
}
