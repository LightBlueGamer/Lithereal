package org.lithereal.item.ability;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lithereal.core.component.SpecialAbility;

import java.util.Map;

public interface IAbility {
    void onAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    void postAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    void onItemTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot);
    void onArmourTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot);
    default float getLavaMovementEfficiency(SpecialAbility ability, ItemStack itemStack, LivingEntity user, float efficiency) {
        return efficiency;
    }

    static int getValueFromMapForEffect(Map<Holder<MobEffect>, Integer> effectMap, Holder<MobEffect> effectHolder) {
        if (!effectMap.containsKey(effectHolder)) effectMap.put(effectHolder, 0);
        return effectMap.getOrDefault(effectHolder, -1);
    }

    MapCodec<? extends IAbility> type();
}
