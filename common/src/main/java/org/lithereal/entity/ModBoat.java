package org.lithereal.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.item.ModItems;

import java.util.function.IntFunction;

public class ModBoat extends Boat implements CustomBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoat.class, EntityDataSerializers.INT);

    public ModBoat(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModBoat(Level level, double pX, double pY, double pZ) {
        this(ModEntities.MOD_BOAT.get(), level);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public Item getDropItem() {
        return switch (getModVariant()) {
            case PHANTOM_OAK -> ModItems.PHANTOM_OAK_BOAT.get();
        };
    }

    @Override
    public Boat self() {
        return this;
    }

    public void setVariant(Type type) {
        this.entityData.set(DATA_ID_TYPE, type.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, Type.PHANTOM_OAK.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(Type.byName(pCompound.getString("Type")));
        }
    }

    public enum Type implements StringRepresentable {
        PHANTOM_OAK(ModTreeBlocks.PHANTOM_OAK_SET, "phantom_oak");

        private final String name;
        private final BlockSetType setType;
        public static final StringRepresentable.EnumCodec<ModBoat.Type> CODEC = StringRepresentable.fromEnum(ModBoat.Type::values);
        private static final IntFunction<ModBoat.Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        Type(BlockSetType blockSetType, String name) {
            this.name = name;
            this.setType = blockSetType;
        }

        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public BlockSetType getSetType() {
            return this.setType;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by its enum ordinal
         */
        public static ModBoat.Type byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static ModBoat.Type byName(String pName) {
            return CODEC.byName(pName, PHANTOM_OAK);
        }
    }
}