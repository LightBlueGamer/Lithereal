package org.lithereal.item.custom.withering;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.lithereal.item.custom.Hammer;

public class WitheringLitheriteHammer extends Hammer {
    public WitheringLitheriteHammer(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
        return super.hurtEnemy(itemStack, attacked, attacker);
    }
}
