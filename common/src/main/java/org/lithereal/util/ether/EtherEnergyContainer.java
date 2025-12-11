package org.lithereal.util.ether;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EtherEnergyContainer implements IEnergyUser {
    public int energy;
    public int maxEnergy;
    public int transferRate;

    public EtherEnergyContainer(int energy, int maxEnergy, int transferRate) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.transferRate = transferRate;
    }

    @Override
    public boolean canProvideEnergy(BlockEntity pEntity) {
        int requiredTransfer = ((IEnergyUserProvider)pEntity).requiredEnergy();
        return this.energy >= requiredTransfer;
    }

    @Override
    public void absorbEnergy(int transferred) {
        this.energy += transferred;
    }

    @Override
    public void removeEnergy(int transferred) {
        this.energy -= transferred;
    }

    @Override
    public int restrictTransferred(int transferred) {
        return Math.min(this.energy, transferred);
    }

    @Override
    public int restrictAbsorbed(int absorbed) {
        if (maxEnergy < energy + absorbed) absorbed = maxEnergy - energy;
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
        tag.putInt("transfer_rate", this.transferRate);
        tag.putInt("energy", this.energy);
        tag.putInt("max_energy", this.maxEnergy);
    }

    @Override
    public void load(CompoundTag energyUser, HolderLookup.Provider provider) {
        this.transferRate = energyUser.getInt("transfer_rate");
        this.energy = energyUser.getInt("energy");
        this.maxEnergy = energyUser.getInt("max_energy");
    }
}
