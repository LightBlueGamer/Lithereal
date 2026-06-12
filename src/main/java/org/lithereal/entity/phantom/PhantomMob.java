package org.lithereal.entity.phantom;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.lithereal.Lithereal;
import org.lithereal.entity.attack.Attack;
import org.lithereal.entity.attack.AttackType;
import org.lithereal.entity.attack.AttackingEntity;
import org.lithereal.entity.attack.ExpandingBoundingBoxAttack;

import java.util.List;

public interface PhantomMob<T extends Mob & PhantomMob<T>> extends AttackingEntity<T> {
    AttributeModifier PHASING_SPEED_MODIFIER = new AttributeModifier(Lithereal.id("phasing_speed"), 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    Identifier PHANTOM_EXPULSION = Lithereal.id("phantom_expulsion");
    default void init() {
        addAttackType(PHANTOM_EXPULSION, new AttackType<>() {
            @Override
            public Identifier id() {
                return PHANTOM_EXPULSION;
            }

            @Override
            public void attackEntity(T causalEntity, LivingEntity target, List<LivingEntity> entities, ServerLevel serverLevel) {
                float attackDamage = (float)causalEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                DamageSource damageSource = causalEntity.damageSources().magic();
                attackDamage = EnchantmentHelper.modifyDamage(serverLevel, causalEntity.getWeaponItem(), target, damageSource, attackDamage);

                attackDamage /= 4;
                boolean hurt = target.hurtServer(serverLevel, damageSource, attackDamage);
                if (hurt) {
                    EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
                    causalEntity.setLastHurtMob(target);
                }
            }

            @Override
            public Attack<T> createAttack(T attackingEntity, Vec3 position) {
                return new ExpandingBoundingBoxAttack<>(attackingEntity, position, this);
            }
        });
    }
    default boolean canStartPhasing() {
        return getPhasingTimer() <= -100;
    }
    default void activatePhasing() {
        AttributeInstance speed = self().getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) speed.addOrUpdateTransientModifier(PHASING_SPEED_MODIFIER);
        setPhasing(true);
        startPhasingTimer(100);
        self().setInvulnerable(true);
        if (self().isOnFire()) self().setRemainingFireTicks(0);
    }
    default void deactivatePhasing() {
        AttributeInstance speed = self().getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) speed.removeModifier(PHASING_SPEED_MODIFIER);
        setPhasing(false);
        setPhasingTimer(0);
        self().setInvulnerable(false);
    }
    default void expulsionAttack() {
        setPhasingTimer(Math.max(0, getPhasingTimer() - 15));
        addRunnableAttack(new ExpandingBoundingBoxAttack<>(self(), self().position(), getAttackType(PHANTOM_EXPULSION), 1, 1, 3, 2, 5));
    }
    @Override
    default void onTick() {
        AttackingEntity.super.onTick();
        setPhasingTimer(getPhasingTimer() - 1);
    }
    default void loadPhantom(ValueInput valueInput) {
        ValueInput.ValueInputList attacks = valueInput.childrenListOrEmpty("attacks");
        for (ValueInput attack : attacks) {
            addRunnableAttack(Attack.loadSaveData(self(), attack));
        }
        setPhasing(valueInput.getBooleanOr("is_phasing", false));
        startPhasingTimer(valueInput.getIntOr("initial_phasing_timer", 100));
        setPhasingTimer(valueInput.getIntOr("phasing_timer", 0));
    }
    default void savePhantom(ValueOutput valueOutput) {
        ValueOutput.ValueOutputList attacks = valueOutput.childrenList("attacks");
        for (Attack<T> attack : getAttacks()) {
            attack.addSaveData(attacks.addChild());
        }
        valueOutput.putBoolean("is_phasing", isPhasing());
        valueOutput.putInt("initial_phasing_timer", getInitialPhasingTimer());
        valueOutput.putInt("phasing_timer", getPhasingTimer());
    }
    default boolean isTransitioningOut() {
        return getPhasingTimer() < 0 && getPhasingTimer() > -7;
    }

    void setPhasing(boolean nowPhasing);
    boolean isPhasing();
    void startPhasingTimer(int phasingTimer);
    void setPhasingTimer(int phasingTimer);
    int getPhasingTimer();
    int getInitialPhasingTimer();
}
