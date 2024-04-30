package org.lithereal.forge.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import org.lithereal.item.custom.WarHammer;

public class ForgeWarHammer extends WarHammer {
    public ForgeWarHammer(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return super.canPerformAction(stack, toolAction) || toolAction == ToolActions.SWORD_SWEEP;
    }
}
