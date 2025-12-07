package org.lithereal.data.mixin;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import org.lithereal.client.AuraBufferSource;
import org.lithereal.data.extension.RenderBuffersExtension;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public class RenderBuffersMixin implements RenderBuffersExtension {
    @Shadow
    @Final
    private MultiBufferSource.BufferSource bufferSource;
    @Unique
    @Mutable
    @Final
    public AuraBufferSource lithereal$auraBufferSource;
    @Inject(method = "<init>", at = @At("TAIL"))
    public void initialise(int i, CallbackInfo ci) {
        this.lithereal$auraBufferSource = new AuraBufferSource(this.bufferSource);
    }

    @Override
    public AuraBufferSource lithereal$auraBufferSource() {
        return this.lithereal$auraBufferSource;
    }
}
