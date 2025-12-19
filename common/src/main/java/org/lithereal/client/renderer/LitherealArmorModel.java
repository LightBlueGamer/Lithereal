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

public class LitherealArmorModel extends HumanoidModel<LivingEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation INNER_ARMOR = new ModelLayerLocation(Lithereal.id("base_inner_armor"), "main");
    public static final ModelLayerLocation OUTER_ARMOR = new ModelLayerLocation(Lithereal.id("base_outer_armor"), "main");
    public static LitherealArmorModel INNER;
    public static LitherealArmorModel OUTER;

	public LitherealArmorModel(ModelPart root) {
        super(root);
	}

	public static MeshDefinition createBodyLayer(CubeDeformation deformation, float offset) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, offset);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                        .texOffs(24, 0).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)),
                PartPose.offsetAndRotation(3.0607F, 11.8237F, -1.0F, 0.0F, 0.0F, 0.1309F));

        body.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                        .texOffs(24, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)),
                PartPose.offsetAndRotation(-3.3218F, 9.8408F, 1.0F, 0.0F, 0.0F, -0.1309F));

        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().mirror()
                        .texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation)
                        .texOffs(48, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)),
                PartPose.offset(-5.0F, 2.0F + offset, 0.0F));

        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation)
                        .texOffs(48, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)),
                PartPose.offset(5.0F, 2.0F + offset, 0.0F));

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation)
                .texOffs(0, 1).addBox(3.25F, -12.0F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 24).addBox(2.25F, -13.0F, -4.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 20).addBox(2.25F, -14.0F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 20).mirror().addBox(-4.25F, -14.0F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).mirror().addBox(-5.25F, -12.0F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 24).mirror().addBox(-4.25F, -13.0F, -4.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0, offset, 0));

		return meshdefinition;
	}
}