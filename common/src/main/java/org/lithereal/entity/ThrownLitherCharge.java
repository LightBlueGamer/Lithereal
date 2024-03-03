package org.lithereal.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownLitherCharge extends ThrowableItemProjectile {
    private double originalY;
    private boolean hasThrownLitherCharge;

    public ThrownLitherCharge(Level arg, LivingEntity arg2) {
        super(EntityType.ENDER_PEARL, arg2, arg);
        if (arg2 instanceof Player) {
            this.originalY = arg2.getY();
            this.hasThrownLitherCharge = false;
        }
    }

    protected Item getDefaultItem() {
        return Items.ENDER_PEARL;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.getCommandSenderWorld().isClientSide) {
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockState blockState = this.getCommandSenderWorld().getBlockState(blockPos);

                if (this.getCommandSenderWorld().getFluidState(blockPos).is(FluidTags.WATER)) {
                    return;
                }

                if (blockState.getBlock() != Blocks.AIR) {
                    if (this.getOwner() instanceof Player && !((Player) this.getOwner()).isSpectator()) {
                        double fallDistance = this.getOwner().getY() - blockPos.getY();

                        if (fallDistance > 3) {
                            this.getOwner().fallDistance = (float) fallDistance;
                        }

                        double launchSpeed = 1.0;
                        if (this.getOwner().getXRot() > 70 && this.getOwner().getXRot() < 110) {
                            launchSpeed = 1.0;
                        }

                        this.getOwner().setDeltaMovement(this.getOwner().getDeltaMovement().x, launchSpeed, this.getOwner().getDeltaMovement().z);
                        this.getCommandSenderWorld().explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3.0f, Level.ExplosionInteraction.BLOCK);
                        this.hasThrownLitherCharge = true;
                        this.discard();
                    }
                }

                if (this.hasThrownLitherCharge && this.getOwner() instanceof Player && ((Player) this.getOwner()).fallDistance == 0) {
                    this.getOwner().teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                }
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                Entity targetEntity = entityHitResult.getEntity();
                if (targetEntity instanceof Player) {
                    Player playerHit = (Player) targetEntity;
                    if (this.getOwner() instanceof Player && playerHit == this.getOwner() && !playerHit.isSpectator()) {
                        return;
                    }
                }

                if (targetEntity.getType() == EntityType.TNT) {
                    if (this.getOwner() instanceof Player) {
                        Player owner = (Player) this.getOwner();
                        owner.teleportTo(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ());
                    }
                } else {
                    LivingEntity target = (LivingEntity) targetEntity;

                    float damageAmount = 2.0f;
                    target.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);

                    if (!this.getCommandSenderWorld().isClientSide) {
                        if (this.getOwner() != null && this.getOwner() instanceof LivingEntity && this.getOwner() != target) {
                            this.discard();
                        }
                        double explosionPower = 1.0;
                        if (!this.hasThrownLitherCharge) {
                            this.getCommandSenderWorld().explode(null, target.getX(), target.getY(), target.getZ(), (float) explosionPower, Level.ExplosionInteraction.NONE);
                        }

                        double xDiff = target.getX() - this.getX();
                        double zDiff = target.getZ() - this.getZ();
                        double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

                        double knockbackStrength = 1.0;
                        if (distance > 0) {
                            double normalizedX = xDiff / distance;
                            double normalizedZ = zDiff / distance;
                            target.push(normalizedX * knockbackStrength, 0.0, normalizedZ * knockbackStrength);

                            target.setDeltaMovement(target.getDeltaMovement().x, 0.5, target.getDeltaMovement().z);
                        }
                    }
                }
            }

            if (this.getOwner() instanceof Player) {
                double fallDistance = this.originalY - this.getOwner().getY();
                if (fallDistance > 0 && shouldApplyFallDamage(fallDistance)) {
                    this.getOwner().fallDistance += (float) fallDistance;
                }
            }
            this.discard();
        }
    }

    private boolean shouldApplyFallDamage(double fallDistance) {
        double distanceRemaining = fallDistance;
        double gravity = 0.08;
        double terminalVelocity = 3.92;
        double distanceFalling = 0;

        while (distanceRemaining > 0) {
            distanceFalling += Math.min(terminalVelocity, Math.sqrt(2 * gravity * distanceRemaining));
            distanceRemaining -= terminalVelocity;
        }

        return fallDistance > distanceFalling;
    }

    protected void onHitEntity(EntityHitResult arg) {
        super.onHitEntity(arg);
        Entity targetEntity = arg.getEntity();

        if (this.getOwner() instanceof Player && targetEntity == this.getOwner()) {
            return;
        }

        if (!(targetEntity instanceof LivingEntity)) {
            return;
        }

        LivingEntity target = (LivingEntity) targetEntity;

        float damageAmount = 2.0f;
        target.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);

        if (!this.getCommandSenderWorld().isClientSide) {
            if (target instanceof Player && !target.getCommandSenderWorld().isClientSide) {
                Player playerTarget = (Player) target;
                ItemStack activeItemStack = playerTarget.getUseItem();
                if (!activeItemStack.isEmpty() && activeItemStack.getItem() instanceof ShieldItem) {
                    playerTarget.getCooldowns().addCooldown(activeItemStack.getItem(), 100);
                    return;
                }
            }

            if (this.getOwner() != null && this.getOwner() instanceof LivingEntity && this.getOwner() != target) {
                this.discard();
            }
            double explosionPower = 1.0;
            if (!this.hasThrownLitherCharge) {
                this.getCommandSenderWorld().explode(null, target.getX(), target.getY(), target.getZ(), (float) explosionPower, Level.ExplosionInteraction.NONE);
            }

            double xDiff = target.getX() - this.getX();
            double zDiff = target.getZ() - this.getZ();
            double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

            double knockbackStrength = 1.0;
            if (distance > 0) {
                double normalizedX = xDiff / distance;
                double normalizedZ = zDiff / distance;
                target.push(normalizedX * knockbackStrength, 0.0, normalizedZ * knockbackStrength);

                target.setDeltaMovement(target.getDeltaMovement().x, 0.5, target.getDeltaMovement().z);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("shouldRender", false);
    }
}