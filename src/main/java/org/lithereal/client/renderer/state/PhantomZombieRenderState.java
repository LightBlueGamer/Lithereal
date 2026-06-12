package org.lithereal.client.renderer.state;

import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.entity.Mob;
import org.lithereal.entity.phantom.PhantomMob;

public class PhantomZombieRenderState extends ZombieRenderState implements PhantomRenderState {
    public boolean isPhasing = false;
    public boolean isTransitioningOut = false;
    public int phasingTimer = 0;
    public int initialPhasingTimer = 100;
    @Override
    public boolean isPhasing() {
        return this.isPhasing;
    }

    @Override
    public boolean isTransitioningOut() {
        return this.isTransitioningOut;
    }

    @Override
    public int getPhasingTimer() {
        return this.phasingTimer;
    }

    @Override
    public int getInitialPhasingTimer() {
        return this.initialPhasingTimer;
    }

    @Override
    public <E extends Mob & PhantomMob<E>> void extract(E phantom) {
        this.isPhasing = phantom.isPhasing();
        this.isTransitioningOut = phantom.isTransitioningOut();
        this.phasingTimer = phantom.getPhasingTimer();
        this.initialPhasingTimer = phantom.getInitialPhasingTimer();
    }
}
