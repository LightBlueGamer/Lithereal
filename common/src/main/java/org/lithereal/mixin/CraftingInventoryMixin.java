package org.lithereal.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TransientCraftingContainer.class)
public class CraftingInventoryMixin {
	@Shadow
	@Final
	private AbstractContainerMenu menu;

	@Inject(method = "setChanged", at = @At("HEAD"))
	public void onMarkDirty(CallbackInfo callbackInfo) {
		menu.slotsChanged((TransientCraftingContainer) (Object) this);
	}
}