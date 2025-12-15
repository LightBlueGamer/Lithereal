package org.lithereal.client.renderer.zombie;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.lithereal.entity.phantom.PhantomMob;

public class PhantomZombieModel<T extends Zombie> extends ZombieModel<T> {
    public PhantomZombieModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(T monster, float f, float g, float h, float i, float j) {
        float partialTicks = h - monster.tickCount;
        if (monster instanceof PhantomMob<?> phantomMob) {
            float progress = 0;
            if (phantomMob.isPhasing()) progress = Math.min(1, (phantomMob.getInitialPhasingTimer() - phantomMob.getPhasingTimer() - 1 + partialTicks) / 6);
            else if (phantomMob.isTransitioningOut()) progress = 1 - ((phantomMob.getPhasingTimer() + 1 - partialTicks) / -6F);
            if (progress > 0) {
                super.setupAnim(monster, 0, 0, h, i, j);
                hoverLeg(this.leftLeg, h * progress);
                hoverLeg(this.rightLeg, -h * progress);
            } else super.setupAnim(monster, f, g, h, i, j);
        } else super.setupAnim(monster, f, g, h, i, j);
    }

    public static void hoverLeg(ModelPart leg, float f) {
        leg.xRot = leg.xRot + Mth.sin(f * 0.067F) * 0.125F + 0.25F;
    }

    @Override
    public boolean isAggressive(T zombie) {
        return !(zombie instanceof PhantomMob<?> phantomMob && phantomMob.isPhasing()) || super.isAggressive(zombie);
    }

    public void prepareDrownedModel(T monster) {
        this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemStack = monster.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.is(Items.TRIDENT) && monster.isAggressive()) {
            if (monster.getMainArm() == HumanoidArm.RIGHT) {
                this.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
            } else {
                this.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
            }
        }
    }

    public void setupDrownedAnim(T monster, float h) {
        if (this.leftArmPose == HumanoidModel.ArmPose.THROW_SPEAR) {
            this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
            this.leftArm.yRot = 0.0F;
        }

        if (this.rightArmPose == HumanoidModel.ArmPose.THROW_SPEAR) {
            this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
            this.rightArm.yRot = 0.0F;
        }

        if (this.swimAmount > 0.0F) {
            this.rightArm.xRot = this.rotlerpRad(this.swimAmount, this.rightArm.xRot, (float) (-Math.PI * 4.0 / 5.0)) + this.swimAmount * 0.35F * Mth.sin(0.1F * h);
            this.leftArm.xRot = this.rotlerpRad(this.swimAmount, this.leftArm.xRot, (float) (-Math.PI * 4.0 / 5.0)) - this.swimAmount * 0.35F * Mth.sin(0.1F * h);
            this.rightArm.zRot = this.rotlerpRad(this.swimAmount, this.rightArm.zRot, -0.15F);
            this.leftArm.zRot = this.rotlerpRad(this.swimAmount, this.leftArm.zRot, 0.15F);
            this.leftLeg.xRot = this.leftLeg.xRot - this.swimAmount * 0.55F * Mth.sin(0.1F * h);
            this.rightLeg.xRot = this.rightLeg.xRot + this.swimAmount * 0.55F * Mth.sin(0.1F * h);
            this.head.xRot = 0.0F;
            this.hat.xRot = 0.0F;
        }
    }
}
