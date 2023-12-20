package org.lithereal.item.custom.frozen;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class FrozenLitheriteAxe extends AxeItem {
    public FrozenLitheriteAxe(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {

        if(attacked.isOnFire()) attacked.extinguishFire();
        attacked.setTicksFrozen(1000);

        return super.hurtEnemy(itemStack, attacked, attacker);
    }
}