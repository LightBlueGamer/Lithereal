package org.lithereal.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.Nullable;

public class ModBowItem extends BowItem {
    private final float speedFactor;
    private final float fatigueFactor;
    private final float damageFactor;

    public ModBowItem(ToolMaterial toolMaterial, Properties properties, float speedFactor, float fatigueFactor, float damageFactor) {
		super(properties
                .durability(toolMaterial.durability())
                .repairable(toolMaterial.repairItems())
                .enchantable(toolMaterial.enchantmentValue()));
        this.speedFactor = speedFactor;
        this.fatigueFactor = fatigueFactor;
        this.damageFactor = damageFactor;
    }

    @Override
    protected void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float f, float g, float h, @Nullable LivingEntity livingEntity2) {
        super.shootProjectile(livingEntity, projectile, i, f * speedFactor, g * fatigueFactor, h, livingEntity2);
        if (projectile instanceof AbstractArrow abstractArrow) abstractArrow.setBaseDamage(2.0F * damageFactor);
    }

    @Override
    public int getDefaultProjectileRange() {
        return (int) (super.getDefaultProjectileRange() * speedFactor);
    }
}
