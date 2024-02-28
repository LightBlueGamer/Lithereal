package org.lithereal.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.tags.FluidTags;
import net.minecraft.nbt.CompoundTag;

public class ThrownLitherCharge extends ThrowableItemProjectile {
    public ThrownLitherCharge(EntityType<? extends net.minecraft.world.entity.projectile.ThrownEnderpearl> arg, Level arg2) {
        super(arg, arg2);
    }

    public ThrownLitherCharge(Level arg, LivingEntity arg2) {
        super(EntityType.ENDER_PEARL, arg2, arg);
    }

    protected Item getDefaultItem() {
        return Items.ENDER_PEARL;
    }

    protected void onHitEntity(EntityHitResult arg) {
        super.onHitEntity(arg);
        LivingEntity target = (LivingEntity)arg.getEntity();

        float damageAmount = 2.0f;
        target.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);

        double knockbackStrength = 1.0;
        double xRatio = target.getX() - this.getX();
        double zRatio = target.getZ() - this.getZ();
        double ratioLength = Math.sqrt(xRatio * xRatio + zRatio * zRatio);

        target.push(xRatio / ratioLength * knockbackStrength, 0.0, zRatio / ratioLength * knockbackStrength);

        if (!this.getCommandSenderWorld().isClientSide) {
            if (this.getOwner() != null && this.getOwner() instanceof LivingEntity && this.getOwner() != target) {
                this.getCommandSenderWorld().explode(null, this.getX(), this.getY(), this.getZ(), 1.0f, Level.ExplosionInteraction.NONE);
            }
        }
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
                    if (this.getOwner() instanceof Player && !((Player)this.getOwner()).isSpectator()) {
                        if (!(this.getOwner().getXRot() > 70 && this.getOwner().getXRot() < 110)) {
                            double fallDistance = this.getOwner().getY() - blockPos.getY();
                            if (fallDistance > 3) {
                                int fallDamage = (int) Math.ceil((fallDistance - 3) / 2.0);
                                if (fallDamage > 0) {
                                    this.getOwner().hurt(this.damageSources().fall(), fallDamage);
                                }
                            }
                            this.getCommandSenderWorld().explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3.0f, Level.ExplosionInteraction.BLOCK);
                        }
                        this.getOwner().teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    }
                }
            }
            this.discard();
        }
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("shouldRender", false);
    }
}