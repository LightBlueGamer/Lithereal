package org.lithereal.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.entity.ModBoat;
import org.lithereal.sounds.ModSounds;
import org.lithereal.tags.ModTags;
import org.lithereal.item.compat.CompatInit;
import org.lithereal.util.BaseBossEssence;


import java.util.Collections;
import java.util.List;

import static dev.architectury.platform.Platform.isModLoaded;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ITEM);

    public static final Tier LITHERITE = ModTier.create("litherite", 4, 1791, 11.0F, 4.0F, 20, () -> Ingredient.of(LitherealExpectPlatform.getLitheriteItem()), ModTags.INCORRECT_FOR_LITHERITE_TOOL);

    public static final Tier ODYSIUM = ModTier.create("odysium", 5, 2431, 13.0F, 5.0F, 22, () -> Ingredient.of(ModRawMaterialItems.ODYSIUM_INGOT.get()), ModTags.INCORRECT_FOR_ODYSIUM_TOOL);

    public static final RegistrySupplier<Item> MYSTERIOUS_ROD = ITEMS.register("mysterious_rod", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistrySupplier<Item> MOLTEN_LITHERITE_BUCKET = ITEMS.register("molten_litherite_bucket", () ->
            new Item(new Item.Properties().stacksTo(1)));

    public static final RegistrySupplier<Item> LITHER_CHARGE = ITEMS.register("lither_charge", () ->
            new LitherChargeItem(new Item.Properties()));

    public static final RegistrySupplier<Item> LITHEREAL_KEY = ITEMS.register("lithereal_key", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MUSIC_DISC_SPARKLE = ITEMS.register("music_disc_sparkle", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).jukeboxPlayable(ModSounds.SPARKLE)));

    public static final RegistrySupplier<Item> BOSS_ESSENCE = ITEMS.register("boss_essence", () ->
            new BossEssenceItem(new Item.Properties().rarity(Rarity.RARE), new BaseBossEssence(14100520, 0, Collections.emptyList())));

    public static final RegistrySupplier<Item> AWAKENED_BOSS_ESSENCE = ITEMS.register("awakened_boss_essence", () ->
            new BossEssenceItem(new Item.Properties().rarity(Rarity.RARE), new BaseBossEssence(4726207, 1, Collections.emptyList())));

    public static final RegistrySupplier<Item> PURE_BOSS_ESSENCE = ITEMS.register("pure_boss_essence", () ->
            new BossEssenceItem(new Item.Properties().rarity(Rarity.RARE), new BaseBossEssence(13985849, 2, List.of(new MobEffectInstance(MobEffects.REGENERATION, 6000)))));

    public static final RegistrySupplier<Item> ODYSIUM_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("odysium_upgrade_smithing_template",
            ModSmithingTemplateItem::createOdysiumUpgradeTemplate);

    public static final RegistrySupplier<Item> PHANTOM_OAK_SIGN = ITEMS.register("phantom_oak_sign", () -> new SignItem(new Item.Properties(), ModTreeBlocks.PHANTOM_OAK_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_SIGN.get()));

    public static final RegistrySupplier<Item> PHANTOM_OAK_HANGING_SIGN = ITEMS.register("phantom_oak_hanging_sign", () -> new HangingSignItem(ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> PHANTOM_OAK_BOAT = ITEMS.register("phantom_oak_boat", () -> new ModBoatItem(false, ModBoat.Type.PHANTOM_OAK, new Item.Properties().stacksTo(1)));

    public static final RegistrySupplier<Item> PHANTOM_OAK_CHEST_BOAT = ITEMS.register("phantom_oak_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.PHANTOM_OAK, new Item.Properties().stacksTo(1)));

    public static final RegistrySupplier<Item> LITHER_TORCH = ITEMS.register("lither_torch", () -> new StandingAndWallBlockItem(ModBlocks.LITHER_TORCH.get(), ModBlocks.LITHER_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));



    public static void register() {
        ModRawMaterialItems.register();
        ModArmorItems.register();
        ModToolItems.register();
        if (isModLoaded("combatify"))
            CompatInit.initCombatify();
        ITEMS.register();
    }
}
