package org.lithereal.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.ModArmorMaterials;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CommonUtils {
    public static final List<ResourceKey<EquipmentAsset>> CROSS_COMPATIBLE_LITHERITE = List.of(ModArmorMaterials.SMOLDERING_LITHERITE.assetId(), ModArmorMaterials.FROSTBITTEN_LITHERITE.assetId(), ModArmorMaterials.WITHERING_LITHERITE.assetId(), ModArmorMaterials.INFUSED_LITHERITE.assetId());
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

    public static boolean hasCorrectArmorOn(List<ResourceKey<EquipmentAsset>> materials, LivingEntity livingEntity, EquipmentSlot.Type slotType) {
        boolean hasCorrectArmorOn = true;
        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            if (slot.getType() != slotType) continue;
            ItemStack armorStack = livingEntity.getItemBySlot(slot);
            Equippable equippable = armorStack.get(DataComponents.EQUIPPABLE);
            Optional<ResourceKey<EquipmentAsset>> assetId;
            if(equippable == null || (assetId = equippable.assetId()).isEmpty())
                return false;
            hasCorrectArmorOn &= materials.contains(assetId.get());
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
