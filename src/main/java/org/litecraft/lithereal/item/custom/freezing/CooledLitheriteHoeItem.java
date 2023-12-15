package org.litecraft.lithereal.item.custom.freezing;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

public class CooledLitheriteHoeItem extends HoeItem {
    public CooledLitheriteHoeItem(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            if(livingEntity.isOnFire()) livingEntity.extinguishFire();
            livingEntity.setTicksFrozen(1000);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
