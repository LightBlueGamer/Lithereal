package org.lithereal.neoforge.world.block.entity;

import org.lithereal.block.entity.ImplementedInventory;

public interface NeoForgeInventory extends ImplementedInventory {
    ImplementedItemHandler getHandler();
}
