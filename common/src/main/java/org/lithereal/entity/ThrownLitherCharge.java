package org.lithereal.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
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

    public ThrownLitherCharge(Level arg, LivingEntity arg2) {
        super(EntityType.ENDER_PEARL, arg2, arg);
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
                        if (!(this.getOwner().getXRot() > 70 && this.getOwner().getXRot() < 110)) {
                            double fallDistance = this.getOwner().getY() - blockPos.getY();

                            if (fallDistance > 3) {
                                this.getOwner().fallDistance = (float) fallDistance;
                            }
                            this.getCommandSenderWorld().explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3.0f, Level.ExplosionInteraction.BLOCK);
                        }
                        this.getOwner().teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    }
                }
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                if (entityHitResult.getEntity() instanceof Player) {
                    Player playerHit = (Player) entityHitResult.getEntity();
                    if (this.getOwner() instanceof Player && playerHit == this.getOwner() && !playerHit.isSpectator()) {
                        return;
                    }
                }

                LivingEntity target = (LivingEntity) entityHitResult.getEntity();

                float damageAmount = 2.0f;
                target.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);

                if (!this.getCommandSenderWorld().isClientSide) {
                    if (this.getOwner() != null && this.getOwner() instanceof LivingEntity && this.getOwner() != target) {
                        this.discard();
                    }
                    double explosionPower = 1.0;
                    this.getCommandSenderWorld().explode(null, target.getX(), target.getY(), target.getZ(), (float) explosionPower, Level.ExplosionInteraction.NONE);

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
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult arg) {
        super.onHitEntity(arg);
        LivingEntity target = (LivingEntity) arg.getEntity();

        if (this.getOwner() instanceof Player && target == this.getOwner()) {
            return;
        }

        float damageAmount = 2.0f;
        target.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);

        if (!this.getCommandSenderWorld().isClientSide) {
            if (target instanceof Player && !target.getCommandSenderWorld().isClientSide) {
                Player playerTarget = (Player) target;
                ItemStack activeItemStack = playerTarget.getUseItem();
                if (!activeItemStack.isEmpty() && activeItemStack.getItem() instanceof ShieldItem) {
                    playerTarget.getCooldowns().addCooldown(activeItemStack.getItem(), 100); // 100 ticks = 5 seconds
                    return;
                }
            }

            if (this.getOwner() != null && this.getOwner() instanceof LivingEntity && this.getOwner() != target) {
                this.discard();
            }
            double explosionPower = 1.0;
            this.getCommandSenderWorld().explode(null, target.getX(), target.getY(), target.getZ(), (float) explosionPower, Level.ExplosionInteraction.NONE);

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