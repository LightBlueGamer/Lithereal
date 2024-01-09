package org.lithereal.forge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.item.ModItems;

import java.util.ArrayList;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Lithereal.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
    }
}
