package org.lithereal.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;

public class PhantomDrownedModel<T extends ZombieRenderState> extends PhantomZombieModel<T> {
    public PhantomDrownedModel(ModelPart modelPart) {
        super(modelPart);
    }

    public void setupAnim(T state) {
        super.setupAnim(state);
        setupDrownedAnim(state);
    }
}
