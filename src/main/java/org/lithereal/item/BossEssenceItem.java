package org.lithereal.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import org.lithereal.util.BaseBossEssence;

public class BossEssenceItem extends Item {
    public BossEssenceItem(Properties properties, BaseBossEssence bossEssence) {
        super(properties
                .component(DataComponents.POTION_CONTENTS, bossEssence.generatePotionContents())
                .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
                .usingConvertsTo(Items.GLASS_BOTTLE));
    }
}
