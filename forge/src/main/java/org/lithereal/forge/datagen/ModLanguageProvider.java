package org.lithereal.forge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.lithereal.Lithereal;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Lithereal.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
    }
}
