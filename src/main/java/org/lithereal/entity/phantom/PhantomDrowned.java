package org.lithereal.entity.phantom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
    public final Map<Identifier, AttackType<PhantomDrowned>> attackTypes = new HashMap<>();
    public List<Attack<PhantomDrowned>> activeAttacks = new ArrayList<>();
    public PhantomDrowned(EntityType<? extends Drowned> entityType, Level level) {
        super(entityType, level);
        init();
        this.xpReward = 20;
    }

    public PhantomDrowned(Level level) {
        this(ModEntities.PHANTOM_DROWNED.get(), level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return PhantomZombie.createAttributes().add(Attributes.STEP_HEIGHT, 1.0);
    }

    public static boolean checkPhantomDrownedSpawnRules(
            final EntityType<PhantomDrowned> type, final ServerLevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random
    ) {
        if (!level.getFluidState(pos.below()).is(FluidTags.WATER) && !EntitySpawnReason.isSpawner(spawnReason)) {
            return false;
        } else {
            Holder<Biome> biome = level.getBiome(pos);
            boolean canMonsterSpawn = level.getDifficulty() != Difficulty.PEACEFUL
                    && (EntitySpawnReason.ignoresLightRequirements(spawnReason) || isDarkEnoughToSpawn(level, pos, random))
                    && (EntitySpawnReason.isSpawner(spawnReason) || level.getFluidState(pos).is(FluidTags.WATER));
            if (!canMonsterSpawn || !EntitySpawnReason.isSpawner(spawnReason) && spawnReason != EntitySpawnReason.REINFORCEMENT) {
                return biome.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
                        ? random.nextInt(15) == 0 && canMonsterSpawn
                        : random.nextInt(40) == 0 && isDeepEnoughToSpawn(level, pos) && canMonsterSpawn;
            } else {
                return true;
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
    protected void doUnderWaterConversion(ServerLevel level) {
        this.convertToZombieType(level, ModEntities.PHANTOM_DROWNED.get());
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1040, this.blockPosition(), 0);
        }
        super.doUnderWaterConversion(level);
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        loadPhantom(valueInput);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        savePhantom(valueOutput);
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
    public AttackType<PhantomDrowned> getAttackType(Identifier id) {
        return attackTypes.get(id);
    }

    @Override
    public void addAttackType(Identifier id, AttackType<PhantomDrowned> attackType) {
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
}
