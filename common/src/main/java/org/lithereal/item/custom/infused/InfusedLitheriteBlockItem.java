package org.lithereal.item.custom.infused;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;

public class InfusedLitheriteBlockItem extends BlockItem implements InfusedItem {
    public InfusedLitheriteBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }
}
