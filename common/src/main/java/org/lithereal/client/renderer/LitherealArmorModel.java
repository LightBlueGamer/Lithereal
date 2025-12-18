// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package org.lithereal.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import org.lithereal.Lithereal;

public class LitherealArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation INNER_ARMOR = new ModelLayerLocation(Lithereal.id("base_inner_armor"), "main");
    public static final ModelLayerLocation OUTER_ARMOR = new ModelLayerLocation(Lithereal.id("base_outer_armor"), "main");

	public LitherealArmorModel(ModelPart root) {
        super(root);
	}

	public static MeshDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 12.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(0.0F, 12.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation.extend(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)), PartPose.offsetAndRotation(3.0607F, 11.8237F, -1.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)), PartPose.offsetAndRotation(-3.3218F, 9.8408F, 1.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.0F))
                .texOffs(48, 0).addBox(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.125F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.0F))
                .texOffs(48, 0).mirror().addBox(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.125F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation.extend(0.0F))
                .texOffs(0, 1).addBox(3.0F, -12.0F, -5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 24).addBox(2.0F, -13.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 20).addBox(2.0F, -14.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 20).mirror().addBox(-4.0F, -14.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).mirror().addBox(-5.0F, -12.0F, -5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 24).mirror().addBox(-4.0F, -13.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return meshdefinition;
	}
}