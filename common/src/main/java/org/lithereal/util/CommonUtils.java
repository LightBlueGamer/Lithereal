package org.lithereal.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.lithereal.core.component.ModComponents;
import org.lithereal.item.component.Enhanced;

import java.util.Optional;
import java.util.function.Consumer;

public class CommonUtils {
    public static Block DRIPSTONE_REPLACEMENT;
    public static boolean hasFullSuitOfArmorOn(Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            bl &= !armorStack.isEmpty();
        }

        return bl;
    }

    public static boolean hasCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
            bl &= ((ArmorItem)armorStack.getItem()).getMaterial() == material;
        }

        return bl;
    }

    @SafeVarargs
    public static <T> boolean isAnyOf(T t, T... objects) {
        boolean ret = false;
        for (T object : objects) {
            ret |= t == object;
        }
        return ret;
    }
    public static MobEffectInstance clone(MobEffectInstance original) {
        return new MobEffectInstance(original);
    }

    public static NonNullList<Ingredient> of(Ingredient... values) {
        return NonNullList.of(Ingredient.EMPTY, values);
    }

    public static boolean isEnhanced(ItemStack stack) {
        return stack.has(ModComponents.ENHANCED.get());
    }

    public static boolean isEnhanced(CompoundTag tag) {
        return tag != null && tag.getBoolean("Enhanced");
    }

    public static ItemStack setEnhanced(ItemStack stack, boolean showInTooltip, MutableComponent component) {
        stack.set(ModComponents.ENHANCED.get(), new Enhanced(showInTooltip, Optional.of(component.withStyle(ChatFormatting.BLUE))));
        return stack;
    }

    public static ItemStack removeEnhanced(ItemStack stack) {
        stack.remove(ModComponents.ENHANCED.get());
        return stack;
    }

    public static void setEnhanced(CompoundTag tag, boolean bl) {
        tag.putBoolean("Enhanced", bl);
    }

    public static <T extends TooltipProvider> void addToTooltip(ItemStack stack, DataComponentType<T> dataComponentType, Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        T tooltipProvider = stack.get(dataComponentType);
        if (tooltipProvider != null) {
            tooltipProvider.addToTooltip(tooltipContext, consumer, tooltipFlag);
        }

    }
}
