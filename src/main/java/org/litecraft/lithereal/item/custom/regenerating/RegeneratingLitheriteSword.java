package org.litecraft.lithereal.item.custom.regenerating;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.litecraft.lithereal.Lithereal;

public class RegeneratingLitheriteSword extends SwordItem {
    public RegeneratingLitheriteSword(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity) {
            double damage = stack.getDamageValue() * 0.8;
            Lithereal.LOGGER.debug(String.valueOf(damage));
            player.heal((float) damage);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
