package org.lithereal.fabric.data.mixin.client;

//? fabric {
/*import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.server.packs.resources.ResourceManager;
import org.lithereal.client.model.LitherealArmorModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Shadow
    @Final
    private Supplier<EntityModelSet> entityModels;

    @Inject(method = "onResourceManagerReload", at = @At(value = "TAIL"))
    public void injectModelCapture(ResourceManager resourceManager, CallbackInfo ci) {
        LitherealArmorModel.ARMOR_MODEL_SET = ArmorModelSet.bake(LitherealArmorModel.LITHEREAL_ARMOR_SET, this.entityModels.get(), LitherealArmorModel::new);
        LitherealArmorModel.BABY_ARMOR_MODEL_SET = ArmorModelSet.bake(LitherealArmorModel.LITHEREAL_ARMOR_SET_BABY, this.entityModels.get(), LitherealArmorModel::new);
    }
}
*///?}