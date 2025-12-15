package org.lithereal.entity.attack;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
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

    public static <E extends Entity & AttackingEntity<E>> Attack<E> loadSaveData(E attackingEntity, CompoundTag compoundTag, HolderLookup.Provider provider) {
        AttackType<E> attackType = attackingEntity.getAttackType(ResourceLocation.parse(compoundTag.getString("attack_type")));
        Attack<E> attack = attackType.createAttack(attackingEntity, Vec3.CODEC.parse(NbtOps.INSTANCE, compoundTag.get("position")).getOrThrow());
        attack.readAdditionalSaveData(compoundTag, provider);
        return attack;
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag, HolderLookup.Provider provider) {

    }

    public final void addSaveData(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putString("attack_type", attackType.id().toString());
        Tag tag = Vec3.CODEC.encodeStart(NbtOps.INSTANCE, position).getOrThrow();
        compoundTag.put("position", tag);
        addAdditionalSaveData(compoundTag, provider);
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag, HolderLookup.Provider provider) {

    }
}
