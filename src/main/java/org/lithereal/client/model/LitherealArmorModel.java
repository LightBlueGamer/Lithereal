// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package org.lithereal.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import org.lithereal.Lithereal;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class LitherealArmorModel extends HumanoidModel<HumanoidRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ArmorModelSet<ModelLayerLocation> LITHEREAL_ARMOR_SET = createArmorSet(Lithereal.id("base_armor"));
    public static final ArmorModelSet<ModelLayerLocation> LITHEREAL_ARMOR_SET_BABY = createArmorSet(Lithereal.id("base_armor_baby"));
    public static ArmorModelSet<LitherealArmorModel> ARMOR_MODEL_SET;
    public static ArmorModelSet<LitherealArmorModel> BABY_ARMOR_MODEL_SET;

    private static ArmorModelSet<ModelLayerLocation> createArmorSet(final Identifier modelId) {
        return new ArmorModelSet<>(new ModelLayerLocation(modelId, "helmet"), new ModelLayerLocation(modelId, "chestplate"), new ModelLayerLocation(modelId, "leggings"), new ModelLayerLocation(modelId, "boots"));
    }

	public LitherealArmorModel(ModelPart root) {
        super(root);
	}

	public static MeshDefinition createBodyLayer(CubeDeformation deformation, float offset) {
        MeshDefinition mesh = HumanoidModel.createMesh(deformation, offset);
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.getChild("body");

        body.addOrReplaceChild("attached_1", CubeListBuilder.create()
                        .texOffs(24, 0).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)),
                PartPose.offsetAndRotation(3.0607F, 11.8237F + offset, -1.0F, 0.0F, 0.0F, 0.1309F));

        body.addOrReplaceChild("attached_2", CubeListBuilder.create()
                        .texOffs(24, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)),
                PartPose.offsetAndRotation(-3.3218F, 9.8408F + offset, 1.0F, 0.0F, 0.0F, -0.1309F));

        root.getChild("left_arm").addOrReplaceChild("left_sleeve", CubeListBuilder.create()
                        .mirror().texOffs(48, 0)
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)),
                PartPose.ZERO);

        root.getChild("right_arm").addOrReplaceChild("right_sleeve", CubeListBuilder.create()
                        .texOffs(48, 0)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)),
                PartPose.ZERO);

        PartDefinition head = root.getChild("head");

        head.addOrReplaceChild("horns", CubeListBuilder.create()
                .texOffs(0, 1).addBox(3.25F, -12.0F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 24).addBox(2.25F, -13.0F, -4.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 20).addBox(2.25F, -14.0F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 20).mirror().addBox(-4.25F, -14.0F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).mirror().addBox(-5.25F, -12.0F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 24).mirror().addBox(-4.25F, -13.0F, -4.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);

        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		return mesh;
	}

    private static MeshDefinition createBabyArmorMesh(final CubeDeformation g, final PartPose armOffset) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild(
                "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -7.0F, -4.5F, 9.0F, 8.0F, 8.0F, g), PartPose.offset(0.0F, 15.0F, 0.0F)
        );
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 5.0F, 3.0F, g), PartPose.offset(0.0F, 18.0F, 0.0F));
        root.addOrReplaceChild(
                "waist", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, -1.2F, -1.49F, 5.9F, 2.0F, 2.9F, g.extend(-0.1F)), PartPose.offset(0.0F, 19.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create().texOffs(30, 25).addBox(-1.0F, 0.0F, -1.53F, 2.0F, 5.0F, 3.0F, g),
                PartPose.offset(-3.5F - armOffset.x(), 15.5F + armOffset.y(), 0.0F + armOffset.z())
        );
        root.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create().texOffs(30, 17).addBox(-1.0F, 0.0F, -1.53F, 2.0F, 5.0F, 3.0F, g),
                PartPose.offset(3.5F + armOffset.x(), 15.5F + armOffset.y(), 0.0F + armOffset.z())
        );
        root.addOrReplaceChild(
                "inner_body", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 5.0F, 3.0F, g), PartPose.offset(0.0F, 18.0F, 0.0F)
        );
        PartDefinition rightLeg = root.addOrReplaceChild(
                "left_leg", CubeListBuilder.create().texOffs(18, 24).addBox(-2.0F, -0.2F, -2.0F, 3.0F, 4.0F, 3.0F, g.extend(-0.1F)), PartPose.offset(1.5F, 20.0F, 0.5F)
        );
        PartDefinition leftLeg = root.addOrReplaceChild(
                "right_leg", CubeListBuilder.create().texOffs(18, 17).addBox(-1.0F, -0.2F, -2.0F, 3.0F, 4.0F, 3.0F, g.extend(-0.1F)), PartPose.offset(-1.5F, 20.0F, 0.5F)
        );
        rightLeg.addOrReplaceChild(
                "right_foot", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, 2.9F, -2.0F, 3.0F, 1.0F, 3.0F, g), PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        leftLeg.addOrReplaceChild(
                "left_foot",
                CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-1.0F, 2.9F, -2.0F, 3.0F, 1.0F, 3.0F, g).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        return mesh;
    }

    public static MeshDefinition createBabyBodyLayer(CubeDeformation deformation, PartPose armOffset) {
        MeshDefinition meshdefinition = createBabyArmorMesh(deformation, armOffset);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.getChild("body");

        body.addOrReplaceChild("attached_1", CubeListBuilder.create()
                        .texOffs(24, 0).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)),
                PartPose.offsetAndRotation(3.0607F, 11.8237F, -1.0F, 0.0F, 0.0F, 0.1309F).scaled(0.5F));

        body.addOrReplaceChild("attached_2", CubeListBuilder.create()
                        .texOffs(24, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 4.0F, deformation.extend(0.125F)),
                PartPose.offsetAndRotation(-3.3218F, 9.8408F, 1.0F, 0.0F, 0.0F, -0.1309F).scaled(0.5F));

        partdefinition.getChild("left_arm").addOrReplaceChild("left_sleeve", CubeListBuilder.create()
                        .texOffs(30, 17).addBox(-1.0F, 0.0F, -1.53F, 2.0F, 5.0F, 3.0F, deformation.extend(0.25F)),
                PartPose.ZERO);

        partdefinition.getChild("right_arm").addOrReplaceChild("right_sleeve", CubeListBuilder.create()
                        .texOffs(30, 25).addBox(-1.0F, 0.0F, -1.53F, 2.0F, 5.0F, 3.0F, deformation.extend(0.25F)),
                PartPose.ZERO);

        PartDefinition head = partdefinition.getChild("head");

        head.addOrReplaceChild("horns", CubeListBuilder.create()
                        .texOffs(0, 1).addBox(3.25F, -12.0F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 24).addBox(2.25F, -13.0F, -4.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 20).addBox(2.25F, -14.0F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 20).mirror().addBox(-4.25F, -14.0F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 1).mirror().addBox(-5.25F, -12.0F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 24).mirror().addBox(-4.25F, -13.0F, -4.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO.scaled(0.5F));

        return meshdefinition;
    }

    public static ArmorModelSet<MeshDefinition> createArmorMeshSet(final CubeDeformation innerDeformation, final CubeDeformation outerDeformation) {
        return createArmorMeshSet(LitherealArmorModel::createBodyLayer, ADULT_ARMOR_PARTS_PER_SLOT, innerDeformation, outerDeformation);
    }

    private static MeshDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        return createBodyLayer(cubeDeformation, 0);
    }

    public static ArmorModelSet<MeshDefinition> createBabyArmorMeshSet(
            final CubeDeformation innerDeformation, final CubeDeformation outerDeformation, final PartPose armOffset
    ) {
        return createArmorMeshSet(cube -> createBabyBodyLayer(cube, armOffset), BABY_ARMOR_PARTS_PER_SLOT, innerDeformation, outerDeformation);
    }

    protected static ArmorModelSet<MeshDefinition> createArmorMeshSet(
            final Function<CubeDeformation, MeshDefinition> baseFactory,
            final Map<EquipmentSlot, Set<String>> partsPerSlot,
            final CubeDeformation innerDeformation,
            final CubeDeformation outerDeformation
    ) {
        MeshDefinition head = baseFactory.apply(outerDeformation);
        head.getRoot().retainPartsAndChildren(partsPerSlot.get(EquipmentSlot.HEAD));
        MeshDefinition chest = baseFactory.apply(outerDeformation);
        chest.getRoot().retainExactParts(partsPerSlot.get(EquipmentSlot.CHEST));
        MeshDefinition legs = baseFactory.apply(innerDeformation);
        legs.getRoot().retainExactParts(partsPerSlot.get(EquipmentSlot.LEGS));
        MeshDefinition feet = baseFactory.apply(outerDeformation);
        feet.getRoot().retainExactParts(partsPerSlot.get(EquipmentSlot.FEET));
        return new ArmorModelSet<>(head, chest, legs, feet);
    }
}