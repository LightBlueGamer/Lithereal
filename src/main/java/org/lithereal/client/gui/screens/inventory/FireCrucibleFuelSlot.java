package org.lithereal.client.gui.screens.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FireCrucibleFuelSlot extends Slot {
    private final FireCrucibleMenu menu;

    public FireCrucibleFuelSlot(FireCrucibleMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack itemStack) {
        return this.menu.isFuel(itemStack) || isBucket(itemStack);
    }

    public int getMaxStackSize(ItemStack itemStack) {
        return isBucket(itemStack) ? 1 : super.getMaxStackSize(itemStack);
    }

    public static boolean isBucket(ItemStack itemStack) {
        return itemStack.is(Items.BUCKET);
    }
}
