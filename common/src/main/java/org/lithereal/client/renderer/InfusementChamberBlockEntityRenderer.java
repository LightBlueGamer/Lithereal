package org.lithereal.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.alchemy.PotionContents;
import org.lithereal.Lithereal;
import org.lithereal.block.InfusementChamberBlock;
import org.lithereal.block.entity.InfusementChamberBlockEntity;

public class InfusementChamberBlockEntityRenderer implements BlockEntityRenderer<InfusementChamberBlockEntity> {
    public static final ResourceLocation textureEmpty = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/block/infusement_chamber_block_entity_empty.png");
    public static final ResourceLocation textureFilled = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/block/infusement_chamber_block_entity_filled.png");
    public static final ResourceLocation textureOverlay = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/block/infusement_chamber_block_entity_overlay.png");

    public final InfusementChamberBlockEntityModel model;
    public InfusementChamberBlockEntityRenderer(BlockEntityRendererProvider.Context p_i226006_1_) {
        model = new InfusementChamberBlockEntityModel(p_i226006_1_.bakeLayer(InfusementChamberBlockEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(InfusementChamberBlockEntity blockEntity, float p_112308_, PoseStack matrixStack, MultiBufferSource multiBufferSource, int packedLight, int p_112312_) {
        VertexConsumer builder = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(textureEmpty));
        if (!blockEntity.getStoredItem().isEmpty()) builder = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(textureFilled));
        matrixStack.pushPose();
        matrixStack.translate(0.5f, 1.5f, 0.5f);
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockState().getValue(InfusementChamberBlock.FACING).getOpposite().toYRot()));
        model.renderToBuffer(matrixStack, builder, 15728880, p_112312_, FastColor.ARGB32.colorFromFloat(1, 1, 1, 1));
        if(blockEntity.getStoredPotion() != PotionContents.EMPTY) {
            int hex = blockEntity.getStoredPotion().getColor();
            builder = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(textureOverlay));
            model.renderToBuffer(matrixStack, builder, 15728880, p_112312_, FastColor.ARGB32.color(255, hex));
        }
        matrixStack.popPose();
    }
}