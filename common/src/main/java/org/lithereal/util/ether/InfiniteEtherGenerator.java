package org.lithereal.util.ether;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InfiniteEtherGenerator implements IEnergyUser {
    public int storage = 0;
    public int capacity = 1000;
    public int transferRate;
    public int storeCount = 0;

    public InfiniteEtherGenerator(int transferRate) {
        this.transferRate = transferRate;
    }

    @Override
    public <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
        if (this.storeCount % 10 == 0) {
            this.storage += restrictAbsorbed(this.transferRate);
            this.storeCount = 0;
        }
        this.storeCount++;
    }

    @Override
    public boolean canProvideEnergy(BlockEntity pEntity) {
        return this.transferRate + this.storage >= ((IEnergyUserProvider)pEntity).requiredEnergy();
    }

    @Override
    public boolean couldProvideEnergy(BlockEntity pEntity) {
        return true;
    }

    @Override
    public void absorbEnergy(int transferred) {
        this.storage += transferred;
    }

    @Override
    public void removeEnergy(int transferred) {
        this.storage -= transferred;
    }

    @Override
    public int restrictTransferred(int transferred) {
        return transferred;
    }

    @Override
    public int restrictAbsorbed(int absorbed) {
        if (capacity < storage + absorbed) absorbed = capacity - storage;
        return absorbed;
    }

    @Override
    public int getTransferRate() {
        return transferRate;
    }

    @Override
    public int getAbsorbRate() {
        return -1;
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("storage", storage);
        tag.putInt("capacity", capacity);
        tag.putInt("transfer_rate", transferRate);
    }

    @Override
    public void load(CompoundTag energyUser, HolderLookup.Provider provider) {
        storage = energyUser.getInt("storage");
        capacity = energyUser.getInt("capacity");
        transferRate = energyUser.getInt("transfer_rate");
    }
}
