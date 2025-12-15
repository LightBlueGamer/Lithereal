package org.lithereal.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ModItems;

public class ModChestBoat extends ChestBoat implements CustomBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModChestBoat.class, EntityDataSerializers.INT);

    public ModChestBoat(EntityType<? extends ChestBoat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModChestBoat(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.MOD_CHEST_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public @NotNull Item getDropItem() {
        switch (getModVariant()) {
            case PHANTOM_OAK -> {
                return ModItems.PHANTOM_OAK_CHEST_BOAT.get();
            }
        }
        return super.getDropItem();
    }

    @Override
    public Boat self() {
        return this;
    }

    public void setVariant(ModBoat.Type type) {
        this.entityData.set(DATA_ID_TYPE, type.ordinal());
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, ModBoat.Type.PHANTOM_OAK.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(ModBoat.Type.byName(pCompound.getString("Type")));
        }
    }

    public ModBoat.Type getModVariant() {
        return ModBoat.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }
}