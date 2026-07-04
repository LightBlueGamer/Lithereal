package org.lithereal.item;

import com.mojang.datafixers.util.Either;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import org.lithereal.item.ability.*;
import org.lithereal.item.datagen.ItemDataTemplate;
import org.lithereal.item.datagen.ItemDataTemplates;
import org.lithereal.item.infused.*;

import static org.lithereal.item.ModItems.*;
import static org.lithereal.item.ModItems.ODYSIUM;

public class ModToolItems {
    public static final RegistrySupplier<Item> ODYSIUM_BOW = ItemDataTemplates.BOW.consumeSpecialWithIngredients("odysium_bow", properties ->
            new ModBowItem(ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON), 2, 0.2F, 1), Either.left(ModRawMaterialItems.ODYSIUM_INGOT), Either.left(() -> Items.IRON_NUGGET), Either.left(() -> Items.STRING));

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_BOW = ItemDataTemplates.BOW
            .copyWithRecipeOverride(ItemDataTemplate.NONE).consumeSpecial("enhanced_odysium_bow", properties -> 
                    new ModBowItem(ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON), 3, 0.1F, 1.5F));

    public static final RegistrySupplier<Item> WAR_HAMMER = ItemDataTemplates.WAR_HAMMER.consumeSpecialWithIngredients("war_hammer", properties ->
            new WarHammerItem(ODYSIUM, 1, -3.4F, properties.durability(500).fireResistant().rarity(Rarity.EPIC)), Either.left(() -> Items.HEAVY_CORE), Either.left(MYSTERIOUS_ROD));

    public static final RegistrySupplier<Item> LITHERITE_BRUSH = ItemDataTemplates.BRUSH.consumeSpecial("litherite_brush", properties ->
            new LitheriteBrushItem(properties.durability(256)), Either.left(ModRawMaterialItems.LITHERITE_CRYSTAL));

    public static final RegistrySupplier<Item> LITHERITE_WRENCH = ItemDataTemplates.WRENCH.consumeSpecialWithIngredients("litherite_wrench", properties ->
            new WrenchItem(properties.stacksTo(1).durability(128)), Either.left(ModRawMaterialItems.LITHERITE_CRYSTAL), Either.right(ItemTags.IRON_TOOL_MATERIALS));

    public static void register() {

    }
}
