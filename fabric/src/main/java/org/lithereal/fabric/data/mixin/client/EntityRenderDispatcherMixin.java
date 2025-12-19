package org.lithereal.fabric.data.mixin.client;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.server.packs.resources.ResourceManager;
import org.lithereal.client.renderer.LitherealArmorModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Shadow
    @Final
    private EntityModelSet entityModels;

    @Inject(method = "onResourceManagerReload", at = @At(value = "TAIL"))
    public void injectModelCapture(ResourceManager resourceManager, CallbackInfo ci) {
        LitherealArmorModel.OUTER = new LitherealArmorModel(this.entityModels.bakeLayer(LitherealArmorModel.OUTER_ARMOR));
        LitherealArmorModel.INNER = new LitherealArmorModel(this.entityModels.bakeLayer(LitherealArmorModel.INNER_ARMOR));
    }
}
