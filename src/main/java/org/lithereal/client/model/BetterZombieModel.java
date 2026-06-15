package org.lithereal.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.entity.HumanoidArm;
import org.lithereal.Lithereal;

public class BetterZombieModel<T extends ZombieRenderState> extends PhantomZombieModel<T> {
    public static final ModelLayerLocation ZOMBIE = new ModelLayerLocation(Lithereal.id("better_zombie"), "main");
    public static final ModelLayerLocation BABY_ZOMBIE = new ModelLayerLocation(Lithereal.id("better_baby_zombie"), "main");
    public static final ModelLayerLocation ZOMBIE_INNER_ARMOR = new ModelLayerLocation(Lithereal.id("better_zombie"), "inner_armor");
    public static final ModelLayerLocation ZOMBIE_OUTER_ARMOR = new ModelLayerLocation(Lithereal.id("better_zombie"), "outer_armor");
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;
    public BetterZombieModel(ModelPart modelPart) {
        super(modelPart);
        this.leftSleeve = this.leftArm.getChild("left_sleeve");
        this.rightSleeve = this.rightArm.getChild("right_sleeve");
        this.leftPants = this.leftLeg.getChild("left_pants");
        this.rightPants = this.rightLeg.getChild("right_pants");
        this.jacket = this.body.getChild("jacket");
    }

    public static LayerDefinition createBodyLayer(CubeDeformation scale) {
        MeshDefinition mesh = HumanoidModel.createMesh(scale, 0.0F);
        PartDefinition root = mesh.getRoot();
        float overlayScale = 0.25F;
        PartDefinition leftArm = root.addOrReplaceChild(
                "left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale), PartPose.offset(5.0F, 2.0F, 0.0F)
        );
        PartDefinition rightArm = root.getChild("right_arm");
        leftArm.addOrReplaceChild(
                "left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale.extend(overlayScale)), PartPose.ZERO
        );
        rightArm.addOrReplaceChild(
                "right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale.extend(overlayScale)), PartPose.ZERO
        );

        PartDefinition leftLeg = root.addOrReplaceChild(
                "left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale), PartPose.offset(1.9F, 12.0F, 0.0F)
        );
        PartDefinition rightLeg = root.getChild("right_leg");
        leftLeg.addOrReplaceChild(
                "left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale.extend(overlayScale)), PartPose.ZERO
        );
        rightLeg.addOrReplaceChild(
                "right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale.extend(overlayScale)), PartPose.ZERO
        );
        PartDefinition body = root.getChild("body");
        body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, scale.extend(overlayScale)), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createBabyBodyLayer(final CubeDeformation scale) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        float overlayScale = 0.15F;
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 2.0F, scale), PartPose.offset(0.0F, 17.5F, 0.0F));
        body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 23).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 2.0F, scale.extend(overlayScale)), PartPose.ZERO);
        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(3, 3)
                        .addBox(-3.0F, -6.25F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(35, 3)
                        .addBox(-3.0F, -6.15F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(overlayScale)),
                PartPose.offset(0.0F, 15.25F, 0.0F)
        );
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition leftArm = root.addOrReplaceChild(
                "left_arm", CubeListBuilder.create().texOffs(28, 16).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, scale), PartPose.offset(3.0F, 15.5F, 0.0F)
        );
        PartDefinition rightArm = root.addOrReplaceChild(
                "right_arm", CubeListBuilder.create().texOffs(36, 16).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, scale), PartPose.offset(-3.0F, 15.5F, 0.0F)
        );
        PartDefinition leftLeg = root.addOrReplaceChild(
                "left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, scale), PartPose.offset(1.0F, 20.0F, 0.0F)
        );
        PartDefinition rightLeg = root.addOrReplaceChild(
                "right_leg", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, scale), PartPose.offset(-1.0F, 20.0F, 0.0F)
        );
        leftArm.addOrReplaceChild(
                "left_sleeve", CubeListBuilder.create().texOffs(28, 23).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, scale.extend(overlayScale)), PartPose.ZERO
        );
        rightArm.addOrReplaceChild(
                "right_sleeve", CubeListBuilder.create().texOffs(36, 23).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, scale.extend(overlayScale)), PartPose.ZERO
        );
        leftLeg.addOrReplaceChild(
                "left_pants", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, scale.extend(overlayScale)), PartPose.ZERO
        );
        rightLeg.addOrReplaceChild(
                "right_pants", CubeListBuilder.create().texOffs(8, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, scale.extend(overlayScale)), PartPose.ZERO
        );
        return LayerDefinition.create(mesh, 64, 64);
    }

    public void translateToHand(final T state, final HumanoidArm arm, final PoseStack poseStack) {
        this.root().translateAndRotate(poseStack);
        ModelPart part = this.getArm(arm);
        part.translateAndRotate(poseStack);
    }
}
