package org.lithereal.client.renderer;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class IvRenderHelper {
    public static final float width = 2.5f;

    public static void renderLights(float ticks, float scale, float height, int color, float alpha, int number, PoseStack poseStack, MultiBufferSource multiBufferSource) {
        submitRays(ticks, scale, height, color, alpha, number, poseStack, multiBufferSource, RenderType.dragonRays());
        submitRays(ticks, scale, height, color, alpha, number, poseStack, multiBufferSource, RenderType.dragonRaysDepth());
    }
    private static void submitRays(float ticks, float scale, float height, int color, float alpha, int number, PoseStack poseStack, MultiBufferSource multiBufferSource, RenderType renderType) {
        poseStack.pushPose();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
        PoseStack.Pose pose = poseStack.last();
        float progress = ticks / 200.0F;
        float g = Math.min(progress > 0.8F ? (progress - 0.8F) / 0.2F : 0.0F, 1.0F);
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        int brtRed = Math.min(red + 30, 255);
        int brtGreen = Math.min(green + 30, 255);
        int brtBlue = Math.min(blue + 30, 255);
        RandomSource randomSource = RandomSource.create(432L);
        Vector3f vector3f = new Vector3f();
        Vector3f vector3f2 = new Vector3f();
        Vector3f vector3f3 = new Vector3f();
        Vector3f vector3f4 = new Vector3f();
        Quaternionf quaternionf = new Quaternionf();
        poseStack.translate(0.0F, height + 0.1F, 0.0F);
        poseStack.scale(scale, scale, scale);

        for (int n = 0; n < number; n++) {
            float xLogFunc = (((float) n / number * 28493.0f + ticks) / 10.0f) % 20.0f;
            if (xLogFunc > 10.0f) {
                xLogFunc = 20.0f - xLogFunc;
            }
            float lightAlpha = 1.0f / (1.0f + (float) Math.pow(2.71828f, -0.8f * xLogFunc) * ((1.0f / 0.01f) - 1.0f));
            if (lightAlpha > 0.01f) {
                quaternionf.rotationXYZ(
                                randomSource.nextFloat() * (float) (Math.PI * 2), randomSource.nextFloat() * (float) (Math.PI * 2), randomSource.nextFloat() * (float) (Math.PI * 2)
                        )
                        .rotateXYZ(
                                randomSource.nextFloat() * (float) (Math.PI * 2),
                                randomSource.nextFloat() * (float) (Math.PI * 2),
                                randomSource.nextFloat() * (float) (Math.PI * 2) + progress * (float) (Math.PI / 2)
                        );
                poseStack.mulPose(quaternionf);
                float h = randomSource.nextFloat() * 20.0F + 5.0F + g * 10.0F;
                float m = randomSource.nextFloat() * 2.0F + 1.0F + g * 2.0F;
                vector3f2.set(-width * m, h, -0.5F * m);
                vector3f3.set(width * m, h, -0.5F * m);
                vector3f4.set(0.0F, h, m);
                int finalAlpha = (int) (alpha * lightAlpha);
                int weakerAlpha = (int) (finalAlpha * 0.5);
                vertexConsumer.addVertex(pose, vector3f).setColor(red, green, blue, finalAlpha);
                vertexConsumer.addVertex(pose, vector3f2).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f3).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f).setColor(red, green, blue, finalAlpha);
                vertexConsumer.addVertex(pose, vector3f3).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f4).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f).setColor(red, green, blue, finalAlpha);
                vertexConsumer.addVertex(pose, vector3f4).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f2).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
            }
        }
        poseStack.popPose();
    }
    public static void renderTrail(Vec3 dir, double height, float scale, int color, float alpha, PoseStack poseStack, MultiBufferSource multiBufferSource) {
        submitTrail(dir, height, scale, color, alpha, poseStack, multiBufferSource, RenderType.dragonRays());
        submitTrail(dir, height, scale, color, alpha, poseStack, multiBufferSource, RenderType.dragonRaysDepth());
    }
    private static void submitTrail(Vec3 dir, double height, float scale, int color, float alpha, PoseStack poseStack, MultiBufferSource multiBufferSource, RenderType renderType) {
        poseStack.pushPose();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
        PoseStack.Pose pose = poseStack.last();
        float xRot = (float) Math.acos(new Vec3(0, dir.y, dir.z).normalize().dot(new Vec3(0, 1 ,0)));
        float zRot = (float) Math.acos(new Vec3(dir.x, dir.y, 0).normalize().dot(new Vec3(0, 1 ,0)));
        float yRot = (float) Math.acos(new Vec3(dir.x, 0, dir.z).normalize().dot(new Vec3(0, 0 ,1)));
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        int brtRed = Math.min(red + 30, 255);
        int brtGreen = Math.min(green + 30, 255);
        int brtBlue = Math.min(blue + 30, 255);
        float v = (float) (height * 0.5);
        float h = 5.0F;
        Vector3f vector3f = new Vector3f(0, (float) height + v, 0);
        Vector3f vector3f2 = new Vector3f(-width * h, (float) height + v * 0.125F, -0.5F * h);
        Vector3f vector3f3 = new Vector3f(width * h, (float) height - v * 0.125F, -0.5F * h);
        Vector3f vector3f4 = new Vector3f(0.0F, (float) height - v, h);
        Quaternionf quaternionf = new Quaternionf().rotationXYZ(xRot, yRot, zRot);
        poseStack.scale(scale, scale, scale);
        for (int n = 0; n < 10; n++) {
            float xLogFunc = (((float) n / 10 * 28493.0f + 200) / 10.0f) % 20.0f;
            if (xLogFunc > 10.0f) {
                xLogFunc = 20.0f - xLogFunc;
            }
            float lightAlpha = 1.0f / (1.0f + (float) Math.pow(2.71828f, -0.8f * xLogFunc) * ((1.0f / 0.01f) - 1.0f));
            if (lightAlpha > 0.01f) {
                poseStack.mulPose(quaternionf);
                int finalAlpha = (int) (alpha * lightAlpha);
                int weakerAlpha = (int) (finalAlpha * 0.5);
                vertexConsumer.addVertex(pose, vector3f).setColor(red, green, blue, finalAlpha);
                vertexConsumer.addVertex(pose, vector3f2).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f3).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f).setColor(red, green, blue, finalAlpha);
                vertexConsumer.addVertex(pose, vector3f3).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f4).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f).setColor(red, green, blue, finalAlpha);
                vertexConsumer.addVertex(pose, vector3f4).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
                vertexConsumer.addVertex(pose, vector3f2).setColor(brtRed, brtGreen, brtBlue, weakerAlpha);
            }
        }
        poseStack.popPose();
    }
}