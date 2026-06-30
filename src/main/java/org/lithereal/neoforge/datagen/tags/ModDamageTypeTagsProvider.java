package org.lithereal.neoforge.datagen.tags;

//? neoforge {
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import org.lithereal.Lithereal;
import org.lithereal.entity.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Lithereal.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)
                .add(ModDamageTypes.BURN, ModDamageTypes.FROST, ModDamageTypes.FROSTBURN, ModDamageTypes.HOLY);
        this.tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(ModDamageTypes.HOLY);
        this.tag(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(ModDamageTypes.BURN, ModDamageTypes.FROST, ModDamageTypes.FROSTBURN);
        this.tag(DamageTypeTags.IS_FIRE)
                .add(ModDamageTypes.BURN, ModDamageTypes.BURNING_ITEM, ModDamageTypes.BURNING_SPEAR);
        this.tag(DamageTypeTags.IS_FREEZING)
                .add(ModDamageTypes.FROST, ModDamageTypes.FREEZING_ITEM, ModDamageTypes.FREEZING_SPEAR,
                        ModDamageTypes.FROSTBURN, ModDamageTypes.FROSTBITTEN_ITEM, ModDamageTypes.FROSTBITTEN_SPEAR);
        this.tag(DamageTypeTags.NO_KNOCKBACK)
                .add(ModDamageTypes.HOLY, ModDamageTypes.HOLY_SPEAR,
                        ModDamageTypes.BURNING_SPEAR,
                        ModDamageTypes.FREEZING_SPEAR,
                        ModDamageTypes.FROSTBITTEN_SPEAR);
        this.tag(DamageTypeTags.IS_PLAYER_ATTACK)
                .add(ModDamageTypes.HOLY_SPEAR,
                        ModDamageTypes.BURNING_SPEAR,
                        ModDamageTypes.FREEZING_SPEAR,
                        ModDamageTypes.FROSTBITTEN_SPEAR);
    }
}
//?}