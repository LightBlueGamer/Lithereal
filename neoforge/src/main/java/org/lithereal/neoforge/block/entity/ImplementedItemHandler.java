package org.lithereal.neoforge.block.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ImplementedItemHandler extends ItemStackHandler {
    public final NeoForgeInventory representation;
    public ImplementedItemHandler(int size, NeoForgeInventory neoForgeInventory) {
        super(size);
        representation = neoForgeInventory;
    }
    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        representation.setChanged();
    }
    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }
    public ItemStack setStackInSlotNoUpdate(int slot, ItemStack stack) {
        validateSlotIndex(slot);
        ItemStack ret = getStackInSlot(slot);
        this.stacks.set(slot, stack);
        return ret;
    }
}
