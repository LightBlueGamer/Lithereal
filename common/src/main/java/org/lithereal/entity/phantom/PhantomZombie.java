package org.lithereal.entity.phantom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.lithereal.entity.ModEntities;
import org.lithereal.entity.attack.Attack;
import org.lithereal.entity.attack.AttackType;
import org.lithereal.entity.phantom.brain.goals.PhantomAttackGoal;

import java.util.*;

public class PhantomZombie extends Zombie implements PhantomMob<PhantomZombie> {
    private static final EntityDataAccessor<Boolean> DATA_PHASING_ID = SynchedEntityData.defineId(PhantomZombie.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_PHASING_TIMER_ID = SynchedEntityData.defineId(PhantomZombie.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_INITIAL_PHASING_TIMER_ID = SynchedEntityData.defineId(PhantomZombie.class, EntityDataSerializers.INT);
    public final Map<ResourceLocation, AttackType<PhantomZombie>> attackTypes = new HashMap<>();
    public List<Attack<PhantomZombie>> activeAttacks = new ArrayList<>();
    public PhantomZombie(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
        init();
    }

    public PhantomZombie(Level level) {
        this(ModEntities.PHANTOM_ZOMBIE.get(), level);
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new PhantomAttackGoal<>(this, 0.6, 3.0F));
        super.addBehaviourGoals();
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
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
    public PhantomZombie self() {
        return this;
    }

    @Override
    public AttackType<PhantomZombie> getAttackType(ResourceLocation id) {
        return attackTypes.get(id);
    }

    @Override
    public void addAttackType(ResourceLocation id, AttackType<PhantomZombie> attackType) {
        attackTypes.put(id, attackType);
    }

    @Override
    public List<Attack<PhantomZombie>> getAttacks() {
        return activeAttacks;
    }

    @Override
    public void addRunnableAttack(Attack<PhantomZombie> attack) {
        activeAttacks.add(attack);
    }

    @Override
    public void cullAttacks() {
        activeAttacks = new ArrayList<>(activeAttacks.stream().filter(attack -> !attack.hasExpired()).toList());
    }

    @Override
    public boolean isUnderWaterConverting() {
        return super.isUnderWaterConverting() && !this.isPhasing();
    }

    @Override
    protected @NotNull ItemStack getSkull() {
        return ItemStack.EMPTY;
    }
}
