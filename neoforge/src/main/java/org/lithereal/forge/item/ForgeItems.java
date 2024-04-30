package org.lithereal.forge.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lithereal.Lithereal;
import org.lithereal.forge.item.custom.ForgeLitheriteItem;

public class ForgeItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, Lithereal.MOD_ID);

    public static final DeferredHolder<Item, ForgeLitheriteItem> LITHERITE_CRYSTAL = ITEMS.register("litherite_crystal",
            () -> new ForgeLitheriteItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
