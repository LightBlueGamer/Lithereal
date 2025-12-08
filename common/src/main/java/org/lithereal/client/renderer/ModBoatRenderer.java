package org.lithereal.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.lithereal.Lithereal;
import org.lithereal.entity.CustomBoat;
import org.lithereal.entity.ModBoat;

import java.util.Map;
import java.util.stream.Stream;

public class ModBoatRenderer<T extends Boat & CustomBoat> extends EntityRenderer<T> {
    public static final ModelLayerLocation PHANTOM_OAK_BOAT_LAYER = new ModelLayerLocation(
            Lithereal.id("boat/phantom_oak"), "main");
    public static final ModelLayerLocation PHANTOM_OAK_CHEST_BOAT_LAYER = new ModelLayerLocation(
            Lithereal.id("chest_boat/phantom_oak"), "main");
    private final Map<ModBoat.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public ModBoatRenderer(EntityRendererProvider.Context pContext, boolean pChestBoat) {
        super(pContext);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(ModBoat.Type.values()).collect(ImmutableMap.toImmutableMap(type -> type,
                type -> Pair.of(Lithereal.id(getTextureLocation(type, pChestBoat)), this.createBoatModel(pContext, type, pChestBoat))));
    }

    private static String getTextureLocation(ModBoat.Type pType, boolean pChestBoat) {
        return pChestBoat ? "textures/entity/chest_boat/" + pType.getName() + ".png" : "textures/entity/boat/" + pType.getName() + ".png";
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context pContext, ModBoat.Type pType, boolean pChestBoat) {
        ModelLayerLocation modellayerlocation = pChestBoat ? ModBoatRenderer.createChestBoatModelName(pType) : ModBoatRenderer.createBoatModelName(pType);
        ModelPart modelpart = pContext.bakeLayer(modellayerlocation);
        return pChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
    }

    public static ModelLayerLocation createBoatModelName(ModBoat.Type pType) {
        return createLocation("boat/" + pType.getName());
    }

    public static ModelLayerLocation createChestBoatModelName(ModBoat.Type pType) {
        return createLocation("chest_boat/" + pType.getName());
    }

    private static ModelLayerLocation createLocation(String pPath) {
        return new ModelLayerLocation(Lithereal.id(pPath), "main");
    }

    public void render(T boat, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f));
        float h = (float)boat.getHurtTime() - g;
        float j = boat.getDamage() - g;
        if (j < 0.0F) {
            j = 0.0F;
        }

        if (h > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0F * (float)boat.getHurtDir()));
        }

        float k = boat.getBubbleAngle(g);
        if (!Mth.equal(k, 0.0F)) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(boat.getBubbleAngle(g) * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = this.boatResources.get(boat.getModVariant());
        ResourceLocation resourceLocation = pair.getFirst();
        ListModel<Boat> listModel = pair.getSecond();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        listModel.setupAnim(boat, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(listModel.renderType(resourceLocation));
        listModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.waterMask());
            if (listModel instanceof WaterPatchModel waterPatchModel) {
                waterPatchModel.waterPatch().render(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY);
            }
        }

        poseStack.popPose();
        super.render(boat, f, g, poseStack, multiBufferSource, i);
    }

    public @NotNull ResourceLocation getTextureLocation(T boat) {
        return this.boatResources.get(boat.getModVariant()).getFirst();
    }
}