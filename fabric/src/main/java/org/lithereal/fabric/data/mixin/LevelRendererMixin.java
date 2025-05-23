package org.lithereal.fabric.data.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lithereal.client.EtherealCoreSpecialEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @Shadow private @Nullable ClientLevel level;

    @Shadow private int ticks;

    @WrapMethod(method = "renderSky")
    public void renderSkyWithCustom(Matrix4f matrix4f, Matrix4f matrix4f2, float f, Camera camera, boolean bl, Runnable runnable, Operation<Void> original) {
        if (!(this.minecraft.level.effects() instanceof EtherealCoreSpecialEffects etherealCoreSpecialEffects && etherealCoreSpecialEffects.renderSky(this.level, this.ticks, f, matrix4f, camera, matrix4f2, bl, runnable))) original.call(matrix4f, matrix4f2, f, camera, bl, runnable);
    }
}
