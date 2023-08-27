package org.litecraft.lithereal.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.litecraft.lithereal.block.entity.ModBlockEntities;

public class InfusedLitheriteBlockEntityRenderer implements BlockEntityRenderer<InfusedLitheriteBlockEntity> {
    public static final ResourceLocation texture = new ResourceLocation(Lithereal.MOD_ID, "textures/block/infused_litherite_block_entity.png");
    public final InfusedLitheriteBlockEntityModel model;
    public InfusedLitheriteBlockEntityRenderer(BlockEntityRendererProvider.Context p_i226006_1_) {
        model = new InfusedLitheriteBlockEntityModel(p_i226006_1_.bakeLayer(InfusedLitheriteBlockEntityModel.LAYER_LOCATION));
    }
    public static void register(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.INFUSED_LITHERITE_BLOCK.get(), InfusedLitheriteBlockEntityRenderer::new);
    }

    @Override
    public void render(InfusedLitheriteBlockEntity blockEntity, float p_112308_, PoseStack matrixStack, MultiBufferSource multiBufferSource, int p_112311_, int p_112312_) {
        VertexConsumer builder = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        matrixStack.pushPose();
        matrixStack.translate(0.5f, 1.5f, 0.5f);
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
        int i = PotionUtils.getColor(blockEntity.getStoredPotion());
        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;
        model.renderToBuffer(matrixStack, builder, p_112311_, p_112312_, f,f1,f2,1);
        matrixStack.popPose();
    }
}