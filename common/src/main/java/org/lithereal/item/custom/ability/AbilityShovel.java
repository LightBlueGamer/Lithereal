package org.lithereal.item.custom.ability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.lithereal.item.custom.Ability;

public class AbilityShovel extends ShovelItem implements AbilityItem {
    final Ability ability;
    public AbilityShovel(Ability ability, Tier tier, Properties properties) {
        super(tier, properties.attributes(ShovelItem.createAttributes(tier, 1.5f, -3f)));
        this.ability = ability;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        getAbility().onAttack(this, itemStack, attacked, attacker);
        return super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        getAbility().onItemTick(this, itemStack, level, entity, i, bl);
        super.inventoryTick(itemStack, level, entity, i, bl);
    }

    @Override
    public Ability getAbility() {
        return ability;
    }
}
