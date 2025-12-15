package org.lithereal.neoforge.world.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.WarHammerItem;

public class ForgeWarHammerItem extends WarHammerItem {
    public ForgeWarHammerItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        return super.canPerformAction(stack, itemAbility) || itemAbility == ItemAbilities.SWORD_SWEEP;
    }
}
