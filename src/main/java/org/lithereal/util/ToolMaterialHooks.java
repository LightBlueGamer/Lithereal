package org.lithereal.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.lithereal.data.compat.DefaultedHooks;
import org.lithereal.item.compat.CompatInit;

import static dev.architectury.platform.Platform.isModLoaded;

public class ToolMaterialHooks {
    public static ToolMaterial create(String name, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull TagKey<Item> repairItems, TagKey<Block> incorrect) {
        if (isModLoaded("combatify"))
            attackDamageBonus -= CompatInit.getToolMaterialDamageNerf();
        ToolMaterial toolMaterial = new ToolMaterial(incorrect, uses, speed, attackDamageBonus, enchantmentValue, repairItems);
        if (isModLoaded("defaulted"))
            return DefaultedHooks.registerToolMaterial(name, toolMaterial);
        return toolMaterial;
    }
}