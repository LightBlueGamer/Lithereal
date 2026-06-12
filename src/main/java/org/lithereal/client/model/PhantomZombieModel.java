package org.lithereal.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.util.Mth;
import org.lithereal.client.renderer.state.PhantomRenderState;

public class PhantomZombieModel<T extends ZombieRenderState> extends ZombieModel<T> {
    public PhantomZombieModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(T state) {
        float partialTicks = state.ageInTicks - Mth.floor(state.ageInTicks);
        if (state instanceof PhantomRenderState phantom) {
            state.isAggressive |= phantom.isPhasing();
            float progress = 0;
            if (phantom.isPhasing()) progress = Math.min(1, (phantom.getInitialPhasingTimer() - phantom.getPhasingTimer() - 1 + partialTicks) / 6);
            else if (phantom.isTransitioningOut()) progress = 1 - ((phantom.getPhasingTimer() + 1 - partialTicks) / -6F);
            if (progress > 0) {
                state.walkAnimationPos = 0;
                state.walkAnimationSpeed = 0;
                super.setupAnim(state);
                hoverLeg(this.leftLeg, state.ageInTicks * progress);
                hoverLeg(this.rightLeg, -state.ageInTicks * progress);
            } else super.setupAnim(state);
        } else super.setupAnim(state);
    }

    public static void hoverLeg(ModelPart leg, float f) {
        leg.xRot = leg.xRot + Mth.sin(f * 0.067F) * 0.125F + 0.25F;
    }

    public void setupDrownedAnim(T state) {
        if (state.leftArmPose == ArmPose.THROW_TRIDENT) {
            this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
            this.leftArm.yRot = 0.0F;
        }

        if (state.rightArmPose == ArmPose.THROW_TRIDENT) {
            this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
            this.rightArm.yRot = 0.0F;
        }

        if (state.swimAmount > 0.0F) {
            this.rightArm.xRot = Mth.rotLerpRad(state.swimAmount, this.rightArm.xRot, (float) (-Math.PI * 4.0 / 5.0)) + state.swimAmount * 0.35F * Mth.sin(0.1F * state.ageInTicks);
            this.leftArm.xRot = Mth.rotLerpRad(state.swimAmount, this.leftArm.xRot, (float) (-Math.PI * 4.0 / 5.0)) - state.swimAmount * 0.35F * Mth.sin(0.1F * state.ageInTicks);
            this.rightArm.zRot = Mth.rotLerpRad(state.swimAmount, this.rightArm.zRot, -0.15F);
            this.leftArm.zRot = Mth.rotLerpRad(state.swimAmount, this.leftArm.zRot, 0.15F);
            this.leftLeg.xRot = this.leftLeg.xRot - state.swimAmount * 0.55F * Mth.sin(0.1F * state.ageInTicks);
            this.rightLeg.xRot = this.rightLeg.xRot + state.swimAmount * 0.55F * Mth.sin(0.1F * state.ageInTicks);
            this.head.xRot = 0.0F;
            this.hat.xRot = 0.0F;
        }
    }
}
