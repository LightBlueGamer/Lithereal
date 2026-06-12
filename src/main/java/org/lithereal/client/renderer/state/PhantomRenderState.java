package org.lithereal.client.renderer.state;

import net.minecraft.world.entity.Mob;
import org.lithereal.entity.phantom.PhantomMob;

public interface PhantomRenderState {
    boolean isPhasing();
    boolean isTransitioningOut();
    int getPhasingTimer();
    int getInitialPhasingTimer();
    <E extends Mob & PhantomMob<E>> void extract(E phantom);
}
