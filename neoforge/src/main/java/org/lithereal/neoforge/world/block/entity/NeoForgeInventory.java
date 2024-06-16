package org.lithereal.neoforge.world.block.entity;

import org.lithereal.world.block.entity.ImplementedInventory;

public interface NeoForgeInventory extends ImplementedInventory {
    ImplementedItemHandler getHandler();
}
