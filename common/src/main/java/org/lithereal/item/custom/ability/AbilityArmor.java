package org.lithereal.item.custom.ability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.item.custom.Ability;

public class AbilityArmor extends ArmorItem implements AbilityItem {
    final Ability ability;
    public AbilityArmor(Ability ability, ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        this.ability = ability;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        getAbility().onArmourTick(this, itemStack, level, entity, slot, isSelected);
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    @Override
    public Ability getAbility() {
        return ability;
    }
}
