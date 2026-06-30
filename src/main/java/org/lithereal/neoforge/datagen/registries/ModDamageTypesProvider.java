package org.lithereal.neoforge.datagen.registries;

//? neoforge {
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import org.lithereal.entity.ModDamageTypes;

public class ModDamageTypesProvider implements RegistryEntryProvider<DamageType> {
    @Override
    public ResourceKey<Registry<DamageType>> key() {
        return Registries.DAMAGE_TYPE;
    }

    @Override
    public void run(BootstrapContext<DamageType> context) {
        context.register(ModDamageTypes.BURN, new DamageType("burn", 0.1F, DamageEffects.BURNING));
        context.register(ModDamageTypes.BURNING_ITEM, new DamageType("burning_item", 0.1F, DamageEffects.BURNING));
        context.register(ModDamageTypes.BURNING_SPEAR, new DamageType("burning_spear", 0.1F, DamageEffects.BURNING));
        context.register(ModDamageTypes.FROST, new DamageType("frost", 0F, DamageEffects.FREEZING));
        context.register(ModDamageTypes.FREEZING_ITEM, new DamageType("freezing_item", 0F, DamageEffects.FREEZING));
        context.register(ModDamageTypes.FREEZING_SPEAR, new DamageType("freezing_spear", 0F, DamageEffects.FREEZING));
        context.register(ModDamageTypes.FROSTBURN, new DamageType("frostburn", 0F, DamageEffects.FREEZING));
        context.register(ModDamageTypes.FROSTBITTEN_ITEM, new DamageType("frostbitten_item", 0F, DamageEffects.FREEZING));
        context.register(ModDamageTypes.FROSTBITTEN_SPEAR, new DamageType("frostbitten_spear", 0F, DamageEffects.FREEZING));
        context.register(ModDamageTypes.HOLY, new DamageType("holy", 0.2F));
        context.register(ModDamageTypes.HOLY_ITEM, new DamageType("holy_item", 0.2F));
        context.register(ModDamageTypes.HOLY_SPEAR, new DamageType("holy_spear", 0.2F));
    }
}
//?}