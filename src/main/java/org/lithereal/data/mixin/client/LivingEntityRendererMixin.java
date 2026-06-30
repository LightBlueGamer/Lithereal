package org.lithereal.data.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.lithereal.data.extension.client.LivingEntityRenderStateExtension;
import org.lithereal.util.ChillData;
import org.lithereal.util.ChillState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @WrapOperation(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getModelTint(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)I"))
    public int addChilledTint(LivingEntityRenderer<T, S, M> instance, S state, Operation<Integer> original) {
        int result = original.call(instance, state);
        int chill = ((LivingEntityRenderStateExtension) state).lithereal$getChill();
        if (chill > 0) {
            result = ChillState.modifyColor(result, chill);
        }
        return result;
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At(value = "TAIL"))
    public void extractChill(T entity, S state, float partialTicks, CallbackInfo ci) {
        ((LivingEntityRenderStateExtension) state).lithereal$setChill(ChillData.get(entity).chill());
    }
}
