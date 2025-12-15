package org.lithereal.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import static net.minecraft.client.renderer.blockentity.TheEndPortalRenderer.END_SKY_LOCATION;

@Environment(EnvType.CLIENT)
public class EtherealCoreSpecialEffects extends DimensionSpecialEffects {
    private static final int SKYBOX_COLOR = 2039623;
    public EtherealCoreSpecialEffects() {
        super(Float.NaN, true, SkyType.NONE, true, false);
    }

    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 arg, float f) {
        return arg.multiply(f * 0.94F + 0.06F, f * 0.94F + 0.06F, f * 0.91F + 0.09F);
    }

    public boolean isFoggyAt(int i, int j) {
        return false;
    }

    @Override
    public float @NotNull [] getSunriseColor(float f, float g) {
        return null;
    }
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        FogType fogType = camera.getFluidInCamera();
        if (fogType != FogType.POWDER_SNOW && fogType != FogType.LAVA && !this.doesMobEffectBlockSky(camera)) {
            PoseStack poseStack = new PoseStack();
            poseStack.mulPose(modelViewMatrix);
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();

            for (int i = 0; i < 6; i++) {
                poseStack.pushPose();
                switch (i) {
                    case 1 -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                    case 2 -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                    case 3 -> poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                    case 4 -> poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                    case 5 -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                }

                Matrix4f matrix4f = poseStack.last().pose();
                BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(FastColor.ARGB32.color(255, SKYBOX_COLOR));
                bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, 16.0F).setColor(FastColor.ARGB32.color(255, SKYBOX_COLOR));
                bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(16.0F, 16.0F).setColor(FastColor.ARGB32.color(255, SKYBOX_COLOR));
                bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(16.0F, 0.0F).setColor(FastColor.ARGB32.color(255, SKYBOX_COLOR));
                BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
                poseStack.popPose();
            }

            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
        }
        return true;
    }

    private boolean doesMobEffectBlockSky(Camera camera) {
        return camera.getEntity() instanceof LivingEntity livingEntity && (livingEntity.hasEffect(MobEffects.BLINDNESS) || livingEntity.hasEffect(MobEffects.DARKNESS));
    }
}