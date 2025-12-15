package org.lithereal.entity.phantom;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.phys.Vec3;
import org.lithereal.Lithereal;
import org.lithereal.entity.attack.Attack;
import org.lithereal.entity.attack.AttackType;
import org.lithereal.entity.attack.AttackingEntity;
import org.lithereal.entity.attack.ExpandingBoundingBoxAttack;

import java.util.List;

public interface PhantomMob<T extends Mob & PhantomMob<T>> extends AttackingEntity<T> {
    AttributeModifier PHASING_SPEED_MODIFIER = new AttributeModifier(Lithereal.id("phasing_speed"), 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    ResourceLocation PHANTOM_EXPULSION = Lithereal.id("phantom_expulsion");
    default void init() {
        addAttackType(PHANTOM_EXPULSION, new AttackType<>() {
            @Override
            public ResourceLocation id() {
                return PHANTOM_EXPULSION;
            }

            @Override
            public void attackEntity(T causalEntity, LivingEntity target, List<LivingEntity> entities, Level level) {
                float attackDamage = (float)causalEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                DamageSource damageSource = causalEntity.damageSources().magic();
                if (level instanceof ServerLevel serverLevel)
                    attackDamage = EnchantmentHelper.modifyDamage(serverLevel, causalEntity.getWeaponItem(), target, damageSource, attackDamage);

                attackDamage /= 4;
                boolean hurt = target.hurt(damageSource, attackDamage);
                if (hurt) {
                    if (level instanceof ServerLevel serverLevel)
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
    default void loadPhantom(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ListTag tag = compoundTag.getList("attacks", 10);
        for (Tag attack : tag) {
            CompoundTag asCompound = (CompoundTag) attack;
            addRunnableAttack(Attack.loadSaveData(self(), asCompound, provider));
        }
        setPhasing(compoundTag.getBoolean("is_phasing"));
        startPhasingTimer(compoundTag.getInt("initial_phasing_timer"));
        setPhasingTimer(compoundTag.getInt("phasing_timer"));
    }
    default void savePhantom(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ListTag attacks = new ListTag();
        for (Attack<T> attack : getAttacks()) {
            CompoundTag tag = new CompoundTag();
            attack.addSaveData(tag, provider);
            attacks.add(tag);
        }
        compoundTag.put("attacks", attacks);
        compoundTag.putBoolean("is_phasing", isPhasing());
        compoundTag.putInt("initial_phasing_timer", getInitialPhasingTimer());
        compoundTag.putInt("phasing_timer", getPhasingTimer());
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
