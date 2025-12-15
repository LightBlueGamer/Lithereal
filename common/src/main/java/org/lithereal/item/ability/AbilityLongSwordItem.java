package org.lithereal.item.ability;

import net.atlas.combatify.item.LongSwordItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class AbilityLongSwordItem extends LongSwordItem implements AbilityItem {
    final Ability ability;
    public AbilityLongSwordItem(Ability ability, Tier tier, int weaponLevel, Properties properties) {
        super(tier, weaponLevel, properties);
        this.ability = ability;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        ability.onAttack(this, itemStack, attacked, attacker);
        return super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        ability.postAttack(this, itemStack, attacked, attacker);
        super.postHurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        ability.onItemTick(this, itemStack, level, entity, i, bl);
        super.inventoryTick(itemStack, level, entity, i, bl);
    }

    @Override
    public Ability getAbility() {
        return ability;
    }
}
