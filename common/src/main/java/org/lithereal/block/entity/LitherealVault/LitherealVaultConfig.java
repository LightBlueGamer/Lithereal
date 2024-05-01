package org.lithereal.block.entity.LitherealVault;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector.EntitySelector;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.lithereal.item.ModItems;

import java.util.Optional;

public record LitherealVaultConfig(ResourceKey<LootTable> lootTable, double activationRange, double deactivationRange, ItemStack keyItem, Optional<ResourceKey<LootTable>> overrideLootTableToDisplay, PlayerDetector playerDetector, PlayerDetector.EntitySelector entitySelector) {
    static final String TAG_NAME = "config";
    static LitherealVaultConfig DEFAULT = new LitherealVaultConfig();
    static Codec<LitherealVaultConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ResourceKey.codec(Registries.LOOT_TABLE).lenientOptionalFieldOf("loot_table", DEFAULT.lootTable()).forGetter(LitherealVaultConfig::lootTable), Codec.DOUBLE.lenientOptionalFieldOf("activation_range", DEFAULT.activationRange()).forGetter(LitherealVaultConfig::activationRange), Codec.DOUBLE.lenientOptionalFieldOf("deactivation_range", DEFAULT.deactivationRange()).forGetter(LitherealVaultConfig::deactivationRange), ItemStack.lenientOptionalFieldOf("key_item").forGetter(LitherealVaultConfig::keyItem), ResourceKey.codec(Registries.LOOT_TABLE).lenientOptionalFieldOf("override_loot_table_to_display").forGetter(LitherealVaultConfig::overrideLootTableToDisplay)).apply(instance, LitherealVaultConfig::new);
    }).validate(LitherealVaultConfig::validate);

    private LitherealVaultConfig() {
        this(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, 4.0, 4.5, new ItemStack(ModItems.LITHEREAL_KEY), Optional.empty(), PlayerDetector.INCLUDING_CREATIVE_PLAYERS, EntitySelector.SELECT_FROM_LEVEL);
    }

    public LitherealVaultConfig(ResourceKey<LootTable> resourceKey, double d, double e, ItemStack itemStack, Optional<ResourceKey<LootTable>> optional) {
        this(resourceKey, d, e, itemStack, optional, DEFAULT.playerDetector(), DEFAULT.entitySelector());
    }

    public LitherealVaultConfig(ResourceKey<LootTable> lootTable, double activationRange, double deactivationRange, ItemStack keyItem, Optional<ResourceKey<LootTable>> overrideLootTableToDisplay, PlayerDetector playerDetector, PlayerDetector.EntitySelector entitySelector) {
        this.lootTable = lootTable;
        this.activationRange = activationRange;
        this.deactivationRange = deactivationRange;
        this.keyItem = keyItem;
        this.overrideLootTableToDisplay = overrideLootTableToDisplay;
        this.playerDetector = playerDetector;
        this.entitySelector = entitySelector;
    }

    public PlayerDetector playerDetector() {
        return this.playerDetector;
    }

    private DataResult<LitherealVaultConfig> validate() {
        return this.activationRange > this.deactivationRange ? DataResult.error(() -> {
            return "Activation range must (" + this.activationRange + ") be less or equal to deactivation range (" + this.deactivationRange + ")";
        }) : DataResult.success(this);
    }

    public ResourceKey<LootTable> lootTable() {
        return this.lootTable;
    }

    public double activationRange() {
        return this.activationRange;
    }

    public double deactivationRange() {
        return this.deactivationRange;
    }

    public ItemStack keyItem() {
        return this.keyItem;
    }

    public Optional<ResourceKey<LootTable>> overrideLootTableToDisplay() {
        return this.overrideLootTableToDisplay;
    }

    public PlayerDetector.EntitySelector entitySelector() {
        return this.entitySelector;
    }
}