package org.lithereal.entity.phantom.brain.goals;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.lithereal.entity.phantom.PhantomMob;

import java.util.EnumSet;

public class PhantomAttackGoal<E extends PathfinderMob & PhantomMob<E>> extends Goal {
    protected final E mob;
    private final double speedModifier;
    private final float attackRadiusSqr;
    private long lastCanUseCheck;
    private long randomDelay = 0;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public PhantomAttackGoal(E mob, double speedModifier, float attackRadius) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackRadiusSqr = attackRadius * attackRadius;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long time = this.mob.level().getGameTime();
        if (time - this.lastCanUseCheck < this.randomDelay) return false;
        this.lastCanUseCheck = time;
        this.randomDelay = this.mob.getRandom().nextInt(20);
        return hasTargetAndCouldPhase();
    }

    public boolean hasTargetAndCouldPhase() {
        return this.mob.getTarget() != null && (this.mob.isPhasing() || this.mob.canStartPhasing());
    }

    @Override
    public boolean canContinueToUse() {
        return this.hasTargetAndCouldPhase() && this.mob.getPhasingTimer() > 0;
    }

    @Override
    public void start() {
        super.start();
        if (!this.mob.isPhasing()) this.mob.activatePhasing();
        this.mob.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        LivingEntity livingEntity = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
            this.mob.setTarget(null);
        }

        this.mob.setAggressive(false);
        this.seeTime = 0;
        this.mob.deactivatePhasing();
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            double distToTarget = this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean canSee = this.mob.getSensing().hasLineOfSight(livingEntity);
            boolean hasBeenAbleToSee = this.seeTime > 0;
            if (canSee != hasBeenAbleToSee) {
                this.seeTime = 0;
            }

            if (canSee) this.seeTime++;
            else this.seeTime--;

            if (!(distToTarget > this.attackRadiusSqr) && this.seeTime >= 20) {
                this.mob.getNavigation().stop();
                this.strafingTime++;
            } else {
                this.mob.getNavigation().moveTo(livingEntity, this.speedModifier);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if (this.mob.getRandom().nextFloat() < 0.3) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if (this.mob.getRandom().nextFloat() < 0.3) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (distToTarget > this.attackRadiusSqr * 0.5F)
                    this.strafingBackwards = false;
                else if (distToTarget < this.attackRadiusSqr * 0.1F)
                    this.strafingBackwards = true;

                this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.1F : 0.1F, 0);
                if (this.mob.getControlledVehicle() instanceof Mob mob)
                    mob.lookAt(livingEntity, 30.0F, 30.0F);

                this.mob.lookAt(livingEntity, 30.0F, 30.0F);
            } else {
                this.mob.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
            }

            if (this.mob.getPhasingTimer() > 0 && this.mob.getRandom().nextInt(16) == 0)
                this.mob.expulsionAttack();
        }
    }
}
