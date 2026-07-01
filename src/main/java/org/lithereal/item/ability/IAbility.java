package org.lithereal.item.ability;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lithereal.core.component.SpecialAbility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface IAbility {
    void onAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    void postAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    void onItemTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot);
    void onArmourTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot);
    default float getLavaMovementEfficiency(SpecialAbility ability, ItemStack itemStack, LivingEntity user, float efficiency) {
        return efficiency;
    }
    List<ResourceKey<EquipmentAsset>> supportedMaterials();
    void addToTooltip(SpecialAbility ability, Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter);

    static int getValueFromMapForEffect(Map<Holder<MobEffect>, Integer> effectMap, Holder<MobEffect> effectHolder) {
        if (!effectMap.containsKey(effectHolder)) effectMap.put(effectHolder, 0);
        return effectMap.getOrDefault(effectHolder, -1);
    }

    default Component combineSupportedMaterialsTogether() {
        MutableComponent ret = Component.empty();
        Component[] arr = supportedMaterials().stream()
                .map(resourceKey ->
                        Component.translatableWithFallback("equipment_material." + resourceKey.identifier().getNamespace() + "." + resourceKey.identifier().getPath(),
                                snakeCaseToName(resourceKey.identifier().getPath()))).toArray(Component[]::new);
        if (arr.length > 0) {
            for (int i = 0; i < (arr.length - 1); i++) {
                Component comp = arr[i];
                ret.append(comp.copy().append(", "));
            }
            ret.append(arr[arr.length - 1]);
        }
        return ret;
    }

    static String snakeCaseToName(String input) {
        List<Integer> capitalIndices = new ArrayList<>();
        capitalIndices.add(0);
        while (input.contains("_")) {
            int index = input.indexOf('_');
            if (index + 1 < input.length()) capitalIndices.add(index + 1);
            input = input.substring(0, index) + " " + input.substring(index + 1);
        }
        String[] output = {input};
        capitalIndices.forEach(capitalIndex -> {
            char original = output[0].charAt(capitalIndex);
            output[0] = output[0].substring(0, capitalIndex) + Character.toUpperCase(original) + (capitalIndex + 1 >= output[0].length() ? "" : output[0].substring(capitalIndex + 1));
        });
        return output[0];
    }

    MapCodec<? extends IAbility> type();
}
