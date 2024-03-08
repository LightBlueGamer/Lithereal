package org.lithereal.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;

public class WarHammer extends Hammer {

    public WarHammer(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) attacker;
            if (!serverPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
                double horizontalKnockback = 2;
                double verticalKnockback = 2.5;
                double xRatio = Math.sin(attacker.yRotO * 0.017453292F);
                double zRatio = -Math.cos(attacker.yRotO * 0.017453292F);

                ItemStack targetOffhand = target.getOffhandItem();
                if (targetOffhand.getItem() instanceof ShieldItem) {
                    targetOffhand.hurtAndBreak(1, target, (entity) -> entity.broadcastBreakEvent(target.getOffhandItem() == targetOffhand ? net.minecraft.world.entity.EquipmentSlot.OFFHAND : net.minecraft.world.entity.EquipmentSlot.MAINHAND));
                    serverPlayer.getCooldowns().addCooldown(targetOffhand.getItem(), 100);

                    horizontalKnockback /= 2;
                    verticalKnockback /= 2;
                }

                float damageDealt = super.getAttackDamage();

                target.knockback(horizontalKnockback, xRatio, zRatio);
                target.knockback(verticalKnockback, 0, 0);

                List<Entity> nearbyEntities = attacker.getCommandSenderWorld().getEntities(attacker, target.getBoundingBox().inflate(3.0), entity -> entity instanceof LivingEntity && entity != target);
                for (Entity entity : nearbyEntities) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity nearbyEntity = (LivingEntity) entity;
                        double distanceX = nearbyEntity.getX() - target.getX();
                        double distanceZ = nearbyEntity.getZ() - target.getZ();
                        double distanceSq = distanceX * distanceX + distanceZ * distanceZ;
                        double maxDistanceSq = 9.0;
                        if (distanceSq <= maxDistanceSq) {
                            nearbyEntity.knockback(horizontalKnockback, xRatio, zRatio);
                            nearbyEntity.knockback(verticalKnockback, 0, 0);
                            nearbyEntity.hurt(attacker.getLastDamageSource(), damageDealt / 2);
                        }
                    }
                }
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    public float getDamage(ItemStack stack) {
        int sharpnessLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, stack);
        float extraDamage = sharpnessLevel * 0.5F + 0.5F;
        return super.getAttackDamage() + extraDamage;
    }
}