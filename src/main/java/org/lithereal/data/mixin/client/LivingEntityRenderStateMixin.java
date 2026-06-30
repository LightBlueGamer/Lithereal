package org.lithereal.data.mixin.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.lithereal.data.extension.client.LivingEntityRenderStateExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements LivingEntityRenderStateExtension {
    @Unique
    int lithereal$chill;

    @Override
    public int lithereal$getChill() {
        return this.lithereal$chill;
    }

    @Override
    public void lithereal$setChill(int chill) {
        this.lithereal$chill = chill;
    }
}
