package org.lithereal.data.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.lithereal.item.infused.InfusedItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeMixin {

    @Shadow
    @Final
    private ItemStack result;

    @Inject(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    public void assemble(CraftingContainer craftingContainer, HolderLookup.Provider provider, CallbackInfoReturnable<ItemStack> cir) {
        AtomicBoolean bl = new AtomicBoolean(true);
        ItemStack result = this.result.copy();
        if(result.getItem() instanceof InfusedItem) craftingContainer.getItems().forEach(itemStack -> {
            if (itemStack.getItem() instanceof InfusedItem) {
                PotionContents oldContents = result.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                PotionContents newContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                if(oldContents == PotionContents.EMPTY) result.set(DataComponents.POTION_CONTENTS, newContents);
                else if(oldContents != newContents) bl.set(false);
            }
        });
        if(!bl.get()) cir.setReturnValue(ItemStack.EMPTY);
        else cir.setReturnValue(result);
    }
}
