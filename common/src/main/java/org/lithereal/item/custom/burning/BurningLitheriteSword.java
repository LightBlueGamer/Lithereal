package org.lithereal.item.custom.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BurningLitheriteSword extends SwordItem {
    public BurningLitheriteSword(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if(attacked.isFreezing()) attacked.setTicksFrozen(0);
        attacked.setSecondsOnFire(1000);
        return super.hurtEnemy(itemStack, attacked, attacker);
    }
}
