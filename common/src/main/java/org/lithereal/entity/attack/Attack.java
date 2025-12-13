package org.lithereal.entity.attack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class Attack<E extends Entity, AE extends AttackingEntity<E>> {
    @org.jetbrains.annotations.NotNull
    private AE parent;
    private Vec3 position;
    private AttackType<E> attackType;
    public Attack(AE parent,
                  Vec3 position,
                  AttackType<E> attackType) {
        this.parent = parent;
        this.position = position;
        this.attackType = attackType;
    }

    public @NotNull AE parent() {
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

    public final void loadSaveData(AE attackingEntity, CompoundTag compoundTag) {
        parent = attackingEntity;
        attackType = attackingEntity.getAttackType(ResourceLocation.parse(compoundTag.getString("attack_type")));
        position = Vec3.CODEC.parse(NbtOps.INSTANCE, compoundTag.get("position")).getOrThrow();
        readAdditionalSaveData(compoundTag);
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    public final void addSaveData(CompoundTag compoundTag) {
        compoundTag.putString("attack_type", attackType.id().toString());
        Tag tag = Vec3.CODEC.encodeStart(NbtOps.INSTANCE, position).getOrThrow();
        compoundTag.put("position", tag);
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
