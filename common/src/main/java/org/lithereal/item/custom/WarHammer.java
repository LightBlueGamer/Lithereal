package org.lithereal.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Mth;

import java.util.List;

public class WarHammer extends SwordItem {

    public WarHammer(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            if (player.fallDistance > 0.0F && !player.isFallFlying() && !player.isCrouching()) {

                player.fallDistance = 0.0F;

                float knockbackStrength = 1.0F;

                stack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));

                if (player.isUsingItem()) {
                    handleSweepAttack(player, target, knockbackStrength);
                } else {
                    handleSingleAttack(player, target, knockbackStrength);
                    applyKnockbackToNearbyEntities(player, target, knockbackStrength);
                }

                return true;
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private void handleSweepAttack(Player player, LivingEntity target, float knockbackStrength) {
        double radius = 3.0;
        List<LivingEntity> nearbyEntities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius)).stream()
                .filter(entity -> entity instanceof LivingEntity && !entity.isCrouching() && !entity.isFallFlying() && entity.isBlocking())
                .toList();

        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Player player1) {
                player1.getCooldowns().addCooldown(player1.getUseItem().getItem(), 100);
                player1.stopUsingItem();
                player1.level().broadcastEntityEvent(player1, (byte)30);
            }
            if (Math.abs(nearbyEntity.getY() - target.getY()) < 0.1) {
                nearbyEntity.knockback(knockbackStrength / 2, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
            }
        }
    }

    private void handleSingleAttack(Player player, LivingEntity target, float knockbackStrength) {
        target.knockback(knockbackStrength, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
    }

    private void applyKnockbackToNearbyEntities(Player player, LivingEntity target, float knockbackStrength) {
        double radius = 3.0;
        List<LivingEntity> nearbyEntities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius));
        nearbyEntities.remove(target);

        int affectedEntities = 0;

        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (!nearbyEntity.isCrouching() && !nearbyEntity.isFallFlying() && Math.abs(nearbyEntity.getY() - target.getY()) < 0.1) {
                nearbyEntity.knockback(knockbackStrength, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                affectedEntities++;

                if (affectedEntities >= 4) {
                    break;
                }
            }
        }
    }
}