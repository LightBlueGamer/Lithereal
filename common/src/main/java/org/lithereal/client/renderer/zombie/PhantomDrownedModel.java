package org.lithereal.client.renderer.zombie;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Zombie;

public class PhantomDrownedModel<T extends Zombie> extends PhantomZombieModel<T> {
    public PhantomDrownedModel(ModelPart modelPart) {
        super(modelPart);
    }

    public void prepareMobModel(T zombie, float f, float g, float h) {
        prepareDrownedModel(zombie);

        super.prepareMobModel(zombie, f, g, h);
    }

    public void setupAnim(T zombie, float f, float g, float h, float i, float j) {
        super.setupAnim(zombie, f, g, h, i, j);
        setupDrownedAnim(zombie, h);
    }
}
