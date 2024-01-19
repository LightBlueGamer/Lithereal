package org.lithereal.item.custom.infused;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

        ItemStack potion = PotionUtils.setPotion(new ItemStack(Items.POTION), PotionUtils.getPotion(itemStack));
        Component name = potion.getHoverName();
        String hoverStr = name.getString().replaceAll("(?i)(potion of the |potion of |potion )", "");
        Component newName = Component.literal(hoverStr+ " Litherite Ingot").withStyle(Style.EMPTY.withItalic(false));
        itemStack.setHoverName(newName);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }
}