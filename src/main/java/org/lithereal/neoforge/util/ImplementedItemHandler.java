package org.lithereal.neoforge.util;

//? neoforge {
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public class ImplementedItemHandler extends ItemStacksResourceHandler {
    public final NeoForgeInventory representation;
    public ImplementedItemHandler(int size, NeoForgeInventory neoForgeInventory) {
        super(size);
        representation = neoForgeInventory;
    }

    @Override
    protected void onContentsChanged(int index, ItemStack previousContents) {
        super.onContentsChanged(index, previousContents);
        representation.setChanged();
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }
    public ItemStack setStackInSlotNoUpdate(int slot, ItemStack stack) {
        isValid(slot, ItemResource.of(stack));
        ItemStack ret = getResource(slot).toStack(getAmountAsInt(slot));
        this.stacks.set(slot, stack);
        return ret;
    }
}
//?}