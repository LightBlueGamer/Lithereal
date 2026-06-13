package org.lithereal.entity.attack;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExpandingBoundingBoxAttack<E extends LivingEntity & AttackingEntity<E>> extends Attack<E> {
    private float initialWidth;
    private float initialHeight;
    private float resultingWidth;
    private float resultingHeight;
    private int startTime;
    private int destTime;

    public ExpandingBoundingBoxAttack(E parent,
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

    public ExpandingBoundingBoxAttack(E parent,
                                      Vec3 position,
                                      AttackType<E> attackType) {
        super(parent, position, attackType);
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

    protected void readAdditionalSaveData(ValueInput valueInput) {
        initialWidth = valueInput.getFloatOr("initial_width", 0);
        initialHeight = valueInput.getFloatOr("initial_height", 0);
        resultingWidth = valueInput.getFloatOr("resulting_width", 0);
        resultingHeight = valueInput.getFloatOr("resulting_height", 0);
        startTime = valueInput.getIntOr("start_time", 0);
        destTime = valueInput.getIntOr("dest_time",0);
    }

    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putFloat("initial_width", initialWidth);
        valueOutput.putFloat("initial_height", initialHeight);
        valueOutput.putFloat("resulting_width", resultingWidth);
        valueOutput.putFloat("resulting_height", resultingHeight);
        valueOutput.putInt("start_time", startTime);
        valueOutput.putInt("dest_time", destTime);
    }
}
