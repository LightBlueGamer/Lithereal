package org.lithereal.neoforge.block.entity;

import org.lithereal.block.entity.ImplementedInventory;

public interface NeoForgeInventory extends ImplementedInventory {
    ImplementedItemHandler getHandler();
}
