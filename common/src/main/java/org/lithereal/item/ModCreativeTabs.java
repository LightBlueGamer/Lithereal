package org.lithereal.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModStoneBlocks;
import org.lithereal.block.ModTreeBlocks;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Lithereal.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> LITHEREAL_TAB = TABS.register("lithereal_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + Lithereal.MOD_ID + ".lithereal.tab"),
                    () -> new ItemStack(LitherealExpectPlatform.getLitheriteItem())));

    public static final RegistrySupplier<CreativeModeTab> BUILDING_BLOCKS_TAB = TABS.register("building_blocks_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + Lithereal.MOD_ID + ".building_blocks.tab"),
                    () -> new ItemStack(ModTreeBlocks.PHANTOM_OAK_LOG.get())));

    public static final RegistrySupplier<CreativeModeTab> NATURAL_BLOCKS_TAB = TABS.register("natural_blocks_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + Lithereal.MOD_ID + ".natural_blocks.tab"),
                    () -> new ItemStack(ModStoneBlocks.PAILITE.get())));

    public static final RegistrySupplier<CreativeModeTab> MATERIALS_TAB = TABS.register("materials_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + Lithereal.MOD_ID + ".materials.tab"),
                    () -> new ItemStack(ModRawMaterialItems.UNIFIER.get())));

    public static final RegistrySupplier<CreativeModeTab> TOOLS_TAB = TABS.register("tools_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + Lithereal.MOD_ID + ".tools.tab"),
                    () -> new ItemStack(ModToolItems.LITHERITE_PICKAXE.get())));

    public static final RegistrySupplier<CreativeModeTab> COMBAT_TAB = TABS.register("combat_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + Lithereal.MOD_ID + ".combat.tab"),
                    () -> new ItemStack(ModToolItems.ODYSIUM_SWORD.get())));

    public static void register() {
        TABS.register();
    }
}
