package org.lithereal.item.custom.obscured;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MysteriousItem extends Item implements ObscuredItem {
    public MysteriousItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        return ObscuredItem.setRevealed(super.getDefaultInstance());
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return this.getDescriptionId() + (ObscuredItem.isRevealed(p_43364_) ? ".revealed" : ".obscured");
    }
}
