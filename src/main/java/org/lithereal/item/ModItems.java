package org.lithereal.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.entity.ModEntities;
import org.lithereal.sounds.ModSounds;
import org.lithereal.tags.ModTags;
import org.lithereal.item.compat.CompatInit;
import org.lithereal.util.BaseBossEssence;
import org.lithereal.util.ToolMaterialHooks;


import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static dev.architectury.platform.Platform.isModLoaded;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Lithereal.MOD_ID, Registries.ITEM);

    public static final ToolMaterial LITHERITE = ToolMaterialHooks.create("litherite", 1791, 11.0F, 3.0F, 20, ModTags.LITHERITE_REPAIR_ITEMS, BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

    public static final ToolMaterial ODYSIUM = ToolMaterialHooks.create("odysium", 2431, 13.0F, 5.0F, 22, ModTags.ODYSIUM_REPAIR_ITEMS, ModTags.INCORRECT_FOR_ODYSIUM_TOOL);

    public static final RegistrySupplier<Item> MYSTERIOUS_ROD = registerItem("mysterious_rod", properties ->
            new Item(properties.fireResistant()));

    public static final RegistrySupplier<Item> MOLTEN_LITHERITE_BUCKET = registerItem("molten_litherite_bucket", properties ->
            new Item(properties.craftRemainder(Items.BUCKET).stacksTo(16)));

    public static final RegistrySupplier<Item> LITHER_CHARGE = registerItem("lither_charge", LitherChargeItem::new);

    public static final RegistrySupplier<Item> LITHEREAL_KEY = registerItem("lithereal_key", Item::new);

    public static final RegistrySupplier<Item> MUSIC_DISC_SPARKLE = registerItem("music_disc_sparkle", properties ->
            new Item(properties.rarity(Rarity.RARE).stacksTo(1).jukeboxPlayable(ModSounds.SPARKLE)));

    public static final RegistrySupplier<Item> BOSS_ESSENCE = registerItem("boss_essence", properties ->
            new BossEssenceItem(properties.craftRemainder(Items.GLASS_BOTTLE).rarity(Rarity.RARE), new BaseBossEssence(14100520, 0, Collections.emptyList())));

    public static final RegistrySupplier<Item> AWAKENED_BOSS_ESSENCE = registerItem("awakened_boss_essence", properties ->
            new BossEssenceItem(properties.craftRemainder(Items.GLASS_BOTTLE).rarity(Rarity.RARE), new BaseBossEssence(4726207, 1, Collections.emptyList())));

    public static final RegistrySupplier<Item> PURE_BOSS_ESSENCE = registerItem("pure_boss_essence", properties ->
            new BossEssenceItem(properties.craftRemainder(Items.GLASS_BOTTLE).rarity(Rarity.RARE), new BaseBossEssence(13985849, 2, List.of(new MobEffectInstance(MobEffects.REGENERATION, 6000)))));

    public static final RegistrySupplier<Item> ODYSIUM_UPGRADE_SMITHING_TEMPLATE = registerItem("odysium_upgrade_smithing_template",
            ModSmithingTemplates.createOdysiumUpgradeTemplate());

    public static final RegistrySupplier<Item> PHANTOM_OAK_SIGN = registerItem("phantom_oak_sign", properties -> new SignItem(ModTreeBlocks.PHANTOM_OAK_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_SIGN.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> PHANTOM_OAK_HANGING_SIGN = registerItem("phantom_oak_hanging_sign", properties -> new HangingSignItem(ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> PHANTOM_OAK_BOAT = registerItem("phantom_oak_boat", properties -> new BoatItem(ModEntities.PHANTOM_OAK_BOAT.get(), properties.stacksTo(1)));

    public static final RegistrySupplier<Item> PHANTOM_OAK_CHEST_BOAT = registerItem("phantom_oak_chest_boat", properties -> new BoatItem(ModEntities.PHANTOM_OAK_CHEST_BOAT.get(), properties.stacksTo(1)));

    public static final RegistrySupplier<Item> FORTSHROOM_SIGN = registerItem("fortshroom_sign", properties -> new SignItem(ModTreeBlocks.FORTSHROOM_SIGN.get(), ModTreeBlocks.FORTSHROOM_WALL_SIGN.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> FORTSHROOM_HANGING_SIGN = registerItem("fortshroom_hanging_sign", properties -> new HangingSignItem(ModTreeBlocks.FORTSHROOM_HANGING_SIGN.get(), ModTreeBlocks.FORTSHROOM_WALL_HANGING_SIGN.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> FORTSHROOM_BOAT = registerItem("fortshroom_boat", properties -> new BoatItem(ModEntities.FORTSHROOM_BOAT.get(), properties.stacksTo(1)));

    public static final RegistrySupplier<Item> FORTSHROOM_CHEST_BOAT = registerItem("fortshroom_chest_boat", properties -> new BoatItem(ModEntities.FORTSHROOM_CHEST_BOAT.get(), properties.stacksTo(1)));

    public static final RegistrySupplier<Item> MALISHROOM_SIGN = registerItem("malishroom_sign", properties -> new SignItem(ModTreeBlocks.MALISHROOM_SIGN.get(), ModTreeBlocks.MALISHROOM_WALL_SIGN.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> MALISHROOM_HANGING_SIGN = registerItem("malishroom_hanging_sign", properties -> new HangingSignItem(ModTreeBlocks.MALISHROOM_HANGING_SIGN.get(), ModTreeBlocks.MALISHROOM_WALL_HANGING_SIGN.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> MALISHROOM_BOAT = registerItem("malishroom_boat", properties -> new BoatItem(ModEntities.MALISHROOM_BOAT.get(), properties.stacksTo(1)));

    public static final RegistrySupplier<Item> MALISHROOM_CHEST_BOAT = registerItem("malishroom_chest_boat", properties -> new BoatItem(ModEntities.MALISHROOM_CHEST_BOAT.get(), properties.stacksTo(1)));

    public static final RegistrySupplier<Item> LITHER_TORCH = registerItem("lither_torch", properties -> new StandingAndWallBlockItem(ModBlocks.LITHER_TORCH.get(), ModBlocks.LITHER_WALL_TORCH.get(), Direction.DOWN, properties.useBlockDescriptionPrefix()));

    public static <T extends Item> RegistrySupplier<Item> registerItem(String name, Function<Item.Properties, T> propertiesItemFunction) {
        ResourceKey<Item> resourceKey = Lithereal.key(Registries.ITEM, name);
        return ITEMS.register(name, () -> propertiesItemFunction.apply(new Item.Properties().setId(resourceKey)));
    }

    public static void register() {
        ModRawMaterialItems.register();
        ModArmorItems.register();
        ModToolItems.register();
        if (isModLoaded("combatify"))
            CompatInit.initCombatify();
        ITEMS.register();
    }
}
