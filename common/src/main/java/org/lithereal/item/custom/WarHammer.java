package org.lithereal.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.stream.Collectors;

public class WarHammer extends SwordItem {

    public WarHammer(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            if (player.fallDistance > 0.0F && !player.isFallFlying() && !player.isCrouching()) {
                float knockbackStrength = 1.0F;

                if (player.isUsingItem()) {
                    handleSweepAttack(player, target, knockbackStrength);
                } else {
                    handleSingleAttack(player, target, knockbackStrength);
                    applyKnockbackToNearbyEntities(player, target, knockbackStrength);
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private void handleSweepAttack(Player player, LivingEntity target, float knockbackStrength) {
        double radius = 3.0;
        List<LivingEntity> nearbyEntities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius));
        List<LivingEntity> nearbyLivingEntitiesWithShield = nearbyEntities.stream()
                .filter(entity -> entity instanceof LivingEntity && !((LivingEntity) entity).isCrouching() && !((LivingEntity) entity).isFallFlying())
                .filter(entity -> !entity.getMainHandItem().isEmpty() && entity.getMainHandItem().getItem() == Items.SHIELD)
                .collect(Collectors.toList());

        for (LivingEntity nearbyEntity : nearbyLivingEntitiesWithShield) {
            nearbyEntity.knockback(knockbackStrength / 2, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
        }
    }

    private void handleSingleAttack(Player player, LivingEntity target, float knockbackStrength) {
        target.knockback(knockbackStrength, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
    }

    private void applyKnockbackToNearbyEntities(Player player, LivingEntity target, float knockbackStrength) {
        double radius = 3.0;
        List<LivingEntity> nearbyEntities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius));
        nearbyEntities.remove(target);

        int knockbackCount = 0;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (!nearbyEntity.isCrouching() && !nearbyEntity.isFallFlying() && knockbackCount < 5) {
                nearbyEntity.knockback(knockbackStrength, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                knockbackCount++;
            }
        }
    }
}