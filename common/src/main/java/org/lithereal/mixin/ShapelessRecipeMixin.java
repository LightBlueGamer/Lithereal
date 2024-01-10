package org.lithereal.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.lithereal.item.custom.infused.InfusedItem;
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

    @Inject(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    public void assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess, CallbackInfoReturnable<ItemStack> cir) {
        AtomicBoolean bl = new AtomicBoolean(true);
        ItemStack result = this.result.copy();
        if(result.getItem() instanceof InfusedItem) craftingContainer.getItems().forEach(itemStack -> {
            if (itemStack.getItem() instanceof InfusedItem) {
                if(PotionUtils.getPotion(result) == Potions.EMPTY) PotionUtils.setPotion(result, PotionUtils.getPotion(itemStack));
                else if(PotionUtils.getPotion(result) != PotionUtils.getPotion(itemStack)) bl.set(false);
            }
        });
        if(!bl.get()) cir.setReturnValue(ItemStack.EMPTY);
        else cir.setReturnValue(result);
    }
}
