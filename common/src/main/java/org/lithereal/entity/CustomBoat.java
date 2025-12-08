package org.lithereal.entity;

import net.minecraft.world.entity.vehicle.Boat;

public interface CustomBoat {
    Boat self();
    ModBoat.Type getModVariant();
    void setVariant(ModBoat.Type type);
}
