package org.lithereal.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.base.ModArmorItem;

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
    public static boolean hasFullSuitOfArmorOn(LivingEntity livingEntity, EquipmentSlot.Type slotType) {
        boolean hasFullSuitOn = true;
        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            if (slot.getType() != slotType) continue;
            ItemStack armorStack = livingEntity.getItemBySlot(slot);
            hasFullSuitOn &= !armorStack.isEmpty();
        }

        return hasFullSuitOn;
    }

    public static boolean hasCorrectArmorOn(ArmorMaterial material, LivingEntity livingEntity, EquipmentSlot.Type slotType) {
        return hasCorrectArmorOn(Collections.singletonList(material), livingEntity, slotType);
    }

    public static boolean hasCorrectArmorOn(List<ArmorMaterial> materials, LivingEntity livingEntity, EquipmentSlot.Type slotType) {
        boolean hasCorrectArmorOn = true;
        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            if (slot.getType() != slotType) continue;
            ItemStack armorStack = livingEntity.getItemBySlot(slot);
            if(!(armorStack.getItem() instanceof ModArmorItem armorItem)) {
                return false;
            }
            hasCorrectArmorOn &= materials.contains(armorItem.getArmorMaterial());
        }

        return hasCorrectArmorOn;
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
        return NonNullList.of(Ingredient.of(), values);
    }

    public static <T extends TooltipProvider> void addToTooltip(ItemStack stack, DataComponentType<T> dataComponentType, Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        T tooltipProvider = stack.get(dataComponentType);
        if (tooltipProvider != null) {
            tooltipProvider.addToTooltip(tooltipContext, consumer, tooltipFlag, stack);
        }

    }
}
