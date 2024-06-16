package org.lithereal.data.mixin;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResourceLocation.class)
public class ResourceLocationMixin {

	@Shadow @Mutable @Final private String path;

	@Inject(method = "<init>(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation$Dummy;)V", at = @At(value = "TAIL"))
	public void modifyNamespace(String string, String string2, ResourceLocation.Dummy dummy, CallbackInfo ci) {
		if(path.contains("cooled_litherite")) {
			path = path.replace("cooled_litherite", "frozen_litherite");
		} else if(path.contains("heated_litherite")) {
			path = path.replace("heated_litherite", "burning_litherite");
		}
	}
}