package org.lithereal.util;

import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.Hammer;
import org.lithereal.item.custom.ability.AbilityHammer;
import org.lithereal.item.custom.burning.BurningLitheriteHammer;
import org.lithereal.item.custom.component.ModComponents;
import org.lithereal.item.custom.infused.InfusedLitheriteHammer;

import static dev.architectury.platform.Platform.isModLoaded;

public class CommonUtils {
    public static boolean hasFullSuitOfArmorOn(Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            bl &= !armorStack.isEmpty();
        }

        return bl;
    }

    public static boolean hasCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        boolean bl = true;
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
            bl &= ((ArmorItem)armorStack.getItem()).getMaterial() == material;
        }

        return bl;
    }

    public static <T> boolean isAnyOf(T t, T... objects) {
        boolean ret = false;
        for (T object : objects) {
            ret |= t == object;
        }
        return ret;
    }
    public static MobEffectInstance clone(MobEffectInstance original) {
        return new MobEffectInstance(original);
    }

    public static NonNullList<Ingredient> of(Ingredient... values) {
        return NonNullList.of(Ingredient.EMPTY, values);
    }

    public static boolean isEnhanced(ItemStack stack) {
        return stack.getOrDefault(ModComponents.ENHANCED.get(), false);
    }

    public static boolean isEnhanced(CompoundTag tag) {
        return tag != null && tag.getBoolean("Enhanced");
    }

    public static ItemStack setEnhanced(ItemStack stack, boolean bl) {
        stack.set(ModComponents.ENHANCED.get(), bl);
        return stack;
    }

    public static void setEnhanced(CompoundTag tag, boolean bl) {
        tag.putBoolean("Enhanced", bl);
    }

    public static Hammer createHammer(Tier tier, int damage, float attackSpeed, Item.Properties properties) {
        if (isModLoaded("combatify"))
            return LitherealExpectPlatform.createHammerWithType(tier, damage, attackSpeed, properties);
        return new Hammer(tier, damage, attackSpeed, properties);
    }

    public static AbilityHammer createAbilityHammer(Ability ability, Tier tier, int damage, float attackSpeed, Item.Properties properties) {
        if (isModLoaded("combatify"))
            return LitherealExpectPlatform.createAbilityHammerWithType(ability, tier, damage, attackSpeed, properties);
        return new AbilityHammer(ability, tier, damage, attackSpeed, properties);
    }

    public static BurningLitheriteHammer createBurningHammer(Tier tier, int damage, float attackSpeed, Item.Properties properties) {
        if (isModLoaded("combatify"))
            return LitherealExpectPlatform.createBurningHammerWithType(tier, damage, attackSpeed, properties);
        return new BurningLitheriteHammer(tier, damage, attackSpeed, properties);
    }

    public static InfusedLitheriteHammer createInfusedHammer(Tier tier, int damage, float attackSpeed, Item.Properties properties) {
        if (isModLoaded("combatify"))
            return LitherealExpectPlatform.createInfusedHammerWithType(tier, damage, attackSpeed, properties);
        return new InfusedLitheriteHammer(tier, damage, attackSpeed, properties);
    }
}
