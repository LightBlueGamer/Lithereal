package org.lithereal.entity.attack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExpandingBoundingBoxAttack<E extends Entity, AE extends AttackingEntity<E>> extends Attack<E, AE> {
    private float initialWidth;
    private float initialHeight;
    private float resultingWidth;
    private float resultingHeight;
    private int startTime;
    private int destTime;

    public ExpandingBoundingBoxAttack(AE parent,
                                      Vec3 position,
                                      AttackType<E> attackType,
                                      float initialWidth,
                                      float initialHeight,
                                      float resultingWidth,
                                      float resultingHeight,
                                      int destTime) {
        super(parent, position, attackType);
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
        this.resultingWidth = resultingWidth;
        this.resultingHeight = resultingHeight;
        this.startTime = parent.self().tickCount;
        this.destTime = destTime;
    }

    public EntityDimensions getDimensions(int baseTime) {
        float width = Mth.lerp((float) (parent().self().tickCount - startTime + baseTime) / destTime, initialWidth, resultingWidth);
        float height = Mth.lerp((float) (parent().self().tickCount - startTime + baseTime) / destTime, initialHeight, resultingHeight);
        return EntityDimensions.fixed(width, height);
    }

    @Override
    public boolean hasExpired() {
        return parent().self().tickCount - startTime >= destTime;
    }

    public void tick() {
        AABB newBoundingBox = getDimensions(0).makeBoundingBox(position());
        if (parent().self().tickCount == startTime) {
            attackType().checkEntitiesAttacked(parent().self(), null, getDimensions(0).makeBoundingBox(position()));
            return;
        }
        AABB originalBoundingBox = getDimensions(-1).makeBoundingBox(position());
        attackType().checkEntitiesAttacked(parent().self(), originalBoundingBox, newBoundingBox);
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        initialWidth = compoundTag.getFloat("initial_width");
        initialHeight = compoundTag.getFloat("initial_height");
        resultingWidth = compoundTag.getFloat("resulting_width");
        resultingHeight = compoundTag.getFloat("resulting_height");
        startTime = compoundTag.getInt("start_time");
        destTime = compoundTag.getInt("dest_time");
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putFloat("initial_width", initialWidth);
        compoundTag.putFloat("initial_height", initialHeight);
        compoundTag.putFloat("resulting_width", resultingWidth);
        compoundTag.putFloat("resulting_height", resultingHeight);
        compoundTag.putInt("start_time", startTime);
        compoundTag.putInt("dest_time", destTime);
    }
}
