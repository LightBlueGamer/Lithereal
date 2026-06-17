package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.lithereal.item.infused.InfusedItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    @ModifyReturnValue(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;", at = @At("RETURN"))
    public ItemStack assemble(ItemStack original, @Local(ordinal = 0, argsOnly = true) CraftingInput craftingInput) {
        AtomicReference<ItemStack> ret = new AtomicReference<>(original);
        if (original.getItem() instanceof InfusedItem) craftingInput.items().forEach(itemStack -> {
            if (itemStack.getItem() instanceof InfusedItem) {
                PotionContents oldContents = original.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                PotionContents newContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                if (oldContents == PotionContents.EMPTY) original.set(DataComponents.POTION_CONTENTS, newContents);
                else if (!oldContents.equals(newContents)) ret.set(ItemStack.EMPTY);
            }
        });
        return ret.get();
    }
}
