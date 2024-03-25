package org.lithereal.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownLitherCharge extends ThrowableItemProjectile {

    public ThrownLitherCharge(EntityType<? extends ThrownLitherCharge> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownLitherCharge(Level arg, LivingEntity arg2) {
        super(EntityType.ENDER_PEARL, arg2, arg);
        if (arg2 instanceof Player) {
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

                if (blockState.getBlock() instanceof TntBlock) {
                    this.getCommandSenderWorld().playSound(null, blockPos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0f, 1.0f);
                    this.getCommandSenderWorld().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    PrimedTnt primedTNT = new PrimedTnt(this.getCommandSenderWorld(), blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, null);
                    this.getCommandSenderWorld().addFreshEntity(primedTNT);
                    this.discard();
                    return;
                }

                if (this.getCommandSenderWorld().getFluidState(blockPos).is(FluidTags.WATER)) {
                    return;
                }

                if (blockState.getBlock() != Blocks.AIR) {
                    if (this.getOwner() instanceof Player && !((Player) this.getOwner()).isSpectator()) {
                        double launchSpeed = 1.0;
                        if (this.getOwner().getXRot() > 70 && this.getOwner().getXRot() < 110) {
                            launchSpeed = 1.0;
                        }

                        this.getOwner().setDeltaMovement(this.getOwner().getDeltaMovement().x, launchSpeed, this.getOwner().getDeltaMovement().z);
                        this.getCommandSenderWorld().explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3.0f, Level.ExplosionInteraction.BLOCK);
                        this.teleportPlayerToExplosion(blockPos);
                        this.discard();
                    }
                }
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                Entity targetEntity = entityHitResult.getEntity();

                if (targetEntity instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity) targetEntity;
                    onHitEntity(target);
                } else {
                    BlockPos entityPos = targetEntity.blockPosition();
                    this.teleportPlayerToExplosion(entityPos);
                    this.getCommandSenderWorld().explode(null, entityPos.getX(), entityPos.getY(), entityPos.getZ(), 3.0f, Level.ExplosionInteraction.NONE);
                    this.discard();
                }
            }

            this.discard();
        }
    }

    private void onHitEntity(LivingEntity target) {
        if (this.getOwner() != target) {
            float damageAmount = 4.0f;
            if (this.getOwner() instanceof Player) {
                Player playerOwner = (Player) this.getOwner();
                ItemStack heldItem = playerOwner.getMainHandItem();
                if (heldItem.getItem() instanceof ShieldItem) {
                    damageAmount = 0.0f;
                }
            }
            target.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);

            if (!this.getCommandSenderWorld().isClientSide) {
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
    }

    private void teleportPlayerToExplosion(BlockPos blockPos) {
        if (this.getOwner() instanceof Player) {
            Player owner = (Player) this.getOwner();
            owner.teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
    }
}