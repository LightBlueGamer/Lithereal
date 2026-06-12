package org.lithereal.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;

public class BetterDrownedModel<T extends ZombieRenderState> extends BetterZombieModel<T> {
    public BetterDrownedModel(ModelPart modelPart) {
        super(modelPart);
    }

    public void setupAnim(T state) {
        super.setupAnim(state);
        setupDrownedAnim(state);
    }
}
