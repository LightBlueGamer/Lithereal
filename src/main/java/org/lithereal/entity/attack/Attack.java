package org.lithereal.entity.attack;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class Attack<E extends Entity & AttackingEntity<E>> {
    @org.jetbrains.annotations.NotNull
    private E parent;
    private Vec3 position;
    private AttackType<E> attackType;
    public Attack(@NotNull E parent,
                  Vec3 position,
                  AttackType<E> attackType) {
        this.parent = parent;
        this.position = position;
        this.attackType = attackType;
    }

    public @NotNull E parent() {
        return parent;
    }

    public Vec3 position() {
        return position;
    }

    public AttackType<E> attackType() {
        return attackType;
    }

    public abstract boolean hasExpired();

    public abstract void tick();

    public static <E extends Entity & AttackingEntity<E>> Attack<E> loadSaveData(E attackingEntity, ValueInput valueInput) {
        AttackType<E> attackType = attackingEntity.getAttackType(Identifier.parse(valueInput.getStringOr("attack_type", "")));
        Attack<E> attack = attackType.createAttack(attackingEntity, valueInput.read("position", Vec3.CODEC).orElse(Vec3.ZERO));
        attack.readAdditionalSaveData(valueInput);
        return attack;
    }

    protected void readAdditionalSaveData(ValueInput valueInput) {

    }

    public final void addSaveData(ValueOutput valueOutput) {
        valueOutput.putString("attack_type", attackType.id().toString());
        valueOutput.store("position", Vec3.CODEC, position);
        addAdditionalSaveData(valueOutput);
    }

    protected void addAdditionalSaveData(ValueOutput valueOutput) {

    }
}
