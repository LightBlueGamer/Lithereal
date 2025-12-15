package org.lithereal.client.renderer.zombie;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;

public class BetterZombieModel<T extends Zombie> extends PhantomZombieModel<T> {
    public static final ModelLayerLocation ZOMBIE = new ModelLayerLocation(Lithereal.id("better_zombie"), "main");
    public static final ModelLayerLocation ZOMBIE_INNER_ARMOR = new ModelLayerLocation(Lithereal.id("better_zombie"), "inner_armor");
    public static final ModelLayerLocation ZOMBIE_OUTER_ARMOR = new ModelLayerLocation(Lithereal.id("better_zombie"), "outer_armor");
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;
    public BetterZombieModel(ModelPart modelPart) {
        super(modelPart);
        this.leftSleeve = modelPart.getChild("left_sleeve");
        this.rightSleeve = modelPart.getChild("right_sleeve");
        this.leftPants = modelPart.getChild("left_pants");
        this.rightPants = modelPart.getChild("right_pants");
        this.jacket = modelPart.getChild("jacket");
    }

    public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = HumanoidModel.createMesh(cubeDeformation, 0.0F);
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(
                "left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(5.0F, 2.0F, 0.0F)
        );
        partDefinition.addOrReplaceChild(
                "right_sleeve",
                CubeListBuilder.create().texOffs(40, 32)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F)
        );
        partDefinition.addOrReplaceChild(
                "left_sleeve",
                CubeListBuilder.create().texOffs(48, 48)
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)),
                PartPose.offset(5.0F, 2.0F, 0.0F)
        );
        partDefinition.addOrReplaceChild(
                "left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(1.9F, 12.0F, 0.0F)
        );
        partDefinition.addOrReplaceChild(
                "right_pants",
                CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)),
                PartPose.offset(-1.9F, 12.0F, 0.0F)
        );
        partDefinition.addOrReplaceChild(
                "left_pants",
                CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)),
                PartPose.offset(1.9F, 12.0F, 0.0F)
        );
        partDefinition.addOrReplaceChild(
                "jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
        );
        return LayerDefinition.create(meshDefinition, 64, 64);
    }


    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.rightSleeve, this.jacket));
    }

    @Override
    public void setupAnim(T monster, float f, float g, float h, float i, float j) {
        super.setupAnim(monster, f, g, h, i, j);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.jacket.copyFrom(this.body);
    }

    @Override
    public void setAllVisible(boolean bl) {
        super.setAllVisible(bl);
        this.leftSleeve.visible = bl;
        this.rightSleeve.visible = bl;
        this.leftPants.visible = bl;
        this.rightPants.visible = bl;
        this.jacket.visible = bl;
    }

    @Override
    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        ModelPart modelPart = this.getArm(humanoidArm);
        modelPart.translateAndRotate(poseStack);
    }
}
