package org.lithereal.entity.attack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface AttackingEntity<E extends Entity & AttackingEntity<E>> {
    E self();
    AttackType<E> getAttackType(ResourceLocation id);
    void addAttackType(ResourceLocation id, AttackType<E> attackType);
    List<Attack<E>> getAttacks();
    void addRunnableAttack(Attack<E> attack);
    void cullAttacks();
    default void onTick() {
        getAttacks().forEach(Attack::tick);
        cullAttacks();
    }
}
