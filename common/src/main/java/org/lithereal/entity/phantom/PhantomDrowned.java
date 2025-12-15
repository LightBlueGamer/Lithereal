package org.lithereal.entity.phantom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.lithereal.entity.ModEntities;
import org.lithereal.entity.attack.Attack;
import org.lithereal.entity.attack.AttackType;
import org.lithereal.entity.phantom.brain.goals.PhantomAttackGoal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhantomDrowned extends Drowned implements PhantomMob<PhantomDrowned> {
    private static final EntityDataAccessor<Boolean> DATA_PHASING_ID = SynchedEntityData.defineId(PhantomDrowned.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_PHASING_TIMER_ID = SynchedEntityData.defineId(PhantomDrowned.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_INITIAL_PHASING_TIMER_ID = SynchedEntityData.defineId(PhantomDrowned.class, EntityDataSerializers.INT);
    public final Map<ResourceLocation, AttackType<PhantomDrowned>> attackTypes = new HashMap<>();
    public List<Attack<PhantomDrowned>> activeAttacks = new ArrayList<>();
    public PhantomDrowned(EntityType<? extends Drowned> entityType, Level level) {
        super(entityType, level);
        init();
    }

    public PhantomDrowned(Level level) {
        this(ModEntities.PHANTOM_DROWNED.get(), level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return PhantomZombie.createAttributes().add(Attributes.STEP_HEIGHT, 1.0);
    }

    public static boolean checkPhantomDrownedSpawnRules(
            EntityType<? extends Drowned> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource
    ) {
        if (!serverLevelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && !MobSpawnType.isSpawner(mobSpawnType)) {
            return false;
        } else {
            Holder<Biome> holder = serverLevelAccessor.getBiome(blockPos);
            boolean bl = serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL
                    && (MobSpawnType.ignoresLightRequirements(mobSpawnType) || isDarkEnoughToSpawn(serverLevelAccessor, blockPos, randomSource))
                    && (MobSpawnType.isSpawner(mobSpawnType) || serverLevelAccessor.getFluidState(blockPos).is(FluidTags.WATER));
            if (bl && MobSpawnType.isSpawner(mobSpawnType)) {
                return true;
            } else {
                return holder.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
                        ? randomSource.nextInt(15) == 0 && bl
                        : randomSource.nextInt(40) == 0 && isDeepEnoughToSpawn(serverLevelAccessor, blockPos) && bl;
            }
        }
    }

    private static boolean isDeepEnoughToSpawn(LevelAccessor levelAccessor, BlockPos blockPos) {
        return blockPos.getY() < levelAccessor.getSeaLevel() - 5;
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new PhantomAttackGoal<>(this, 0.6, 3.0F));
        super.addBehaviourGoals();
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        onTick();
    }

    @Override
    protected void doUnderWaterConversion() {
        this.convertToZombieType(ModEntities.PHANTOM_DROWNED.get());
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1040, this.blockPosition(), 0);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        loadPhantom(compoundTag, registryAccess());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        savePhantom(compoundTag, registryAccess());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_PHASING_ID, false);
        builder.define(DATA_PHASING_TIMER_ID, -60);
        builder.define(DATA_INITIAL_PHASING_TIMER_ID, -60);
    }

    @Override
    public void setPhasing(boolean nowPhasing) {
        getEntityData().set(DATA_PHASING_ID, nowPhasing);
    }

    @Override
    public boolean isPhasing() {
        return getEntityData().get(DATA_PHASING_ID);
    }

    @Override
    public void startPhasingTimer(int phasingTimer) {
        setPhasingTimer(phasingTimer);
        getEntityData().set(DATA_INITIAL_PHASING_TIMER_ID, phasingTimer);
    }

    @Override
    public void setPhasingTimer(int phasingTimer) {
        getEntityData().set(DATA_PHASING_TIMER_ID, phasingTimer);
    }

    @Override
    public int getPhasingTimer() {
        return getEntityData().get(DATA_PHASING_TIMER_ID);
    }

    @Override
    public int getInitialPhasingTimer() {
        return getEntityData().get(DATA_INITIAL_PHASING_TIMER_ID);
    }

    @Override
    public PhantomDrowned self() {
        return this;
    }

    @Override
    public AttackType<PhantomDrowned> getAttackType(ResourceLocation id) {
        return attackTypes.get(id);
    }

    @Override
    public void addAttackType(ResourceLocation id, AttackType<PhantomDrowned> attackType) {
        attackTypes.put(id, attackType);
    }

    @Override
    public List<Attack<PhantomDrowned>> getAttacks() {
        return activeAttacks;
    }

    @Override
    public void addRunnableAttack(Attack<PhantomDrowned> attack) {
        activeAttacks.add(attack);
    }

    @Override
    public void cullAttacks() {
        activeAttacks = new ArrayList<>(activeAttacks.stream().filter(attack -> !attack.hasExpired()).toList());
    }

    @Override
    protected @NotNull ItemStack getSkull() {
        return ItemStack.EMPTY;
    }
}
