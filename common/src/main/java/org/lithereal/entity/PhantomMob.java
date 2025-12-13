package org.lithereal.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.lithereal.Lithereal;
import org.lithereal.entity.attack.AttackType;
import org.lithereal.entity.attack.AttackingEntity;
import org.lithereal.entity.attack.ExpandingBoundingBoxAttack;

public interface PhantomMob<T extends Mob> extends AttackingEntity<T> {
    AttributeModifier PHASING_SPEED_MODIFIER = new AttributeModifier(Lithereal.id("phasing_speed"), 2.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    ResourceLocation PHANTOM_EXPULSION = Lithereal.id("phantom_expulsion");
    default void init() {
        addAttackType(PHANTOM_EXPULSION, new AttackType<>() {
            @Override
            public ResourceLocation id() {
                return PHANTOM_EXPULSION;
            }

            @Override
            public void attackEntity(T causalEntity, LivingEntity target, Level level) {
                float attackDamage = (float)causalEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                DamageSource damageSource = causalEntity.damageSources().magic();
                if (level instanceof ServerLevel serverLevel)
                    attackDamage = EnchantmentHelper.modifyDamage(serverLevel, causalEntity.getWeaponItem(), target, damageSource, attackDamage);

                attackDamage *= 0.25F;
                boolean hurt = target.hurt(damageSource, attackDamage);
                if (hurt) {
                    if (level instanceof ServerLevel serverLevel)
                        EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
                    causalEntity.setLastHurtMob(target);
                }
            }
        });
    }
    default void activatePhasing() {
        AttributeInstance speed = self().getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) speed.addOrUpdateTransientModifier(PHASING_SPEED_MODIFIER);
        setPhasing(true);
        setPhasingTimer(5000);
        self().refreshDimensions();
    }
    default void deactivatePhasing() {
        AttributeInstance speed = self().getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) speed.removeModifier(PHASING_SPEED_MODIFIER);
        setPhasing(false);
        self().refreshDimensions();
    }
    default void expulsionAttack() {
        addRunnableAttack(new ExpandingBoundingBoxAttack<>(this, self().position(), getAttackType(PHANTOM_EXPULSION), 1, 1, 3, 3, 5));
    }
    void setPhasing(boolean nowPhasing);
    boolean isPhasing();
    void setPhasingTimer(int phasingTimer);
    int getPhasingTimer();
}
