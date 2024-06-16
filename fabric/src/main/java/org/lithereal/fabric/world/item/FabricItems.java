package org.lithereal.fabric.world.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.lithereal.Lithereal;
import org.lithereal.world.item.LitheriteItem;

@SuppressWarnings("ALL")
public class FabricItems {

    public static final Item LITHERITE_CRYSTAL =
            registerItem("litherite_crystal", new LitheriteItem(new Item.Properties()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Lithereal.MOD_ID, name), item);
    }

    public static void registerModItems() {
    }
}
