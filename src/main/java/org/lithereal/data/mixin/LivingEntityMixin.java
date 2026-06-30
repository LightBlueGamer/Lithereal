package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.lithereal.core.component.ModComponents;
import org.lithereal.core.component.SpecialAbility;
import org.lithereal.util.ChillData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract float getSpeed();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;aiStep()V"))
    public void updateChill(CallbackInfo ci) {
        ChillData.get(LivingEntity.class.cast(this)).tickChill(LivingEntity.class.cast(this));
    }

    @ModifyReturnValue(method = "getSpeed", at = @At(value = "RETURN"))
    public float addChill(float original) {
        return original - original * ChillData.get(LivingEntity.class.cast(this)).chillSpeedMod();
    }

    @WrapOperation(method = "travelInLava", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    public void useFasterMovementInLavaIfArmorSaysSo(LivingEntity instance, float v, Vec3 vec3, Operation<Void> original, @Share("lavaMovementEfficiency") LocalFloatRef scalarRef) {
        float scalar = 0;
        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            ItemStack armorItem = instance.getItemBySlot(slot);
            SpecialAbility specialAbility = armorItem.get(ModComponents.SPECIAL_ABILITY.get());
            if (specialAbility != null)
                scalar = specialAbility.getLavaMovementEfficiency(armorItem, instance, scalar);
        }
        if (!this.onGround())
            scalar *= 0.5F;

        if (scalar > 0.0F)
            v += (this.getSpeed() - v) * scalar;
        scalarRef.set(scalar);
        original.call(instance, v, vec3);
    }
    @ModifyExpressionValue(method = "travelInLava", at = @At(value = "CONSTANT", args = "doubleValue=0.5"))
    public double useFasterMovementThroughLavaForArmor(double original, @Share("lavaMovementEfficiency") LocalFloatRef scalarRef) {
        float scalar = scalarRef.get();
        if (scalar > 0) {
            original += (0.54600006F - original) * scalar;
        }
        return original;
    }
}
