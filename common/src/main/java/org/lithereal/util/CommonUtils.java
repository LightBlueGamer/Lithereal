package org.lithereal.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CommonUtils {
    public static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }
    public static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
    public static boolean hasFullSuitOfArmorOn(Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            bl &= !armorStack.isEmpty();
        }

        return bl;
    }

    public static boolean hasCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        return hasCorrectArmorOn(Collections.singletonList(material), player);
    }

    public static boolean hasCorrectArmorOn(List<Holder<ArmorMaterial>> materials, Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
            bl &= materials.contains(((ArmorItem)armorStack.getItem()).getMaterial());
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

    public static <T extends TooltipProvider> void addToTooltip(ItemStack stack, DataComponentType<T> dataComponentType, Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        T tooltipProvider = stack.get(dataComponentType);
        if (tooltipProvider != null) {
            tooltipProvider.addToTooltip(tooltipContext, consumer, tooltipFlag);
        }

    }
}
