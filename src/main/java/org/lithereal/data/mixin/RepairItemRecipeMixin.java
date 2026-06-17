package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import org.lithereal.item.infused.InfusedItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {
    @ModifyReturnValue(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;", at = @At("TAIL"))
    public ItemStack assemble(ItemStack original, @Local(ordinal = 0) Pair<ItemStack, ItemStack> input) {
        if (original.getItem() instanceof InfusedItem)
            if (input.getSecond().getItem() instanceof InfusedItem) {
                PotionContents outputContents = original.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                PotionContents firstContents = input.getFirst().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                PotionContents secondContents = input.getSecond().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                if (!firstContents.equals(secondContents)) return ItemStack.EMPTY;
                if (outputContents == PotionContents.EMPTY) original.set(DataComponents.POTION_CONTENTS, firstContents);
            }
        return original;
    }
}
