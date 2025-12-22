package org.lithereal.data.compat;

import net.atlas.combatify.Combatify;
import net.atlas.combatify.item.KnifeItem;
import net.atlas.combatify.item.LongSwordItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.lithereal.item.ability.AbilityKnifeItem;
import org.lithereal.item.ability.AbilityLongSwordItem;
import org.lithereal.item.infused.InfusedLitheriteKnifeItem;
import org.lithereal.item.infused.InfusedLitheriteLongSwordItem;
import org.lithereal.item.ability.Ability;

public class CombatifyHooks {
    public static Item generateLongsword(Tier tier, int weaponLevel, Item.Properties properties) {
        return new LongSwordItem(tier, weaponLevel, properties);
    }
    public static Item generateKnife(Tier tier, Item.Properties properties) {
        return new KnifeItem(tier, properties);
    }
    public static Item generateAbilityLongsword(Ability ability, Tier tier, int weaponLevel, Item.Properties properties) {
        return new AbilityLongSwordItem(ability, tier, weaponLevel, properties);
    }
    public static Item generateAbilityKnife(Ability ability, Tier tier, Item.Properties properties) {
        return new AbilityKnifeItem(ability, tier, properties);
    }
    public static Item generateInfusedLongsword(Tier tier, int weaponLevel, Item.Properties properties) {
        return new InfusedLitheriteLongSwordItem(tier, weaponLevel, properties);
    }
    public static Item generateInfusedKnife(Tier tier, Item.Properties properties) {
        return new InfusedLitheriteKnifeItem(tier, properties);
    }

    public static float getTierDamageNerf() {
        return Combatify.CONFIG.tierDamageNerf() ? 1 : 0;
    }
}
