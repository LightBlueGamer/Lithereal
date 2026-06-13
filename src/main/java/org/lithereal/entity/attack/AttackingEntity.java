package org.lithereal.entity.attack;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
//? neoforge {
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
//?}

import java.util.List;

//? fabric {
/*public interface AttackingEntity<E extends LivingEntity & AttackingEntity<E>> {
*///?}
//? neoforge {
public interface AttackingEntity<E extends LivingEntity & AttackingEntity<E>> extends ILivingEntityExtension {
//?}
    E self();
    AttackType<E> getAttackType(Identifier id);
    void addAttackType(Identifier id, AttackType<E> attackType);
    List<Attack<E>> getAttacks();
    void addRunnableAttack(Attack<E> attack);
    void cullAttacks();
    default void onTick() {
        getAttacks().forEach(Attack::tick);
        cullAttacks();
    }
}
