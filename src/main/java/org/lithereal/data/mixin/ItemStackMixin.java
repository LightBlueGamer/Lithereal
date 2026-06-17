package org.lithereal.data.mixin;

import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.core.component.ModComponents;
import org.lithereal.core.component.SpecialAbility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {
    @Inject(method = "hurtEnemy", at = @At("HEAD"))
    private void onHurtEnemy(LivingEntity mob, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        SpecialAbility specialAbility = get(ModComponents.SPECIAL_ABILITY.get());
        if (specialAbility != null)
            specialAbility.onAttack(ItemStack.class.cast(this), mob, attacker);
    }
    @Inject(method = "postHurtEnemy", at = @At("HEAD"))
    private void afterHurtEnemy(LivingEntity mob, LivingEntity attacker, CallbackInfo ci) {
        SpecialAbility specialAbility = get(ModComponents.SPECIAL_ABILITY.get());
        if (specialAbility != null)
            specialAbility.postAttack(ItemStack.class.cast(this), mob, attacker);
    }
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void onInventoryTick(Level level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        SpecialAbility specialAbility = get(ModComponents.SPECIAL_ABILITY.get());
        if (specialAbility != null) {
            specialAbility.onItemTick(ItemStack.class.cast(this), level, owner, slot);
            specialAbility.onArmourTick(ItemStack.class.cast(this), level, owner, slot);
        }
    }
}
