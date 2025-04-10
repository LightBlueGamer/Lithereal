//package org.lithereal.neoforge.world.item.infused;
//
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Tier;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.item.alchemy.PotionUtils;
//import net.minecraft.world.item.alchemy.Potions;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.Nullable;
//import org.lithereal.neoforge.world.item.ability.ForgeAbilityLongSword;
//import org.lithereal.item.ability.Ability;
//import org.lithereal.item.infused.InfusedItem;
//
//import java.util.List;
//
//public class ForgeInfusedLongSword extends ForgeAbilityLongSword implements InfusedItem {
//    public ForgeInfusedLongSword(Tier tier, Properties properties) {
//        super(Ability.INFUSED, tier, properties);
//    }
//
//    public ItemStack getDefaultInstance() {
//        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.EMPTY);
//    }
//
//    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
//        PotionUtils.addPotionTooltip(itemStack, components, 1F);
//    }
//
//    public String getDescriptionId(ItemStack p_43364_) {
//        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
//    }
//
//    @Override
//    public Component getName(ItemStack itemStack) {
//        return getModifiedName(itemStack);
//    }
//
//    @Override
//    public String getBaseName(ItemStack stack) {
//        return "Litherite Longsword";
//    }
//}
