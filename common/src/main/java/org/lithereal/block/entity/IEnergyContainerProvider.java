package org.lithereal.block.entity;

import org.lithereal.util.LitherEnergyContainer;

public interface IEnergyContainerProvider {
    int[] sides = {
            // 0 = off, 1 = input only, 2 = output only, 3 = both
            0, // down
            0, // up
            0, // north
            0, // south
            0, // west
            0, // east
    };
    LitherEnergyContainer getEnergyContainer();
}
