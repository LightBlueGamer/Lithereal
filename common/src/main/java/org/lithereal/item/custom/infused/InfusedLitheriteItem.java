package org.lithereal.item.custom.infused;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfusedLitheriteItem extends Item implements InfusedItem {
    public InfusedLitheriteItem(Properties properties) {
        super(properties);
    }


    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        PotionUtils.addPotionTooltip(itemStack, components, 1F);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }
}