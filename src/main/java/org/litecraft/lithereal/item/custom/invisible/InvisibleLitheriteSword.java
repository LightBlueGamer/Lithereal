package org.litecraft.lithereal.item.custom.invisible;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class InvisibleLitheriteSword extends SwordItem {
    public InvisibleLitheriteSword(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity) {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.INVISIBILITY, 60, 1);
            player.addEffect(mobEffectInstance);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}