package org.lithereal.client.renderer.zombie;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Drowned;

public class BetterDrownedModel<T extends Drowned> extends BetterZombieModel<T> {
    public BetterDrownedModel(ModelPart modelPart) {
        super(modelPart);
    }

    public void prepareMobModel(T zombie, float f, float g, float h) {
        prepareDrownedModel(zombie);

        super.prepareMobModel(zombie, f, g, h);
    }

    public void setupAnim(T zombie, float f, float g, float h, float i, float j) {
        super.setupAnim(zombie, f, g, h, i, j);
        setupDrownedAnim(zombie, h);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
    }
}
