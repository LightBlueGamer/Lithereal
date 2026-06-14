package org.lithereal.client.gui.screens.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.lithereal.tags.ModTags;

public class FreezingStationChillersSlot extends Slot {

    public FreezingStationChillersSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.is(ModTags.CHILLERS) || isBucket(itemStack);
    }

    public int getMaxStackSize(ItemStack itemStack) {
        return isBucket(itemStack) ? 1 : super.getMaxStackSize(itemStack);
    }

    public static boolean isBucket(ItemStack itemStack) {
        return itemStack.is(Items.BUCKET);
    }
}
