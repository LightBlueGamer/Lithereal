package org.lithereal.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModItems;

import static net.minecraft.world.level.block.TntBlock.explode;

public class ThrownLitherCharge extends ThrowableItemProjectile {

    public ThrownLitherCharge(EntityType<? extends ThrownLitherCharge> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownLitherCharge(Level level, LivingEntity livingEntity) {
        super(ModEntities.LITHER_CHARGE.get(), livingEntity, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.LITHER_CHARGE.get();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (!this.level().isClientSide()) {
            switch (hitResult.getType()) {
                case BLOCK:
                    handleBlockHit((BlockHitResult) hitResult);
                    break;
                case ENTITY:
                    handleEntityHit((EntityHitResult) hitResult);
                    break;
            }
        }
        this.discard();
    }

    private void handleBlockHit(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = this.level().getBlockState(blockPos);

        if (blockState.getBlock() instanceof TntBlock) {
            level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            explode(level(), blockPos);
        } else if (this.isInWater()) {
            addEffects();
            causeExplosion(blockHitResult.getLocation(), 3, Level.ExplosionInteraction.BLOCK, shouldTeleport());
        } else if (!blockState.isAir()) {
            if (this.getOwner() != null && !this.getOwner().isSpectator()) {
                float explosionRange = (blockState.getBlock() == Blocks.STONE ||
                        blockState.getBlock() == Blocks.DEEPSLATE ||
                        blockState.getBlock() == Blocks.END_STONE ||
                        blockState.getBlock() == Blocks.COBBLESTONE ||
                        blockState.getBlock() == Blocks.COBBLED_DEEPSLATE) ? 5 : 3;
                addEffects();
                causeExplosion(blockHitResult.getLocation(), explosionRange, Level.ExplosionInteraction.BLOCK, true);
            } else {
                causeExplosion(blockHitResult.getLocation(), 3, Level.ExplosionInteraction.BLOCK, false);
            }
        }
    }

    private void handleEntityHit(EntityHitResult entityHitResult) {
        Entity targetEntity = entityHitResult.getEntity();

        if (targetEntity instanceof MinecartTNT) {
            ((MinecartTNT) targetEntity).primeFuse();
        } else if (targetEntity instanceof LivingEntity) {
            handleLivingEntityHit((LivingEntity) targetEntity);
        } else if (targetEntity instanceof Boat || targetEntity instanceof AbstractMinecart) {
            Vec3 targetPos = targetEntity.position();
            causeExplosion(targetPos, 3, Level.ExplosionInteraction.NONE, false);
        }
    }

    private void handleLivingEntityHit(LivingEntity livingEntity) {
        if (!livingEntity.is(this.getOwner())) {
            if (livingEntity instanceof Player player && player.isBlocking()) {
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);
                player.stopUsingItem();
                player.level().broadcastEntityEvent(player, (byte)30);
                double distance = player.distanceTo(this);
                if (distance <= 3) {
                    return;
                }
            }
            livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 12);

            if (!this.level().isClientSide()) {
                causeExplosion(livingEntity.position(), 1, Level.ExplosionInteraction.NONE, false);

                double xDiff = livingEntity.getX() - this.getX();
                double zDiff = livingEntity.getZ() - this.getZ();
                double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

                if (distance <= 3) {
                    double normalizedX = xDiff / distance;
                    double normalizedZ = zDiff / distance;
                    livingEntity.push(normalizedX, 0.0, normalizedZ);
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().x, 0.5, livingEntity.getDeltaMovement().z);
                }
            }
        }
    }

    public void causeExplosion(Vec3 pos, float range, Level.ExplosionInteraction interaction, boolean teleport) {
        this.level().explode(null, pos.x, pos.y, pos.z, range, interaction);
        if (teleport) {
            Entity owner = this.getOwner();
            if (owner instanceof Player) {
                owner.teleportTo(pos.x, pos.y + 0.01, pos.z);
            }
        }
    }

    private void addEffects() {
        Entity owner = this.getOwner();
        if (owner instanceof Player) {
            Player player = (Player) owner;
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0, false, false));
        }
    }

    private boolean shouldTeleport() {
        Entity owner = this.getOwner();
        return owner != null && !owner.isSpectator();
    }
}