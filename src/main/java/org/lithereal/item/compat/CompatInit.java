package org.lithereal.item.compat;

import dev.architectury.registry.CreativeTabRegistry;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import org.lithereal.data.compat.CombatifyHooks;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModToolItems;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class CompatInit {
    public static void initCombatify() {
        CombatifyItems.init();
    }

    public static float getToolMaterialDamageNerf() {
        return CombatifyHooks.getTierDamageNerf();
    }

    public static void populateCombatTabForCombatify() {
        CreativeTabRegistry.modify(ModCreativeTabs.COMBAT_TAB, (flags, output, canUseGameMasterBlocks) -> {
            output.acceptAllAfter(ModToolItems.LITHERITE_SWORD.get(), List.of(CombatifyItems.LITHERITE_KNIFE.get().getDefaultInstance(), CombatifyItems.LITHERITE_LONGSWORD.get().getDefaultInstance()));
            output.acceptAllAfter(ModToolItems.BURNING_LITHERITE_SWORD.get(), List.of(CombatifyItems.BURNING_LITHERITE_KNIFE.get().getDefaultInstance(), CombatifyItems.BURNING_LITHERITE_LONGSWORD.get().getDefaultInstance()));
            output.acceptAllAfter(ModToolItems.FROZEN_LITHERITE_SWORD.get(), List.of(CombatifyItems.FROZEN_LITHERITE_KNIFE.get().getDefaultInstance(), CombatifyItems.FROZEN_LITHERITE_LONGSWORD.get().getDefaultInstance()));
            output.acceptAllAfter(ModToolItems.SMOLDERING_LITHERITE_SWORD.get(), List.of(CombatifyItems.SMOLDERING_LITHERITE_KNIFE.get().getDefaultInstance(), CombatifyItems.SMOLDERING_LITHERITE_LONGSWORD.get().getDefaultInstance()));
            output.acceptAllAfter(ModToolItems.FROSTBITTEN_LITHERITE_SWORD.get(), List.of(CombatifyItems.FROSTBITTEN_LITHERITE_KNIFE.get().getDefaultInstance(), CombatifyItems.FROSTBITTEN_LITHERITE_LONGSWORD.get().getDefaultInstance()));
            output.acceptAllAfter(ModToolItems.WITHERING_LITHERITE_SWORD.get(), List.of(CombatifyItems.WITHERING_LITHERITE_KNIFE.get().getDefaultInstance(), CombatifyItems.WITHERING_LITHERITE_LONGSWORD.get().getDefaultInstance()));
            output.acceptAllAfter(ModToolItems.ODYSIUM_SWORD.get(), List.of(CombatifyItems.ODYSIUM_KNIFE.get().getDefaultInstance(), CombatifyItems.ODYSIUM_LONGSWORD.get().getDefaultInstance()));
            output.acceptAllAfter(ModToolItems.ENHANCED_ODYSIUM_SWORD.get(), List.of(CombatifyItems.ENHANCED_ODYSIUM_KNIFE.get().getDefaultInstance(), CombatifyItems.ENHANCED_ODYSIUM_LONGSWORD.get().getDefaultInstance()));
        });
    }

    public static List<Supplier<ItemStack>> populateInfusedForCombatify(List<Supplier<ItemStack>> litherite, Holder<Potion> potion) {
        List<ItemLike> itemLikes = Arrays.asList(CombatifyItems.INFUSED_LITHERITE_LONGSWORD.get(),
                CombatifyItems.INFUSED_LITHERITE_KNIFE.get());
        for (ItemLike itemLike : itemLikes) {
            litherite.add(() -> {
                ItemStack current = new ItemStack(itemLike);
                current.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
                return current;
            });
        }
        return litherite;
    }

    public static void addInfusedSubtypesForCombatify(ISubtypeRegistration registration, ISubtypeInterpreter<ItemStack> interpreter) {
        registration.registerSubtypeInterpreter(CombatifyItems.INFUSED_LITHERITE_LONGSWORD.get(), interpreter);
        registration.registerSubtypeInterpreter(CombatifyItems.INFUSED_LITHERITE_KNIFE.get(), interpreter);
    }
}