package org.lithereal.util;

import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.storage.ValueOutput;

public interface PotionStorage {
    static ValueOutput setPotion(ValueOutput valueOutput, PotionContents contents) {
        if (contents.potion().isEmpty()) valueOutput.discard("Potion");
        else valueOutput.store("Potion", PotionContents.CODEC, contents);

        return valueOutput;
    }

    void setPotion(PotionContents potion);

    PotionContents getStoredPotion();
}
