package org.lithereal.entity.attack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface AttackingEntity<E extends Entity> {
    E self();
    AttackType<E> getAttackType(ResourceLocation id);
    void addAttackType(ResourceLocation id, AttackType<E> attackType);
    <AE extends AttackingEntity<E>> List<Attack<E, AE>> getAttacks();
    <AE extends AttackingEntity<E>> void addRunnableAttack(Attack<E, AE> attack);
    void cullAttacks();
    default void onTick() {
        getAttacks().forEach(Attack::tick);
        cullAttacks();
    }
}
