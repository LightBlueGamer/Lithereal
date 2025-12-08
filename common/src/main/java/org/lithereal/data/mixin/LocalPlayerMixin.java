package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.lithereal.block.entity.CustomHangingSignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @WrapOperation(method = "openTextEdit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", ordinal = 1))
    public void swapScreen(Minecraft instance, Screen old, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) SignBlockEntity entity, @Local(ordinal = 0, argsOnly = true) boolean bl) {
        if (entity instanceof CustomHangingSignBlockEntity) original.call(instance, new HangingSignEditScreen(entity, bl, instance.isTextFilteringEnabled()));
        else original.call(instance, old);
    }
}
