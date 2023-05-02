package org.litecraft.lithereal.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.litecraft.lithereal.Lithereal;

public class ModItemGroup {
    public static ItemGroup LITHEREAL;

    public static void registerItemGroups() {
        LITHEREAL = FabricItemGroup.builder(new Identifier(Lithereal.MOD_ID, "lithereal"))
                .displayName(Text.translatable("itemgroup.lithereal"))
                .icon(() -> new ItemStack(ModItems.LITHERITE_CRYSTAL)).build();
    }
}