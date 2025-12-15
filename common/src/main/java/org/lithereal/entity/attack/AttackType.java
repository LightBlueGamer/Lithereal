package org.lithereal.entity.attack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AttackType<E extends Entity & AttackingEntity<E>> {
    default void checkEntitiesAttacked(E causalEntity, @Nullable AABB originalBoundingBox, AABB newBoundingBox) {
        Level level = causalEntity.level();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, newBoundingBox.inflate(1, 1, 1), target -> canAttackEntity(causalEntity, target));
        for (LivingEntity target : entities) {
            AABB targetBoundingBox = target.getBoundingBox().inflate(target.getPickRadius());
            if (originalBoundingBox != null && targetBoundingBox.intersects(originalBoundingBox)) continue;
            if (!targetBoundingBox.intersects(newBoundingBox)) continue;
            attackEntity(causalEntity, target, entities, level);
        }
    }
    ResourceLocation id();
    void attackEntity(E causalEntity, LivingEntity target, List<LivingEntity> entities, Level level);
    Attack<E> createAttack(E attackingEntity, Vec3 position);
    default boolean canAttackEntity(E causalEntity, LivingEntity target) {
        return target != causalEntity;
    }
}
