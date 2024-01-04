package org.lithereal.forge.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lithereal.Lithereal;
import org.lithereal.forge.item.custom.ForgeLitheriteItem;

public class ForgeItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Lithereal.MOD_ID);

    public static final RegistryObject<Item> LITHERITE_CRYSTAL = ITEMS.register("litherite_crystal",
            () -> new ForgeLitheriteItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
