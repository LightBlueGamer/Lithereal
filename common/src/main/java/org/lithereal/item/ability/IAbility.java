package org.lithereal.item.ability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public interface IAbility<I extends AbilityItem> {
    default boolean canCast(AbilityItem abilityItem) {
        return true;
    }
    default @Nullable I checkedCast(AbilityItem abilityItem) {
        if (!canCast(abilityItem)) return null;
        return (I) abilityItem;
    }
    default void onAttackRaw(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        I castedItem = checkedCast(item);
        if (castedItem == null) return;
        onAttack(castedItem, itemStack, attacked, attacker);
    }
    default void postAttackRaw(AbilityItem item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        I castedItem = checkedCast(item);
        if (castedItem == null) return;
        postAttack(castedItem, itemStack, attacked, attacker);
    }
    default void onItemTickRaw(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        I castedItem = checkedCast(item);
        if (castedItem == null) return;
        onItemTick(castedItem, itemStack, level, entity, slot, isSelected);
    }
    default void onArmourTickRaw(AbilityItem item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        I castedItem = checkedCast(item);
        if (castedItem == null) return;
        onArmourTick(castedItem, itemStack, level, entity, slot, isSelected);
    }
    default float getLavaMovementEfficiencyRaw(AbilityItem item, ItemStack itemStack, LivingEntity user, float efficiency) {
        I castedItem = checkedCast(item);
        if (castedItem == null) return efficiency;
        return getLavaMovementEfficiency(castedItem, itemStack, user, efficiency);
    }
    void onAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    void postAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker);
    void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected);
    void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected);
    default float getLavaMovementEfficiency(I castedItem, ItemStack itemStack, LivingEntity user, float efficiency) {
        return efficiency;
    }

    record IdentityForPlayer(UUID uuid, AbilityItem abilityItem) {
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IdentityForPlayer that)) return false;
            return Objects.equals(uuid(), that.uuid()) && Objects.equals(abilityItem(), that.abilityItem());
        }

        @Override
        public int hashCode() {
            return Objects.hash(uuid(), abilityItem());
        }
    }
}
