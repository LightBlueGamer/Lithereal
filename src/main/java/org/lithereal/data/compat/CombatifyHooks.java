package org.lithereal.data.compat;

import net.atlas.combatify.Combatify;
import net.atlas.combatify.item.KnifeItem;
import net.atlas.combatify.item.LongSwordItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.lithereal.item.ability.AbilityKnifeItem;
import org.lithereal.item.ability.AbilityLongSwordItem;
import org.lithereal.item.infused.InfusedLitheriteKnifeItem;
import org.lithereal.item.infused.InfusedLitheriteLongSwordItem;
import org.lithereal.item.ability.Ability;

public class CombatifyHooks {
    public static Item generateLongsword(ToolMaterial toolMaterial, int weaponLevel, Item.Properties properties) {
        return new LongSwordItem(toolMaterial, weaponLevel, properties);
    }
    public static Item generateKnife(ToolMaterial toolMaterial, Item.Properties properties) {
        return new KnifeItem(toolMaterial, properties);
    }
    public static Item generateAbilityLongsword(Ability ability, ToolMaterial toolMaterial, int weaponLevel, Item.Properties properties) {
        return new AbilityLongSwordItem(ability, toolMaterial, weaponLevel, properties);
    }
    public static Item generateAbilityKnife(Ability ability, ToolMaterial toolMaterial, Item.Properties properties) {
        return new AbilityKnifeItem(ability, toolMaterial, properties);
    }
    public static Item generateInfusedLongsword(ToolMaterial toolMaterial, int weaponLevel, Item.Properties properties) {
        return new InfusedLitheriteLongSwordItem(toolMaterial, weaponLevel, properties);
    }
    public static Item generateInfusedKnife(ToolMaterial toolMaterial, Item.Properties properties) {
        return new InfusedLitheriteKnifeItem(toolMaterial, properties);
    }

    public static float getTierDamageNerf() {
        return Combatify.CONFIG.tierDamageNerf() ? 1 : 0;
    }
}
