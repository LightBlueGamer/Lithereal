package org.lithereal.data.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.lithereal.item.ability.AbilityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract float getSpeed();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(method = "travel", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInLava()Z")), at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    public void useFasterMovementInLavaIfArmorSaysSo(LivingEntity instance, float v, Vec3 vec3, Operation<Void> original, @Share("lavaMovementEfficiency") LocalFloatRef scalarRef) {
        float scalar = 0;
        for (ItemStack armorItem : instance.getArmorSlots()) {
            if (armorItem.getItem() instanceof AbilityItem abilityItem && abilityItem.getAbility().providesEasierLavaMovement(abilityItem, armorItem, instance))
                scalar += 0.1F;
        }
        if (!this.onGround())
            scalar *= 0.5F;

        if (scalar > 0.0F)
            v += (this.getSpeed() - v) * scalar;
        scalarRef.set(scalar);
        original.call(instance, v, vec3);
    }
    @ModifyExpressionValue(method = "travel", at = @At(value = "CONSTANT", args = "doubleValue=0.5"))
    public double useFasterMovementThroughLavaForArmor(double original, @Share("lavaMovementEfficiency") LocalFloatRef scalarRef) {
        float scalar = scalarRef.get();
        if (scalar > 0) {
            original = 0.7;
            original += (0.54600006F - original) * scalar;
        }
        return original;
    }
}
