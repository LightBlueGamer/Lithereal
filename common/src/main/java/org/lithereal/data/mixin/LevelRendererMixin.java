package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.*;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix4f;
import org.lithereal.data.extension.RenderBuffersExtension;
import org.lithereal.util.EtherealUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    @Final
    private RenderBuffers renderBuffers;

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderEntity(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V"))
    public void renderSpecialForEthereal(LevelRenderer instance, Entity entity, double d, double e, double f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, Operation<Void> original) {
        MultiBufferSource newBufferSource = multiBufferSource;
        float ticksExisted = entity.tickCount + g;
        if (EtherealUtils.isEntityPhantom(entity)) {
            newBufferSource = ((RenderBuffersExtension)this.renderBuffers).lithereal$auraBufferSource().setAltBufferSource(multiBufferSource).setOffset(ticksExisted * 0.005F % 1.0F);
        }
        original.call(instance, entity, d, e, f, g, poseStack, newBufferSource);
        ((RenderBuffersExtension)this.renderBuffers).lithereal$auraBufferSource().setAltBufferSource(null);
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V"))
    public void endAuraBatch(DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        ((RenderBuffersExtension)this.renderBuffers).lithereal$auraBufferSource().endAuraBatch();
    }
}
