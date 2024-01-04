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

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Lithereal.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> LITHEREAL_TAB = TABS.register("lithereal_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + Lithereal.MOD_ID + ".lithereal.tab"),
                    () -> new ItemStack(LitherealExpectPlatform.getLitheriteItem())));

    public static void register() {
        TABS.register();
    }
}
