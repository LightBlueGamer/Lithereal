package org.lithereal.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
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
import org.lithereal.item.ModItems;

import static net.minecraft.world.level.block.TntBlock.explode;

public class ThrownLitherCharge extends ThrowableItemProjectile {

    public ThrownLitherCharge(EntityType<? extends ThrownLitherCharge> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownLitherCharge(Level arg, LivingEntity arg2) {
        super(ModEntities.LITHER_CHARGE.get(), arg2, arg);
    }

    protected Item getDefaultItem() {
        return ModItems.LITHER_CHARGE.get();
    }

    protected void onHit(HitResult hitResult) {
        if (!this.level().isClientSide) {
            switch (hitResult.getType()) {
                case BLOCK -> {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    BlockState blockState = this.level().getBlockState(blockPos);

                    if (blockState.getBlock() instanceof TntBlock) {
                        level().setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
                        explode(level(), blockPos);
                        this.discard();
                        return;
                    }

                    if (this.isInWater()) {
                        causeExplosion(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), 5, Level.ExplosionInteraction.BLOCK, this.getOwner() != null && !this.getOwner().isSpectator());
                        this.discard();
                        return;
                    }

                    if (blockState.getBlock() != Blocks.AIR) {
                        if (this.getOwner() != null && !this.getOwner().isSpectator()) {
                            this.getOwner().setDeltaMovement(this.getOwner().getDeltaMovement().x, 1, this.getOwner().getDeltaMovement().z);
                            causeExplosion(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), 5, Level.ExplosionInteraction.BLOCK, true);
                        } else causeExplosion(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), 5, Level.ExplosionInteraction.BLOCK, false);
                    }
                }
                case ENTITY -> {
                    EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                    Entity targetEntity = entityHitResult.getEntity();

                    if (targetEntity instanceof MinecartTNT tnt)
                        tnt.primeFuse();
                    else if (targetEntity instanceof LivingEntity target)
                        onHitEntity(target);
                    else
                        causeExplosion(targetEntity.position(), 5, Level.ExplosionInteraction.NONE, true);
                }
            }

            this.discard();
        }
    }

    private void onHitEntity(LivingEntity target) {
        if (!target.is(getOwner())) {
            target.hurt(this.damageSources().thrown(this, getOwner()), 4);
            if (target instanceof Player player && target.isBlocking()) {
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);
                player.stopUsingItem();
                player.level().broadcastEntityEvent(player, (byte)30);
            }

            if (!this.level().isClientSide) {
                causeExplosion(target.position(), 1, Level.ExplosionInteraction.NONE, false);

                double xDiff = target.getX() - this.getX();
                double zDiff = target.getZ() - this.getZ();
                double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

                if (distance > 0) {
                    double normalizedX = xDiff / distance;
                    double normalizedZ = zDiff / distance;
                    target.push(normalizedX, 0.0, normalizedZ);

                    target.setDeltaMovement(target.getDeltaMovement().x, 0.5, target.getDeltaMovement().z);
                }
            }
        }
    }
    private void teleportPlayerToExplosion(Vec3 pos) {
        Entity owner = getOwner();
        if (owner != null)
            owner.teleportTo(pos.x, pos.y + 0.01, pos.z);
    }
    private void causeExplosion(Vec3 pos, float range, Level.ExplosionInteraction interaction, boolean teleport) {
        this.level().explode(null, pos.x, pos.y, pos.z, range, interaction);
        if (teleport)
            teleportPlayerToExplosion(pos);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround()) {
            if (this.getOwner() != null)
                getOwner().fallDistance = 0.0f;
            this.discard();
        } else {
            if (this.getOwner() != null) {
                Entity owner = this.getOwner();
                if (owner.getY() < this.getY()) {
                    owner.fallDistance = 0.0f;
                }
            }
        }
    }
}