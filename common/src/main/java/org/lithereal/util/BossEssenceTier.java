package org.lithereal.util;

import net.minecraft.world.item.alchemy.PotionContents;

public sealed interface BossEssenceTier permits BaseBossEssence {
    PotionContents generatePotionContents();
}